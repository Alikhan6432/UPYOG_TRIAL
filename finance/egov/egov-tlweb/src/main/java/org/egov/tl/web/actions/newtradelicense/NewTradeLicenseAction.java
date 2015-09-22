/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *     accountability and the service delivery of the government  organizations.
 *
 *      Copyright (C) <2015>  eGovernments Foundation
 *
 *      The updated version of eGov suite of products as by eGovernments Foundation
 *      is available at http://www.egovernments.org
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program. If not, see http://www.gnu.org/licenses/ or
 *      http://www.gnu.org/licenses/gpl.html .
 *
 *      In addition to the terms of the GPL license to be adhered to in using this
 *      program, the following additional terms are to be complied with:
 *
 *  	1) All versions of this program, verbatim or modified must carry this
 *  	   Legal Notice.
 *
 *  	2) Any misrepresentation of the origin of the material is prohibited. It
 *  	   is required that all modified versions of this material be marked in
 *  	   reasonable ways as different from the original version.
 *
 *  	3) This license does not grant any rights to any user of the program
 *  	   with regards to rights under trademark law for use of the trade names
 *  	   or trademarks of eGovernments Foundation.
 *
 *    In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 ******************************************************************************/
package org.egov.tl.web.actions.newtradelicense;


import static org.egov.tl.utils.Constants.TRANSACTIONTYPE_CREATE_LICENSE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.service.BoundaryService;
import org.egov.infra.persistence.entity.PermanentAddress;
import org.egov.infra.web.struts.annotation.ValidationErrorPage;
import org.egov.infra.workflow.service.WorkflowService;
import org.egov.tl.domain.entity.License;
import org.egov.tl.domain.entity.LicenseDocumentType;
import org.egov.tl.domain.entity.Licensee;
import org.egov.tl.domain.entity.MotorDetails;
import org.egov.tl.domain.entity.TradeLicense;
import org.egov.tl.domain.entity.WorkflowBean;
import org.egov.tl.domain.service.BaseLicenseService;
import org.egov.tl.domain.service.TradeService;
import org.egov.tl.utils.Constants;
import org.egov.tl.web.actions.BaseLicenseAction;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@ParentPackage("egov")
@Results({ @Result(name = NewTradeLicenseAction.NEW, location = "newTradeLicense-new.jsp"),
	       @Result(name = Constants.ACKNOWLEDGEMENT, location = "newTradeLicense-"+Constants.ACKNOWLEDGEMENT+".jsp")})
public class NewTradeLicenseAction extends BaseLicenseAction {

    private static final long serialVersionUID = 1L;
    protected TradeLicense tradeLicense = new TradeLicense();
    WorkflowService<TradeLicense> tradeLicenseWorkflowService;
    private TradeService ts;
    @Autowired
    private BoundaryService boundaryService;
    private List<LicenseDocumentType> documentTypes = new ArrayList<>();

    public NewTradeLicenseAction() {
        super();
        tradeLicense.setLicensee(new Licensee());
        tradeLicense.setAddress(new PermanentAddress());
        tradeLicense.getLicensee().setAddress(new PermanentAddress());
        
    }

    /* to log errors and debugging information */
    private final Logger LOGGER = Logger.getLogger(getClass());

    @Override
    @SkipValidation
    @Action(value = "/newtradelicense/newTradeLicense-newForm")
    public String newForm() {
        return super.newForm();
    }

    @Override
    @SkipValidation
    public String approve() {
        return super.approve();
    }

    @Override
    @Validations(requiredFields = {
            @RequiredFieldValidator(fieldName = "licensee.applicantName", message = "", key = Constants.REQUIRED),
            @RequiredFieldValidator(fieldName = "licenseeZoneId", message = "", key = Constants.REQUIRED),
            @RequiredFieldValidator(fieldName = "licenseZoneId", message = "", key = Constants.REQUIRED),
            @RequiredFieldValidator(fieldName = "tradeName", message = "", key = Constants.REQUIRED),
           // @RequiredFieldValidator(fieldName = "applicationDate", message = "", key = Constants.REQUIRED),
            @RequiredFieldValidator(fieldName = "licensee.gender", message = "", key = Constants.REQUIRED),
            @RequiredFieldValidator(fieldName = "nameOfEstablishment", message = "", key = Constants.REQUIRED)
           // @RequiredFieldValidator(fieldName = "address.houseNo", message = "", key = Constants.REQUIRED),
            //@RequiredFieldValidator(fieldName = "licensee.address.houseNo", message = "", key = Constants.REQUIRED) 
            },
            emails = {
            @EmailValidator(message = "Please enter the valid Email Id", fieldName = "licensee.emailId", key = "Please enter the valid Email Id")
    },
    stringLengthFields = {
            @StringLengthFieldValidator(fieldName = "nameOfEstablishment", maxLength = "100", message = "", key = "Maximum length for Name of Establishment is 100"),
            @StringLengthFieldValidator(fieldName = "remarks", maxLength = "500", message = "", key = "Maximum length for Remarks is 500"),
            @StringLengthFieldValidator(fieldName = "address.streetAddress1", maxLength = "500", message = "", key = "Maximum length for remaining address is 500"),
            @StringLengthFieldValidator(fieldName = "address.houseNo", maxLength = "10", message = "", key = "Maximum length for house number is 10"),
            @StringLengthFieldValidator(fieldName = "address.streetAddress2", maxLength = "10", message = "", key = "Maximum length for house number is 10"),
            @StringLengthFieldValidator(fieldName = "phoneNumber", maxLength = "15", message = "", key = "Maximum length for Phone Number is 15"),
            @StringLengthFieldValidator(fieldName = "licensee.applicantName", maxLength = "100", message = "", key = "Maximum length for Applicant Name is 100"),
            @StringLengthFieldValidator(fieldName = "licensee.nationality", maxLength = "50", message = "", key = "Maximum length for Nationality is 50"),
            @StringLengthFieldValidator(fieldName = "licensee.fatherOrSpouseName", maxLength = "100", message = "", key = "Maximum length for Father Or SpouseName is 100"),
            @StringLengthFieldValidator(fieldName = "licensee.qualification", maxLength = "50", message = "", key = "Maximum length for Qualification is 50"),
            @StringLengthFieldValidator(fieldName = "licensee.panNumber", maxLength = "10", message = "", key = "Maximum length for PAN Number is 10"),
          //  @StringLengthFieldValidator(fieldName = "licensee.address.houseNo", maxLength = "10", message = "", key = "Maximum length for house number is 10"),
            @StringLengthFieldValidator(fieldName = "licensee.address.streetAddress2", maxLength = "10", message = "", key = "Maximum length for house number is 10"),
            @StringLengthFieldValidator(fieldName = "licensee.address.streetAddress1", maxLength = "500", message = "", key = "Maximum length for remaining address is 500"),
            @StringLengthFieldValidator(fieldName = "licensee.phoneNumber", maxLength = "15", message = "", key = "Maximum length for Phone Number is 15"),
            @StringLengthFieldValidator(fieldName = "licensee.mobilePhoneNumber", maxLength = "15", message = "", key = "Maximum length for Phone Number is 15"),
            @StringLengthFieldValidator(fieldName = "licensee.uid", maxLength = "12", message = "", key = "Maximum length for UID is 12")
    },
    intRangeFields = {
            @IntRangeFieldValidator(fieldName = "noOfRooms", min = "1", max = "999", message = "", key = "Number of rooms should be in the range 1 to 999"),
            /*@IntRangeFieldValidator(fieldName = "address.pinCode", min = "100000", max = "999999", message = "", key = "Minimum and Maximum length for Pincode is 6 and all Digit Cannot be 0"),*/
            @IntRangeFieldValidator(fieldName = "licensee.age", min = "1", max = "100", message = "", key = "Age should be in the range of 1 to 100")
            /*,@IntRangeFieldValidator(fieldName = "licensee.address.pinCode", min = "100000", max = "999999", message = "", key = "Minimum and Maximum length for Pincode is 6 and all Digit Cannot be 0")*/
    }
            )
    @ValidationErrorPage(Constants.NEW)
    @Action(value = "/newtradelicense/newTradeLicense-create")
    public String create() {
        LOGGER.debug("Trade license Creation Parameters:<<<<<<<<<<>>>>>>>>>>>>>:" + tradeLicense);
        if (tradeLicense.getLicenseZoneId() != null && tradeLicense.getBoundary() == null) {
            final Boundary boundary = boundaryService.getBoundaryById(tradeLicense.getLicenseZoneId());
            tradeLicense.setBoundary(boundary);
        }

        if (tradeLicense.getLicenseeZoneId() != null && tradeLicense.getLicensee().getBoundary() == null) {
            final Boundary boundary = boundaryService.getBoundaryById(tradeLicense.getLicenseeZoneId());
            tradeLicense.getLicensee().setBoundary(boundary);
        }
        if (tradeLicense.getInstalledMotorList() != null) {
            final Iterator<MotorDetails> motorDetails = tradeLicense.getInstalledMotorList().iterator();
            while (motorDetails.hasNext()) {
                final MotorDetails installedMotor = motorDetails.next();
                if (installedMotor != null && installedMotor.getHp() != null && installedMotor.getNoOfMachines() != null
                        && installedMotor.getHp().compareTo(BigDecimal.ZERO) != 0
                        && installedMotor.getNoOfMachines().compareTo(Long.valueOf("0")) != 0)
                    installedMotor.setLicense(tradeLicense);
                else
                    motorDetails.remove();
            }

        }
        LOGGER.debug(" Create Trade License Application Name of Establishment:<<<<<<<<<<>>>>>>>>>>>>>:"
                + tradeLicense.getNameOfEstablishment());
        return super.create();
    }

    @Override
    public void prepareNewForm() {
        super.prepareNewForm();
        setDocumentTypes(service().getDocumentTypesByTransaction(TRANSACTIONTYPE_CREATE_LICENSE));
        tradeLicense.setHotelGradeList(tradeLicense.populateHotelGradeList());
        tradeLicense.setHotelSubCatList(ts.getHotelCategoriesForTrade());
    }

    @Override
    @SkipValidation
    public String renew() {
        LOGGER.debug("Trade license renew Parameters:<<<<<<<<<<>>>>>>>>>>>>>:"
                + tradeLicense);
        final BigDecimal deduction = tradeLicense.getDeduction();
        final BigDecimal otherCharges = tradeLicense.getOtherCharges();
        final BigDecimal swmFee = tradeLicense.getSwmFee();
        tradeLicense = (TradeLicense) ts.getPersistenceService()
                .find("from License where id=?", tradeLicense.getId());
        tradeLicense.setOtherCharges(otherCharges);
        tradeLicense.setDeduction(deduction);
        tradeLicense.setSwmFee(swmFee);
        LOGGER
        .debug("Renew Trade License Application Name of Establishment:<<<<<<<<<<>>>>>>>>>>>>>:"
                + tradeLicense.getNameOfEstablishment());
        return super.renew();
    }

    @Override
    @SkipValidation
    @Action(value = "/newtradelicense/newTradeLicense-beforeRenew")
    public String beforeRenew() {
        LOGGER
        .debug("Entering in the beforeRenew method:<<<<<<<<<<>>>>>>>>>>>>>:");
        tradeLicense = (TradeLicense) ts.getPersistenceService()
                .find("from License where id=?", tradeLicense.getId());
        LOGGER
        .debug("Exiting from the beforeRenew method:<<<<<<<<<<>>>>>>>>>>>>>:");
        return super.beforeRenew();
    }

    @Override
    public License getModel() {
        return tradeLicense;
    }

    public WorkflowBean getWorkflowBean() {
        return workflowBean;
    }

    @Override
    protected License license() {
        return tradeLicense;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BaseLicenseService service() {
        ts.getPersistenceService().setType(TradeLicense.class);
        return ts;
    }

    @SuppressWarnings("unchecked")
    public void setTradeLicenseWorkflowService(
            final WorkflowService tradeLicenseWorkflowService) {
        this.tradeLicenseWorkflowService = tradeLicenseWorkflowService;
    }

    public void setTs(final TradeService ts) {
        this.ts = ts;
    }

    public void setWorkflowBean(final WorkflowBean workflowBean) {
        this.workflowBean = workflowBean;
    }

    public List<LicenseDocumentType> getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(List<LicenseDocumentType> documentTypes) {
        this.documentTypes = documentTypes;
    }

}
