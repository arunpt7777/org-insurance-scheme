package com.motta.insurance_scheme_service.entity;

import javax.validation.constraints.Size;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "insurance_scheme")
@SequenceGenerator(name = "Custom_Sequence", sequenceName = "custom_sequence", initialValue = 4, allocationSize = 1)
public class Scheme {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Custom_Sequence")
	private Integer id;

	@NotEmpty(message = "Scheme Name must not be empty")
	private String name;

	@Column(name = "valid_from_date", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date validFromDate;

	@Column(name = "valid_to_date", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date validToDate;

	@Size(min = 1, message = "Scheme Amount must not be empty")
	private Double schemeAmount;

	@NotEmpty(message = "Scheme Type must not be empty")
	private String schemeType;

	public Scheme() {
	}

	public Scheme(Integer id, String name, Date validFromDate, Date validToDate, Double schemeAmount, String schemeType) {
		this.id = id;
		this.name = name;
		this.validFromDate = validFromDate;
		this.validToDate = validToDate;
		this.schemeAmount = schemeAmount;
		this.schemeType = schemeType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public @NotEmpty(message = "Scheme Name must not be empty") String getName() {
		return name;
	}

	public void setName(@NotEmpty(message = "Scheme Name must not be empty") String name) {
		this.name = name;
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

	public Double getSchemeAmount() {
		return schemeAmount;
	}

	public void setSchemeAmount(Double schemeAmount) {
		this.schemeAmount = schemeAmount;
	}

	public @NotEmpty(message = "Scheme Type must not be empty") String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(@NotEmpty(message = "Scheme Type must not be empty") String schemeType) {
		this.schemeType = schemeType;
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
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Scheme scheme = (Scheme) o;
		return Objects.equals(id, scheme.id) && Objects.equals(name, scheme.name) && Objects.equals(validFromDate, scheme.validFromDate) && Objects.equals(validToDate, scheme.validToDate) && Objects.equals(schemeAmount, scheme.schemeAmount) && Objects.equals(schemeType, scheme.schemeType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, validFromDate, validToDate, schemeAmount, schemeType);
	}
}
