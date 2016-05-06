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

package org.egov.wtms.web.controller.masters;

import org.egov.wtms.masters.entity.DonationDetails;
import org.egov.wtms.masters.entity.DonationHeader;
import org.egov.wtms.masters.service.ConnectionCategoryService;
import org.egov.wtms.masters.service.DonationDetailsService;
import org.egov.wtms.masters.service.DonationHeaderService;
import org.egov.wtms.masters.service.PipeSizeService;
import org.egov.wtms.masters.service.PropertyTypeService;
import org.egov.wtms.masters.service.UsageTypeService;
import org.egov.wtms.utils.constants.WaterTaxConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/masters")
public class DonationMasterController {

    @Autowired
    private DonationDetailsService donationDetailsService;

    @Autowired
    private final PropertyTypeService propertyTypeService;

    @Autowired
    private final DonationHeaderService donationHeaderService;

    @Autowired
    private final ConnectionCategoryService connectionCategoryService;

    @Autowired
    private final UsageTypeService usageTypeService;

    @Autowired
    private final PipeSizeService pipeSizeService;

    public static final String CONTENTTYPE_JSON = "application/json";

    @Autowired
    public DonationMasterController(final PropertyTypeService propertyTypeService,
            final ConnectionCategoryService connectionCategoryService, final UsageTypeService usageTypeService,
            final PipeSizeService pipeSizeService, final DonationHeaderService donationHeaderService) {
        this.propertyTypeService = propertyTypeService;
        this.connectionCategoryService = connectionCategoryService;
        this.usageTypeService = usageTypeService;
        this.pipeSizeService = pipeSizeService;
        this.donationHeaderService = donationHeaderService;
    }

    @RequestMapping(value = "/donationMaster", method = GET)
    public String viewForm(final Model model) {
        final DonationDetails donationDetails = new DonationDetails();
        model.addAttribute("donationDetails", donationDetails);
        model.addAttribute("typeOfConnection", WaterTaxConstants.DONATIONMASTER);
        model.addAttribute("categoryType", connectionCategoryService.getAllActiveConnectionCategory());
        model.addAttribute("propertyType", propertyTypeService.getAllActivePropertyTypes());
        model.addAttribute("usageType", usageTypeService.getActiveUsageTypes());
        model.addAttribute("reqAttr", "false");
        return "donation-master";
    }

    @RequestMapping(value = "/donationMaster/list", method = GET)
    public String getdonationMasterList(final Model model) {

        final List<DonationDetails> donationDetailsList = donationDetailsService.findAll();
        model.addAttribute("donationDetailsList", donationDetailsList);
        return "donation-master-list";

    }

    @RequestMapping(value = "/donationMaster/{donationid}", method = GET)
    public String getWaterRatesMasterData(final Model model, @PathVariable final Long donationid) {
        final DonationDetails donationDetails = donationDetailsService.findBy(donationid);
        model.addAttribute("donationDetails", donationDetails);
        model.addAttribute("typeOfConnection", WaterTaxConstants.DONATIONMASTER);
        model.addAttribute("categoryType", connectionCategoryService.getAllActiveConnectionCategory());
        model.addAttribute("propertyType", propertyTypeService.getAllActivePropertyTypes());
        model.addAttribute("usageType", usageTypeService.getActiveUsageTypes());
        model.addAttribute("maxPipeSizeList", pipeSizeService.getAllActivePipeSize());
        model.addAttribute("minPipeSizeList", pipeSizeService.getAllActivePipeSize());
        model.addAttribute("reqAttr", "true");
        return "donation-master";

    }

    @RequestMapping(value = "/donationMaster", method = RequestMethod.POST)
    public String addDonationMasterDetails(@Valid @ModelAttribute final DonationDetails donationDetails,
            final RedirectAttributes redirectAttrs, final Model model, final BindingResult resultBinder) {
        if (resultBinder.hasErrors())
            return "donation-master";
        final List<DonationHeader> donationHeaderTempList = donationHeaderService
                .findDonationDetailsByPropertyAndCategoryAndUsageandPipeSize(donationDetails.getDonationHeader()
                        .getPropertyType(), donationDetails.getDonationHeader().getCategory(), donationDetails
                        .getDonationHeader().getUsageType(), donationDetails.getDonationHeader().getMinPipeSize()
                        .getSizeInInch(), donationDetails.getDonationHeader().getMaxPipeSize().getSizeInInch());

        final Calendar cal = Calendar.getInstance();
        if (!donationHeaderTempList.isEmpty()) {
            for (final DonationHeader donationHeaderTemp : donationHeaderTempList) {
                final DonationDetails donationDetailsTemp = donationDetailsService
                        .findByDonationHeader(donationHeaderTemp);
                cal.setTime(donationDetails.getFromDate());
                cal.add(Calendar.DAY_OF_YEAR, -1);
                donationDetailsTemp.setToDate(cal.getTime());
                donationDetailsTemp.getDonationHeader().setActive(false);
                donationHeaderService.updateDonationHeader(donationDetailsTemp.getDonationHeader());
            }
            donationDetails.getDonationHeader().setActive(true);
            cal.setTime(donationDetails.getFromDate());
            cal.add(Calendar.DAY_OF_YEAR, 365);
            donationDetails.setToDate(cal.getTime());
            donationHeaderService.createDonationHeader(donationDetails.getDonationHeader());
            donationDetailsService.createDonationDetails(donationDetails);
            redirectAttrs.addFlashAttribute("donationDetails", donationDetails);
            model.addAttribute("message", "Donation Master Data updated successfully");
        } else {
            donationDetails.getDonationHeader().setActive(true);
            cal.add(Calendar.DATE, 365);
            donationDetails.setToDate(cal.getTime());
            donationHeaderService.createDonationHeader(donationDetails.getDonationHeader());
            donationDetailsService.createDonationDetails(donationDetails);
            redirectAttrs.addFlashAttribute("donationDetails", donationDetails);
            model.addAttribute("message", "Donation Master Data created successfully");
        }
        return getdonationMasterList(model);
    }

    @RequestMapping(value = "/donationMaster/{donationid}", method = RequestMethod.POST)
    public String editDonationMasterData(@Valid @ModelAttribute final DonationDetails donationDetails,
            @PathVariable final Long donationid, final RedirectAttributes redirectAttrs, final Model model,
            final BindingResult resultBinder) {
        if (resultBinder.hasErrors())
            return "donation-master";
        final DonationHeader donationheader = donationDetails.getDonationHeader();
        final DonationDetails donationdetails = donationDetailsService.findBy(donationid);
        final DonationHeader donationHeader = donationdetails.getDonationHeader();
        final List<DonationHeader> donationHeaderTempList = donationHeaderService
                .findDonationDetailsByPropertyAndCategoryAndUsageandPipeSize(donationDetails.getDonationHeader()
                        .getPropertyType(), donationDetails.getDonationHeader().getCategory(), donationDetails
                        .getDonationHeader().getUsageType(), donationDetails.getDonationHeader().getMinPipeSize()
                        .getSizeInInch(), donationDetails.getDonationHeader().getMaxPipeSize().getSizeInInch());
        final Calendar cal = Calendar.getInstance();
        if (!donationHeaderTempList.isEmpty())
            for (final DonationHeader donationHeaderTemp : donationHeaderTempList) {
                final DonationDetails donationDetailsTemp = donationDetailsService
                        .findByDonationHeader(donationHeaderTemp);
                if (donationDetailsTemp!=null){
                cal.setTime(donationDetails.getFromDate());
                cal.add(Calendar.DAY_OF_YEAR, -1);
                donationDetailsTemp.setToDate(cal.getTime());
                donationDetailsTemp.getDonationHeader().setActive(false);
                donationHeaderService.updateDonationHeader(donationDetailsTemp.getDonationHeader());
                }
            }
        donationHeader.setActive(donationheader.isActive());
        donationHeader.setCategory(donationheader.getCategory());
        donationHeader.setMaxPipeSize(donationheader.getMaxPipeSize());
        donationHeader.setMinPipeSize(donationheader.getMinPipeSize());
        donationHeader.setPropertyType(donationheader.getPropertyType());
        donationHeader.setUsageType(donationheader.getUsageType());
        donationdetails.setAmount(donationDetails.getAmount());
        donationdetails.setFromDate(donationDetails.getFromDate());
        donationdetails.setDonationHeader(donationHeader);
        donationHeaderService.createDonationHeader(donationdetails.getDonationHeader());
        donationDetailsService.createDonationDetails(donationdetails);
        return getdonationMasterList(model);
    }

}