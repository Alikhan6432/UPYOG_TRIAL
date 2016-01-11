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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="row">
	<span class="bold"><spring:message code="${param.header}"/></span>
</div>
<div class="row">
	<div class="col-sm-7">
	<div class="row">
	<div class="form-group">
		<label class="col-sm-5 text-right" style="padding-right: 5px;">
			<spring:message code="lbl.fullname"/><span class="mandatory"></span>
		</label>
		witness: <c:out value="${witness}" />
		<div class="col-sm-5" style="padding-left: 26px;">
			<form:input path="${witness}.name.firstName" id="txt-firstName" type="text" class="form-control low-width is_valid_alphabet" maxlength="30" placeholder="First Name" autocomplete="off" required="required"/>
            <form:errors path="${witness}.name.firstName" cssClass="add-margin error-msg"/>
		</div>
		<div class="col-sm-2"></div>
	</div>
	</div>
	</div>
	<div class="col-sm-5">
	<div class="row">
		<div class="col-sm-6">
		</div>		
		<div class="col-sm-6">
		</div>
	</div>
	</div>
</div>
<div class="row">
	<div class="col-sm-7">
	<div class="row">
		<div class="form-group">
			<label class="col-sm-5 text-right" style="padding-right: 5px;">
				<spring:message code="lbl.occupation"/>
			</label>
			<div class="col-sm-5" style="padding-left: 26px;">
				<form:input path="${witness}.occupation" id="txt-witness-occupation" type="text" class="form-control low-width is_valid_alphanumeric" maxlength="60" placeholder="" autocomplete="off"/>
	            <form:errors path="${witness}.occupation" cssClass="add-margin error-msg"/>
			</div>
			<div class="col-sm-2"></div>
		</div>
	</div>
	<div class="row">
		<div class="form-group">
			<label class="col-sm-5 text-right" style="padding-right: 5px;">
				<spring:message code="lbl.religion"/>
			</label>
			<div class="col-sm-5" style="padding-left: 26px;">
				<form:input path="${witness}.relationshipWithApplicant" id="txt-witness-relationship" type="text" class="form-control low-width is_valid_alphabet" maxlength="30" placeholder="" autocomplete="off"/>
	            <form:errors path="${witness}.relationshipWithApplicant" cssClass="add-margin error-msg"/>
			</div>
			<div class="col-sm-2"></div>
		</div>
	</div>
	<div class="row">
		<div class="form-group">
			<label class="col-sm-5 text-right" style="padding-right: 5px;">
				<spring:message code="lbl.residence.address"/>
			</label>
			<div class="col-sm-5" style="padding-left: 26px;">
				<form:textarea path="${witness}.contactInfo.residenceAddress" id="txt-witness-residenceAddress" type="text" class="form-control low-width" data-pattern="alphanumericwithspecialcharacters" maxlength="256" placeholder="" autocomplete="off" />
                <form:errors path="${witness}.contactInfo.residenceAddress" cssClass="add-margin error-msg"/>
			</div>
			<div class="col-sm-2"></div>
		</div>
	</div>
	<div class="row">
		<div class="form-group">
			<label class="col-sm-5 text-right" style="padding-right: 5px;">
				<spring:message code="lbl.residence.address"/>
			</label>
			<div class="col-sm-5" style="padding-left: 26px;">
				<form:textarea path="${witness}.contactInfo.officeAddress" id="txt-witness-officeAddress" type="text" class="form-control low-width" data-pattern="alphanumericwithspecialcharacters" maxlength="256" placeholder="" autocomplete="off" />
                <form:errors path="${witness}.contactInfo.officeAddress" cssClass="add-margin error-msg"/>
			</div>
			<div class="col-sm-2"></div>
		</div>
	</div>
	</div>
	<div class="col-sm-5">
		<div class="row">
			<label class="col-sm-5 text-right" style="padding: 25px;">
				<spring:message code="lbl.photo"/>
			</label>
			<div class="col-sm-6">			 	
				<img class="add-border attach-photo" height="160" width="140">
				<span></span>
				<input type="hidden" name="${witness}.photo">
			</div>
		</div>
	</div>
</div>