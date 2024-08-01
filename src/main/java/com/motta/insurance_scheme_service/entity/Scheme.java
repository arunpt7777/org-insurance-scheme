package com.motta.insurance_scheme_service.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "insurance_scheme")
@SequenceGenerator(name = "Custom_Sequence", sequenceName = "custom_sequence", initialValue = 100, allocationSize = 1)
public class Scheme {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Custom_Sequence")
	private Integer id;

	@Column(name = "valid_from_date", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.DATE)
	private Date validFromDate;

	@Column(name = "valid_to_date", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.DATE)
	private Date validToDate;

	private String name;
	private Double schemeAmount;
	private String schemeType;
	private Double share;
	private Double commission;
	private Double brokerage;

	public Scheme() {
	}

	public Scheme(Integer id, String name, Date validFromDate, Date validToDate, Double schemeAmount, String schemeType, Double share, Double commission, Double brokerage) {
		this.id = id;
		this.name = name;
		this.validFromDate = validFromDate;
		this.validToDate = validToDate;
		this.schemeAmount = schemeAmount;
		this.schemeType = schemeType;
		this.share = share;
		this.commission = commission;
		this.brokerage = brokerage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getValidFromDate() {
		return validFromDate;
	}

	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}

	public Date getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSchemeAmount() {
		return schemeAmount;
	}

	public void setSchemeAmount(Double schemeAmount) {
		this.schemeAmount = schemeAmount;
	}

	public String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	public Double getShare() {
		return share;
	}

	public void setShare(Double share) {
		this.share = share;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(Double brokerage) {
		this.brokerage = brokerage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Scheme scheme = (Scheme) o;
		return Objects.equals(id, scheme.id) && Objects.equals(name, scheme.name) && Objects.equals(validFromDate, scheme.validFromDate) && Objects.equals(validToDate, scheme.validToDate) && Objects.equals(schemeAmount, scheme.schemeAmount) && Objects.equals(schemeType, scheme.schemeType) && Objects.equals(share, scheme.share) && Objects.equals(commission, scheme.commission) && Objects.equals(brokerage, scheme.brokerage);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, validFromDate, validToDate, schemeAmount, schemeType, share, commission, brokerage);
	}

	@Override
	public String toString() {
		return "Scheme{" +
				"id=" + id +
				", name='" + name + '\'' +
				", validFromDate=" + validFromDate +
				", validToDate=" + validToDate +
				", schemeAmount=" + schemeAmount +
				", schemeType='" + schemeType + '\'' +
				", share=" + share +
				", commission=" + commission +
				", brokerage=" + brokerage +
				'}';
	}
}
