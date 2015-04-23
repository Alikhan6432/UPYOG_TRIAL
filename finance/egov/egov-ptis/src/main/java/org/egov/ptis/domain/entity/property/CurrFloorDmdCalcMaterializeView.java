package org.egov.ptis.domain.entity.property;

import java.io.Serializable;
import java.math.BigDecimal;

public class CurrFloorDmdCalcMaterializeView implements Serializable {

	private Long floorId;
	private PropertyMaterlizeView propMatView;
	private String unitNo;
	private String unitTypeConst;
	private BigDecimal fireTax;
	private BigDecimal lightTax;
	private BigDecimal sewerageTax;
	private BigDecimal generalTax;
	private BigDecimal waterTax;
	private String waterScheme;
	private BigDecimal egsTax;
	private BigDecimal bigBldgTax;
	private BigDecimal eduCessResdTax;
	private BigDecimal eduCessNonResdTax;
	private BigDecimal totalTax = BigDecimal.ZERO; 
	private BigDecimal alv;
	
	
	public Long getFloorId() {
		return floorId;
	}

	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}

	public PropertyMaterlizeView getPropMatView() {
		return propMatView;
	}

	public void setPropMatView(PropertyMaterlizeView propMatView) {
		this.propMatView = propMatView;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getUnitTypeConst() {
		return unitTypeConst;
	}

	public void setUnitTypeConst(String unitTypeConst) {
		this.unitTypeConst = unitTypeConst;
	}

	public BigDecimal getFireTax() {
		return fireTax;
	}

	public void setFireTax(BigDecimal fireTax) {
		this.fireTax = fireTax;
	}

	public BigDecimal getLightTax() {
		return lightTax;
	}

	public void setLightTax(BigDecimal lightTax) {
		this.lightTax = lightTax;
	}

	public BigDecimal getSewerageTax() {
		return sewerageTax;
	}

	public void setSewerageTax(BigDecimal sewerageTax) {
		this.sewerageTax = sewerageTax;
	}

	public BigDecimal getGeneralTax() {
		return generalTax;
	}

	public void setGeneralTax(BigDecimal generalTax) {
		this.generalTax = generalTax;
	}

	public BigDecimal getWaterTax() {
		return waterTax;
	}

	public void setWaterTax(BigDecimal waterTax) {
		this.waterTax = waterTax;
	}

	public BigDecimal getEgsTax() {
		return egsTax;
	}

	public void setEgsTax(BigDecimal egsTax) {
		this.egsTax = egsTax;
	}

	public BigDecimal getBigBldgTax() {
		return bigBldgTax;
	}

	public void setBigBldgTax(BigDecimal bigBldgTax) {
		this.bigBldgTax = bigBldgTax;
	}

	public BigDecimal getEduCessResdTax() {
		return eduCessResdTax;
	}

	public void setEduCessResdTax(BigDecimal eduCessResdTax) {
		this.eduCessResdTax = eduCessResdTax;
	}

	public BigDecimal getEduCessNonResdTax() {
		return eduCessNonResdTax;
	}

	public void setEduCessNonResdTax(BigDecimal eduCessNonResdTax) {
		this.eduCessNonResdTax = eduCessNonResdTax;
	}

	public BigDecimal getTotalTax() {
		totalTax = this.getSewerageTax().add(this.getGeneralTax()).add(this.getLightTax()).add(this.getWaterTax()).add(this.getBigBldgTax());
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public String getWaterScheme() {
		return waterScheme;
	}

	public void setWaterScheme(String waterScheme) {
		this.waterScheme = waterScheme;
	}

	public BigDecimal getAlv() {
		return alv;
	}

	public void setAlv(BigDecimal alv) {
		this.alv = alv;
	}

	@Override
	public String toString() {
		StringBuilder objStr = new StringBuilder();

		objStr.append("PropertyMatView: " + getPropMatView()).append("|UnitNo: ").append(getUnitNo())
				.append("|UnitTypeConst").append(getUnitTypeConst())
				.append("|GeneralTax: ").append(getGeneralTax()).append("|EgsTax: ").append(getEgsTax())
				.append("|EduCessResdTax: ").append(getEduCessResdTax()).append("|EduCessNonResdTax: ").append(getEduCessNonResdTax())
				.append("|WaterTax: ").append(getWaterTax()).append("|FireTax: ").append(getFireTax())
				.append("|SewerageTax: ").append(getSewerageTax()).append("|LightTax: ").append(getLightTax())
				.append("|BigBldgTax: ").append(getBigBldgTax()).append("|WaterScheme").append(getWaterScheme())
				.append("|TotalTax").append(getTotalTax()).append("|ALV").append(getAlv());

		return objStr.toString();
	}
}
