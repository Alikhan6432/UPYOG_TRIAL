<%@page contentType="text/json" %>
<%@taglib prefix="s" uri="/WEB-INF/taglibs/struts-tags.tld" %> 
{
"ResultSet": {
    "Result":[
    <s:iterator var="s" value="wardList" status="status">
    {"Text":"<s:property value="%{boundaryNum}" />-<s:property value="%{localName}" />",
    "Value":"<s:property value="%{id}" />"
    }<s:if test="!#status.last">,</s:if>
    </s:iterator>       
    ]
  }
}
