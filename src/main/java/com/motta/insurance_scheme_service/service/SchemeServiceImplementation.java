package com.motta.insurance_scheme_service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.motta.insurance_scheme_service.exception.InvalidDateRangeException;
import com.motta.insurance_scheme_service.exception.InvalidSchemeException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class SchemeServiceImplementation implements SchemeService {

	private static final Logger log = LoggerFactory.getLogger(SchemeServiceImplementation.class);

	@Value("${scheme.id.initalValue}")
	private Integer initalValueOfPrimaryKey;

	@Autowired
	private SchemeRepository repository;

	@Override
	public SchemeDTO createScheme(SchemeDTO schemeDTO) {

		// Check if From and To Dates are valid
		validateSchemeTO(schemeDTO);

		// CHeck if id already exists
		Optional<Scheme> scheme = repository.findById(schemeDTO.getId());
		log.error("Scheme id = {} not found. Please enter different id", schemeDTO.getId());
		if (scheme.isPresent()) {
			throw new SchemeAlreadyExistsException("Scheme id = " + schemeDTO.getId() + " already Exists!");
		}

		// Convert SchemeDTO into User JPA Entity
		Scheme newScheme = SchemeMapper.mapToScheme(schemeDTO);
		Scheme savedScheme = repository.save(newScheme);
		log.info("Scheme id = {} persisted", schemeDTO.getId());

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
		validateSchemeTO(schemeDTO);

		Scheme existingScheme = repository.findById(schemeDTO.getId()).orElse(new Scheme(schemeDTO.getId(), schemeDTO.getName(), schemeDTO.getValidFromDate(), schemeDTO.getValidToDate(), schemeDTO.getSchemeAmount(), schemeDTO.getSchemeType(), schemeDTO.getShare(), schemeDTO.getCommission(), schemeDTO.getBrokerage() ));
		existingScheme.setSchemeAmount(schemeDTO.getSchemeAmount());
		existingScheme.setSchemeType(schemeDTO.getSchemeType());
		existingScheme.setName(schemeDTO.getName());
		existingScheme.setValidFromDate(schemeDTO.getValidFromDate());
		existingScheme.setValidToDate(schemeDTO.getValidToDate());
		existingScheme.setShare(schemeDTO.getShare());
		existingScheme.setCommission(schemeDTO.getCommission());
		existingScheme.setBrokerage(schemeDTO.getBrokerage());

		Scheme updatedScheme = repository.save(existingScheme);
		log.error("Updating scheme id = {} has failed.", existingScheme.getId());
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

	// Method to check if SchemeDTO is valid
	@Override
	public void validateSchemeTO(SchemeDTO schemeDTO) {

		if (schemeDTO.getValidFromDate().after(schemeDTO.getValidToDate())) {
			throw new InvalidDateRangeException("From Date should be less than To Date");
		}

		if (schemeDTO.getId()==null) {
			throw new InvalidSchemeException("Scheme Id is mandatory");
		}

		if (schemeDTO.getName()==null) {
			throw new InvalidSchemeException("Scheme Name is mandatory");
		}

		if (schemeDTO.getId()<initalValueOfPrimaryKey) {
			throw new InvalidSchemeException("Scheme Id must not be less than the initial value of: " + initalValueOfPrimaryKey);
		}
		if (schemeDTO.getValidFromDate()==null) {
			throw new InvalidSchemeException("Valid From Date is mandatory");
		}

		if (schemeDTO.getValidToDate()==null) {
			throw new InvalidSchemeException("Valid To Date is mandatory");
		}
		if (schemeDTO.getSchemeType()==null) {
			throw new InvalidSchemeException("Scheme Type is mandatory");
		}
		if (schemeDTO.getSchemeAmount()==null) {
			throw new InvalidSchemeException("Scheme Amount is mandatory");
		}

		if (schemeDTO.getShare()==null) {
			throw new InvalidSchemeException("Share is mandatory");
		}
		if (schemeDTO.getCommission()==null) {
			throw new InvalidSchemeException("Commission  is mandatory");
		}
	}

}
