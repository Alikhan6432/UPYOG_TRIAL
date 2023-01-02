package org.ksmart.birth.birthapplication.validator;

import org.egov.tracer.model.CustomException;
import org.ksmart.birth.birthapplication.model.BirthApplicationDetail;
import org.ksmart.birth.birthapplication.model.birth.BirthDetailsRequest;
import org.ksmart.birth.config.BirthDeathConfiguration;
import org.ksmart.birth.utils.enums.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.ksmart.birth.utils.enums.ErrorCodes.BIRTH_DETAILS_REQUIRED;
@Component
public class MultipleBirthApplicationValidator {

    private final BirthDeathConfiguration bndCofig;
    private final MdmsValidator mdmsValidator;


    @Autowired
    MultipleBirthApplicationValidator(BirthDeathConfiguration bndCofig, MdmsValidator mdmsValidator) {

        this.bndCofig = bndCofig;

        this.mdmsValidator = mdmsValidator;
    }

    /**
     * Validate abirth details create request.
     *
     * @param request the {@link BirthDetailsRequest}
     */
    public void validateCreate(BirthDetailsRequest request, Object mdmsData) {
        List<BirthApplicationDetail> applicantPersonals = request.getBirthDetails();
        if (CollectionUtils.isEmpty(request.getBirthDetails())) {
            throw new CustomException(ErrorCodes.BIRTH_DETAILS_REQUIRED.getCode(),
                    "Birth details is required.");
        }

        if (applicantPersonals.size() > request.getBirthDetails().get(0).getNoOfAliveBirth()) { // NOPMD

            throw new CustomException(BIRTH_DETAILS_REQUIRED.getCode(),
                    "Supports multiple Birth  application create request.");
        }

        mdmsValidator.validateMdmsData(request, mdmsData);
    }
}
