package org.ksmart.birth.adoption.repository;

import lombok.extern.slf4j.Slf4j;
import org.ksmart.birth.birthregistry.service.MdmsDataService;
import org.ksmart.birth.common.producer.BndProducer;
import org.ksmart.birth.config.BirthConfiguration;
 
import org.ksmart.birth.adoption.enrichment.AdoptionEnrichment;
import org.ksmart.birth.adoption.repository.querybuilder.AdoptionQueryBuilder;
import org.ksmart.birth.adoption.repository.rowmapper.AdoptionApplicationRowMapper;
 
import org.ksmart.birth.utils.BirthConstants;
import org.ksmart.birth.utils.MdmsUtil;
import org.ksmart.birth.web.model.SearchCriteria;
import org.ksmart.birth.web.model.adoption.AdoptionApplication;
import org.ksmart.birth.web.model.adoption.AdoptionDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class AdoptionRepository {
    private final BndProducer producer;
    private final AdoptionEnrichment adoptionEnrichment;
    private final BirthConfiguration birthDeathConfiguration;
    private final JdbcTemplate jdbcTemplate;
 
    private final AdoptionQueryBuilder adoptionQueryBuilder;
    private final AdoptionApplicationRowMapper adoptionApplicationRowMapper;
 
    private final  MdmsDataService mdmsDataService;
    private final  MdmsUtil mdmsUtil;



    @Autowired 
    AdoptionRepository(JdbcTemplate jdbcTemplate, AdoptionEnrichment adoptionEnrichment, BirthConfiguration birthDeathConfiguration,
                       BndProducer producer, AdoptionQueryBuilder adoptionQueryBuilder, AdoptionApplicationRowMapper adoptionApplicationRowMapper, 
                       MdmsDataService mdmsDataService, MdmsUtil mdmsUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.adoptionEnrichment = adoptionEnrichment;
        this.birthDeathConfiguration = birthDeathConfiguration;
        this.producer = producer;
        this.adoptionQueryBuilder = adoptionQueryBuilder;
        this.adoptionApplicationRowMapper = adoptionApplicationRowMapper;
        this.mdmsDataService = mdmsDataService;
        this.mdmsUtil = mdmsUtil;
    }

    public List<AdoptionApplication> saveAdoptionDetails(AdoptionDetailRequest request) {
    	adoptionEnrichment.enrichCreate(request);
        producer.push(birthDeathConfiguration.getSaveBirthAdoptionTopic(), request);
        return request.getAdoptionDetails();
    }

    public List<AdoptionApplication> updateKsmartBirthDetails(AdoptionDetailRequest request) {
    	adoptionEnrichment.enrichUpdate(request);
        producer.push(birthDeathConfiguration.getUpdateBirthAdoptionTopic(), request);
        return request.getAdoptionDetails();
    }


    public List<AdoptionApplication> searchKsmartBirthDetails(AdoptionDetailRequest request, SearchCriteria criteria) {
        List<Object> preparedStmtValues = new ArrayList<>();
        String query = adoptionQueryBuilder.getAdoptionSearchQuery(criteria, preparedStmtValues, Boolean.FALSE);
        List<AdoptionApplication> result = jdbcTemplate.query(query, preparedStmtValues.toArray(), adoptionApplicationRowMapper);
        result.forEach(birth -> {
            if(birth.getPlaceofBirthId()!=null){
                Object mdmsData = mdmsUtil.mdmsCallForLocation(request.getRequestInfo(), birth.getTenantId());
                mdmsDataService.setAdoptionLocationDetails(birth, mdmsData);
            }
            if (birth.getParentAddress().getCountryIdPermanent() != null && birth.getParentAddress().getStateIdPermanent() != null) {
                if (birth.getParentAddress().getCountryIdPermanent().contains(BirthConstants.COUNTRY_CODE)) {
                    if (birth.getParentAddress().getStateIdPermanent().contains(BirthConstants.STATE_CODE_SMALL)) {
                        birth.getParentAddress().setPermtaddressCountry(birth.getParentAddress().getCountryIdPermanent());

                        birth.getParentAddress().setPermtaddressStateName(birth.getParentAddress().getStateIdPermanent());

                        birth.getParentAddress().setPermntInKeralaAdrDistrict(birth.getParentAddress().getDistrictIdPermanent());

                        birth.getParentAddress().setPermntInKeralaAdrVillage(birth.getParentAddress().getVillageNamePermanent());

                        birth.getParentAddress().setPermntInKeralaAdrLocalityNameEn(birth.getParentAddress().getLocalityEnPermanent());
                        birth.getParentAddress().setPermntInKeralaAdrLocalityNameMl(birth.getParentAddress().getLocalityMlPermanent());

                        birth.getParentAddress().setPermntInKeralaAdrStreetNameEn(birth.getParentAddress().getStreetNameEnPermanent());
                        birth.getParentAddress().setPermntInKeralaAdrStreetNameMl(birth.getParentAddress().getStreetNameMlPermanent());

                        birth.getParentAddress().setPermntInKeralaAdrHouseNameEn(birth.getParentAddress().getHouseNameNoEnPermanent());
                        birth.getParentAddress().setPermntInKeralaAdrHouseNameMl(birth.getParentAddress().getHouseNameNoMlPermanent());

                        birth.getParentAddress().setPermntInKeralaAdrPincode(birth.getParentAddress().getPinNoPermanent());

                        birth.getParentAddress().setPermntInKeralaAdrPostOffice(birth.getParentAddress().getPoNoPermanent());

                    }
                    else{
                        birth.getParentAddress().setPermntOutsideKeralaDistrict(birth.getParentAddress().getDistrictIdPermanent());

                        birth.getParentAddress().setPermntOutsideKeralaVillage(birth.getParentAddress().getVillageNamePermanent());

                        birth.getParentAddress().setPermntOutsideKeralaPincode(birth.getParentAddress().getPinNoPermanent());

                        birth.getParentAddress().setPermntOutsideKeralaLocalityNameEn(birth.getParentAddress().getLocalityEnPermanent());
                        birth.getParentAddress().setPermntOutsideKeralaLocalityNameMl(birth.getParentAddress().getLocalityMlPermanent());

                        birth.getParentAddress().setPermntOutsideKeralaStreetNameEn(birth.getParentAddress().getStreetNameEnPermanent());
                        birth.getParentAddress().setPermntOutsideKeralaStreetNameMl(birth.getParentAddress().getStreetNameMlPermanent());

                        birth.getParentAddress().setPermntOutsideKeralaHouseNameEn(birth.getParentAddress().getHouseNameNoEnPermanent());
                        birth.getParentAddress().setPermntOutsideKeralaHouseNameMl(birth.getParentAddress().getHouseNameNoMlPermanent());



                    }
                }
            }
            if(birth.getParentAddress().getCountryIdPresent()!=null && birth.getParentAddress().getStateIdPresent()!=null) {
                if (birth.getParentAddress().getCountryIdPresent().contains(BirthConstants.COUNTRY_CODE)) {
                    if (birth.getParentAddress().getStateIdPresent().contains(BirthConstants.STATE_CODE_SMALL)) {

                        birth.getParentAddress().setPresentaddressCountry(birth.getParentAddress().getCountryIdPresent());

                        birth.getParentAddress().setPresentaddressStateName(birth.getParentAddress().getStateIdPresent());

                        birth.getParentAddress().setPresentaddressCountry(birth.getParentAddress().getDistrictIdPresent());

                        birth.getParentAddress().setPresentInsideKeralaDistrict(birth.getParentAddress().getDistrictIdPresent());

                        birth.getParentAddress().setPresentInsideKeralaVillage(birth.getParentAddress().getVillageNamePresent());

                        birth.getParentAddress().setPresentInsideKeralaLocalityNameEn(birth.getParentAddress().getLocalityEnPresent());
                        birth.getParentAddress().setPresentInsideKeralaLocalityNameMl(birth.getParentAddress().getLocalityMlPresent());

                        birth.getParentAddress().setPresentInsideKeralaStreetNameEn(birth.getParentAddress().getStreetNameEnPermanent());
                        birth.getParentAddress().setPresentInsideKeralaStreetNameMl(birth.getParentAddress().getStreetNameMlPermanent());

                        birth.getParentAddress().setPresentInsideKeralaHouseNameEn(birth.getParentAddress().getHouseNameNoEnPresent());
                        birth.getParentAddress().setPresentInsideKeralaHouseNameMl(birth.getParentAddress().getHouseNameNoMlPresent());

                        birth.getParentAddress().setPresentInsideKeralaPincode(birth.getParentAddress().getPinNoPresent());

                        birth.getParentAddress().setPresentInsideKeralaPostOffice(birth.getParentAddress().getPoNoPresent());

                    }

                    else{
                        birth.getParentAddress().setPresentOutsideKeralaDistrict(birth.getParentAddress().getDistrictIdPresent());

                        birth.getParentAddress().setPresentOutsideKeralaVillageName(birth.getParentAddress().getVillageNamePresent());

                        birth.getParentAddress().setPresentOutsideKeralaPincode(birth.getParentAddress().getPinNoPresent());

                        birth.getParentAddress().setPresentOutsideKeralaLocalityNameEn(birth.getParentAddress().getLocalityEnPresent());
                        birth.getParentAddress().setPresentOutsideKeralaLocalityNameMl(birth.getParentAddress().getLocalityMlPresent());

                        birth.getParentAddress().setPresentOutsideKeralaStreetNameEn(birth.getParentAddress().getStreetNameEnPresent());
                        birth.getParentAddress().setPresentOutsideKeralaStreetNameMl(birth.getParentAddress().getStreetNameMlPresent());

                        birth.getParentAddress().setPresentOutsideKeralaHouseNameEn(birth.getParentAddress().getHouseNameNoEnPresent());
                        birth.getParentAddress().setPresentOutsideKeralaHouseNameMl(birth.getParentAddress().getHouseNameNoMlPresent());

                    }
                }
            }
        });

        return result;
    }
}

