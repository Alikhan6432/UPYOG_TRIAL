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
package org.egov.collection.entity;

// Generated 10 Sep, 2009 12:59:27 PM by Hibernate Tools 3.2.0.CR1

import org.egov.commons.Bank;
import org.egov.commons.Functionary;
import org.egov.commons.Fund;
import org.egov.commons.Fundsource;
import org.egov.commons.Scheme;
import org.egov.commons.SubScheme;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.persistence.validator.annotation.Required;

/**
 * ReceiptMisc generated by hbm2java
 */
public class ReceiptMisc implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private ReceiptHeader receiptHeader;
	private Fund fund;
	private Fundsource fundsource;
	
	private Boundary boundary;
	private Department department;
	private Scheme scheme;
	private SubScheme subscheme;
	private Functionary idFunctionary;
	private Bank depositedInBank;
	
	public ReceiptMisc() {
	}

	public ReceiptMisc(Boundary boundary, Fund fund, Functionary functionary, Fundsource fundSource,
            Department department, ReceiptHeader receiptHeader, Scheme scheme, SubScheme subscheme,
            Bank depositedInBank) {
        this.boundary = boundary;
        this.fund = fund;
        this.idFunctionary = functionary;
        this.fundsource = fundSource;
        this.department = department;
        this.receiptHeader = receiptHeader;
        this.scheme = scheme;
        this.subscheme = subscheme;
        this.depositedInBank = depositedInBank;
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReceiptHeader getReceiptHeader() {
		return this.receiptHeader;
	}

	public void setReceiptHeader(
			ReceiptHeader receiptHeader) {
		this.receiptHeader = receiptHeader;
	}
	
	@Required(message="billingsystem.fund.null")
	public Fund getFund() {
		return this.fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public Fundsource getFundsource() {
		return this.fundsource;
	}

	public void setFundsource(Fundsource fundsource) {
		this.fundsource = fundsource;
	}

	public Boundary getBoundary() {
		return this.boundary;
	}

	public void setBoundary(Boundary boundary) {
		this.boundary = boundary;
	}
	
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Scheme getScheme() {
		return this.scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public SubScheme getSubscheme() {
		return this.subscheme;
	}

	public void setSubscheme(SubScheme subscheme) {
		this.subscheme = subscheme;
	}

	/**
	 * @return the idFunctionary
	 */
	public Functionary getIdFunctionary() {
		return idFunctionary;
	}

	/**
	 * @param idFunctionary the idFunctionary to set
	 */
	public void setIdFunctionary(Functionary idFunctionary) {
		this.idFunctionary = idFunctionary;
	}
	
	public Bank getDepositedInBank() {
		return depositedInBank;
	}

	public void setDepositedInBank(Bank depositedInBank) {
		this.depositedInBank = depositedInBank;
	}
	
}
