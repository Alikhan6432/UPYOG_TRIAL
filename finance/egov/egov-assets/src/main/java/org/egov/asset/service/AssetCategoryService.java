package org.egov.asset.service;

import org.egov.asset.model.AssetCategory;

/**
 * This class will have all business logic related to AssetCategory.
 * @author Nils
 *
 */
public interface AssetCategoryService extends BaseService<AssetCategory,Long> {
	
	public void setAssetCategoryNumber(AssetCategory entity);
	
	public boolean isAssetCategoryCodeAutoGenerated();
}
