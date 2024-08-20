package com.motta.insurance_scheme_service.mapper;

import com.motta.insurance_scheme_service.entity.Scheme;
import com.motta.insurance_scheme_service.model.SchemeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SchemeMapper {

	// Convert Scheme JPA Entity into SchemeDTO
	public SchemeDTO mapToSchemeDTO(Scheme scheme) {
		SchemeDTO schemeDTO = new SchemeDTO();
		BeanUtils.copyProperties(scheme, schemeDTO);
        return schemeDTO;
	}

	// Convert SchemeDTO into Scheme JPA Entity
	public Scheme mapToScheme(SchemeDTO schemeDTO) {
		Scheme scheme = new Scheme();
		BeanUtils.copyProperties(schemeDTO, scheme);
		return scheme;
	}
}
