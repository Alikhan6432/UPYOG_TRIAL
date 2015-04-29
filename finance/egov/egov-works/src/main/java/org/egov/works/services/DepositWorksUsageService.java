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
package org.egov.works.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.egov.commons.Accountdetailtype;
import org.egov.commons.CChartOfAccounts;
import org.egov.commons.Fund;
import org.egov.works.models.estimate.AbstractEstimate;
import org.egov.works.models.estimate.DepositWorksUsage;
import org.egov.works.models.estimate.FinancialDetail;




/**
 * This class will have all business logic related to DepositWorksUsage.
 *
 */
public interface DepositWorksUsageService extends BaseService<DepositWorksUsage,Long> {

	public Map<String,List>  getDepositFolioDetails(AbstractEstimate abstractEstimate,Fund fund,CChartOfAccounts coa,Accountdetailtype accountdetailtype,Long depositCode,Date appropriationDate);
	public BigDecimal getTotalDepositWorksAmount(Fund fund,CChartOfAccounts coa,Accountdetailtype accountdetailtype,Long depositCode,Date appropriationDate); 
	public BigDecimal getTotalUtilizedAmountForDepositWorks(FinancialDetail financialDetail);
	/*
	 * This API returns utilized amount upto appDate accross financial year
	 * @param financailDeatil
	 * @param appDate
	 */
	public BigDecimal getTotalUtilizedAmountForDepositWorks(FinancialDetail financialDetail,Date appDate);
    public DepositWorksUsage getDepositWorksUsage(AbstractEstimate estimate,String appropriationNumber);
}