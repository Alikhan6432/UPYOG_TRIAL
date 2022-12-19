/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) 2016  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.dx.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.dx.service.PaymentService;
import org.egov.dx.service.UserService;
import org.egov.dx.web.models.DocDetailsResponse;
import org.egov.dx.web.models.IssuedTo;
import org.egov.dx.web.models.Payment;
import org.egov.dx.web.models.PaymentSearchCriteria;
import org.egov.dx.web.models.Person;
import org.egov.dx.web.models.PullURIRequestPOJO;
import org.egov.dx.web.models.PullURIResponsePOJO;
import org.egov.dx.web.models.RequestInfoWrapper;
import org.egov.dx.web.models.UserResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;


@RestController
@RequestMapping("/dataexchange")
public class DataExchangeController {
	
	@Autowired
    private PaymentService paymentService;
	
	@Autowired
    private UserService userService;
	
	@RequestMapping(path = {"/_searchReceipt"}, method = RequestMethod.POST ,consumes = {MediaType.APPLICATION_XML_VALUE},produces = {"application/xml","text/xml"})
    @ResponseBody()
    public String search(@Valid @RequestBody String requestBody) throws IOException
     {
		
		XStream xstream = new XStream();
		xstream .addPermission(NoTypePermission.NONE); //forbid everything
		xstream .addPermission(NullPermission.NULL);   // allow "null"
		xstream .addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream .addPermission(AnyTypePermission.ANY);
        //xstream.processAnnotations(DocDetails.class);
        xstream.processAnnotations(PullURIRequestPOJO.class);
		Object obj=xstream.fromXML(requestBody);
		ObjectMapper om=new ObjectMapper();
		PullURIRequestPOJO pojo=om.convertValue(obj, PullURIRequestPOJO.class);
    	String encoded=null;
		PaymentSearchCriteria criteria = new PaymentSearchCriteria();
        criteria.setTenantId("pg."+pojo.getDocDetails().getCity());
        criteria.setConsumerCodes(Collections.singleton(pojo.getDocDetails().getPropertyId()));
        RequestInfo request=new RequestInfo();
        request.setApiId("Rainmaker");
        request.setMsgId("1670564653696|en_IN");
        RequestInfoWrapper requestInfoWrapper=new RequestInfoWrapper();
        UserResponse userResponse =new UserResponse();
        try {
        	userResponse=userService.getUser();
        	}
        catch(Exception e)
        {
        	
        }
        request.setAuthToken(userResponse.getAuthToken());
        request.setUserInfo(userResponse.getUser());
        requestInfoWrapper.setRequestInfo(request);
		List<Payment> payments = paymentService.getPayments(criteria, requestInfoWrapper);
		System.out.println("payments are:" + (!payments.isEmpty()?payments.get(0):"No payments fould"));
		PullURIResponsePOJO model= new PullURIResponsePOJO();
	     
	    
	      
		if(!payments.isEmpty()){ 
				
		 String o=paymentService.getFilestore(criteria, requestInfoWrapper,
				 payments.get(0).getPaymentDetails().get(0).getReceiptNumber(),
				 payments.get(0).getFileStoreId()).toString().split("url=")[1];
		 System.out.println("opening connection");
		 String pdfPath=o.substring(0,o.length()-3);
		 URL url1 =new URL(pdfPath);
		 try {

			          // Read the PDF from the URL and save to a local file
			     InputStream is1 = url1.openStream();
			     ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			     int nRead;
			     byte[] data = new byte[1024];

			     while ((nRead = is1.read(data, 0, data.length)) != -1) {
			         buffer.write(data, 0, nRead);
			     }

			     buffer.flush();
			     byte[] targetArray = buffer.toByteArray();
			     //byte[] sourceBytes = IOUtils.toByteArray(is1)

			     String encodedString = Base64.getEncoder().encodeToString(targetArray); 
			     
			     model.setResponseStatus("1");
			     
			     DocDetailsResponse docDetailsResponse=new DocDetailsResponse();
			     IssuedTo issuedTo=new IssuedTo();
			     List<Person> persons= new ArrayList<Person>();
			     issuedTo.setPersons(persons);
			     docDetailsResponse.setURI(null);
			     docDetailsResponse.setIssuedTo(issuedTo);
			     docDetailsResponse.setDataContent(encodedString);
			     model.setDocDetails(docDetailsResponse);
			       
			    
			     //return targetArray.toString();


			    } catch (NullPointerException npe) {
			      System.out.println("FAILED.\n[" + npe.getMessage() + "]\n");
			    }
			  }	
		else
		{
			encoded="No payments found for this payment Id";
			model.setResponseStatus("1");
		}
		
		
			
        xstream = new XStream();
		xstream .addPermission(NoTypePermission.NONE); //forbid everything
		xstream .addPermission(NullPermission.NULL);   // allow "null"
		xstream .addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream .addPermission(AnyTypePermission.ANY);
        //xstream.processAnnotations(DocDetails.class);
        xstream.processAnnotations(PullURIResponsePOJO.class);
        JSONObject jsonObj = new JSONObject( model );
        xstream.toXML(model);
        
		return xstream.toXML(model);   
    }
	
}