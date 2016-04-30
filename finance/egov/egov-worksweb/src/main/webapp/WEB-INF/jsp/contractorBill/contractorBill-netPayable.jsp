<!-- -------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency,
#    accountability and the service delivery of the government  organizations.
# 
#     Copyright (C) <2015>  eGovernments Foundation
# 
#     The updated version of eGov suite of products as by eGovernments Foundation
#     is available at http://www.egovernments.org
# 
#     This program is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     any later version.
# 
#     This program is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
# 
#     You should have received a copy of the GNU General Public License
#     along with this program. If not, see http://www.gnu.org/licenses/ or
#     http://www.gnu.org/licenses/gpl.html .
# 
#     In addition to the terms of the GPL license to be adhered to in using this
#     program, the following additional terms are to be complied with:
# 
# 	1) All versions of this program, verbatim or modified must carry this
# 	   Legal Notice.
# 
# 	2) Any misrepresentation of the origin of the material is prohibited. It
# 	   is required that all modified versions of this material be marked in
# 	   reasonable ways as different from the original version.
# 
# 	3) This license does not grant any rights to any user of the program
# 	   with regards to rights under trademark law for use of the trade names
# 	   or trademarks of eGovernments Foundation.
# 
#   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#------------------------------------------------------------------------------- -->
<tr>
	<td colspan="3" class="headingwk">
		<div class="arrowiconwk"><img src="/egworks/resources/erp2/images/arrow.gif" /></div>
		<div class="headplacer"><s:text name="contractorBill.tab.netpayable" /></div>
	</td>
	<td align="right" class="headingwk" style="border-left-width: 0px">
      		&nbsp;
      	</td>
</tr>
<tr>
		<td class="greyboxwk"><s:text name="contractorBill.netpayable.code" /><span class="mandatory">*</span> : </td>
        <td class="greybox2wk"><s:select headerKey="0" 
        headerValue="%{getText('estimate.default.select')}" name="netPayableCode" id="netPayableCode" 
        cssClass="selectwk" list="contratorCoaPayableMap"   /></td></td>
        <td class="greyboxwk"><s:text name="contractorBill.tab.amount" /><span class="mandatory">*</span> : </td>
        <td class="greybox2wk" ><s:textfield  name="netPayableAmount" cssClass='selectamountwk' id="netPayableAmount" readonly="true"/></td>
    </tr>
<script>
 <s:if test="%{skipBudget && dropdownData.assestList.isEmpty() && model.id==null && !hasErrors()}">
                	workValue(); </s:if> 
               
</script>
