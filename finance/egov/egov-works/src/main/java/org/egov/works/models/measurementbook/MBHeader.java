package org.egov.works.models.measurementbook;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.egov.commons.EgwStatus;
import org.egov.infra.persistence.validator.annotation.DateFormat;
import org.egov.infra.persistence.validator.annotation.GreaterThan;
import org.egov.infra.persistence.validator.annotation.Required;
import org.egov.infra.persistence.validator.annotation.ValidateDate;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.infstr.ValidationError;
import org.egov.model.bills.EgBillregister;
import org.egov.pims.model.PersonalInformation;
import org.egov.works.models.workorder.WorkOrder;
import org.egov.works.models.workorder.WorkOrderEstimate;
import org.hibernate.validator.constraints.Length;

public class MBHeader extends StateAware{
	
	public enum MeasurementBookStatus{
		NEW,CREATED,CHECKED,REJECTED,RESUBMITTED,CANCELLED,APPROVED
	}
	
	public enum Actions{
		SAVE,SUBMIT_FOR_APPROVAL,REJECT,CANCEL,APPROVAL;

		public String toString() {
			return this.name().toLowerCase();
		}
	}	
	
	@Required(message = "mbheader.workorder.null")
	private WorkOrder workOrder;
	@Required(message = "mbheader.mbrefno.null")
	@Length(max = 50, message = "mbheader.mbrefno.length")
	private String mbRefNo;
	@Required(message = "mbheader.mbPreparedBy.null")
	private PersonalInformation mbPreparedBy;
	@Length(max = 400, message = "mbheader.contractorComments.length")
	private String contractorComments;
	@Required(message = "mbheader.mbdate.null")
	@ValidateDate(allowPast=true,dateFormat="dd/MM/yyyy",message="mbheader.mbDate.futuredate")
	@DateFormat(message = "invalid.fieldvalue.mbDate")
	private Date mbDate;
	@Required(message = "mbheader.mbabstract.null")
	@Length(max = 400, message = "mbheader.mbabstract.length")
	private String mbAbstract;
	@Required(message = "mbheader.fromPageNo.null")
	@GreaterThan(value=0,message="mbheader.fromPageNo.non.negative")
	private Integer fromPageNo;
	@Min(value=0,message="mbheader.toPageNo.non.negative")
	private Integer toPageNo;

	private WorkOrderEstimate workOrderEstimate;
	private Long documentNumber;
	private Integer approverUserId;
	private EgBillregister egBillregister;
	@Valid
	private List<MBDetails> mbDetails = new LinkedList<MBDetails>();
	private String owner;
	private List<String> mbActions = new ArrayList<String>();
	private EgwStatus egwStatus;
	private boolean isLegacyMB;
	private BigDecimal mbAmount;
		
	public List<ValidationError> validate() {
		List<ValidationError> validationErrors = new ArrayList<ValidationError>();
		if (workOrder != null
				&& (workOrder.getId() == null || workOrder.getId() == 0 || workOrder
						.getId() == -1)) {
			validationErrors.add(new ValidationError("workOrder", "mbheader.workorder.null"));
		} 
		if (mbPreparedBy != null
				&& (mbPreparedBy.getIdPersonalInformation() == null
						|| mbPreparedBy.getIdPersonalInformation() == 0 || mbPreparedBy
						.getIdPersonalInformation() == -1)) {
			validationErrors.add(new ValidationError("mbPreparedBy", "mbheader.mbPreparedBy.null"));
		} 
		if ((fromPageNo != null && toPageNo != null) && fromPageNo > toPageNo) {
			validationErrors.add(new ValidationError("toPageNo", "mbheader.toPageNo.invalid"));
		} 
		if(mbDate != null && workOrder!=null && workOrder.getWorkOrderDate() != null && mbDate.before(workOrder.getWorkOrderDate())){
			validationErrors.add(new ValidationError("mbDate", "mbheader.mbDate.invalid"));
		}
		
		return validationErrors;
	}
	
	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setMbRefNo(String mbRefNo) {
		this.mbRefNo = mbRefNo;
	}

	public String getMbRefNo() {
		return mbRefNo;
	}

	public void setMbDate(Date mbDate) {
		this.mbDate = mbDate;
	}

	public Date getMbDate() {
		return mbDate;
	}

	public void setMbAbstract(String mbAbstract) {
		this.mbAbstract = mbAbstract;
	}

	public String getMbAbstract() {
		return mbAbstract;
	}

	public Integer getFromPageNo() {
		return fromPageNo;
	}

	public void setFromPageNo(Integer fromPageNo) {
		this.fromPageNo = fromPageNo;
	}

	public Integer getToPageNo() {
		return toPageNo;
	}

	public void setToPageNo(Integer toPageNo) {
		this.toPageNo = toPageNo;
	}

	public PersonalInformation getMbPreparedBy() {
		return mbPreparedBy;
	}

	public void setMbPreparedBy(PersonalInformation mbPreparedBy) {
		this.mbPreparedBy = mbPreparedBy;
	}

	public String getContractorComments() {
		return contractorComments;
	}

	public void setContractorComments(String contractorComments) {
		this.contractorComments = contractorComments;
	}

	public List<MBDetails> getMbDetails() {
		return mbDetails;
	}

	public void setMbDetails(List<MBDetails> mbDetails) {
		this.mbDetails = mbDetails;
	}

	public void addMbDetails(MBDetails mbDetails) {
		this.mbDetails.add(mbDetails);
	}
	
	//to show in inbox
	@Override
	public String getStateDetails() {
		return "MbHeader : " + getMbRefNo();
	}

	public EgBillregister getEgBillregister() {
		return egBillregister;
	}

	public void setEgBillregister(EgBillregister egBillregister) {
		this.egBillregister = egBillregister;
	}

	public Integer getApproverUserId() {
		return approverUserId;
	}

	public void setApproverUserId(Integer approverUserId) {
		this.approverUserId = approverUserId;
	}
	public WorkOrderEstimate getWorkOrderEstimate() {
		return workOrderEstimate;
	}

	public void setWorkOrderEstimate(WorkOrderEstimate workOrderEstimate) {
		this.workOrderEstimate = workOrderEstimate;
	}


	public Long getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(Long documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<String> getMbActions() {
		return mbActions;
	}

	public void setMbActions(List<String> mbActions) {
		this.mbActions = mbActions;
	}

	public EgwStatus getEgwStatus() {
		return egwStatus;
	}

	public void setEgwStatus(EgwStatus egwStatus) {
		this.egwStatus = egwStatus;
	}

	public boolean getIsLegacyMB() {
		return isLegacyMB;
	}

	public void setIsLegacyMB(boolean isLegacyMB) {
		this.isLegacyMB = isLegacyMB;
	}

	public BigDecimal getMbAmount() {
		return mbAmount;
	}

	public void setMbAmount(BigDecimal mbAmount) {
		this.mbAmount = mbAmount;
	}
	
	public String toString() {
       return "MBHeader ( Id : " + this.getId() + "MB Ref No: " + this.mbRefNo +")";
    }
	
	public BigDecimal getTotalMBAmount(){
		double amount= 0.0;
		BigDecimal resultAmount = BigDecimal.ZERO;
		for(MBDetails mbd : mbDetails){
			if(mbd.getWorkOrderActivity().getActivity().getNonSor()==null){
				amount = mbd.getWorkOrderActivity().getApprovedRate()*mbd.getQuantity()*mbd.getWorkOrderActivity().getConversionFactor();
			}
			else{
				amount = mbd.getWorkOrderActivity().getApprovedRate()*mbd.getQuantity();
			}
			resultAmount = resultAmount.add(BigDecimal.valueOf(amount));
		}
		return resultAmount;
	}

}