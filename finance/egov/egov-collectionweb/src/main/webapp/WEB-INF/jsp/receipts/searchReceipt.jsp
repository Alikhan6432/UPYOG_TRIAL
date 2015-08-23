
<!-- eGov suite of products aim to improve the internal efficiency,transparency, 
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
-->

<%@ include file="/includes/taglibs.jsp" %>
<%@ taglib prefix="egov-authz" uri="/WEB-INF/taglib/egov-authz.tld" %> 
<link rel="stylesheet" type="text/css" href="<egov:url path='/yui/assets/skins/sam/autocomplete.css'/>" />
<head>
	<title><s:text name="searchreceipts.title"/></title>
<script  >

jQuery.noConflict();
jQuery(document).ready(function() {
  	 
     jQuery(" form ").submit(function( event ) {
    	 doLoadingMask();
    });
     doLoadingMask();
 });

jQuery(window).load(function () {
	undoLoadingMask();
});

function isChecked(chk) {
	if (chk.length == undefined) {
 	if (chk.checked == true)
  	return true;
 	else return false;	
 } else {
 	for (i = 0; i < chk.length; i++)
		{
			if (chk[i].checked == true ) return true;
		}
	return false;
 }
}

function checkselectedreceiptcount(obj)
{
	var cnt=document.getElementsByName('selectedReceipts');
	var receiptstatus=document.getElementsByName('receiptstatus');
	var j=0;
	for (i = 0; i < cnt.length; i++)
	{
		if (cnt[i].checked == true )
		{
			j++; 
			if(obj=='cancel' && receiptstatus[i].value=="Cancelled")
			{
				dom.get("selectedcancelledreceiptserror").style.display="block";
				return -1;
			}
			else
			{
				dom.get("selectedcancelledreceiptserror").style.display="none";
			}
		}
		else
		{
			dom.get("selectedcancelledreceiptserror").style.display="none";
		}
	}
	if(j==0)
		return 0;
	else if(j>1)
		return 2;
	else 
		return 1;
}

function checkcancelforselectedrecord()
{
    dom.get("pendingreceiptcancellationerror").style.display="none";
	dom.get("selectcancelerror").style.display="none";
	var check=checkselectedreceiptcount('cancel');
	// more than one receipt has been chosen. Should not allow cancellation
	if(check==2)
	{
		dom.get("norecordselectederror").style.display="none";
		dom.get("selectcancelerror").style.display="block";
		dom.get("selectprinterror").style.display="none";
		window.scroll(0,0);
		return false;
	}
	// no receipts have been chosen. should not allow cancellation
	else if(check==0)
	{
		dom.get("selectcancelerror").style.display="none";
		dom.get("norecordselectederror").style.display="block";
		dom.get("selectprinterror").style.display="none";
		window.scroll(0,0);
		return false;
	}
	// one or more cancelled receipts have been chosen. should not allow cancellation
	else if(check==-1)
	{
		dom.get("selectcancelerror").style.display="none";
		dom.get("norecordselectederror").style.display="none";
		dom.get("selectprinterror").style.display="none";
		window.scroll(0,0);
		return false;
	}
	//one receipt has been chosen. Cancellation is allowed
	else
	{
		var cnt=document.getElementsByName('selectedReceipts');
		var receiptstatus=document.getElementsByName('receiptstatus');
		var j=0;
		for (m = 0; m < cnt.length; m++)
		{
			if (cnt[m].checked == true )
			{
				if(receiptstatus[m].value=="Pending")
				{
					dom.get("pendingreceiptcancellationerror").style.display="block";
					window.scroll(0,0);
					return false;
				}
				
			}
		}
		dom.get("selectcancelerror").style.display="none";
		var receipttype=document.getElementsByName('receipttype');
		var cnt=document.getElementsByName('selectedReceipts');
		
		for (m = 0; m < cnt.length; m++)
		{
			if (cnt[m].checked == true )
			{
				if(receipttype[m].value=="A" || receipttype[m].value=="B")
				{
					document.searchReceiptForm.action="receipt-cancel.action";
				}
				if(receipttype[m].value=='C')
				{
					document.searchReceiptForm.action="challan-cancelReceipt.action";
				}
				
			}
		}
		
		document.searchReceiptForm.submit();
	}
}
function checkprintforselectedrecord()
{
	var check=checkselectedreceiptcount('print');
	// more than one receipts have been chosen. should not print
	if(check==2)
	{
		dom.get("norecordselectederror").style.display="none";
		dom.get("selectprinterror").style.display="block";
		dom.get("selectcancelerror").style.display="none";
		window.scroll(0,0);
		return false;
	}
	// no receipts ahev been chosen for print
	else if(check==0)
	{
		dom.get("selectprinterror").style.display="none";
		dom.get("norecordselectederror").style.display="block";
		dom.get("selectcancelerror").style.display="none";
		window.scroll(0,0);
		return false;
	}
	// single receipt has been chosen. Print is allowed
	else
	{
		dom.get("selectprinterror").style.display="none";
		document.searchReceiptForm.action="receipt-printReceipts.action";
		document.searchReceiptForm.submit();
	}
}

function validate()
{
	var fromdate=dom.get("fromDate").value;
	var todate=dom.get("toDate").value;
	
	if(fromdate!="" && todate!="" && fromdate!=todate)
	{
		if(!checkFdateTdate(fromdate,todate))
		{
			dom.get("comparedatemessage").style.display="block";
			window.scroll(0,0);
			return false;
		}
	}
	else
	{		
		dom.get("comparedatemessage").style.display="none";
		doLoadingMask('#loadingMask');
		return true;
	}
	
	
}

var receiptNumberSelectionEnforceHandler = function(sType, arguments) {
      		warn('improperreceiptNumberSelection');
}
var receiptNumberSearchSelectionHandler = function(sType, arguments) { 
			var oData = arguments[2];
			dom.get("receiptNumberSearch").value=oData[0];
}


var manualReceiptNumberSearchSelectionHandler = function(sType, arguments) { 
	var oData = arguments[2];
	dom.get("manualReceiptNumberSearch").value=oData[0];
}
var manualReceiptNumberSelectionEnforceHandler = function(sType, arguments) {
		warn('impropermanualReceiptNumberSelectionWarning');
}
function checkviewforselectedrecord()
{
	dom.get("norecordselectederror").style.display="none";
	dom.get("selectprinterror").style.display="none";
	dom.get("selectcancelerror").style.display="none";
	var cnt=document.getElementsByName('selectedReceipts');
	var receiptstatus=document.getElementsByName('receiptstatus');
	var j=0;
	for (i = 0; i < cnt.length; i++)
	{
		if (cnt[i].checked == true )
		{
			j++; 
		}
	}
	//no records have been selected for view
	if(j==0)
	{
		dom.get("norecordselectederror").style.display="block";
		window.scroll(0,0);
		return false;
	}
	// multiple records have been chosen . Viewing is allowed
	else
	{	
		doLoadingMask('#loadingMask');
		document.searchReceiptForm.action="receipt-viewReceipts.action";
		document.searchReceiptForm.submit();
	}	

}

</script> 
</head>
<body>
<span align="center" style="display:none" id="pendingreceiptcancellationerror">
  <li>
     <font size="2" color="red"><b><s:text name="error.pendingreceipt.cancellation"/></b></font>
  </li>
</span>
<span align="center" style="display:none" id="selectprinterror">
  <li>
     <font size="2" color="red"><b><s:text name="error.print.nomultipleprintreceipts"/>  </b></font>
  </li>
</span>
<span align="center" style="display:none" id="selectcancelerror">
  <li>
     <font size="2" color="red"><b><s:text name="error.print.nomultiplecancelreceipts"/>  </b></font>
  </li>
</span>
<span align="center" style="display:none" id="norecordselectederror">
  <li>
     <font size="2" color="red"><b><s:text name="error.norecordselected"/></b></font>
  </li>
</span>
<span align="center" style="display:none" id="selectedcancelledreceiptserror">
  <li>
     <font size="2" color="red"><b><s:text name="error.selectedcancelledreceiptserror"/></b></font>
  </li>
</span>
<span align="center" style="display:none" id="invaliddateformat">
  <li>
     <font size="2" color="red"><b>
		<s:text name="common.dateformat.errormessage"/>
	</b></font>
  </li>
</span>
<span align="center" style="display:none" id="comparedatemessage">
  <li>
     <font size="2" color="red"><b>
		<s:text name="common.comparedate.errormessage"/>
	</b></font>
  </li>
</span>

<s:form theme="simple" name="searchReceiptForm" action="searchReceipt-search.action">
<div class="formmainbox"><div class="subheadnew"><s:text name="searchreceipts.title"/>
</div>
<div class="subheadsmallnew"><span class="subheadnew"><s:text name="searchreceipts.criteria"/></span></div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">

	    <tr>
	      <td width="4%" class="bluebox">&nbsp;</td>
	      <td width="21%" class="bluebox"><s:text name="searchreceipts.criteria.servicetype"/></td>
	      <td width="24%" class="bluebox"><s:select headerKey="-1" headerValue="%{getText('searchreceipts.servicetype.select')}" name="serviceTypeId" id="serviceType" cssClass="selectwk" list="dropdownData.serviceTypeList" listKey="id" listValue="name" value="%{serviceTypeId}" /> </td>
	      <td width="21%" class="bluebox"><s:text name="searchreceipts.criteria.counter"/></td>
	      <td width="30%" class="bluebox"><s:select headerKey="-1" headerValue="%{getText('searchreceipts.counter.select')}" name="counterId" id="counter" cssClass="selectwk" list="dropdownData.counterList" listKey="id" listValue="name" value="%{counterId}" /> </td>
	    </tr>
	     <tr>
	      <td width="4%" class="bluebox">&nbsp;</td>
	      <td width="21%" class="bluebox"><s:text name="searchreceipts.criteria.fromdate"/></td>
		  <s:date name="fromDate" var="cdFormat" format="dd/MM/yyyy"/>
		  <td width="24%" class="bluebox"><s:textfield id="fromDate" name="fromDate" value="%{cdFormat}" onfocus="javascript:vDateType='3';" onkeyup="DateFormat(this,this.value,event,false,'3')"/><a href="javascript:show_calendar('forms[0].fromDate');" onmouseover="window.status='Date Picker';return true;"  onmouseout="window.status='';return true;"  ><img src="/egi/images/calendaricon.gif" alt="Date" width="18" height="18" border="0" align="absmiddle" /></a><div class="highlight2" style="width:80px">DD/MM/YYYY</div></td>
	      <td width="21%" class="bluebox"><s:text name="searchreceipts.criteria.todate"/></td>
	      <s:date name="toDate" var="cdFormat1" format="dd/MM/yyyy"/>
		  <td width="30%" class="bluebox"><s:textfield id="toDate" name="toDate" value="%{cdFormat1}" onfocus="javascript:vDateType='3';" onkeyup="DateFormat(this,this.value,event,false,'3')"/><a href="javascript:show_calendar('forms[0].toDate');" onmouseover="window.status='Date Picker';return true;"  onmouseout="window.status='';return true;"  ><img src="/egi/images/calendaricon.gif" alt="Date" width="18" height="18" border="0" align="absmiddle" /></a><div class="highlight2" style="width:80px">DD/MM/YYYY</div></td>
	    </tr>
	    <tr>
	      <td width="4%" class="bluebox">&nbsp;</td>
	      <td width="21%" class="bluebox"><s:text name="searchreceipts.criteria.receiptno"/></td>
	      <td width="24%" class="bluebox"><div class="yui-skin-sam"><div id="receiptNumberSearch_autocomplete"><div><s:textfield id="receiptNumberSearch" type="text" name="receiptNumber"/></div><span id="receiptNumberSearchResults"></span></div></div><egov:autocomplete name="receiptNumberSearch" width="15" field="receiptNumberSearch" url="/egi/receipts/receiptNumberSearch-searchAjax.action" queryQuestionMark="true" results="receiptNumberSearchResults" handler="receiptNumberSearchSelectionHandler" forceSelectionHandler="receiptNumberSelectionEnforceHandler"/><span class='warning' id="improperreceiptNumberSelectionWarning"></span></td>
	      <td width="21%" class="bluebox"><s:text name="searchreceipts.criteria.user"/></td>
	      <td width="30%" class="bluebox"><s:select headerKey="-1" headerValue="%{getText('searchreceipts.user.select')}" name="userId" id="user" cssClass="selectwk" list="dropdownData.userList" listKey="id" listValue="name" value="%{userId}" /> </td>
	   
	    </tr>	    
	    <tr>
	      <td width="4%" class="bluebox">&nbsp;</td>
	      <td width="21%" class="bluebox"><s:text name="searchreceipts.criteria.status"/></td>
	      <td width="24%" class="bluebox"><s:select id="searchStatus" name="searchStatus" headerKey="-1" headerValue="%{getText('searchreceipts.status.select')}" cssClass="selectwk" list="%{receiptStatuses}" value="%{id}" listKey="id" listValue="description" /> </td>
	      <td width="21%" class="bluebox"><s:text name="searchreceipts.criteria.paymenttype"/></td>
	      <td width="30%" class="bluebox"><s:select headerKey="" headerValue="%{getText('searchreceipts.paymenttype.select')}" name="instrumentType" id="instrumentType" cssClass="selectwk" list="dropdownData.instrumentTypeList" listKey="type" listValue="type" value="%{instrumentType}" /> </td>	
	    </tr>
	    <tr>
	      <td width="4%" class="bluebox">&nbsp;</td>
	      <td width="21%" class="bluebox"><s:text name="searchreceipts.criteria.manual.receiptno"/></td>
	      <td width="24%" class="bluebox"><div class="yui-skin-sam"><div id="manualReceiptNumberSearch_autocomplete"><div><s:textfield id="manualReceiptNumberSearch" type="text" name="manualReceiptNumber"/></div><span id="manualReceiptNumberSearchResults"></span></div></div><egov:autocomplete name="manualReceiptNumberSearch" width="15" field="manualReceiptNumberSearch" url="${pageContext.request.contextPath}/receipts/receiptNumberSearch-searchManualReceiptNumberAjax.action" queryQuestionMark="true"  queryLength="3" results="manualReceiptNumberSearchResults" handler="manualReceiptNumberSearchSelectionHandler" forceSelectionHandler="manualReceiptNumberSelectionEnforceHandler"/><span class='warning' id="impropermanualReceiptNumberSelectionWarning"></span></td>
	      <td width="21%" class="bluebox">&nbsp;</td>
	      <td width="30%" class="bluebox"> &nbsp; </td>   
	   
	    </tr>
	    </table>
</div>
<div id="loadingMask" style="display:none;overflow:hidden;text-align: center"><img src="/egi/resources/erp2/images/bar_loader.gif"/> <span style="color: red">Please wait....</span></div>
    <div class="buttonbottom">
      <label><s:submit type="submit" cssClass="buttonsubmit" id="button" value="Search" method="search" onclick="return validate();"/></label>&nbsp;
      <label><s:submit type="submit" cssClass="button" value="Reset" method="reset"/></label>&nbsp;
      <logic:empty name="results">
      	<input name="closebutton" type="button" class="button" id="closebutton" value="Close" onclick="window.close();"/>
      </logic:empty>
      
</div>


<logic:notEmpty name="searchResult">

<div align="center">		
<display:table name="searchResult" uid="currentRow" pagesize = "20" style="border:1px;width:100%;empty-cells:show;border-collapse:collapse;" cellpadding="0" cellspacing="0" export="false" requestURI="">
<display:caption media="pdf">&nbsp;</display:caption>
<display:column headerClass="bluebgheadtd"  class="blueborderfortd" style="width:3%">
<input name="selectedReceipts" type="checkbox" id="selectedReceipts"
				value="${currentRow.id}"/>
<input type="hidden" name="receiptstatus" id="receiptstatus" value="${currentRow.status.description}" />
<input type="hidden" name="receipttype" id="receipttype" value="${currentRow.receipttype}" />
</display:column>
<display:column headerClass="bluebgheadtd" class="blueborderfortd" title="Receipt No." style="width:8%;text-align:center" property="receiptnumber"/>
<display:column headerClass="bluebgheadtd" class="blueborderfortd" title="Manual receipt number" style="width:8%;text-align:center" property="manualreceiptnumber"/>
<display:column headerClass="bluebgheadtd" class="blueborderfortd" property="receiptDate" title="Receipt Date" format="{0,date,dd/MM/yyyy}" style="width:8%;text-align: center" />
<display:column headerClass="bluebgheadtd" class="blueborderfortd" title="Service" style="width:12%;text-align:center" property="service.name" />
<display:column headerClass="bluebgheadtd" class="blueborderfortd" title="Bill Number" style="width:8%;text-align:center" property="referencenumber" />
<display:column headerClass="bluebgheadtd" class="blueborderfortd" title="Bill Description" style="width:27%;text-align:center" property="referenceDesc" />
<display:column headerClass="bluebgheadtd" class="blueborderfortd" title="Amount (Rs.)" property="amount" style="width:8%; text-align: right" format="{0, number, #,##0.00}" />
<display:column headerClass="bluebgheadtd" class="blueborderfortd" title="Mode of Payment" style="width:8%" >
<div align="center">
<s:iterator status="stat1" value="#attr.currentRow.receiptInstrument">
<s:if test="instrumentType.type!=null">
<s:property value="instrumentType.type"/>
</s:if>
<s:if test="!#stat1.last">, </s:if>
</s:iterator>&nbsp;
</div>
</display:column>
<display:column headerClass="bluebgheadtd" class="blueborderfortd" title="Status" style="width:8%;text-align:center" property="status.description"></display:column>
</display:table>	
</div>
<br/>
 
<div class="buttonbottom">
  <input name="button32" type="button" class="buttonsubmit" id="button32" value="View" onclick="return checkviewforselectedrecord()"/>&nbsp;
  <input name="button32" type="button" class="buttonsubmit" id="button32" value="Print" onclick="return checkprintforselectedrecord()"/>&nbsp;
   <egov-authz:authorize actionName="CancelReceipts">
  <input name="button32" type="button" class="buttonsubmit" id="button32" value="Cancel Receipt" onclick="return checkcancelforselectedrecord()"/>&nbsp;
  </egov-authz:authorize>
  <input name="button32" type="button" class="button" id="button32" value="Close" onclick="window.close();"/>
</div>

	
				
</logic:notEmpty>
<logic:empty name="searchResult">
	<s:if test="target=='searchresult'">
	
		<!-- table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" class="tablebottom">
		<tr> 
			<div>&nbsp;</div>
			<div class="subheadnew"><s:text name="searchresult.norecord"/></div>
		</tr>
		</table-->
	
	</s:if>
</logic:empty>


</s:form>
</body>

	
