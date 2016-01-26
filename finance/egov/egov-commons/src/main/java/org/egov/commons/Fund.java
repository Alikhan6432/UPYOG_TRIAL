/**
 * eGov suite of products aim to improve the internal efficiency,transparency, accountability and the service delivery of the
 * government organizations.
 * 
 * Copyright (C) <2015> eGovernments Foundation
 * 
 * The updated version of eGov suite of products as by eGovernments Foundation is available at http://www.egovernments.org
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * http://www.gnu.org/licenses/ or http://www.gnu.org/licenses/gpl.html .
 * 
 * In addition to the terms of the GPL license to be adhered to in using this program, the following additional terms are to be
 * complied with:
 * 
 * 1) All versions of this program, verbatim or modified must carry this Legal Notice.
 * 
 * 2) Any misrepresentation of the origin of the material is prohibited. It is required that all modified versions of this
 * material be marked in reasonable ways as different from the original version.
 * 
 * 3) This license does not grant any rights to any user of the program with regards to rights under trademark law for use of the
 * trade names or trademarks of eGovernments Foundation.
 * 
 * In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.commons;

// Generated Jul 19, 2007 2:41:17 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.Length;

/**
 * Fund generated by hbm2java
 */
@Entity
@Table(name = "FUND")
@SequenceGenerator(name = Fund.SEQ_FUND, sequenceName = Fund.SEQ_FUND)
@NamedQuery(name = "getListOfFundsForCodes", query = "from org.egov.commons.Fund where code in(:param_0)")
public class Fund implements java.io.Serializable {

	private static final long serialVersionUID = -3585005698380325913L;
	public static final String SEQ_FUND = "SEQ_FUND";
	
	@Id
	@GeneratedValue(generator = SEQ_FUND, strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENTID")
    private Fund fund;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PURPOSE_ID")
    private EgfAccountcodePurpose egfAccountcodePurpose;

	@Length(max = 50)
	@NotNull
	@Column(unique = true)
    private String code;
    
	@Length(max = 50)
    private String name;

	@NotNull
    private BigDecimal llevel;

	@NotNull
    private int isactive;

    private Boolean isnotleaf;

    @Length(max = 1)
    private Character identifier;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy="fundId", targetEntity=CVoucherHeader.class)
    private Set<CVoucherHeader> voucherheaders = new HashSet<CVoucherHeader>(0);

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy="fund", targetEntity=Fund.class)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    private Set<Fund> funds = new HashSet<Fund>(0);

    public Fund() {
    }

    public Fund(Long id, String code, String name,BigDecimal llevel,
           int isactive) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.llevel = llevel;
        this.isactive = isactive;
    }

    public Fund(Long id, Fund fund,
            EgfAccountcodePurpose egfAccountcodePurpose,String code,String name, BigDecimal llevel,
             int isactive, Boolean isnotleaf, Character identifier, Set voucherheaders,
            Set funds) {
        this.id = id;
        this.fund = fund;
        this.egfAccountcodePurpose = egfAccountcodePurpose;
        this.code = code;
        this.name = name;
        this.llevel = llevel;
        this.isactive = isactive;
        this.isnotleaf = isnotleaf;
        this.identifier = identifier;
        this.voucherheaders = voucherheaders;
        this.funds = funds;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fund getFund() {
        return this.fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public EgfAccountcodePurpose getEgfAccountcodePurpose() {
        return this.egfAccountcodePurpose;
    }

    public void setEgfAccountcodePurpose(
            EgfAccountcodePurpose egfAccountcodePurpose) {
        this.egfAccountcodePurpose = egfAccountcodePurpose;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLlevel() {
        return this.llevel;
    }

    public void setLlevel(BigDecimal llevel) {
        this.llevel = llevel;
    }


    public Boolean getIsnotleaf() {
        return this.isnotleaf;
    }

    public void setIsnotleaf(Boolean isnotleaf) {
        this.isnotleaf = isnotleaf;
    }

    public Character getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(Character identifier) {
        this.identifier = identifier;
    }

    public Set getVoucherheaders() {
        return this.voucherheaders;
    }

    public void setVoucherheaders(Set voucherheaders) {
        this.voucherheaders = voucherheaders;
    }

    public Set getFunds() {
        return this.funds;
    }

    public void setFunds(Set funds) {
        this.funds = funds;
    }

    public int getIsactive() {
        return isactive;
    }

    public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

}
