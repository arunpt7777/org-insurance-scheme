package com.motta.insurance_scheme_service.model;

import lombok.*;

import java.util.Objects;

@Data
public class AssociationDTO {

	private int id;
	private int employeeId;
	private int schemeId;

	public AssociationDTO(int id, int employeeId, int schemeId) {
		this.id = id;
		this.employeeId = employeeId;
		this.schemeId = schemeId;
	}
}