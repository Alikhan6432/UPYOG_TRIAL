package org.egov.ptis.nmc.adapter;

import static org.egov.ptis.nmc.constants.NMCPTISConstants.GWR_IMPOSED;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egov.commons.Installment;
import org.egov.infstr.services.PersistenceService;
import org.egov.infstr.utils.HibernateUtil;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.ptis.domain.entity.demand.FloorwiseDemandCalculations;
import org.egov.ptis.domain.entity.property.BasicProperty;
import org.egov.ptis.domain.entity.property.BoundaryCategory;
import org.egov.ptis.domain.entity.property.FloorIF;
import org.egov.ptis.domain.entity.property.FloorImpl;
import org.egov.ptis.domain.entity.property.MigratedPropertyFloor;
import org.egov.ptis.domain.entity.property.Property;
import org.egov.ptis.nmc.constants.NMCPTISConstants;
import org.egov.ptis.nmc.model.UnitTaxCalculationInfo;
import org.egov.ptis.nmc.service.NonResidentialUnitTaxCalculator;
import org.egov.ptis.nmc.service.ResidentialUnitTaxCalculator;
import org.egov.ptis.nmc.util.PropertyTaxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaxCalculatorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(TaxCalculatorAdapter.class);

	private ResidentialUnitTaxCalculator residentialUnitTaxCalculator;
	private NonResidentialUnitTaxCalculator nonResidentialUnitTaxCalculator;
	private PersistenceService<MigratedPropertyFloor, Long> migratedPropFloorService;
	private PropertyTaxUtil propertyTaxUtil;

	private List<String> applicableTaxes;
	Map<Long, BigDecimal> migratedFloorAlvByFloorId = new HashMap<Long, BigDecimal>();

	public TaxCalculatorAdapter() {}

	public TaxCalculatorAdapter(ResidentialUnitTaxCalculator residentialUnitTaxCalculator,
			NonResidentialUnitTaxCalculator nonResidentialUnitTaxCalculator,
			PersistenceService<MigratedPropertyFloor, Long> floorService, PropertyTaxUtil propertyTaxUtil) {
		this.residentialUnitTaxCalculator = residentialUnitTaxCalculator;
		this.nonResidentialUnitTaxCalculator = nonResidentialUnitTaxCalculator;
		this.migratedPropFloorService = floorService;
		this.propertyTaxUtil = propertyTaxUtil;
	}

	public void calculateALV(BasicProperty basicProperty) {
		LOG.debug("Entered into calculateALV, basicProperty={}", basicProperty);

		Property property = basicProperty.getProperty();
		String propTypeCode = property.getPropertyDetail().getPropertyTypeMaster().getCode();
		List<BoundaryCategory> categories = new ArrayList<BoundaryCategory>();
		Installment installment = PropertyTaxUtil.getCurrentInstallment();

		Boundary propertyArea = getAreaBoundary(property);

		prepareFloorAndAlv(basicProperty);

		for (FloorIF floorIF : property.getPropertyDetail().getFloorDetails()) {
			FloorImpl floorImpl = (FloorImpl) floorIF;

			if(floorImpl.getPropertyUsage() != null && floorImpl.getStructureClassification() != null) {

				/**
				 * Getting the applicable Base Rent
				 */
				categories = propertyTaxUtil.getBoundaryCategories(floorImpl, propertyArea, installment,
						property.getPropertyDetail());

				if (floorImpl.getManualAlv() != null) {

					UnitTaxCalculationInfo unitTaxCalculationInfo = new UnitTaxCalculationInfo();
					unitTaxCalculationInfo.setBaseRent(BigDecimal.ZERO);

					unitTaxCalculationInfo = calculateUnitTax(property, propTypeCode, installment, floorImpl,
							unitTaxCalculationInfo);

					createMigratedPropertyFloorDetails(basicProperty.getUpicNo(), unitTaxCalculationInfo, floorImpl);


				} else {

					for (BoundaryCategory category : categories) {

						UnitTaxCalculationInfo unitTaxCalculationInfo = new UnitTaxCalculationInfo();
						unitTaxCalculationInfo.setBaseRentEffectiveDate(category.getFromDate());
						unitTaxCalculationInfo.setBaseRent(new BigDecimal(category.getCategory().getCategoryAmount()
								.toString()));

						unitTaxCalculationInfo = calculateUnitTax(property, propTypeCode, installment, floorImpl,
								unitTaxCalculationInfo);

						createMigratedPropertyFloorDetails(basicProperty.getUpicNo(), unitTaxCalculationInfo, floorImpl);

					}
				}
			}
		}

		LOG.debug("Exting from calculateALV");
	}

	/**
	 * @param floorImpl
	 * @return
	 */
	private boolean isWaterTaxImposed(String waterRate) {
		return waterRate.equals(GWR_IMPOSED);
	}

	/**
	 * Calculates the ALV
	 *
	 * @param property
	 * @param propTypeCode
	 * @param installment
	 * @param floorImpl
	 * @param unitTaxCalculationInfo
	 * @return
	 */
	private UnitTaxCalculationInfo calculateUnitTax(Property property, String propTypeCode, Installment installment,
			FloorImpl floorImpl, UnitTaxCalculationInfo unitTaxCalculationInfo) {
		LOG.debug("Entered into calculateUnitTax property={}, propTypeCode={}", property,
				propTypeCode);

		if (propTypeCode.equals(NMCPTISConstants.PROPTYPE_RESD)) {
			unitTaxCalculationInfo = residentialUnitTaxCalculator.calculateUnitTax(unitTaxCalculationInfo,
					floorImpl, installment, property.getPropertyDetail().getExtra_field5(),
					property, null);
		} else if (propTypeCode.equals(NMCPTISConstants.PROPTYPE_NON_RESD)) {
			unitTaxCalculationInfo = nonResidentialUnitTaxCalculator.calculateUnitTax(unitTaxCalculationInfo,
					floorImpl, installment, property.getPropertyDetail().getExtra_field5(),
					property, null);
		}

		LOG.debug("calculateUnitTax, ALV=", unitTaxCalculationInfo.getAnnualRentAfterDeduction());
		LOG.debug("Entered into calculateUnitTax");
		return unitTaxCalculationInfo;
	}

	/**
	 * @param property
	 * @return
	 */
	private Boundary getAreaBoundary(Property property) {
		LOG.debug("Entered into getAreaBoundary property={}", property);

		Boundary propertyArea;
		if (property.getAreaBndry() != null) {
			propertyArea = property.getAreaBndry();
		} else {
			propertyArea = property.getBasicProperty().getPropertyID().getArea();
		}

		LOG.debug("Exiting from getAreaBoundary");
		return propertyArea;
	}

	private void createMigratedPropertyFloorDetails(String propertyId, UnitTaxCalculationInfo unitTax, FloorImpl floor) {
		LOG.debug("Entered into createMigratedPropertyFloorDetails unitTax.ALV = {} and floor = {} ",
				unitTax.getAnnualRentAfterDeduction(), floor);

		MigratedPropertyFloor migratedPropertyFloor = new MigratedPropertyFloor();
		migratedPropertyFloor.setPropertyId(propertyId);
		migratedPropertyFloor.setFloorId(floor.getId());
		migratedPropertyFloor.setCalculatedAlv(unitTax.getAnnualRentBeforeDeduction());
		migratedPropertyFloor.setMigratedAlv(getMigratedFloorAlv(floor.getId()));

		migratedPropFloorService.persist(migratedPropertyFloor);

		LOG.debug("Exiting from createMigratedPropertyFloorDetails");

	}

	@SuppressWarnings("unchecked")
	private void prepareFloorAndAlv(BasicProperty basicProperty) {
		LOG.debug("Entered into prepareFloorAndAlv");

		List<FloorwiseDemandCalculations> floorDemands = HibernateUtil
				.getCurrentSession()
				.createQuery(
						" from FloorwiseDemandCalculations where floor.id in "
								+ PropertyTaxUtil.getFloorIdsAsString(basicProperty.getProperty())).list();

		for (FloorwiseDemandCalculations floorDemand : floorDemands) {
			migratedFloorAlvByFloorId.put(floorDemand.getFloor().getId(), floorDemand.getAlv());
		}

		LOG.debug("prepareFloorAndAlv - migratedFloorAlvByFloorId={}", migratedFloorAlvByFloorId);
	}

	private BigDecimal getMigratedFloorAlv(Long floorId) {
		return migratedFloorAlvByFloorId.get(floorId);
	}

	private Date getOccupancyDate(Property property) {
		return new PropertyTaxUtil().getPropertyOccupancyDate(property);
	}
}