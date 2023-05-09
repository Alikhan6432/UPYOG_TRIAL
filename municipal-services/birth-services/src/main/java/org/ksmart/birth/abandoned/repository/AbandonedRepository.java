package org.ksmart.birth.abandoned.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.ksmart.birth.abandoned.enrichment.AbandonedEnrichment;
import org.ksmart.birth.abandoned.repository.querybuilder.AbandonedQueryBuilder;
import org.ksmart.birth.abandoned.repository.rowmapper.AbandonedApplicationRowMapper;
import org.ksmart.birth.abandoned.service.MdmsForAbandonedService;
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
import org.ksmart.birth.utils.enums.ErrorCodes;
import org.ksmart.birth.web.model.SearchCriteria;
import org.ksmart.birth.web.model.abandoned.AbandonedApplication;
import org.ksmart.birth.web.model.abandoned.AbandonedRequest;
import org.ksmart.birth.web.model.newbirth.NewBirthApplication;
import org.ksmart.birth.web.model.newbirth.NewBirthDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class AbandonedRepository {

    @Autowired
    CommonQueryBuilder commonQueryBuilder;
    private final BndProducer producer;
    private final AbandonedEnrichment enrichment;
    private final BirthConfiguration birthDeathConfiguration;
    private final JdbcTemplate jdbcTemplate;
    private final CommonQueryBuilder queryBuilder;
    private final AbandonedApplicationRowMapper rowMapper;
    private final MdmsForAbandonedService mdmsBirthService;
    private final  MdmsDataService mdmsDataService;
    private final  MdmsUtil mdmsUtil;
    private final RegisterRowMapperForApp registerRowMapperForApp;

    @Autowired
    AbandonedRepository(JdbcTemplate jdbcTemplate, AbandonedEnrichment enrichment, BirthConfiguration birthDeathConfiguration,
                        BndProducer producer, CommonQueryBuilder queryBuilder,  AbandonedApplicationRowMapper rowMapper,
                        MdmsDataService mdmsDataService, MdmsUtil mdmsUtil, MdmsForAbandonedService mdmsBirthService,RegisterRowMapperForApp registerRowMapperForApp) {
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
    public RegisterBirthDetailsRequest searchBirthDetailsForRegister(AbandonedRequest requestApplication) {
        List<Object> preparedStmtValues = new ArrayList<>();
        SearchCriteria criteria = new SearchCriteria();
        List<RegisterBirthDetail> result = null;
        if (requestApplication.getBirthDetails().size() > 0) {
            criteria.setId(requestApplication.getBirthDetails().get(0).getId());
            criteria.setTenantId(requestApplication.getBirthDetails().get(0).getTenantId());
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
       // Object mdmsDataComm = mdmsUtil.mdmsCall(request.getRequestInfo());
        if(request.getRequestInfo().getUserInfo() != null){
            uuid = request.getRequestInfo().getUserInfo().getUuid();
        }
        criteria.setApplicationType(BirthConstants.FUN_MODULE_ABAN);
        String query = commonQueryBuilder.getBirthApplicationSearchQuery(criteria, uuid, preparedStmtValues, Boolean.FALSE);
        if(preparedStmtValues.size() == 0){
            throw new CustomException(ErrorCodes.NOT_FOUND.getCode(), "No result found.");
        } else{
            List<AbandonedApplication> result = jdbcTemplate.query(query, preparedStmtValues.toArray(), rowMapper);

            if(result.size() == 0){
                throw new CustomException(ErrorCodes.NOT_FOUND.getCode(), "No result found.");
            } else if(result.size() >= 1) {
                result.forEach(birth -> {
                    birth.setIsWorkflow(true);
                    Object mdmsData = mdmsUtil.mdmsCallForLocation(request.getRequestInfo(), birth.getTenantId());
                    if (birth.getPlaceofBirthId() != null) {
                        mdmsBirthService.setLocationDetails(birth, mdmsData);
                    }
                });
            }
            return result;
        }
    }
}

