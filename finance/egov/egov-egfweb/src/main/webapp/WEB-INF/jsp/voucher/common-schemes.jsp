<%@ taglib uri="/WEB-INF/tags/struts-tags.tld" prefix="s" %>

<%@ page contentType="text/json"%>
{
"ResultSet": {
    "Result":[
    <s:iterator var="s" value="schemeList" status="status">
    {"Text":"<s:property value="%{name}" />",
    "Value":"<s:property value="%{id}" />"
    }<s:if test="!#status.last">,</s:if>
    </s:iterator>       
    ]
  }
}