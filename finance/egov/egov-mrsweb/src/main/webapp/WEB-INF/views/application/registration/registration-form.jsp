<!-- #-------------------------------------------------------------------------------
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
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<script src="<cdn:url value='/resources/js/app/viewregistration.js'/> "></script>

 <div class="row">
	<div class="col-md-12"> 
		<div class="text-right error-msg" style="font-size:14px;"></div>
		<c:set value="/mrs/registration/register" var="actionUrl" />
		<c:if test="${registration.status == 'Rejected'}">
			 <c:set value="/mrs/registration/workflow?id=${registration.id}" var="actionUrl" />
		</c:if>
		<c:if test="${registration.status == 'Approved'}">
			<c:set value="/mrs/registration/certificate?registrationId=${registration.id}" var="actionUrl"></c:set>
		</c:if>
		<form:form role="form" action="${actionUrl}"
			modelAttribute="registration" id="form-registration"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">

			<input type="hidden" id="registrationId" value="${registration.id}" />
			<input type="hidden" id="registrationStatus" value="${registration.status}" />
				  <ul class="nav nav-tabs" id="settingstab">
				    <li class="active"><a data-toggle="tab" href="#applicant-info" data-tabidx=0>Applicant's Information</a></li>
				    <li><a data-toggle="tab" href="#witness-info" data-tabidx=1>Witnesses Information</a></li>
				    <li><a data-toggle="tab" href="#checklist-info" data-tabidx=2>Checklist</a></li>
				  </ul>
				<div class="tab-content">	 
				    <div id="applicant-info" class="tab-pane fade in active">
				    	<div class="panel panel-primary" data-collapsed="0">
				    		<jsp:include page="generalinfo.jsp"></jsp:include>
				    	</div>
				    </div>
				    <div id="witness-info" class="tab-pane fade">
				    	<div class="panel panel-primary" data-collapsed="0">
				    		<c:set value="witnesses[0]" var="witness" scope="request"></c:set>
				    		<form:hidden path="witnesses[0].id"/>
					    	<jsp:include page="witnessinfo.jsp">
								<jsp:param value="subheading.witness1.info" name="header" />
							</jsp:include>
							
							<c:set value="witnesses[1]" var="witness" scope="request"></c:set>
							<form:hidden path="witnesses[1].id"/>
							<jsp:include page="witnessinfo.jsp">
								<jsp:param value="subheading.witness2.info" name="header" />
							</jsp:include>
							
							<c:set value="witnesses[2]" var="witness" scope="request"></c:set>
							<form:hidden path="witnesses[2].id"/>
							<jsp:include page="witnessinfo.jsp">
								<jsp:param value="subheading.witness3.info" name="header" />
							</jsp:include>	
							<jsp:include page="priestinfo.jsp"></jsp:include>
						</div>
				    </div>
				    <div id="checklist-info" class="tab-pane fade">
				    	<div class="panel panel-primary" data-collapsed="0">
				    		<jsp:include page="checklist.jsp"></jsp:include>
				    	</div>
				    	<div class="panel panel-primary" data-collapsed="0">
				    		<jsp:include page="documentdetails.jsp"></jsp:include>
			    		</div>
				    </div>
				</div>
			<%-- <c:if test="${registration.rejectionReason != null}">
				<div class="row">
					<label class="col-sm-3 control-label text-right"><spring:message code="lbl.reason.rejection"/></label>
					<div class="col-sm-8 add-margin view-content">
						<c:out value="${registration.rejectionReason}" />
					</div>
				</div>
			</c:if>	 --%>		
			<jsp:include page="../../common/commonWorkflowMatrix.jsp"/>
			<div class="buttonbottom" align="center">
				<jsp:include page="../../common/commonWorkflowMatrix-button.jsp" />
			</div>
		</form:form>
	</div>
</div>
 
<script src="<cdn:url value='/resources/global/js/egov/inbox.js' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/js/app/registrationformvalidation.js?rnd=${app_release_no}'/> "></script>
<script src="<cdn:url value='/resources/js/app/registration.js?rnd=${app_release_no}'/> "></script>