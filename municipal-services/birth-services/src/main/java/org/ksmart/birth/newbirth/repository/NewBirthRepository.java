package org.ksmart.birth.newbirth.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.ksmart.birth.birthregistry.model.RegisterBirthDetail;
import org.ksmart.birth.birthregistry.model.RegisterBirthDetailsRequest;
import org.ksmart.birth.birthregistry.repository.rowmapperfornewapplication.RegisterRowMapperForApp;
import org.ksmart.birth.common.producer.BndProducer;
import org.ksmart.birth.common.repository.builder.CommonQueryBuilder;
import org.ksmart.birth.config.BirthConfiguration;
import org.ksmart.birth.newbirth.enrichment.NewBirthEnrichment;
import org.ksmart.birth.newbirth.enrichment.NewBirthResponseEnrichment;
import org.ksmart.birth.newbirth.repository.rowmapper.BirthApplicationRowMapper;
import org.ksmart.birth.utils.BirthConstants;
import org.ksmart.birth.utils.enums.ErrorCodes;
import org.ksmart.birth.web.model.SearchCriteria;
import org.ksmart.birth.web.model.newbirth.NewBirthApplication;
import org.ksmart.birth.web.model.newbirth.NewBirthDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class NewBirthRepository {
    @Autowired
    CommonQueryBuilder commonQueryBuilder;
    private final BndProducer producer;
    private final NewBirthEnrichment ksmartBirthEnrichment;
    private final NewBirthResponseEnrichment responseEnrichment;
    private final BirthConfiguration birthDeathConfiguration;
    private final JdbcTemplate jdbcTemplate;
    private final BirthApplicationRowMapper ksmartBirthApplicationRowMapper;
    private final RegisterRowMapperForApp registerRowMapperForApp;

    @Autowired
    NewBirthRepository(JdbcTemplate jdbcTemplate, NewBirthEnrichment ksmartBirthEnrichment, BirthConfiguration birthDeathConfiguration,
                       BndProducer producer,NewBirthResponseEnrichment responseEnrichment,  BirthApplicationRowMapper ksmartBirthApplicationRowMapper,RegisterRowMapperForApp registerRowMapperForApp) {
        this.jdbcTemplate = jdbcTemplate;
        this.ksmartBirthEnrichment = ksmartBirthEnrichment;
        this.birthDeathConfiguration = birthDeathConfiguration;
        this.producer = producer;
        this.ksmartBirthApplicationRowMapper = ksmartBirthApplicationRowMapper;
        this.responseEnrichment = responseEnrichment;
        this.registerRowMapperForApp = registerRowMapperForApp;
    }

    public List<NewBirthApplication> saveKsmartBirthDetails(NewBirthDetailRequest request, Object mdmsData) {
        ksmartBirthEnrichment.enrichCreate(request, mdmsData);
        producer.push(birthDeathConfiguration.getSaveKsmartBirthApplicationTopic(), request);
        return request.getNewBirthDetails();
    }

    public List<NewBirthApplication> updateKsmartBirthDetails(NewBirthDetailRequest request, Object mdmsData) {
        ksmartBirthEnrichment.enrichUpdate(request, mdmsData);
        producer.push(birthDeathConfiguration.getUpdateKsmartBirthApplicationTopic(), request);
        return request.getNewBirthDetails();
    }
    public RegisterBirthDetailsRequest searchBirthDetailsForRegister(NewBirthDetailRequest requestApplication) {
        List<Object> preparedStmtValues = new ArrayList<>();
        SearchCriteria criteria = new SearchCriteria();
        List<RegisterBirthDetail> result = null;
        if (requestApplication.getNewBirthDetails().size() > 0) {
            criteria.setId(requestApplication.getNewBirthDetails().get(0).getId());
            criteria.setTenantId(requestApplication.getNewBirthDetails().get(0).getTenantId());
            String query = commonQueryBuilder.getApplicationSearchQueryForRegistry(criteria, preparedStmtValues);
            result = jdbcTemplate.query(query, preparedStmtValues.toArray(), registerRowMapperForApp);
        }
        return RegisterBirthDetailsRequest.builder()
                .requestInfo(requestApplication.getRequestInfo())
                .registerBirthDetails(result).build();
    }

    public List<NewBirthApplication> searchBirthDetails(NewBirthDetailRequest request, SearchCriteria criteria) {
        String uuid = null;
        List<Object> preparedStmtValues = new ArrayList<>();

        if(request.getRequestInfo().getUserInfo() != null){
            uuid = request.getRequestInfo().getUserInfo().getUuid();
        } else{
            criteria.setApplicationType(BirthConstants.FUN_MODULE_NEW);
        }
        String query = commonQueryBuilder.getBirthApplicationSearchQuery(criteria, uuid, preparedStmtValues, Boolean.FALSE);
        if(preparedStmtValues.size() == 0){
            throw new CustomException(ErrorCodes.NOT_FOUND.getCode(), "No result found.");
        } else{
            List<NewBirthApplication> result = jdbcTemplate.query(query, preparedStmtValues.toArray(), ksmartBirthApplicationRowMapper);
            RequestInfo requestInfo = request.getRequestInfo();
            responseEnrichment.setNewBirthRequestData(requestInfo, result);
            return result;
        }




    }
    
    public List<NewBirthApplication> searchBirth ( RequestInfo requestInfo, SearchCriteria criteria) {
        String uuid = null;
        List<Object> preparedStmtValues = new ArrayList<>();

        if(requestInfo.getUserInfo() != null){
            uuid = requestInfo.getUserInfo().getUuid();
        } else{
            criteria.setApplicationType(BirthConstants.FUN_MODULE_NEW);
        }
        String query = commonQueryBuilder.getBirthApplicationSearchQuery(criteria, uuid, preparedStmtValues, Boolean.FALSE);
        if(preparedStmtValues.size() == 0){
            throw new CustomException(ErrorCodes.NOT_FOUND.getCode(), "No result found.");
        } else{
            List<NewBirthApplication> result = jdbcTemplate.query(query, preparedStmtValues.toArray(), ksmartBirthApplicationRowMapper);
           
//            responseEnrichment.setNewBirthRequestData(requestInfo, result);
            return result;
        }




    }
}

