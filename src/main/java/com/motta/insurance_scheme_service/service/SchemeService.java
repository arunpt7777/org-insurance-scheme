package com.motta.insurance_scheme_service.service;

import java.util.List;

import com.motta.insurance_scheme_service.model.SchemeDTO;

public interface SchemeService {

	SchemeDTO createScheme(SchemeDTO schemeDTO);

	SchemeDTO retrieveSchemeById(Integer id);

	List<SchemeDTO> retrieveAllSchemes();

	SchemeDTO updateScheme(SchemeDTO schemeDTO);

	void deleteScheme(Integer id);

	List<SchemeDTO> retrieveAllSchemesByType(String schemeType);

	void validateSchemeTO(SchemeDTO schemeDTO);

	Double calculateCommission(Integer schemeId);

	Double calculateShare(Integer schemeId);
}
