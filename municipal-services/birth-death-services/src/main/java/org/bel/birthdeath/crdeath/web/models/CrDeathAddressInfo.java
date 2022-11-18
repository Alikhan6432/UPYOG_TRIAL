package org.bel.birthdeath.crdeath.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrDeathAddressInfo {

    @JsonProperty("presentAddress")
    private CrDeathAddress presentAddress;

    @JsonProperty("permanentAddress")
    private CrDeathAddress permanentAddress;

    @JsonProperty("informantAddress")
    private CrDeathAddress informantAddress;

    @JsonProperty("deathplaceAddress")
    private CrDeathAddress deathplaceAddress;

    @JsonProperty("burialAddress")
    private CrDeathAddress burialAddress;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;    
}
