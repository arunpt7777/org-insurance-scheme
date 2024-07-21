package com.motta.insurance_scheme_service.controller;

import java.util.List;

import com.motta.insurance_scheme_service.model.SchemeDTO;
import com.motta.insurance_scheme_service.service.SchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class SchemeController {

	@Autowired
	private SchemeService schemeService;

	// create Scheme REST API
	@PostMapping("/schemes")
	public ResponseEntity<SchemeDTO> createScheme(@Valid @RequestBody SchemeDTO schemeDTO) {
		SchemeDTO savedScheme = schemeService.createScheme(schemeDTO);
		return new ResponseEntity<>(savedScheme, HttpStatus.CREATED);
	}

	// Retrieve Scheme by id REST API
	@GetMapping("/schemes/{id}")
	public ResponseEntity<SchemeDTO> retrieveSchemeById(@PathVariable("id") Integer id) {
		SchemeDTO scheme = schemeService.retrieveSchemeById(id);
		return new ResponseEntity<>(scheme, HttpStatus.OK);
	}

	// Retrieve Scheme by id using RequestParam REST API
	// For example, http://localhost:8080/scheme?id=10001
	@GetMapping("/scheme")
	public ResponseEntity<SchemeDTO> retrieveSchemeByIdRequestParam(@RequestParam Integer id) {
		SchemeDTO scheme = schemeService.retrieveSchemeById(id);
		return new ResponseEntity<>(scheme, HttpStatus.OK);
	}

	// Retrieve All Schemes REST API
	@GetMapping("/schemes")
	public ResponseEntity<List<SchemeDTO>> retrieveAllSchemes() {
		List<SchemeDTO> schemes = schemeService.retrieveAllSchemes();
		return new ResponseEntity<>(schemes, HttpStatus.OK);
	}

	// Update Scheme REST API
	@PutMapping("/schemes/{id}")
	public ResponseEntity<SchemeDTO> updateScheme(@PathVariable("id") Integer id, @RequestBody SchemeDTO schemeDTO) {
		schemeDTO.setId(id);
		SchemeDTO updatedScheme = schemeService.updateScheme(schemeDTO);
		return new ResponseEntity<>(updatedScheme, HttpStatus.OK);
	}

	// Delete Scheme REST API
	@DeleteMapping("/schemes/{id}")
	public ResponseEntity<String> deleteScheme(@PathVariable("id") Integer id) {
		schemeService.deleteScheme(id);
		return new ResponseEntity<>("Scheme successfully deleted!", HttpStatus.OK);
	}

	// Retrieve All Schemes by currency REST API
	@GetMapping("/schemesbytype/{schemeType}")
	public ResponseEntity<List<SchemeDTO>> getAllSchemesByCurrency(@PathVariable("schemeType") String schemeType) {
		List<SchemeDTO> schemesByType = schemeService.retrieveAllSchemesByType(schemeType);
		return new ResponseEntity<>(schemesByType, HttpStatus.OK);
	}


}
