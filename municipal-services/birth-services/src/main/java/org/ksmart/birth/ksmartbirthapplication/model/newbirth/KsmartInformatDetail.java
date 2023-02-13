package org.ksmart.birth.ksmartbirthapplication.model.newbirth;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

public class KsmartInformatDetail {
    @Size(max = 1000)
    @JsonProperty("infomantFirstNameEn")
    private String infomantFirstNameEn;
    @Size(max = 1000)
    @JsonProperty("infomantAadhar")
    private String infomantAadhar;
    @Size(max = 12)
    @JsonProperty("infomantMobile")
    private String infomantMobile;
    @Size(max = 64)
    @JsonProperty("informerDesi")
    private String informerDesi;
    @Size(max = 2500)
    @JsonProperty("informerAddress")
    private String informerAddress;
    @Size(max = 64)
    @JsonProperty("isDeclarationInfo")
    private String isDeclarationInfo;


}





