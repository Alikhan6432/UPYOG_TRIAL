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
package org.egov.asset.service.impl;

import org.apache.log4j.Logger;
import org.egov.asset.model.Asset;
import org.egov.asset.model.AssetNumberGenrator;
import org.egov.asset.service.AppService;
import org.egov.asset.service.AssetService;
import org.egov.infstr.services.PersistenceService;

/**
 * This class will expose all asset master related operations.
 */
public class AssetServiceImpl extends BaseServiceImpl<Asset, Long> implements AssetService {

    private static final Logger logger = Logger.getLogger(AssetServiceImpl.class);
    private AppService appService;
    private AssetNumberGenrator assetNumberGenrator;

    public AssetServiceImpl(final PersistenceService<Asset, Long> persistenceService) {
        super(persistenceService);
    }

    @Override
    public void setAssetNumber(final Asset entity) {
        final String year = getFinancialYear(entity.getDateOfCreation());
        if (entity != null && (entity.getId() == null || entity.getCode() == null || "".equals(entity.getCode()))
                && isAssetCodeAutoGenerated()) {
            if(logger.isDebugEnabled())
                logger.debug("---Auto generating Asset code....");
            entity.setCode(assetNumberGenrator.getAssetNumber(entity, year));
        }
    }

    @Override
    public boolean isAssetCodeAutoGenerated() {
        final String isAutoGenerated = appService.getUniqueAppConfigValue("IS_ASSET_CODE_AUTOGENERATED");
        if ("yes".equalsIgnoreCase(isAutoGenerated))
            return true;
        return false;
    }

    public void setAppService(final AppService appService) {
        this.appService = appService;
    }

    public void setAssetNumberGenrator(final AssetNumberGenrator assetNumberGenrator) {
        this.assetNumberGenrator = assetNumberGenrator;
    }

}
