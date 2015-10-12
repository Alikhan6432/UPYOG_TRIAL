/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
   accountability and the service delivery of the government  organizations.

    Copyright (C) <2015>  eGovernments Foundation

    The updated version of eGov suite of products as by eGovernments Foundation
    is available at http://www.egovernments.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or
    http://www.gnu.org/licenses/gpl.html .

    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:

        1) All versions of this program, verbatim or modified must carry this
           Legal Notice.

        2) Any misrepresentation of the origin of the material is prohibited. It
           is required that all modified versions of this material be marked in
           reasonable ways as different from the original version.

        3) This license does not grant any rights to any user of the program
           with regards to rights under trademark law for use of the trade names
           or trademarks of eGovernments Foundation.

  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.wtms.web.controller.application;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;

import org.egov.wtms.application.entity.WaterConnectionDetails;
import org.egov.wtms.application.service.ConnectionDemandService;
import org.egov.wtms.application.service.WaterConnectionDetailsService;
import org.egov.wtms.masters.entity.enums.ConnectionStatus;
import org.egov.wtms.utils.WaterTaxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/application")
public class GenericBillGeneratorController {

    private final WaterConnectionDetailsService waterConnectionDetailsService;
    private final ConnectionDemandService connectionDemandService;
    private final WaterTaxUtils waterTaxUtils;

    @Autowired
    public GenericBillGeneratorController(final WaterConnectionDetailsService waterConnectionDetailsService,
            final ConnectionDemandService connectionDemandService, final WaterTaxUtils waterTaxUtils) {
        this.waterConnectionDetailsService = waterConnectionDetailsService;
        this.connectionDemandService = connectionDemandService;
        this.waterTaxUtils = waterTaxUtils;
    }

    @RequestMapping(value = "/generatebill/{applicationCode}", method = GET)
    public String showCollectFeeForm(final Model model, @PathVariable final String applicationCode) {
        return "redirect:/application/collecttax-view?applicationCode=" + applicationCode;
    }

    @RequestMapping(value = "/collecttax-view", method = GET)
    public ModelAndView collectTaxView(@ModelAttribute WaterConnectionDetails waterConnectionDetails,
            final HttpServletRequest request, final Model model) {
        if (request.getParameter("applicationCode") != null)
            waterConnectionDetails = waterConnectionDetailsService.findByApplicationNumberOrConsumerCodeAndStatus(request
                    .getParameter("applicationCode"),ConnectionStatus.ACTIVE);
        model.addAttribute(
                "connectionType",
                waterConnectionDetailsService.getConnectionTypesMap().get(
                        waterConnectionDetails.getConnectionType().name()));
        model.addAttribute("mode", "waterTaxCollection");
        model.addAttribute("checkOperator", waterTaxUtils.checkCollectionOperatorRole());
        model.addAttribute("feeDetails", connectionDemandService.getSplitFee(waterConnectionDetails));
        return new ModelAndView("application/collecttax-view", "waterConnectionDetails", waterConnectionDetails);
    }

    @RequestMapping(value = "/generatebill/{applicationCode}", method = POST)
    public String payTax(@ModelAttribute WaterConnectionDetails waterConnectionDetails,
            final RedirectAttributes redirectAttributes, @PathVariable final String applicationCode, final Model model) {
        waterConnectionDetails = waterConnectionDetailsService.findByApplicationNumberOrConsumerCodeAndStatus(
                applicationCode,ConnectionStatus.ACTIVE);//findByApplicationNumberOrConsumerCode(applicationCode);
        model.addAttribute("collectxml", connectionDemandService.generateBill(waterConnectionDetails.getApplicationNumber()));
        return "collecttax-redirection";
    }

}