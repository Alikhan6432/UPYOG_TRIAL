package org.egov.filemgmnt.web.models;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "A Object holds the fie data against application submited by efile user")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class FileDetail {
	
	@Schema(type = "string", format = "uuid", description = "File id")
    @Size(max = 64)
    @JsonProperty("id")
    private String id;

    
    @Schema(type = "string", description = "Tenant identification number")
	@Size(max = 64)
	@JsonProperty("tenantId")
	private String tenantId;

    @Schema(type = "string", description = "Foreign key servicedetails")
   	@Size(max = 64)
   	@JsonProperty("serviceDetailsId")
   	private String serviceDetailsId;
    
    @Schema(type = "string", description = "File Number")
   	@Size(max = 64)
   	@JsonProperty("fileNumber")
   	private String fileNumber;
    
    @Schema(type = "string", description = "File Code")
   	@Size(max = 64)
   	@JsonProperty("fileCode")
   	private String fileCode;
    
    @Schema(type = "string", description = "File Name")
   	@Size(max = 64)
   	@JsonProperty("fileName")
   	private String fileName;
    
    @Schema(type = "string", description = "File arising mode whether efile/frontoffice")
   	@Size(max = 64)
   	@JsonProperty("fileArisingMode")
   	private String fileArisingMode;
    
    @Schema(type = "int", format = "int64",description = "fileArisingDate")
   	@Size(max = 64)
   	@JsonProperty("fileArisingDate")
   	private String fileArisingDate;
    
    @Schema(type = "string", description = "FinancialYear")
   	@Size(max = 64)
   	@JsonProperty("financialYear")
   	private String financialYear;
    
    @Schema(type = "int", format = "int64", description = "Application submitted Date")
   	@Size(max = 64)
   	@JsonProperty("applicationDate")
   	private String applicationDate;
    
    @Schema(type = "string", description = "workflow code")
   	@Size(max = 64)
   	@JsonProperty("workflowCode")
   	private String workflowCode;
    
    @Schema(type = "string", description = "workflow action")
   	@Size(max = 64)
   	@JsonProperty("action")
   	private String action;
    
    @Schema(type = "string", description = "status of a file")
   	@Size(max = 64)
   	@JsonProperty("fileStatus")
   	private String fileStatus;
    
    @JsonProperty("auditDetails")
    private AuditDetails auditDetails;
}

