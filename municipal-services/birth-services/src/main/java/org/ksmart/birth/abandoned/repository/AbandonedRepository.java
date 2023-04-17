package org.ksmart.birth.abandoned.repository;

import lombok.extern.slf4j.Slf4j;
import org.ksmart.birth.abandoned.enrichment.AbandonedEnrichment;
import org.ksmart.birth.abandoned.repository.querybuilder.AbandonedQueryBuilder;
import org.ksmart.birth.abandoned.repository.rowmapper.AbandonedApplicationRowMapper;
import org.ksmart.birth.birthregistry.model.RegisterBirthDetail;
import org.ksmart.birth.birthregistry.model.RegisterBirthDetailsRequest;
import org.ksmart.birth.birthregistry.repository.rowmapperfornewapplication.RegisterRowMapperForApp;
import org.ksmart.birth.birthregistry.service.MdmsDataService;
import org.ksmart.birth.common.producer.BndProducer;
import org.ksmart.birth.common.repository.builder.CommonQueryBuilder;
import org.ksmart.birth.config.BirthConfiguration;

import org.ksmart.birth.newbirth.service.MdmsForNewBirthService;
import org.ksmart.birth.utils.BirthConstants;
import org.ksmart.birth.utils.MdmsUtil;
import org.ksmart.birth.web.model.SearchCriteria;
import org.ksmart.birth.web.model.abandoned.AbandonedApplication;
import org.ksmart.birth.web.model.abandoned.AbandonedRequest;
import org.ksmart.birth.web.model.newbirth.NewBirthDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class AbandonedRepository {
    private final BndProducer producer;
    private final AbandonedEnrichment enrichment;
    private final BirthConfiguration birthDeathConfiguration;
    private final JdbcTemplate jdbcTemplate;
    private final CommonQueryBuilder queryBuilder;
    private final AbandonedApplicationRowMapper rowMapper;
    private final MdmsForNewBirthService mdmsBirthService;
    private final  MdmsDataService mdmsDataService;
    private final  MdmsUtil mdmsUtil;
    private final RegisterRowMapperForApp registerRowMapperForApp;

    @Autowired
    AbandonedRepository(JdbcTemplate jdbcTemplate, AbandonedEnrichment enrichment, BirthConfiguration birthDeathConfiguration,
                        BndProducer producer, CommonQueryBuilder queryBuilder,  AbandonedApplicationRowMapper rowMapper,
                        MdmsDataService mdmsDataService, MdmsUtil mdmsUtil, MdmsForNewBirthService mdmsBirthService,RegisterRowMapperForApp registerRowMapperForApp) {
        this.jdbcTemplate = jdbcTemplate;
        this.enrichment = enrichment;
        this.birthDeathConfiguration = birthDeathConfiguration;
        this.producer = producer;
        this.queryBuilder = queryBuilder;
        this.rowMapper = rowMapper;
        this.mdmsDataService = mdmsDataService;
        this.mdmsUtil = mdmsUtil;
        this.mdmsBirthService = mdmsBirthService;
        this.registerRowMapperForApp = registerRowMapperForApp;
    }

    public List<AbandonedApplication> saveBirthDetails(AbandonedRequest request) {
        enrichment.enrichCreate(request);
        producer.push(birthDeathConfiguration.getSaveAbandonedBirthTopic(), request);
        return request.getBirthDetails();
    }

    public List<AbandonedApplication> updateBirthDetails(AbandonedRequest request) {
        enrichment.enrichUpdate(request);
        producer.push(birthDeathConfiguration.getUpdateAbandonedBirthTopic(), request);
        return request.getBirthDetails();
    }
    public RegisterBirthDetailsRequest searchBirthDetailsForRegister(NewBirthDetailRequest requestApplication) {
        List<Object> preparedStmtValues = new ArrayList<>();
        SearchCriteria criteria = new SearchCriteria();
        List<RegisterBirthDetail> result = null;
        if (requestApplication.getNewBirthDetails().size() > 0) {
            criteria.getApplicationNumber().add(requestApplication.getNewBirthDetails().get(0).getApplicationNo());
            criteria.setTenantId(requestApplication.getNewBirthDetails().get(0).getTenantId());
            String query = queryBuilder.getApplicationSearchQueryForRegistry(criteria, preparedStmtValues);
            result = jdbcTemplate.query(query, preparedStmtValues.toArray(), registerRowMapperForApp);

        }
        return RegisterBirthDetailsRequest.builder()
                .requestInfo(requestApplication.getRequestInfo())
                .registerBirthDetails(result).build();
    }


    public List<AbandonedApplication> searchBirthDetails(AbandonedRequest request, SearchCriteria criteria) {
        String uuid = null;
        List<Object> preparedStmtValues = new ArrayList<>();
        Object mdmsDataComm = mdmsUtil.mdmsCall(request.getRequestInfo());
        if(request.getRequestInfo().getUserInfo() != null){
            uuid = request.getRequestInfo().getUserInfo().getUuid();
        } else{
            criteria.setApplicationType(BirthConstants.FUN_MODULE_NEW);
        }
        String query = queryBuilder.getBirthApplicationSearchQuery(criteria, uuid, preparedStmtValues, Boolean.FALSE);
        List<AbandonedApplication> result = jdbcTemplate.query(query, preparedStmtValues.toArray(), rowMapper);
        result.forEach(birth -> {
            Object mdmsData = mdmsUtil.mdmsCallForLocation(request.getRequestInfo(), birth.getTenantId());
            if (birth.getPlaceofBirthId() != null) {
             //   mdmsBirthService.setLocationDetails(birth, mdmsData);
               // mdmsBirthService.setInstitutionDetails(birth, mdmsDataComm);
            }
//            if (birth.getParentAddress().getCountryIdPermanent() != null && birth.getParentAddress().getStateIdPermanent() != null) {
//                if (birth.getParentAddress().getCountryIdPermanent().contains(BirthConstants.COUNTRY_CODE)) {
//                    if (birth.getParentAddress().getStateIdPermanent().contains(BirthConstants.STATE_CODE_SMALL)) {
//                        mdmsBirthService.setTenantDetails(birth, mdmsDataComm);
//                        birth.getParentAddress().setPermtaddressCountry(birth.getParentAddress().getCountryIdPermanent());
//
//                        birth.getParentAddress().setPermtaddressStateName(birth.getParentAddress().getStateIdPermanent());
//
//                        birth.getParentAddress().setPermntInKeralaAdrDistrict(birth.getParentAddress().getDistrictIdPermanent());
//
//                        birth.getParentAddress().setPermntInKeralaAdrLocalityNameEn(birth.getParentAddress().getLocalityEnPermanent());
//                        birth.getParentAddress().setPermntInKeralaAdrLocalityNameMl(birth.getParentAddress().getLocalityMlPermanent());
//
//                        birth.getParentAddress().setPermntInKeralaAdrStreetNameEn(birth.getParentAddress().getStreetNameEnPermanent());
//                        birth.getParentAddress().setPermntInKeralaAdrStreetNameMl(birth.getParentAddress().getStreetNameMlPermanent());
//
//                        birth.getParentAddress().setPermntInKeralaAdrHouseNameEn(birth.getParentAddress().getHouseNameNoEnPermanent());
//                        birth.getParentAddress().setPermntInKeralaAdrHouseNameMl(birth.getParentAddress().getHouseNameNoMlPermanent());
//
//                        birth.getParentAddress().setPermntInKeralaAdrPostOffice(birth.getParentAddress().getPoNoPermanent());
//
//                    } else {
//                        birth.getParentAddress().setPermtaddressCountry(birth.getParentAddress().getCountryIdPermanent());
//
//                        birth.getParentAddress().setPermtaddressStateName(birth.getParentAddress().getStateIdPermanent());
//                        birth.getParentAddress().setPermntOutsideKeralaDistrict(birth.getParentAddress().getDistrictIdPermanent());
//
//                        birth.getParentAddress().setPermntOutsideKeralaVillage(birth.getParentAddress().getVillageNamePermanent());
//
//                        birth.getParentAddress().setPermntOutsideKeralaLocalityNameEn(birth.getParentAddress().getLocalityEnPermanent());
//                        birth.getParentAddress().setPermntOutsideKeralaLocalityNameMl(birth.getParentAddress().getLocalityMlPermanent());
//
//                        birth.getParentAddress().setPermntOutsideKeralaStreetNameEn(birth.getParentAddress().getStreetNameEnPermanent());
//                        birth.getParentAddress().setPermntOutsideKeralaStreetNameMl(birth.getParentAddress().getStreetNameMlPermanent());
//
//                        birth.getParentAddress().setPermntOutsideKeralaHouseNameEn(birth.getParentAddress().getHouseNameNoEnPermanent());
//                        birth.getParentAddress().setPermntOutsideKeralaHouseNameMl(birth.getParentAddress().getHouseNameNoMlPermanent());
//
//                    }
//                } else {
//                    birth.getParentAddress().setPermntOutsideIndiaCountry(birth.getParentAddress().getCountryIdPermanent());
//                    birth.getParentAddress().setPermntOutsideKeralaVillage(birth.getParentAddress().getVillageNamePermanent());
//                }
//            }
//            if (birth.getParentAddress().getCountryIdPresent() != null && birth.getParentAddress().getStateIdPresent() != null) {
//                if (birth.getParentAddress().getCountryIdPresent().contains(BirthConstants.COUNTRY_CODE)) {
//                    if (birth.getParentAddress().getStateIdPresent().contains(BirthConstants.STATE_CODE_SMALL)) {
//
//                        birth.getParentAddress().setPresentaddressCountry(birth.getParentAddress().getCountryIdPresent());
//
//                        birth.getParentAddress().setPresentaddressStateName(birth.getParentAddress().getStateIdPresent());
//
//                        birth.getParentAddress().setPresentInsideKeralaDistrict(birth.getParentAddress().getDistrictIdPresent());
//
//                        birth.getParentAddress().setPresentInsideKeralaLBName(birth.getParentAddress().getPermntInKeralaAdrLBName());
//
//                        //birth.getParentAddress().setPresentInsideKeralaVillage(birth.getParentAddress().getVillageNamePresent());
//
//                        birth.getParentAddress().setPresentInsideKeralaLocalityNameEn(birth.getParentAddress().getLocalityEnPresent());
//                        birth.getParentAddress().setPresentInsideKeralaLocalityNameMl(birth.getParentAddress().getLocalityMlPresent());
//
//                        birth.getParentAddress().setPresentInsideKeralaStreetNameEn(birth.getParentAddress().getStreetNameEnPermanent());
//                        birth.getParentAddress().setPresentInsideKeralaStreetNameMl(birth.getParentAddress().getStreetNameMlPermanent());
//
//                        birth.getParentAddress().setPresentInsideKeralaHouseNameEn(birth.getParentAddress().getHouseNameNoEnPresent());
//                        birth.getParentAddress().setPresentInsideKeralaHouseNameMl(birth.getParentAddress().getHouseNameNoMlPresent());
//
//                        birth.getParentAddress().setPresentInsideKeralaPincode(birth.getParentAddress().getPinNoPresent());
//
//                        //birth.getParentAddress().setPresentOutsideKeralaCityVilgeEn(birth.getParentAddress().getTownOrVillagePresent());
//
//                        birth.getParentAddress().setPresentInsideKeralaPostOffice(birth.getParentAddress().getPresentInsideKeralaPostOffice());
//
//                    } else {
//                        birth.getParentAddress().setPresentaddressCountry(birth.getParentAddress().getCountryIdPresent());
//
//                        birth.getParentAddress().setPresentaddressStateName(birth.getParentAddress().getStateIdPresent());
//
//                        birth.getParentAddress().setPresentOutsideKeralaDistrict(birth.getParentAddress().getDistrictIdPresent());
//
//                        birth.getParentAddress().setPresentOutsideKeralaVillageName(birth.getParentAddress().getVillageNamePresent());
//
//                        birth.getParentAddress().setPresentOutsideKeralaPincode(birth.getParentAddress().getPinNoPresent());
//
//                        birth.getParentAddress().setPresentOutsideKeralaLocalityNameEn(birth.getParentAddress().getLocalityEnPresent());
//                        birth.getParentAddress().setPresentOutsideKeralaLocalityNameMl(birth.getParentAddress().getLocalityMlPresent());
//
//                        birth.getParentAddress().setPresentOutsideKeralaStreetNameEn(birth.getParentAddress().getStreetNameEnPresent());
//                        birth.getParentAddress().setPresentOutsideKeralaStreetNameMl(birth.getParentAddress().getStreetNameMlPresent());
//
//                        birth.getParentAddress().setPresentOutsideKeralaHouseNameEn(birth.getParentAddress().getHouseNameNoEnPresent());
//                        birth.getParentAddress().setPresentOutsideKeralaHouseNameMl(birth.getParentAddress().getHouseNameNoMlPresent());
//
//                        birth.getParentAddress().setPresentOutsideKeralaCityVilgeEn(birth.getParentAddress().getTownOrVillagePresent());
//
//                    }
//                } else {
//                    birth.getParentAddress().setPresentOutSideCountry(birth.getParentAddress().getCountryIdPresent());
//                    birth.getParentAddress().setPresentOutSideIndiaadrsVillage(birth.getParentAddress().getVillageNamePresent());
//                    birth.getParentAddress().setPresentOutSideIndiaadrsCityTown(birth.getParentAddress().getTownOrVillagePresent());
//                }
//            }
        });

        return result;
    }
}

