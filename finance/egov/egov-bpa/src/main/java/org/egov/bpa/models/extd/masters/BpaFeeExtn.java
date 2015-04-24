/**
 * eGov suite of products aim to improve the internal efficiency,transparency, 
   accountability and the service delivery of the government  organizations.

    Copyright (C) <2015>  eGovernments Foundation

    The updated version of eGov suite of products as by eGovernments Foundation 
    is available at http://www.egovernments.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or 
    http://www.gnu.org/licenses/gpl.html .

    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:

	1) All versions of this program, verbatim or modified must carry this 
	   Legal Notice.

	2) Any misrepresentation of the origin of the material is prohibited. It 
	   is required that all modified versions of this material be marked in 
	   reasonable ways as different from the original version.

	3) This license does not grant any rights to any user of the program 
	   with regards to rights under trademark law for use of the trade names 
	   or trademarks of eGovernments Foundation.

  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.models.extd.masters;

// Generated 13 Nov, 2012 12:35:05 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.egov.commons.CChartOfAccounts;
import org.egov.commons.CFunction;
import org.egov.commons.Fund;
import org.egov.infstr.models.BaseModel;

/**
 * BpaFee generated by hbm2java
 */
public class BpaFeeExtn extends BaseModel {
	/**
	 * Serial version uid
	 */
	private static final long serialVersionUID = 1L;

	private CChartOfAccounts glcode;
	private CFunction function;
	private ServiceTypeExtn serviceType;
	private Fund fund;
	private String feeType;
	private String feeCode;
	private String feeDescription;
	private Boolean isFixedAmount;
	private Boolean isActive;
	private Boolean isMandatory;
	private Set<BpaFeeDetailExtn> feedetailsesList = new HashSet<BpaFeeDetailExtn>(0);
	private BigDecimal feeAmount;
	private Long demandDetailId;
	private String feeDescriptionLocal;
	private Long orderNumber;
	private Boolean isPlanningPermitFee;
	private String feeGroup;

	public String getFeeGroup() {
		return feeGroup;
	}

	public void setFeeGroup(String feeGroup) {
		this.feeGroup = feeGroup;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getFeeDescriptionLocal() {
		return feeDescriptionLocal;
	}

	public void setFeeDescriptionLocal(String feeDescriptionLocal) {
		this.feeDescriptionLocal = feeDescriptionLocal;
	}

	public Long getDemandDetailId() {
		return demandDetailId;
	}

	public void setDemandDetailId(Long demandDetailId) {
		this.demandDetailId = demandDetailId;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public CChartOfAccounts getGlcode() {
		return glcode;
	}

	public void setGlcode(CChartOfAccounts glcode) {
		this.glcode = glcode;
	}

	public CFunction getFunction() {
		return function;
	}

	public void setFunction(CFunction function) {
		this.function = function;
	}

	public ServiceTypeExtn getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceTypeExtn serviceType) {
		this.serviceType = serviceType;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getFeeDescription() {
		return feeDescription;
	}

	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}

	public Boolean getIsFixedAmount() {
		return isFixedAmount;
	}

	public void setIsFixedAmount(Boolean isFixedAmount) {
		this.isFixedAmount = isFixedAmount;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsPlanningPermitFee() {
		return isPlanningPermitFee;
	}

	public void setIsPlanningPermitFee(Boolean isPlanningPermitFee) {
		this.isPlanningPermitFee = isPlanningPermitFee;
	}

	public Set<BpaFeeDetailExtn> getFeedetailsesList() {
		return feedetailsesList;
	}

	public void setFeedetailsesList(Set<BpaFeeDetailExtn> feedetailsesList) {
		this.feedetailsesList = feedetailsesList;
	}

}
