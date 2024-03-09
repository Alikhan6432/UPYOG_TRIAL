<%--
  ~    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) 2017  eGovernments Foundation
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
  ~            Further, all user interfaces, including but not limited to citizen facing interfaces,
  ~            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
  ~            derived works should carry eGovernments Foundation logo on the top right corner.
  ~
  ~            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
  ~            For any further queries on attribution, including queries on brand guidelines,
  ~            please contact contact@egovernments.org
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
  ~
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tags/cdn.tld" prefix="cdn" %>
<form:form role="form" action="search" modelAttribute="budgetGroupSearchRequest"
	id="budgetGroupsearchform"
	cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	<div class="main-content margin-bottom">
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-primary margin-bottom" data-collapsed="0">
					<div class="panel-heading margin-bottom">
						<div class="panel-title margin-bottom"><spring:message code="title.budgetgroup.search" text="Search Budget Group"/> </div>
					</div>
					<div class="panel-body margin-bottom">
						<div class="form-group row">
							<label class="col-sm-4 control-label text-right"><spring:message
									code="lbl.name" text="Name"/> </label>
							<div class="col-sm-4 add-margin margin-bottom">
								<form:input path="name"
									class="form-control text-left patternvalidation"
									data-pattern="alphanumeric" maxlength="250" />
							</div>
							<input type="hidden" id="mode" name="mode" value="${mode}" />
						</div>
					</div>
					<div class="form-group row">
						<div class="text-center margin-bottom">
							<button type='button' class='btn btn-primary' id="btnsearch">
								<spring:message code='lbl.search' text="Search"/>
							</button>
							<a href='javascript:void(0)' class='btn btn-default'
								onclick="window.parent.postMessage('close','*');window.close();"><spring:message code='lbl.close' text="Close"/></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
</form:form>
<div class="row display-hide report-section">
	<div class="col-md-12 table-header text-left"><spring:message code="lbl.budget.group.search.result" text="BudgetGroup Search Result"/> </div>
	<div class="col-md-12 form-group row report-table-container">
		<table class="table table-bordered table-hover multiheadertbl"
			id="resultTable">
			<thead>
				<tr>
					<th><spring:message code="lbl.name" text="Name"/></th>
					<th><spring:message code="lbl.majorcode" text="Major Code"/></th>
					<th><spring:message code="lbl.maxcode" text="Detailed Max Code"/></th>
					<th><spring:message code="lbl.mincode" text="Detailed Min Code"/></th>
					<th><spring:message code="lbl.accounttype" text="Account Type"/></th>
					<th><spring:message code="lbl.budgetingtype" text="Budgeting Type"/></th>
					<th><spring:message code="lbl.isactive" text="Active"/></th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script>
	$('#btnsearch').click(function(e) {
		if ($('form').valid()) {
		} else {
			e.preventDefault();
		}
	});
</script>
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/jquery.dataTables.min.css' context='/services/egi'/>" />
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/dataTables.bootstrap.min.css' context='/services/egi'/>">
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/buttons.bootstrap.min.css' context='/services/egi'/>">
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/dataTables.buttons.min.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.bootstrap.min.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.flash.min.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/jszip.min.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/pdfmake.min.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/vfs_fonts.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.html5.min.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.print.min.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/global/js/jquery/plugins/jquery.validate.min.js' context='/services/egi'/>"></script>
<script type="text/javascript"
	src="<cdn:url value='/resources/app/js/budgetGroupHelper.js?rnd=${app_release_no}'/>"></script>
