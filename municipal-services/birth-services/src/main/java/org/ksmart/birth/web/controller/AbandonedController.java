package org.ksmart.birth.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.ksmart.birth.abandoned.service.AbandonedService;
import org.ksmart.birth.abandoned.service.RegistryRequestServiceForAbandoned;
import org.ksmart.birth.adoption.service.AdoptionService;
import org.ksmart.birth.adoption.service.RegistryRequestServiceForAdoption;
import org.ksmart.birth.birthregistry.model.BirthCertificate;
import org.ksmart.birth.birthregistry.model.RegisterBirthDetail;
import org.ksmart.birth.birthregistry.model.RegisterBirthDetailsRequest;
import org.ksmart.birth.birthregistry.model.RegisterBirthSearchCriteria;
import org.ksmart.birth.birthregistry.service.RegisterBirthService;
import org.ksmart.birth.utils.ResponseInfoFactory;
import org.ksmart.birth.web.model.SearchCriteria;
import org.ksmart.birth.web.model.abandoned.AbandonedApplication;
import org.ksmart.birth.web.model.abandoned.AbandonedRequest;
import org.ksmart.birth.web.model.abandoned.AbandonedResponse;
import org.ksmart.birth.web.model.abandoned.AbandonedSearchResponse;
import org.ksmart.birth.web.model.adoption.AdoptionApplication;
import org.ksmart.birth.web.model.adoption.AdoptionDetailRequest;
import org.ksmart.birth.web.model.adoption.AdoptionResponse;
import org.ksmart.birth.web.model.adoption.AdoptionSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cr")
public class AbandonedController {
    private final ResponseInfoFactory responseInfoFactory;
    private final AbandonedService abandonedService;
    private final RegisterBirthService registerBirthService;
    private final RegistryRequestServiceForAbandoned registryReq;

    @Autowired
    AbandonedController(AbandonedService abandonedService, ResponseInfoFactory responseInfoFactory,
                        RegisterBirthService registerBirthService, RegistryRequestServiceForAbandoned registryReq) {
        this.abandonedService = abandonedService;
        this.responseInfoFactory = responseInfoFactory;
        this.registerBirthService = registerBirthService;
        this.registryReq = registryReq;
    }

    @PostMapping(value = {"/createabandoned"})
    public ResponseEntity<?> saveAbandonedDetails(@RequestBody AbandonedRequest request) {
        List<AbandonedApplication> details = abandonedService.saveKsmartBirthDetails(request);
        AbandonedResponse response = AbandonedResponse.builder()
                                                      .birthDetails(details)
                                                      .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(),
                                                            true))
                                                      .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"/updateabandoned"})
    public ResponseEntity<?> updateAbandonedDetails(@RequestBody AbandonedRequest request) {
        BirthCertificate birthCertificate = new BirthCertificate();
        List<AbandonedApplication> adoptionApplicationDetails = abandonedService.updateKsmartBirthDetails(request);
        //Download certificate when Approved
        if ((adoptionApplicationDetails.get(0).getApplicationStatus() == "APPROVED" && adoptionApplicationDetails.get(0).getAction() == "APPROVE")) {
            RegisterBirthDetailsRequest registerBirthDetailsRequest = registryReq.createRegistryRequest(request);
            List<RegisterBirthDetail> registerBirthDetails = registerBirthService.saveRegisterBirthDetails(registerBirthDetailsRequest);
            RegisterBirthSearchCriteria criteria = new RegisterBirthSearchCriteria();
            criteria.setTenantId(registerBirthDetails.get(0)
                                                     .getTenantId());
            criteria.setRegistrationNo(registerBirthDetails.get(0)
                                                           .getRegistrationNo());
            birthCertificate = registerBirthService.download(criteria, request.getRequestInfo());
        }
        AbandonedResponse response = AbandonedResponse.builder()
                                                      .birthDetails(adoptionApplicationDetails)
                                                      .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(),
                                                            true))
                                                      .birthCertificate(birthCertificate)
                                                      .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"/searchabandoned"})
    public ResponseEntity<AbandonedSearchResponse> searchAbandonedDetails(@RequestBody AbandonedRequest request, @Valid @ModelAttribute SearchCriteria criteria) {
        List<AbandonedApplication> adoptionDetails = abandonedService.searchKsmartBirthDetails(request, criteria);
        AbandonedSearchResponse response = AbandonedSearchResponse.builder()
                                                                  .responseInfo(responseInfoFactory.createResponseInfoFromRequestInfo(request.getRequestInfo(), Boolean.TRUE))
                                                                  .newBirthDetails(adoptionDetails)
                                                                  .build();
        return ResponseEntity.ok(response);
    }
}
