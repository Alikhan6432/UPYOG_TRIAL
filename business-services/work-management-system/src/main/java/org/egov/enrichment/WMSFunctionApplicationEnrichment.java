package org.egov.enrichment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.egov.repository.WMSWorkRepository;
import org.egov.util.IdgenUtil;
import org.egov.web.models.AuditDetails;
import org.egov.web.models.ScheduleOfRateApplication;
import org.egov.web.models.WMSBankDetailsApplication;
import org.egov.web.models.WMSBankDetailsRequest;
import org.egov.web.models.WMSContractAgreementApplication;
import org.egov.web.models.WMSContractAgreementRequest;
import org.egov.web.models.WMSContractorApplication;
import org.egov.web.models.WMSContractorRequest;
import org.egov.web.models.WMSFunctionApplication;
import org.egov.web.models.WMSFunctionRequest;
import org.egov.web.models.WMSSORRequest;
import org.egov.web.models.WMSWorkApplication;
import org.egov.web.models.WMSWorkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WMSFunctionApplicationEnrichment {
	
	/*
	 * @Value("${egov.idgen.work.idname}") private String idGenName;
	 * 
	 * @Value("${egov.idgen.work.idformat}") private String idGenFormat;
	 */

	@Autowired
	private IdgenUtil idgenUtil;
//	    @Autowired
//	    private UserService userService;
//	    @Autowired
//	    private UserUtil userUtils;

	String pattern = "MM-dd-yyyy hh:mm:ss";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	String date = simpleDateFormat.format(new Date());

	public void enrichFunctionApplication(WMSFunctionRequest wmsFunctionRequest) { 
		//List<String> sorIdList = idgenUtil.getIdList(wmsContractorRequest.getRequestInfo(), wmsContractorRequest.getWmsWorkApplications().get(0).getTenantId(), idGenName, idGenFormat, wmsContractorRequest.getWmsWorkApplications().size());
        //Integer index = 0;
		for (WMSFunctionApplication application : wmsFunctionRequest.getWmsFunctionApplications()) {
			// Enrich audit details
//	            AuditDetails auditDetails = AuditDetails.builder().createdBy(birthRegistrationRequest.getRequestInfo().getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).lastModifiedBy(birthRegistrationRequest.getRequestInfo().getUserInfo().getUuid()).lastModifiedTime(System.currentTimeMillis()).build();
//	            application.setAuditDetails(auditDetails);
			
			AuditDetails auditDetails = AuditDetails.builder().createdBy(wmsFunctionRequest.getRequestInfo().getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).lastModifiedBy(wmsFunctionRequest.getRequestInfo().getUserInfo().getUuid()).lastModifiedTime(System.currentTimeMillis()).build();
            application.setAuditDetails(auditDetails);

			// Enrich UUID
            Long randomNumber=(long) Math.floor(Math.random() * (9999 - 1000 + 1) + 1000);
			application.setFunctionId(Long.toString(randomNumber));
			//application.setId(UUID.randomUUID().toString());
			//application.setWorkName(sorIdList.get(index++));
			//application.setStartDate(date);
			//application.setEndDate(date);

			// Set application number from IdGen
			// application.setApplicationNumber(birthRegistrationIdList.get(index++));

			// Enrich registration Id
			// application.getAddress().setRegistrationId(application.getId());

			// Enrich address UUID
			// application.getAddress().setId(UUID.randomUUID().toString());
		}
	}

	public void enrichFunctionApplicationUpdate(WMSFunctionRequest wmsFunctionRequest, List<WMSFunctionApplication> existingApplication) {
		// Enrich lastModifiedTime and lastModifiedBy in case of update
		for (WMSFunctionApplication application : wmsFunctionRequest.getWmsFunctionApplications()) {
			existingApplication.get(0).setFunctionName(application.getFunctionName());
			existingApplication.get(0).setFunctionCode(application.getFunctionCode());
			existingApplication.get(0).setFunctionLevel(application.getFunctionLevel());
			existingApplication.get(0).setStatus(application.getStatus());
			
			
			
//			application.setEndDate(date);
			// application.getAuditDetails().setLastModifiedBy(birthRegistrationRequest.getRequestInfo().getUserInfo().getUuid());
		}
	}

}
