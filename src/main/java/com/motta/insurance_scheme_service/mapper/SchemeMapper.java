package com.motta.insurance_scheme_service.mapper;

import com.motta.insurance_scheme_service.entity.Scheme;
import com.motta.insurance_scheme_service.model.SchemeDTO;

public class SchemeMapper {

	// Convert Scheme JPA Entity into SchemeDTO
	public static SchemeDTO mapToSchemeDTO(Scheme scheme) {
        return new SchemeDTO(scheme.getId(), scheme.getName(), scheme.getValidFromDate(), scheme.getValidToDate(), scheme.getSchemeAmount(), scheme.getSchemeType());
	}

	// Convert SchemeDTO into Scheme JPA Entity
	public static Scheme mapToScheme(SchemeDTO schemeDTO) {
        return new Scheme(schemeDTO.getId(), schemeDTO.getName(), schemeDTO.getValidFromDate(), schemeDTO.getValidToDate(), schemeDTO.getSchemeAmount(), schemeDTO.getSchemeType());
	}
}
