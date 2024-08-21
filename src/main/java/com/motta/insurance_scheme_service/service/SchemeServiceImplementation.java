package com.motta.insurance_scheme_service.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.motta.insurance_scheme_service.util.SchemeConstants.*;
import com.motta.insurance_scheme_service.exception.InvalidDateRangeException;
import com.motta.insurance_scheme_service.exception.InvalidSchemeException;
import com.motta.insurance_scheme_service.model.AssociationDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.motta.insurance_scheme_service.entity.Scheme;
import com.motta.insurance_scheme_service.exception.SchemeAlreadyExistsException;
import com.motta.insurance_scheme_service.mapper.SchemeMapper;
import com.motta.insurance_scheme_service.model.SchemeDTO;
import com.motta.insurance_scheme_service.repository.SchemeRepository;

import jakarta.transaction.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class SchemeServiceImplementation implements SchemeService {

	private static final Logger logger = LoggerFactory.getLogger(SchemeServiceImplementation.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SchemeMapper schemeMapper;

	@Value("${scheme.id.initialValue}")
	private Integer initialValueOfPrimaryKey;

	@Autowired
	private SchemeRepository repository;

	@Transactional
	@Override
	public SchemeDTO createScheme(SchemeDTO schemeDTO) {

		// Check if From and To Dates are valid
		validateSchemeTO(schemeDTO);

		// CHeck if id already exists
		Scheme scheme = repository.findById(schemeDTO.getId()).orElse(null);
		if (scheme == null) {
			throw new SchemeAlreadyExistsException(EXCEPTION_MESSAGE_SCHEME_NOT_FOUND);
		}

		Scheme newScheme = schemeMapper.mapToScheme(schemeDTO);
		Scheme savedScheme = repository.save(newScheme);
		logger.info(LOG_MESSAGE_SCHEME_PERSISTED, schemeDTO.getId());

		// Convert Scheme JPA entity to SchemeDTO
        return schemeMapper.mapToSchemeDTO(savedScheme);
	}

	@Override
	public SchemeDTO retrieveSchemeById(Integer id) {
		Scheme scheme = repository.findById(id).orElse(null);
        assert scheme != null;
		SchemeDTO schemeDTO = new SchemeDTO();
		BeanUtils.copyProperties(scheme, schemeDTO);
        return schemeDTO;
	}

	@Override
	public List<SchemeDTO> retrieveAllSchemes() {
		List<Scheme> schemes = repository.findAll();
		return schemes.stream().map(schemeMapper::mapToSchemeDTO).toList();

	}

	@Transactional
	@Override
	public SchemeDTO updateScheme(SchemeDTO schemeDTO) {
		// Check if From and To Dates are valid
		validateSchemeTO(schemeDTO);

		Scheme existingScheme = repository.findById(schemeDTO.getId()).orElse(new Scheme());
		BeanUtils.copyProperties(existingScheme, schemeDTO);
		Scheme updatedScheme = repository.save(existingScheme);
		logger.error(LOG_MESSAGE_SCHEME_UPDATE_FAILED, existingScheme.getId());
		return schemeMapper.mapToSchemeDTO(updatedScheme);
	}

	@Transactional
	@Override
	public void deleteScheme(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public List<SchemeDTO> retrieveAllSchemesByType(String schemeType) {
		List<Scheme> schemes = repository.findAll();
		return schemes.stream().filter(scheme -> scheme.getSchemeType().equalsIgnoreCase(schemeType))
				.map(schemeMapper::mapToSchemeDTO).toList();
	}

	// Method to check if SchemeDTO is valid
	@Override
	public void validateSchemeTO(SchemeDTO schemeDTO) {

		if (schemeDTO.getValidFromDate().after(schemeDTO.getValidToDate())) {
			throw new InvalidDateRangeException(EXCEPTION_MESSAGE_INVALID_FROM_DATE);
		}

		if (schemeDTO.getId() == 0) {
			throw new InvalidSchemeException(EXCEPTION_MESSAGE_SCHEME_ID_IS_MANDATORY);
		}

		if (schemeDTO.getName()==null) {
			throw new InvalidSchemeException(EXCEPTION_MESSAGE_SCHEME_NAME_IS_MANDATORY);
		}

		if (schemeDTO.getId()< initialValueOfPrimaryKey) {
			throw new InvalidSchemeException(EXCEPTION_MESSAGE_SCHEME_ID_LESS_THAN_INITIAL_VALUE +  initialValueOfPrimaryKey);
		}

		if (schemeDTO.getValidToDate()==null) {
			throw new InvalidSchemeException(EXCEPTION_MESSAGE_TO_DATE_IS_MANDATORY);
		}
		if (schemeDTO.getSchemeType()==null) {
			throw new InvalidSchemeException(EXCEPTION_MESSAGE_SCHEME_TYPE_IS_MANDATORY);
		}
		if (schemeDTO.getSchemeAmount()==0.0) {
			throw new InvalidSchemeException(EXCEPTION_MESSAGE_SCHEME_AMOUNT_IS_MANDATORY);
		}

		if (schemeDTO.getShare() == 0.0) {
			throw new InvalidSchemeException(EXCEPTION_MESSAGE_SHARE_IS_MANDATORY);
		}
		if (schemeDTO.getCommission() == 0.0) {
			throw new InvalidSchemeException(EXCEPTION_MESSAGE_COMMISSION_IS_MANDATORY);
		}
	}

	public List<AssociationDTO> fetchAssociations (int schemeId) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		// Check if scheme exists or not
		SchemeDTO schemeDTO = retrieveSchemeById(schemeId);
		if(schemeDTO == null) {
			throw new InvalidSchemeException(EXCEPTION_MESSAGE_SCHEME_NOT_FOUND);
		}

		// Get all associations for the scheme id
		ResponseEntity<AssociationDTO[]> response = restTemplate.exchange(URL_GET_ASSOCIATIONS_BY_SCHEME_ID + schemeId, HttpMethod.GET, entity, AssociationDTO[].class);
		AssociationDTO[] associationDTOS = response.getBody();

		if (associationDTOS == null) {
			throw new InvalidSchemeException(EXCEPTION_MESSAGE_SCHEME_NOT_FOUND);
		}
		return  Arrays.stream(associationDTOS).toList();
	}

	@Override
	public double calculateCommission(Integer schemeId) {
		double totalCommission = 0.0;

		List<AssociationDTO> associationDTOS = fetchAssociations(schemeId);
		List<Double> commissionList = new ArrayList<>();

		for (AssociationDTO associationDTO: associationDTOS) {
			SchemeDTO associatedSchemeDTO = retrieveSchemeById(associationDTO.getSchemeId());
			if(associatedSchemeDTO == null) {
				throw new InvalidSchemeException(EXCEPTION_MESSAGE_SCHEME_NOT_FOUND + associationDTO.getSchemeId());
			}
			commissionList.add(associatedSchemeDTO.getCommission());
		}
		totalCommission = commissionList.stream().max(Double::compare).orElse(0.0);
		return totalCommission;
	}

	@Override
	public double calculateShare(Integer schemeId) {
		double totalShare = 0.0;


		List<AssociationDTO> associationDTOS = fetchAssociations(schemeId);
		List<Double> shareList = new ArrayList<>();

		for (AssociationDTO associationDTO: associationDTOS) {
			SchemeDTO associatedSchemeDTO = retrieveSchemeById(associationDTO.getSchemeId());
			if(associatedSchemeDTO == null) {
				throw new InvalidSchemeException(EXCEPTION_MESSAGE_SCHEME_NOT_FOUND + associationDTO.getSchemeId());
			}
			shareList.add(associatedSchemeDTO.getShare());
		}
		totalShare = shareList.stream().min(Double::compare).orElse(0.0);
		return  totalShare;
	}
}
