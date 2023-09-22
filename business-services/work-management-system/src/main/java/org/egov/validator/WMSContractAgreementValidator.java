package org.egov.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.egov.repository.WMSContractAgreementRepository;
import org.egov.repository.WMSContractorRepository;
import org.egov.repository.WMSSORRepository;
import org.egov.repository.WMSWorkRepository;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.SORApplicationSearchCriteria;
import org.egov.web.models.ScheduleOfRateApplication;
import org.egov.web.models.WMSContractAgreementApplication;
import org.egov.web.models.WMSContractAgreementApplicationSearchCriteria;
import org.egov.web.models.WMSContractAgreementRequest;
import org.egov.web.models.WMSContractorApplication;
import org.egov.web.models.WMSContractorApplicationSearchCriteria;
import org.egov.web.models.WMSContractorRequest;
import org.egov.web.models.WMSSORRequest;
import org.egov.web.models.WMSWorkApplication;
import org.egov.web.models.WMSWorkApplicationSearchCriteria;
import org.egov.web.models.WMSWorkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Component
public class WMSContractAgreementValidator {
	
	 @Autowired
	    private WMSContractAgreementRepository repository;

	    public void validateContractAgreementApplication(WMSContractAgreementRequest wmsContractAgreementRequest) {
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getTenantId()))
	                throw new CustomException("EG_WMS_APP_ERR", "tenantId is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getAgreementNo()))
	                throw new CustomException("EG_WMS_APP_ERR", "AgreementNo is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getAgreementDate()))
	                throw new CustomException("EG_WMS_APP_ERR", "AgreementDate is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getDepartmentName()))
	                throw new CustomException("EG_WMS_APP_ERR", "DepartmentName is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getLoaNo()))
	                throw new CustomException("EG_WMS_APP_ERR", "LoaNo is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getTenderNo()))
	                throw new CustomException("EG_WMS_APP_ERR", "TenderNo is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getTenderDate()))
	                throw new CustomException("EG_WMS_APP_ERR", "TenderDate is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getAgreementType()))
	                throw new CustomException("EG_WMS_APP_ERR", "AgreementType is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getContractPeriod()))
	                throw new CustomException("EG_WMS_APP_ERR", "ContractPeriod is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getAgreementAmount()))
	                throw new CustomException("EG_WMS_APP_ERR", "AgreementAmount is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getPaymentType()))
	                throw new CustomException("EG_WMS_APP_ERR", "PaymentType is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getDepartmentName()))
	                throw new CustomException("EG_WMS_APP_ERR", "DepartmentName is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getDesignation()))
	                throw new CustomException("EG_WMS_APP_ERR", "Designation is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getEmployeeName()))
	                throw new CustomException("EG_WMS_APP_ERR", "EmployeeName is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getVendorType()))
	                throw new CustomException("EG_WMS_APP_ERR", "VendorType is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getVendorName()))
	                throw new CustomException("EG_WMS_APP_ERR", "VendorName is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getRepresentedBy()))
	                throw new CustomException("EG_WMS_APP_ERR", "RepresentedBy is mandatory for creating Contract Agreement applications");
	        });
	    	
	    	wmsContractAgreementRequest.getWmsContractAgreementApplications().forEach(application -> {
	            if(ObjectUtils.isEmpty(application.getPrimaryParty()))
	                throw new CustomException("EG_WMS_APP_ERR", "PrimaryParty is mandatory for creating Contract Agreement applications");
	        });
	    }

		public List<WMSContractAgreementApplication> validateApplicationUpdateRequest(
				WMSContractAgreementRequest contractAgreementRequest) {
			List<String> ids = contractAgreementRequest.getWmsContractAgreementApplications().stream().map(WMSContractAgreementApplication::getAgreementNo).collect(Collectors.toList());
	        List<WMSContractAgreementApplication> contractAgreementApplications = repository.getApplications(WMSContractAgreementApplicationSearchCriteria.builder().agreementNo(ids).build());
	        if(contractAgreementApplications.size() != ids.size())
	            throw new CustomException("APPLICATION_DOES_NOT_EXIST", "One of the Contract Agreement ids does not exist.");
	        return contractAgreementApplications;
		}

		/*
		 * public List<WMSWorkApplication>
		 * validateApplicationUpdateRequest(WMSWorkRequest wmsWorkRequest) {
		 * List<Integer> ids =
		 * wmsWorkRequest.getWmsWorkApplications().stream().map(WMSWorkApplication::
		 * getWorkId).collect(Collectors.toList()); List<WMSWorkApplication>
		 * wmsWorkApplications =
		 * repository.getApplications(WMSWorkApplicationSearchCriteria.builder().workId(
		 * ids).build()); if(wmsWorkApplications.size() != ids.size()) throw new
		 * CustomException("APPLICATION_DOES_NOT_EXIST",
		 * "One of the Work ids does not exist."); return wmsWorkApplications; }
		 */

}
