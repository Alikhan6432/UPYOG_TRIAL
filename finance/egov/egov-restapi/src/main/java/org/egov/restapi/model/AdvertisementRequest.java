/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.restapi.model;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.egov.adtax.entity.Advertisement;
import org.egov.adtax.entity.enums.AdvertisementStatus;
import org.egov.infra.admin.master.entity.Boundary;
import org.springframework.data.domain.Example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdvertisementRequest {

    private String ulbCode;
    private Long wardNumber;
    private String advertisementNo;

    public String getUlbCode() {
        return ulbCode;
    }

    public void setUlbCode(final String ulbCode) {
        this.ulbCode = ulbCode;
    }

    public Long getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(final Long wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getAdvertisementNo() {
        return advertisementNo;
    }

    public void setAdvertisementNo(final String advertisementNo) {
        this.advertisementNo = advertisementNo;
    }

    public Example advertisementLike() {
        final Advertisement advertisement = new Advertisement();
        if (isNotBlank(advertisementNo))
            advertisement.setAdvertisementNumber(advertisementNo);
        final Boundary ward = new Boundary();
        ward.setBoundaryNum(wardNumber);
        advertisement.setWard(ward);
        advertisement.setStatus(AdvertisementStatus.ACTIVE);
        return Example.of(advertisement);
    }

}
