<%@ page contentType="text/json" %>
<%@ taglib prefix="s" uri="/struts-tags" %>  
{
"ResultSet": {
    "Result":[
    {"Value":"<s:property value="%{trackMlsCheck}" />",
    "woId":"<s:property value="%{workOrderId}" />"}    
    ]
  }
}
