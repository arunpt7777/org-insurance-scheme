package com.motta.insurance_scheme_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.motta.insurance_scheme_service.exception.InvalidDateRangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.motta.insurance_scheme_service.entity.Scheme;
import com.motta.insurance_scheme_service.exception.SchemeAlreadyExistsException;
import com.motta.insurance_scheme_service.exception.SchemeNotFoundException;
import com.motta.insurance_scheme_service.mapper.SchemeMapper;
import com.motta.insurance_scheme_service.model.SchemeDTO;
import com.motta.insurance_scheme_service.repository.SchemeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SchemeServiceImplementation implements SchemeService {

	@Autowired
	private SchemeRepository repository;

	// Method to check if SchemeDTO is valid
	public void validate(SchemeDTO schemeDTO) throws InvalidDateRangeException {
		if (schemeDTO.getValidFromDate().after(schemeDTO.getValidToDate())) throw new InvalidDateRangeException("From Date should be less than To Date");
	}

	@Override
	public SchemeDTO createScheme(SchemeDTO schemeDTO) {

		// Check if From and To Dates are valid
		validate(schemeDTO);

		// CHeck if id already exists
		Optional<Scheme> scheme = repository.findById(schemeDTO.getId());
		if (scheme.isPresent()) {
			throw new SchemeAlreadyExistsException("Scheme id = " + schemeDTO.getId() + " already Exists!");
		}

		// Convert SchemeDTO into User JPA Entity
		Scheme newScheme = SchemeMapper.mapToScheme(schemeDTO);
		Scheme savedScheme = repository.save(newScheme);

		// Convert Scheme JPA entity to SchemeDTO
        return SchemeMapper.mapToSchemeDTO(savedScheme);
	}

	@Override
	public SchemeDTO retrieveSchemeById(Integer id) {
		Scheme scheme = repository.findById(id).get();
        return SchemeMapper.mapToSchemeDTO(scheme);
	}

	@Override
	public List<SchemeDTO> retrieveAllSchemes() {
		List<Scheme> schemes = repository.findAll();
		return schemes.stream().map(SchemeMapper::mapToSchemeDTO).collect(Collectors.toList());
	}

	@Override
	public SchemeDTO updateScheme(SchemeDTO schemeDTO) {
		// Check if From and To Dates are valid
		validate(schemeDTO);

		Scheme existingScheme = repository.findById(schemeDTO.getId()).get();
		existingScheme.setSchemeAmount(schemeDTO.getSchemeAmount());
		existingScheme.setSchemeType(schemeDTO.getSchemeType());
		existingScheme.setName(schemeDTO.getName());
		existingScheme.setValidFromDate(schemeDTO.getValidFromDate());
		existingScheme.setValidToDate(schemeDTO.getValidToDate());
		existingScheme.setShare(schemeDTO.getShare());
		existingScheme.setCommission(schemeDTO.getCommission());
		existingScheme.setBrokerage(schemeDTO.getBrokerage());

		Scheme updatedScheme = repository.save(existingScheme);
		return SchemeMapper.mapToSchemeDTO(updatedScheme);
	}

	@Override
	public void deleteScheme(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public List<SchemeDTO> retrieveAllSchemesByType(String schemeType) {
		List<Scheme> schemes = repository.findAll();
		return schemes.stream().filter(scheme -> scheme.getSchemeType().equalsIgnoreCase(schemeType))
				.map(SchemeMapper::mapToSchemeDTO).collect(Collectors.toList());
	}
}
