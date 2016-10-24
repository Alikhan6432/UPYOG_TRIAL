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

package org.egov.works.web.controller.dashboard;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.egov.works.elasticsearch.model.WorksMilestoneIndexRequest;
import org.egov.works.elasticsearch.model.WorksMilestoneIndexResponse;
import org.egov.works.elasticsearch.service.WorksMilestoneIndexService;
import org.egov.works.web.adaptor.WorksMilestoneIndexJsonAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value = "/public/worksdashboard")
public class StateLevelDashboardController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StateLevelDashboardController.class);

    @Autowired
    private WorksMilestoneIndexService worksMilestoneIndexService;

    @Autowired
    private WorksMilestoneIndexJsonAdaptor worksMilestoneIndexJsonAdaptor;

    /**
     * Gives the State Wise TypeOfWork Details across all ULBs
     *
     * @return string
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/state-bytypeofwork", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getStateWiseTypeOfWorkDetails() throws IOException {
        final Long startTime = System.currentTimeMillis();
        final List<WorksMilestoneIndexResponse> resultList = worksMilestoneIndexService
                .returnTypeOfWorkWiseAggregationResults(new WorksMilestoneIndexRequest(), true, "lineestimatetypeofwork");
        final String result = new StringBuilder("{ \"data\":").append(toStateWiseTypeOfWorkDetailsJson(resultList))
                .append("}").toString();
        final Long timeTaken = System.currentTimeMillis() - startTime;
        LOGGER.debug("Time taken to serve statecityinfo is : " + timeTaken + " (millisecs)");
        return result;
    }

    public Object toStateWiseTypeOfWorkDetailsJson(final Object object) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.registerTypeAdapter(WorksMilestoneIndexResponse.class, worksMilestoneIndexJsonAdaptor)
                .create();
        final String json = gson.toJson(object);
        return json;
    }

}