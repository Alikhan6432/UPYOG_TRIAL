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

package org.egov.mrs.web.adaptor;

import java.lang.reflect.Type;

import org.egov.infra.utils.StringUtils;
import org.egov.mrs.domain.entity.RegistrationCertificatesResultForReport;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MarriageRegistrationCertificateReportJsonAdaptor implements JsonSerializer<RegistrationCertificatesResultForReport> {
    @Override
    public JsonElement serialize(final RegistrationCertificatesResultForReport registration, final Type type,
            final JsonSerializationContext jsc) {
        final JsonObject jsonObject = new JsonObject();
        if (registration != null) {
            if (registration.getRegistrationNo() != null)
                jsonObject.addProperty("registrationNo", registration.getRegistrationNo());
            else
                jsonObject.addProperty("registrationNo", StringUtils.EMPTY);
            if (registration.getZone() != null)
                jsonObject.addProperty("zone", registration.getZone());
            else
                jsonObject.addProperty("zone", StringUtils.EMPTY);
            if (registration.getRegistrationDate() != null)
                jsonObject.addProperty("registrationDate", registration.getRegistrationDate());
            else
                jsonObject.addProperty("registrationDate", StringUtils.EMPTY);

            if (registration.getDateOfMarriage() != null)
                jsonObject.addProperty("dateOfMarriage", registration.getDateOfMarriage());
            else
                jsonObject.addProperty("dateOfMarriage", StringUtils.EMPTY);

            if (registration.getHusbandName() != null)
                jsonObject.addProperty("husbandName", registration.getHusbandName());
            else
                jsonObject.addProperty("husbandName", StringUtils.EMPTY);

            if (registration.getWifeName() != null)
                jsonObject.addProperty("wifeName", registration.getWifeName());
            else
                jsonObject.addProperty("wifeName", StringUtils.EMPTY);

            if (registration.getRejectReason() != null)
                jsonObject.addProperty("remarks", registration.getRejectReason());
            else
                jsonObject.addProperty("remarks", StringUtils.EMPTY);

            if (registration.getCertificateNo() != null)
                jsonObject.addProperty("certificateNo", registration.getCertificateNo());
            else
                jsonObject.addProperty("certificateNo", StringUtils.EMPTY);

            if (registration.getCertificateType() != null)
                jsonObject.addProperty("certificateType", registration.getCertificateType());
            else
                jsonObject.addProperty("certificateType", StringUtils.EMPTY);

            if (registration.getCertificateDate() != null)
                jsonObject.addProperty("certificateDate", registration.getCertificateDate());
            else
                jsonObject.addProperty("certificateDate", StringUtils.EMPTY);

            jsonObject.addProperty("id", registration.getId());
        }
        return jsonObject;
    }
}