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
package org.egov.works.models.milestone;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.egov.works.models.contractorBill.ContractorBillRegister;
import org.egov.works.models.estimate.AbstractEstimateAppropriation;
import org.egov.works.models.milestone.TrackMilestoneActivity;

public class WorkProgressRegister {
	String dept;
	String ward;
	String location;
	String estimateNo;
	String projectCode;
	String nameOfWork;
	String typeOfWork;
	BigDecimal estimateAmt;
	String estimateDate;
	String techSanctionDate;
	String adminSanctionDate;
	String fund;
	String function;
	String budgetHead;
	BigDecimal completedPercentage;
	Boolean isFinalBillCreated=Boolean.FALSE;
	//apprDetail=finYearRange||apprAmt
	String apprInfo;
	String apprDetails;

	String tenderDate;
	String tenderFinalizationDate;
	String tenderAgreementDate;
	BigDecimal workOrderValue;
	String workOrderDate;
	String contractPeriod;
	String workCommencementDate;
	String siteHandedOverDate;
	BigDecimal totalBillAmt;
	BigDecimal totalReleasedAmt;
	BigDecimal totalOutstandingAmt;
	List<TrackMilestoneActivity> trackMilestoneActivities;
	String contractorName;

	// payment Details
	List<PaymentDetail> paymentDetails;
	
	String projectStatus;

	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEstimateNo() {
		return estimateNo;
	}
	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getNameOfWork() {
		return nameOfWork;
	}
	public void setNameOfWork(String nameOfWork) {
		this.nameOfWork = nameOfWork;
	}
	public String getTypeOfWork() {
		return typeOfWork;
	}
	public void setTypeOfWork(String typeOfWork) {
		this.typeOfWork = typeOfWork;
	}
	public BigDecimal getEstimateAmt() {
		return estimateAmt;
	}
	public void setEstimateAmt(BigDecimal estimateAmt) {
		this.estimateAmt = estimateAmt;
	}
	public String getEstimateDate() {
		return estimateDate;
	}
	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
	}
	public String getTechSanctionDate() {
		return techSanctionDate;
	}
	public void setTechSanctionDate(String techSanctionDate) {
		this.techSanctionDate = techSanctionDate;
	}
	public String getAdminSanctionDate() {
		return adminSanctionDate;
	}
	public void setAdminSanctionDate(String adminSanctionDate) {
		this.adminSanctionDate = adminSanctionDate;
	}
	public String getFund() {
		return fund;
	}
	public void setFund(String fund) {
		this.fund = fund;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getBudgetHead() {
		return budgetHead;
	}
	public void setBudgetHead(String budgetHead) {
		this.budgetHead = budgetHead;
	}

	public String getTenderDate() {
		return tenderDate;
	}
	public void setTenderDate(String tenderDate) {
		this.tenderDate = tenderDate;
	}
	public String getTenderFinalizationDate() {
		return tenderFinalizationDate;
	}
	public void setTenderFinalizationDate(String tenderFinalizationDate) {
		this.tenderFinalizationDate = tenderFinalizationDate;
	}
	public String getTenderAgreementDate() {
		return tenderAgreementDate;
	}
	public void setTenderAgreementDate(String tenderAgreementDate) {
		this.tenderAgreementDate = tenderAgreementDate;
	}
	public BigDecimal getWorkOrderValue() {
		return workOrderValue;
	}
	public void setWorkOrderValue(BigDecimal workOrderValue) {
		this.workOrderValue = workOrderValue;
	}
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getWorkCommencementDate() {
		return workCommencementDate;
	}
	public void setWorkCommencementDate(String workCommencementDate) {
		this.workCommencementDate = workCommencementDate;
	}

	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public List<TrackMilestoneActivity> getTrackMilestoneActivities() {
		return trackMilestoneActivities;
	}
	public void setTrackMilestoneActivities(
			List<TrackMilestoneActivity> trackMilestoneActivities) {
		this.trackMilestoneActivities = trackMilestoneActivities;
	}
	public List<PaymentDetail> getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public BigDecimal getTotalBillAmt() {
		return totalBillAmt;
	}
	public void setTotalBillAmt(BigDecimal totalBillAmt) {
		this.totalBillAmt = totalBillAmt;
	}
	public BigDecimal getTotalReleasedAmt() {
		return totalReleasedAmt;
	}
	public void setTotalReleasedAmt(BigDecimal totalReleasedAmt) {
		this.totalReleasedAmt = totalReleasedAmt;
	}
	public BigDecimal getTotalOutstandingAmt() {
		return totalOutstandingAmt;
	}
	public void setTotalOutstandingAmt(BigDecimal totalOutstandingAmt) {
		this.totalOutstandingAmt = totalOutstandingAmt;
	}
	public Boolean getIsFinalBillCreated() {
		return isFinalBillCreated;
	}
	public void setIsFinalBillCreated(Boolean isFinalBillCreated) {
		this.isFinalBillCreated = isFinalBillCreated;
	}
	public BigDecimal getCompletedPercentage() {
		return completedPercentage;
	}
	public void setCompletedPercentage(BigDecimal completedPercentage) {
		this.completedPercentage = completedPercentage;
	}
	public String getApprDetails() {
		return apprDetails;
	}
	public void setApprDetails(String apprDetails) {
		this.apprDetails = apprDetails;
	}
	public String getApprInfo() {
		return apprInfo;
	}
	public void setApprInfo(String apprInfo) {
		this.apprInfo = apprInfo;
	}
	public String getWorkOrderDate() {
		return workOrderDate;
	}
	public void setWorkOrderDate(String workOrderDate) {
		this.workOrderDate = workOrderDate;
	}
	public String getSiteHandedOverDate() {
		return siteHandedOverDate;
	}
	public void setSiteHandedOverDate(String siteHandedOverDate) {
		this.siteHandedOverDate = siteHandedOverDate;
	}

}



