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
package org.egov.works.contractorbill.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.commons.CFinancialYear;
import org.egov.commons.dao.EgwStatusHibernateDAO;
import org.egov.commons.dao.FinancialYearHibernateDAO;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.validation.exception.ValidationException;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.infstr.workflow.WorkFlowMatrix;
import org.egov.model.bills.EgBillregistermis;
import org.egov.pims.commons.Position;
import org.egov.works.contractorbill.entity.ContractorBillRegister;
import org.egov.works.contractorbill.repository.ContractorBillRegisterRepository;
import org.egov.works.lineestimate.entity.DocumentDetails;
import org.egov.works.lineestimate.entity.LineEstimateDetails;
import org.egov.works.models.workorder.WorkOrder;
import org.egov.works.utils.WorksConstants;
import org.egov.works.utils.WorksUtils;
import org.elasticsearch.common.joda.time.DateTime;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class ContractorBillRegisterService {
    
    private static final Logger LOG = LoggerFactory.getLogger(ContractorBillRegisterService.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final ContractorBillRegisterRepository contractorBillRegisterRepository;

    @Autowired
    private EgwStatusHibernateDAO egwStatusHibernateDAO;

    @Autowired
    private WorksUtils worksUtils;

    @Autowired
    private FinancialYearHibernateDAO financialYearHibernateDAO;
    
    @Autowired
    private SimpleWorkflowService<ContractorBillRegister> contractorBillRegisterWorkflowService;
    
    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private PositionMasterService positionMasterService;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Autowired
    public ContractorBillRegisterService(final ContractorBillRegisterRepository contractorBillRegisterRepository) {
        this.contractorBillRegisterRepository = contractorBillRegisterRepository;
    }

    public ContractorBillRegister getContractorBillById(final Long id) {
        return contractorBillRegisterRepository.findOne(id);
    }

    public Integer getMaxSequenceNumberByWorkOrder(final WorkOrder workOrder) {
        return contractorBillRegisterRepository.findMaxBillSequenceNumberByWorkOrder(workOrder);
    }

    public ContractorBillRegister getContractorBillByBillNumber(final String billNumber) {
        return contractorBillRegisterRepository.findByBillnumber(billNumber);
    }

    @Transactional
    public ContractorBillRegister create(final ContractorBillRegister contractorBillRegister,
            final LineEstimateDetails lineEstimateDetails, final MultipartFile[] files,
            final Long approvalPosition, final String approvalComent, final String additionalRule,
            final String workFlowAction)
                    throws IOException {

        contractorBillRegister.setStatus(egwStatusHibernateDAO.getStatusByModuleAndCode(WorksConstants.CONTRACTORBILL,
                ContractorBillRegister.BillStatus.CREATED.toString()));
        contractorBillRegister.setBillstatus(contractorBillRegister.getStatus().getCode());
        contractorBillRegister.setExpendituretype(WorksConstants.BILL_EXPENDITURE_TYPE);
        final EgBillregistermis egBillRegisterMis = setEgBillRegisterMis(contractorBillRegister, lineEstimateDetails);
        contractorBillRegister.setEgBillregistermis(egBillRegisterMis);
        final ContractorBillRegister savedContractorBillRegister = contractorBillRegisterRepository.save(contractorBillRegister);
        
        createContractorBillRegisterWorkflowTransition(savedContractorBillRegister,
                approvalPosition, approvalComent, additionalRule, workFlowAction);
        
        final List<DocumentDetails> documentDetails = worksUtils.getDocumentDetails(files, savedContractorBillRegister,
                WorksConstants.CONTRACTORBILL);
        if (!documentDetails.isEmpty()) {
            savedContractorBillRegister.setDocumentDetails(documentDetails);
            worksUtils.persistDocuments(documentDetails);
        }
        return savedContractorBillRegister;
    }
    
    @Transactional
    public ContractorBillRegister update(
            final ContractorBillRegister contractorBillRegister,
            final Long approvalPosition, final String approvalComent, final String additionalRule,
            final String workFlowAction, final String mode,
            final MultipartFile[] files) throws ValidationException, IOException {
        ContractorBillRegister updatedContractorBillRegister = null;

        contractorBillRegisterStatusChange(contractorBillRegister, workFlowAction, mode);
        contractorBillRegister.setBillstatus(contractorBillRegister.getStatus().getCode());
        
        updatedContractorBillRegister = contractorBillRegisterRepository.save(contractorBillRegister);

        createContractorBillRegisterWorkflowTransition(updatedContractorBillRegister,
                approvalPosition, approvalComent, additionalRule, workFlowAction);

        return updatedContractorBillRegister;
    }

    private EgBillregistermis setEgBillRegisterMis(final ContractorBillRegister contractorBillRegister,
            final LineEstimateDetails lineEstimateDetails) {
        final EgBillregistermis egBillRegisterMis = contractorBillRegister.getEgBillregistermis();
        egBillRegisterMis.setEgBillregister(contractorBillRegister);
        egBillRegisterMis.setPayto(contractorBillRegister.getWorkOrder().getContractor().getName());
        egBillRegisterMis.setFieldid(lineEstimateDetails.getLineEstimate().getWard());

        if (lineEstimateDetails.getLineEstimate().getFund() != null)
            egBillRegisterMis.setFund(lineEstimateDetails.getLineEstimate().getFund());
        if (lineEstimateDetails.getLineEstimate().getFunction() != null)
            egBillRegisterMis.setFunction(lineEstimateDetails.getLineEstimate().getFunction());
        if (lineEstimateDetails.getLineEstimate().getScheme() != null)
            egBillRegisterMis.setScheme(lineEstimateDetails.getLineEstimate().getScheme());
        if (lineEstimateDetails.getLineEstimate().getSubScheme() != null)
            egBillRegisterMis.setSubScheme(lineEstimateDetails.getLineEstimate().getSubScheme());

        egBillRegisterMis.setEgDepartment(lineEstimateDetails.getLineEstimate().getExecutingDepartment());
        final CFinancialYear financialYear = financialYearHibernateDAO
                .getFinancialYearByDate(contractorBillRegister.getBilldate());
        egBillRegisterMis.setFinancialyear(financialYear);
        egBillRegisterMis.setLastupdatedtime(new Date());
        return egBillRegisterMis;
    }
    
    public Long getApprovalPositionByMatrixDesignation(final ContractorBillRegister contractorBillRegister,
            Long approvalPosition, final String additionalRule, final String mode, final String workFlowAction) {
        final WorkFlowMatrix wfmatrix = contractorBillRegisterWorkflowService.getWfMatrix(contractorBillRegister
                .getStateType(), null, null, additionalRule, contractorBillRegister.getCurrentState().getValue(), null);
        if (contractorBillRegister.getStatus() != null && contractorBillRegister.getStatus().getCode() != null)
            if (contractorBillRegister.getStatus().getCode().equals(ContractorBillRegister.BillStatus.CREATED.toString())
                    && contractorBillRegister.getState() != null && !contractorBillRegister.getState().getHistory().isEmpty())
                if (mode.equals("edit"))
                    approvalPosition = contractorBillRegister.getState().getOwnerPosition().getId();
                else
                    approvalPosition = worksUtils.getApproverPosition(wfmatrix.getNextDesignation(),
                            contractorBillRegister.getState(), contractorBillRegister.getCreatedBy().getId());
        if (workFlowAction.equals(WorksConstants.CANCEL_ACTION)
                && wfmatrix.getNextState().equals(WorksConstants.WF_STATE_CREATED))
            approvalPosition = null;

        return approvalPosition;
    }
    
    public void createContractorBillRegisterWorkflowTransition(final ContractorBillRegister contractorBillRegister,
            final Long approvalPosition, final String approvalComent, final String additionalRule,
            final String workFlowAction) {
        if (LOG.isDebugEnabled())
            LOG.debug(" Create WorkFlow Transition Started  ...");
        final User user = securityUtils.getCurrentUser();
        final DateTime currentDate = new DateTime();
        final Assignment userAssignment = assignmentService.getPrimaryAssignmentForUser(user.getId());
        Position pos = null;
        Assignment wfInitiator = null;
        final String currState = "";
        final String natureOfwork = WorksConstants.WORKFLOWTYPE_CBR_DISPLAYNAME;

        if (null != contractorBillRegister.getId())
            wfInitiator = assignmentService.getPrimaryAssignmentForUser(contractorBillRegister.getCreatedBy().getId());
        if (WorksConstants.REJECT_ACTION.toString().equalsIgnoreCase(workFlowAction)) {
            if (wfInitiator.equals(userAssignment))
                contractorBillRegister.transition(true).end().withSenderName(user.getUsername() + "::" + user.getName())
                .withComments(approvalComent).withDateInfo(currentDate.toDate()).withNatureOfTask(natureOfwork);
            else {
                final String stateValue = WorksConstants.WF_STATE_REJECTED;
                contractorBillRegister.transition(true).withSenderName(user.getUsername() + "::" + user.getName())
                .withComments(approvalComent)
                .withStateValue(stateValue).withDateInfo(currentDate.toDate())
                .withOwner(wfInitiator.getPosition())
                .withNextAction("")
                .withNatureOfTask(natureOfwork);
            }
        } else {
            if (null != approvalPosition && approvalPosition != -1 && !approvalPosition.equals(Long.valueOf(0)))
                pos = positionMasterService.getPositionById(approvalPosition);
            WorkFlowMatrix wfmatrix = null;
            if (null == contractorBillRegister.getState()) {
                wfmatrix = contractorBillRegisterWorkflowService.getWfMatrix(contractorBillRegister.getStateType(), null,
                        null, additionalRule, currState, null);
                contractorBillRegister.transition().start().withSenderName(user.getUsername() + "::" + user.getName())
                .withComments(approvalComent)
                .withStateValue(wfmatrix.getNextState()).withDateInfo(new Date()).withOwner(pos)
                .withNextAction(wfmatrix.getNextAction())
                .withNatureOfTask(natureOfwork);
            } else if (WorksConstants.CANCEL_ACTION.toString().equalsIgnoreCase(workFlowAction)) {
                final String stateValue = WorksConstants.WF_STATE_CANCELLED;
                wfmatrix = contractorBillRegisterWorkflowService.getWfMatrix(contractorBillRegister.getStateType(), null,
                        null, additionalRule, contractorBillRegister.getCurrentState().getValue(), null);
                contractorBillRegister.transition(true).withSenderName(user.getUsername() + "::" + user.getName())
                .withComments(approvalComent)
                .withStateValue(stateValue).withDateInfo(currentDate.toDate()).withOwner(pos)
                .withNextAction("")
                .withNatureOfTask(natureOfwork);
            } else {
                wfmatrix = contractorBillRegisterWorkflowService.getWfMatrix(contractorBillRegister.getStateType(), null,
                        null, additionalRule, contractorBillRegister.getCurrentState().getValue(), null);
                contractorBillRegister.transition(true).withSenderName(user.getUsername() + "::" + user.getName())
                .withComments(approvalComent)
                .withStateValue(wfmatrix.getNextState()).withDateInfo(new Date()).withOwner(pos)
                .withNextAction(wfmatrix.getNextAction())
                .withNatureOfTask(natureOfwork);
            }
        }
        if (LOG.isDebugEnabled())
            LOG.debug(" WorkFlow Transition Completed  ...");
    }
    
    public void contractorBillRegisterStatusChange(final ContractorBillRegister contractorBillRegister, final String workFlowAction,
            final String mode) throws ValidationException {
        if (null != contractorBillRegister && null != contractorBillRegister.getStatus()
                && null != contractorBillRegister.getStatus().getCode())
            if (contractorBillRegister.getStatus().getCode().equals(ContractorBillRegister.BillStatus.CREATED.toString())
                    && contractorBillRegister.getState() != null && workFlowAction.equalsIgnoreCase(WorksConstants.ACTION_APPROVE))
                contractorBillRegister.setStatus(egwStatusHibernateDAO.getStatusByModuleAndCode(WorksConstants.CONTRACTORBILL,
                        ContractorBillRegister.BillStatus.APPROVED.toString()));
            else if (workFlowAction.equals(WorksConstants.REJECT_ACTION))
                contractorBillRegister.setStatus(egwStatusHibernateDAO.getStatusByModuleAndCode(WorksConstants.CONTRACTORBILL,
                        ContractorBillRegister.BillStatus.REJECTED.toString()));
            else if (contractorBillRegister.getStatus().getCode()
                    .equals(ContractorBillRegister.BillStatus.REJECTED.toString()) && workFlowAction.equals(WorksConstants.CANCEL_ACTION))
                contractorBillRegister.setStatus(egwStatusHibernateDAO.getStatusByModuleAndCode(WorksConstants.CONTRACTORBILL,
                        ContractorBillRegister.BillStatus.CANCELLED.toString()));
            else if (contractorBillRegister.getStatus().getCode()
                    .equals(ContractorBillRegister.BillStatus.REJECTED.toString()) && workFlowAction.equals(WorksConstants.FORWARD_ACTION))
                contractorBillRegister.setStatus(egwStatusHibernateDAO.getStatusByModuleAndCode(WorksConstants.CONTRACTORBILL,
                        ContractorBillRegister.BillStatus.CREATED.toString()));

    }

}