<%@ page contentType="text/json" %>
<%@ taglib prefix="s" uri="/struts-tags" %>  
{
"ResultSet": {
    "Result":[
    <s:iterator var="s" value="userList" status="status">
    {"Text":"<s:property value="%{employeeName}" />",
    "Value":"<s:property value="%{idPersonalInformation}" />"
    }<s:if test="!#status.last">,</s:if>
    </s:iterator>       
    ]
  }
}
