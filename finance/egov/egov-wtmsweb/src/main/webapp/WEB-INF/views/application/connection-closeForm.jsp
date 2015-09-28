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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<form:form method="post"
				modelAttribute="waterConnectionDetails"
				id="editmeterWaterConnectionform"
				cssClass="form-horizontal form-groups-bordered" enctype="multipart/form-data">			
<div class="page-container" id="page-container">
	<div class="panel panel-primary" data-collapsed="0">
			<div class="panel-heading">
				<div class="panel-title">
					<spring:message  code="lbl.basicdetails"/>
				</div>
			</div>
			
			<input id="applicationCode" type="hidden" value="<c:out value="${waterConnectionDetails.applicationNumber}" />" />  						
				<jsp:include page="commonappdetails-view.jsp"></jsp:include>
				<jsp:include page="connectiondetails-view.jsp"></jsp:include>
				
		</div>
		<c:if test="${validationMessage==''}">
	<div class="panel panel-primary" data-collapsed="0">
								<div class="panel-heading">
									<div class="panel-title">
										<spring:message  code="lbl.closureheader"/> 
									</div>
									
								</div>
<div class="panel-body">
<div class="form-group">
										<label class="col-sm-3 col-xs-12 control-label text-right"><spring:message  code="lbl.connectiontype"/><span class="mandatory"></span>
                                        </label>
						<div class="col-sm-6 add-margin dynamic-span capitalize">
							<form:radiobuttons path="closeConnectionType" items="${radioButtonMap}"  required="required" name="closeConnectionType" element="span"  /> 
						</div>
					</div>
									</div>
                                    <div class="form-group">
										<label class="col-sm-3 col-xs-12 control-label text-right"><spring:message  code="lbl.closerReason"/><span class="mandatory"></span>
                                        </label>
                                        <div class="col-sm-8 col-xs-12 add-margin">
                                           <form:input id="closeconnectionreason" path="closeconnectionreason" name="closeconnectionreason" required="required"
                                           class="form-control"  />
	
                                        </div>
									</div>
                                    
</div>
</c:if>
</div>			
				
				<c:if test="${validationMessage==''}">
						<jsp:include page="../common/commonWorkflowMatrix.jsp"/>
							<div class="buttonbottom" align="center">
							<jsp:include page="../common/commonWorkflowMatrix-button.jsp" />
					</div></c:if>
				
					
</form:form>
<script>
if($('#validationMessage').val()!='')
	alert($('#validationMessage').val());
function validate(){
	//alert(document.forms[0].action);

	var radioValue = $("input[name='closeConnectionType']:checked").val();
    var ar=document.getElementsByName('closeConnectionType');
    ar[0].value=radioValue;
   return true;
}
</script>

<script src="<c:url value='/resources/js/app/applicationsuccess.js'/>"></script>