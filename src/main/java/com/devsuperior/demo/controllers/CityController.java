package com.devsuperior.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.services.CityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cities")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	@GetMapping
	public ResponseEntity<List<CityDTO>> getAllCities() {
		return new ResponseEntity<>(cityService.findAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<CityDTO> insert(@Valid @RequestBody CityDTO dto) {
		dto = cityService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
}
