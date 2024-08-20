package com.motta.insurance_scheme_service.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "insurance_scheme")
@SequenceGenerator(name = "Custom_Sequence", sequenceName = "custom_sequence", initialValue = 100, allocationSize = 1)

@Data
public class Scheme {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Custom_Sequence")
	private int id;
	private String name;

	@Column(name = "valid_from_date", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.DATE)
	private Date validFromDate;

	@Column(name = "valid_to_date", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.DATE)
	private Date validToDate;

	private double schemeAmount;
	private String schemeType;
	private double share;
	private double commission;
	private double brokerage;

	public Scheme(int id, String name, Date validFromDate, Date validToDate, double schemeAmount, String schemeType, double share, double commission, double brokerage) {
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

	public Scheme() {

	}
}