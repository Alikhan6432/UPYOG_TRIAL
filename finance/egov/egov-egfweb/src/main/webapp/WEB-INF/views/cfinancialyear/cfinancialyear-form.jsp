<!--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2015>  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  -->
<div class="main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">FinancialYear Master</div>
				</div>
				<input type="hidden" value="${mode}" id="mode" />
				<input type="hidden" value="${startingDate}" id="finYearStartDate" />
				
				<div class="panel-body">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.finyearrange" /><span class="mandatory"></span> </label>
						<div class="col-sm-3 add-margin">
							<c:if test="${mode == 'create'}">
								<form:input path="finYearRange"
									class="form-control text-left patternvalidation"
									data-pattern="alphanumeric"  maxlength="25" />
								<form:errors path="finYearRange" cssClass="error-msg" />
							</c:if>
							<c:if test="${mode == 'edit'}">
								<form:input path="finYearRange"
									class="form-control text-left patternvalidation"
									data-pattern="alphanumeric" maxlength="25" readonly="true" />
								<form:errors path="finYearRange" cssClass="error-msg" />
							</c:if>
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.isactiveforposting" /> </label>
						<div class="col-sm-3 add-margin">
							<form:checkbox path="isActiveForPosting" />
							<form:errors path="isActiveForPosting" cssClass="error-msg" />
						</div>
					</div>  
					<div class="form-group">   
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.startingdate" /> <span class="mandatory"></span> </label>
						<div class="col-sm-3 add-margin">
							<c:if test="${mode == 'create'}">
								<form:input path="startingDate"  class="form-control class= datepicker" id="startingDate"
								 required="required"  value="${startingDate}" onchange="validateStartDate();" /> 
								<form:errors path="startingDate" cssClass="error-msg" />
							</c:if>
							<c:if test="${mode == 'edit'}">
								<form:input path="startingDate" 
									required="required" readonly="true" />
								<form:errors path="startingDate" cssClass="error-msg" />
							</c:if>
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.endingdate" /> <span class="mandatory"></span> </label>
						<div class="col-sm-3 add-margin">
							<c:if test="${mode == 'create'}">
								<form:input path="endingDate" class="form-control datepicker" id="endingDate"
									data-inputmask="'mask': 'd/m/y'" required="required" onchange="validateEndDate();"/>
								<form:errors path="endingDate" cssClass="error-msg" />
							</c:if>
							<c:if test="${mode == 'edit'}">
								<form:input path="endingDate" data-inputmask="'mask': 'd/m/y'"
									required="required" readonly="true" />
								<form:errors path="endingDate" cssClass="error-msg" />
							</c:if>
						</div>
					</div>
					<input type="hidden" name="CFinancialYear"
						value="${cFinancialYear.id}" />
					<div id="labelAD" align="center">
						<table id="fiscalPeriodTable" width="60%" border=0 id="labelid"
							class="table table-bordered">
							<thead>
								<th>Fiscal Period Name</th>
								<th>Starting Date</th>
								<th>Ending Date</th>
								<th></th>
							</thead>
						<c:choose>
										<c:when test="${!cFinancialYear.cFiscalPeriod.isEmpty()}">
											<c:forEach items="${cFinancialYear.cFiscalPeriod}" var="var1"
												varStatus="counter">
								<tr id="fiscalPeroid">
									<!--  <td><input type="hidden"  name="cFinancialYearform.cFiscalPeriod[0]" id="fiscalId" value="%{cFinancialYearform.cFiscalPeriod.id}" /></td> -->
									<td><c:if test="${mode == 'create'}">
											<input type="text" value="${var1.name}"
														name="cFiscalPeriod[${counter.index}].name"
														id="name"
												             size="10"
												class="form-control text-right patternvalidation" /> <input
														type="hidden" id="cmdaddListId"
														value="cFiscalPeriod[${counter.index}].id" />
										</c:if> <c:if test="${mode == 'edit'}">
											<input type="text"
												name="cFiscalPeriod[${counter.index}].name"
												value="${var1.name}"
												id="cFiscalPeriod[${counter.index}].name" size="10" readonly="readonly"
												class="form-control text-right patternvalidation" />
										</c:if></td>
									<td><c:if test="${mode == 'create'}"><fmt:formatDate
															value="${var1.startingDate}" var="startDate" 
															pattern="dd/MM/yyyy" /> 
											<input type="text"
												name="cFiscalPeriod[${counter.index}].startingDate"
												value="${startingDate}"
												id="startDate"
												class="form-control datepicker"" />
										</c:if> <c:if test="${mode == 'edit'}">
											<input type="text"
												name="cFiscalPeriod[${counter.index}].startingDate"
												value="${var1.startingDate}"
												id="cFiscalPeriod[${counter.index}].startingDate" readonly="readonly"
												 />
										</c:if></td>
									<td><c:if test="${mode == 'create'}"><fmt:formatDate
															value="${var1.endingDate}" var="endDate"
															pattern="dd/MM/yyyy" /> 
											<input type="text"
												name="cFiscalPeriod[${counter.index}].endingDate"
												value="${endDate}"
												id="endDate"
												class="form-control datepicker"	onchange="validateFiscalEndDate();"/>
										</c:if> <c:if test="${mode == 'edit'}">
											<input type="text"
												name="cFiscalPeriod[${counter.index}].endingDate"
												value="${var1.endingDate}"
												id="cFiscalPeriod[${counter.index}].endingDate" readonly="readonly"
												/>
										</c:if></td>
									<td><c:if test="${mode == 'create'}">
											<input type="button"
														class="btn btn-primary" value="Add" name="Add" id="add"
														onclick="javascript:addRow1(); return false;">
										</c:if></td>
								</tr>
							</c:forEach></c:when></c:choose>
							</table>
					</div>