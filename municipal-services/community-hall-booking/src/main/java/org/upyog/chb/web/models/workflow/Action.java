package org.upyog.chb.web.models.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;
import org.upyog.chb.web.models.AuditDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A Object holds the basic data for a Building Plan
 */
@ApiModel(description = "A Object holds the basic data for a Building Plan")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2018-12-04T11:26:25.532+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//@EqualsAndHashCode(of = {"tenantId","currentState","action"})
@EqualsAndHashCode
public class Action   {

        @Size(max=256)
        @JsonProperty("uuid")
        private String uuid;

        @Size(max=256)
        @JsonProperty("tenantId")
        private String tenantId;

        @Size(max=256)
        @JsonProperty("currentState")
        private String currentState;

        @Size(max=256)
        @JsonProperty("action")
        private String action;

        @Size(max=256)
        @JsonProperty("nextState")
        private String nextState;

        @Size(max=1024)
        @JsonProperty("roles")
        @Valid
        private List<String> roles;

        private AuditDetails auditDetails;


        public Action addRolesItem(String rolesItem) {
            if (this.roles == null) {
            this.roles = new ArrayList<>();
            }
        this.roles.add(rolesItem);
        return this;
        }

}

