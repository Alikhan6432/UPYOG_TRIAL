<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="/WEB-INF/tags/sitemesh-decorator.tld" prefix="decorator"%>
<%@ taglib uri="/WEB-INF/tags/sitemesh-page.tld" prefix="page"%>
<%@ taglib uri="/WEB-INF/tags/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tags/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tags/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tags/struts-nested.tld" prefix="nested" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="egovtags"%>
<%@ taglib uri="/WEB-INF/tags/struts-tags.tld" prefix="s" %>
<%@ taglib prefix="egov" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sx" uri="/WEB-INF/tags/struts-dojo-tags.tld" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title"><spring:message code="title.transactionsummary" /></div>
				</div>
				<center>
						<span class="mandatory1">
							<div id="errors">
							</div>
						</span>
					</center>
				<div class="panel-body">
					<div class="form-group">
						<form:input type="hidden" path="id" />
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.financialyear" /><span
									class="mandatory1">*</span></label>
						<div class="col-sm-3 add-margin">
							<form:select path="financialyear.id" id="financialyear.id"
								cssClass="form-control mandatory" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${cFinancialYears}" itemValue="id"
									itemLabel="finYearRange" />
							</form:select>
							<form:errors path="financialyear" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.fundsource" /></label>
						<div class="col-sm-3 add-margin">
							<form:select path="fundsource.id" id="fundsource.id"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${fundsources}" itemValue="id"
									itemLabel="name" />
							</form:select>
							<form:errors path="fundsource" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.fund" /><span
									class="mandatory1">*</span></label>
						<div class="col-sm-3 add-margin">
							<form:select path="fund.id" id="fund.id" cssClass="form-control mandatory"
								cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${funds}" itemValue="id" itemLabel="name" />
							</form:select>
							<form:errors path="fund" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.functionid" /><span
									class="mandatory1">*</span></label>
						<div class="col-sm-3 add-margin">
							<form:select path="functionid.id" id="functionid.id"
								cssClass="form-control mandatory" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${cFunctions}" itemValue="id"
									itemLabel="name" />
							</form:select>
							<form:errors path="functionid" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.divisionid" /></label>
						<div class="col-sm-3 add-margin">
							<form:input path="divisionid"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="divisionid" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.departmentid" /><span
									class="mandatory1">*</span></label>
						<div class="col-sm-3 add-margin">
							<form:select path="departmentid.id" id="departmentid.id"
								cssClass="form-control mandatory" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${departments}" itemValue="id"
									itemLabel="name" />
							</form:select>
							<form:errors path="departmentid" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.functionaryid" /></label>
						<div class="col-sm-3 add-margin">
							<form:select path="functionaryid.id" id="functionaryid.id"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${functionarys}" itemValue="id"
									itemLabel="name" />
							</form:select>
							<form:errors path="functionaryid" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.type" /></label>
						<div class="col-sm-3 add-margin">
							<select name="type" id="type"
								class="form-control">
								<option value="">
									<spring:message code="lbl.select" />
								</option>
								<option value="A"><spring:message code="value.asset" /></option>
								<option value="L"><spring:message code="value.liability" /></option>
							</select>
							<%-- <form:errors path="glcodeid" cssClass="error-msg" /> --%>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.major" /></label>
						<div class="col-sm-3 add-margin">
							<select name="major" id="major"
								class="form-control">
							</select>
							<%-- <form:errors path="glcodeid" cssClass="error-msg" /> --%>
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.minor" /></label>
						<div class="col-sm-3 add-margin">
							<select name="minor" id="minor"
								class="form-control">
							</select>
							<%-- <form:errors path="glcodeid" cssClass="error-msg" /> --%>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"></label>
						<button type="button" id="search"
							class="col-sm-1 add-margin btn btn-xs btn-secondary search">
							<span class="glyphicon glyphicon-search"></span>Search
						</button>