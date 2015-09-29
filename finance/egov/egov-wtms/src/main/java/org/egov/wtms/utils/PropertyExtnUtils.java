package org.egov.wtms.utils;

import org.egov.ptis.domain.model.AssessmentDetails;
import org.egov.ptis.wtms.PropertyIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyExtnUtils {

    @Autowired
    private PropertyIntegrationService propertyIntegrationService;

    public AssessmentDetails getAssessmentDetailsForFlag(final String asessmentNumber, final Integer flagDetail) {
        final AssessmentDetails assessmentDetails = propertyIntegrationService.getAssessmentDetailsForFlag(asessmentNumber,
                flagDetail);
        return assessmentDetails;
    }

}
