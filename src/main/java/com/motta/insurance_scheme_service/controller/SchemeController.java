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

@RestController("scheme controller")
public class SchemeController {

	@Autowired
	private SchemeService schemeService;

	// create Scheme REST API
	@PostMapping("/schemes")
	public ResponseEntity<SchemeDTO> createScheme(@Valid @RequestBody (required = true) SchemeDTO schemeDTO) {
		SchemeDTO savedScheme = schemeService.createScheme(schemeDTO);
		return new ResponseEntity<>(savedScheme, HttpStatus.CREATED);
	}

	// Retrieve Scheme by id REST API
	@GetMapping("/schemes/{id}")
	public ResponseEntity<SchemeDTO> retrieveSchemeById(@PathVariable(value = "id", required = true) Integer id) {
		SchemeDTO scheme = schemeService.retrieveSchemeById(id);
		return new ResponseEntity<>(scheme, HttpStatus.OK);
	}

	// Retrieve Scheme by id using RequestParam REST API
	// For example, http://localhost:8080/scheme?id=10001
	@GetMapping("/scheme")
	public ResponseEntity<SchemeDTO> retrieveSchemeByIdRequestParam(@RequestParam (required = true) Integer id) {
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
	public ResponseEntity<SchemeDTO> updateScheme(@PathVariable("id") Integer id, @RequestBody (required = true) SchemeDTO schemeDTO) {
		SchemeDTO updatedScheme = schemeService.updateScheme(schemeDTO);
		return new ResponseEntity<>(updatedScheme, HttpStatus.OK);
	}

	// Delete Scheme REST API
	@DeleteMapping("/schemes/{id}")
	public ResponseEntity<String> deleteScheme(@PathVariable(value = "id", required = true) Integer id) {
		schemeService.deleteScheme(id);
		return new ResponseEntity<>("Scheme successfully deleted!", HttpStatus.OK);
	}

	// Retrieve All Schemes by currency REST API
	@GetMapping("/schemesbytype/{schemeType}")
	public ResponseEntity<List<SchemeDTO>> getAllSchemesByCurrency(@PathVariable(value = "schemeType", required = true) String schemeType) {
		List<SchemeDTO> schemesByType = schemeService.retrieveAllSchemesByType(schemeType);
		return new ResponseEntity<>(schemesByType, HttpStatus.OK);
	}

	// Retrieve total commission of a schemeIf for an Employee Id
	@GetMapping("/getcommissionforschemeid/{schemeId}")
	public ResponseEntity<Double> retrieveCommissionForScheme(@PathVariable(value = "schemeId", required = true) Integer schemeId) {
		Double totalAmount = schemeService.calculateCommission(schemeId);
		return new ResponseEntity<>(totalAmount, HttpStatus.OK);
	}

	// Retrieve total share of a schemeIf for an Employee Id
	@GetMapping("/getshareforschemeid/{schemeId}")
	public ResponseEntity<Double> retrieveShareForScheme(@PathVariable(value = "schemeId", required = true) Integer schemeId) {
		Double totalAmount = schemeService.calculateShare(schemeId);
		return new ResponseEntity<>(totalAmount, HttpStatus.OK);
	}

}
