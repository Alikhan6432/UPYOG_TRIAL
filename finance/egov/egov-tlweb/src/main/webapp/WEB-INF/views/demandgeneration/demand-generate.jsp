<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/includes/taglibs.jsp" %>
<%--
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
  --%>

<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary" data-collapsed="0">
            <div class="panel-heading">
                <div class="panel-title"><spring:message code="title.demand.generation"/></div>
            </div>
            <div class="panel-body">
                <form:form role="form" id="generatedemand" name="generatedemand"
                           cssClass="form-horizontal form-groups-bordered" method="post">
                    <div class="form-group">
                        <label class="col-sm-4 control-label text-right"><spring:message
                                code="lbl.financialyear"/> </label>
                        <div class="col-sm-4 add-margin">
                            <select name="installmentYear" id="installmentYear" class="form-control"
                                    required="required">
                                <option value=""><spring:message code="lbl.select"/></option>
                                <c:forEach items="${financialYearList}" var="finYear">
                                    <option value="${finYear.finYearRange}">${finYear.finYearRange}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="text-center">
                            <button type="submit" class='btn btn-primary' id="submit">
                                <spring:message code='lbl.generate.demand'/>
                            </button>
                            <button type="submit" class='btn btn-primary' id="genmissingbtn" name="generatemissing">
                                <spring:message code="lbl.generate.missing.demand"/>
                            </button>
                            <button type="button" class="btn btn-default" data-dismiss="modal"
                                    onclick="window.close();">
                                <spring:message code='lbl.close'/></button>
                        </div>
                    </div>
                    <div class="text-center add-margin hidden" id="regeneratediv">
                        <button type="submit" class='btn btn-primary' id="regenbtn" name="regenerate"><spring:message
                                code="lbl.retry.failed.demand"/></button>
                    </div>
                    <c:if test="${retry == 'true'}">
                        <script>$('#regeneratediv').removeClass('hidden');</script>
                    </c:if>
                    <c:if test="${not empty demandgenerationdata}">
                        <script>$("#installmentYear").val('${installmentYear}');</script>
                        <div class="alert alert-info" role="alert"><spring:message code="${message}"/></div>
                        <div class="col-md-12 form-group report-table-container">
                            <table class="table table-bordered datatable dt-responsive table-hover multiheadertbl"
                                   id="tbldemandgenerate">
                            </table>
                        </div>
                    </c:if>

                </form:form>
            </div>
        </div>
    </div>
</div>
<link rel="stylesheet"
      href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/jquery.dataTables.min.css' context='/egi'/>"/>
<link rel="stylesheet"
      href="<cdn:url value='/resources/global/css/jquery/plugins/datatables/dataTables.bootstrap.min.css' context='/egi'/>">
<script type="text/javascript"
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egi'/>"></script>
<script type="text/javascript"
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/dataTables.buttons.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.bootstrap.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.flash.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/jszip.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/pdfmake.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/vfs_fonts.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.html5.min.js' context='/egi'/>"></script>
<script
        src="<cdn:url value='/resources/global/js/jquery/plugins/datatables/extensions/buttons/buttons.print.min.js' context='/egi'/>"></script>
<script src="<cdn:url  value='/resources/js/app/trade-license-demand-generation.js?rnd=${app_release_no}'/>"></script>
<script>
    var reportData =${demandgenerationdata};
    populateData(reportData);
</script>
