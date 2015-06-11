/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency, 
 *    accountability and the service delivery of the government  organizations.
 * 
 *     Copyright (C) <2015>  eGovernments Foundation
 * 
 *     The updated version of eGov suite of products as by eGovernments Foundation 
 *     is available at http://www.egovernments.org
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or 
 *     http://www.gnu.org/licenses/gpl.html .
 * 
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 * 
 * 	1) All versions of this program, verbatim or modified must carry this 
 * 	   Legal Notice.
 * 
 * 	2) Any misrepresentation of the origin of the material is prohibited. It 
 * 	   is required that all modified versions of this material be marked in 
 * 	   reasonable ways as different from the original version.
 * 
 * 	3) This license does not grant any rights to any user of the program 
 * 	   with regards to rights under trademark law for use of the trade names 
 * 	   or trademarks of eGovernments Foundation.
 * 
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 ******************************************************************************/
package org.egov.services.receipt;

import java.util.Date;
import java.util.List;

import org.egov.egf.commons.EgovCommon;
import org.egov.eis.entity.Assignment;
import org.egov.eis.entity.Employee;
import org.egov.eis.service.EisCommonService;
import org.egov.exceptions.EGOVRuntimeException;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.utils.EgovThreadLocals;
import org.egov.infstr.config.dao.AppConfigValuesDAO;
import org.egov.infstr.services.PersistenceService;
import org.egov.model.receipt.ReceiptVoucher;
import org.egov.pims.commons.Position;
import org.egov.pims.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

public class ReceiptService extends PersistenceService<ReceiptVoucher, Long>{
        @Autowired
	protected EisCommonService eisCommonService;
	private @Autowired AppConfigValuesDAO appConfigValuesDAO;
	 private EmployeeService employeeService;
	private  PersistenceService persistenceService;
	public Position getPositionForEmployee(Employee emp)throws EGOVRuntimeException
	{
		return eisCommonService.getPrimaryAssignmentPositionForEmp(emp.getId());
	}
	
	public void setPersistenceService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}
	public void createVoucherfromPreApprovedVoucher(ReceiptVoucher rv){
		final List<AppConfigValues> appList = appConfigValuesDAO.getConfigValuesByModuleAndKey("EGF","APPROVEDVOUCHERSTATUS");
		final String approvedVoucherStatus = appList.get(0).getValue();
		rv.getVoucherHeader().setStatus(Integer.valueOf(approvedVoucherStatus));
	}
	public void cancelVoucher(ReceiptVoucher rv){
		final List<AppConfigValues> appList = appConfigValuesDAO.getConfigValuesByModuleAndKey("EGF","cancelledstatus");
		final String approvedVoucherStatus = appList.get(0).getValue();
		rv.getVoucherHeader().setStatus(Integer.valueOf(approvedVoucherStatus));
	}
	public String getDesginationName()
	{
		 //TODO: Now employee is extending user so passing userid to get assingment -- changes done by Vaibhav
		 Assignment assignment =eisCommonService.getLatestAssignmentForEmployeeByToDate(EgovThreadLocals.getUserId(),new Date());
		 return assignment.getDesignation().getName();
	}
	public Department getDepartmentForWfItem(ReceiptVoucher rv)
	{
		//TODO: Now employee is extending user so passing userid to get assingment -- changes done by Vaibhav
		Assignment assignment = eisCommonService.getLatestAssignmentForEmployeeByToDate(rv.getCreatedBy().getId(),new Date());
		return assignment.getDepartment();
	}
	public Position getPositionForWfItem(ReceiptVoucher rv)
	{
		return eisCommonService.getPositionByUserId(rv.getCreatedBy().getId());
	}
	public Boundary getBoundaryForUser(ReceiptVoucher rv)
	{
		return new EgovCommon().getBoundaryForUser(rv.getCreatedBy());
	}
	public Department getDepartmentForUser(User user) 
	{
		return new EgovCommon().getDepartmentForUser(user, eisCommonService, employeeService,persistenceService);
    }
	
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
