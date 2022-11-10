package org.egov.filemgmnt.service;

import java.util.List;

import org.egov.filemgmnt.config.CommunicationFileManagementConfiguration;
import org.egov.filemgmnt.enrichment.CommunicationFileManagementEnrichment;
import org.egov.filemgmnt.kafka.Producer;
import org.egov.filemgmnt.repository.CommunicationFileManagementRepository;
import org.egov.filemgmnt.util.MdmsUtil;
import org.egov.filemgmnt.validators.CommunicationFileManagementValidator;
import org.egov.filemgmnt.web.models.CommunicationFile;
import org.egov.filemgmnt.web.models.CommunicationFileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommunicationFileManagementService {

	private final Producer producer;
	private final CommunicationFileManagementConfiguration communicationFilemgmntConfig;
	private final CommunicationFileManagementRepository communicationRepository;
	private final CommunicationFileManagementValidator communicationValidator;
	private final CommunicationFileManagementEnrichment enrichmentService;
	private final MdmsUtil mutil;

	@Autowired
	CommunicationFileManagementService(Producer producer,
			CommunicationFileManagementConfiguration communicationFilemgmntConfig,
			CommunicationFileManagementRepository communicationRepository,
			CommunicationFileManagementValidator communicationValidator,
			CommunicationFileManagementEnrichment enrichmentService, MdmsUtil mutil) {

		this.producer = producer;
		this.communicationFilemgmntConfig = communicationFilemgmntConfig;
		this.communicationRepository = communicationRepository;
		this.communicationValidator = communicationValidator;
		this.enrichmentService = enrichmentService;
		this.mutil = mutil;
	}

	public List<CommunicationFile> create(CommunicationFileRequest request) {

		// enrich request
		enrichmentService.enrichCreate(request);

		producer.push(communicationFilemgmntConfig.getSaveCommunicationFileTopic(), request);

		return request.getCommunicationFiles();

	}

}
