package com.examen.poo.app.infraestructure.webApi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.poo.app.application.response.ApiResponseUtil;
import com.examen.poo.app.application.services.OfficerService;
import com.examen.poo.app.domain.entities.OfficerEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/officer")
public class OfficerController {
  @Autowired
  OfficerService officerService;

  @PostMapping("")
  public ResponseEntity<?> createOfficer(@Valid @RequestBody OfficerEntity object, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.createErrorApi(HttpStatus.BAD_REQUEST, bindingResult);
    }

    OfficerEntity createdOfficer = officerService.create(object);
    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, createdOfficer);
  }

  @GetMapping("")
  public ResponseEntity<?> getOfficer() {
    List<OfficerEntity> officers = officerService.getAll();
    if (officers.isEmpty()) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "No hay oficiales registrados");
    } else {
      return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, officers);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getOfficerById(@PathVariable Long id) {
    OfficerEntity officer = officerService.getById(id);
    if (officer == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,
          "No se encontró un oficial con el ID proporcionado");
    }
    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, officer);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> putOfficerById(@PathVariable Long id, @Valid @RequestBody OfficerEntity object,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.createErrorApi(HttpStatus.BAD_REQUEST, bindingResult);
    }

    OfficerEntity updatedOfficer = officerService.updateOfficer(id, object);
    if (updatedOfficer == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,
          "No se encontró un oficial con el ID proporcionado");
    }
    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, updatedOfficer);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteOfficerById(@PathVariable Long id) {
    boolean deleted = officerService.deleteById(id);
    if (deleted) {
      return ApiResponseUtil.createSuccessResponse(HttpStatus.NO_CONTENT, "Oficial eliminado con éxito");
    }
    return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,
        "No se encontró un oficial con el ID proporcionado");
  }
}
