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
      return ApiResponseUtil.responseError(HttpStatus.BAD_REQUEST, bindingResult);
    }

    OfficerEntity existingOfficer = officerService.getByEmail(object.getEmail());
    if (existingOfficer != null) {
      return ApiResponseUtil.responseApi(HttpStatus.BAD_REQUEST, "El email ya está registrado.");
    }

    existingOfficer = officerService.getByDni(object.getDni());
    if (existingOfficer != null) {
      return ApiResponseUtil.responseApi(HttpStatus.BAD_REQUEST, "El DNI ya está registrado.");
    }

    OfficerEntity createdOfficer = officerService.create(object);
    return ApiResponseUtil.responseSuccess(HttpStatus.OK, createdOfficer);
  }

  @GetMapping("")
  public ResponseEntity<?> getOfficer() {
    List<OfficerEntity> officers = officerService.getAll();
    if (officers.isEmpty()) {
      return ApiResponseUtil.responseApi(HttpStatus.NOT_FOUND, "No hay oficiales registrados");
    } else {
      return ApiResponseUtil.responseSuccess(HttpStatus.OK, officers);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getOfficerById(@PathVariable Long id) {
    OfficerEntity officer = officerService.getById(id);
    if (officer == null) {
      return ApiResponseUtil.responseApi(HttpStatus.NOT_FOUND,
          "No se encontró un oficial con el ID proporcionado");
    }
    return ApiResponseUtil.responseSuccess(HttpStatus.OK, officer);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> putOfficerById(@PathVariable Long id, @Valid @RequestBody OfficerEntity object,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.responseError(HttpStatus.BAD_REQUEST, bindingResult);
    }

    OfficerEntity updatedOfficer = officerService.updateOfficer(id, object);
    if (updatedOfficer == null) {
      return ApiResponseUtil.responseApi(HttpStatus.NOT_FOUND,
          "No se encontró un oficial con el ID proporcionado");
    }

    OfficerEntity existingOfficerByDNI = officerService.getByDni(object.getDni());
    if (existingOfficerByDNI != null && !existingOfficerByDNI.getId().equals(id)) {
      return ApiResponseUtil.responseApi(HttpStatus.BAD_REQUEST, "El DNI ya está en uso.");
    }

    OfficerEntity existingOfficerByEmail = officerService.getByEmail(object.getEmail());
    if (existingOfficerByEmail != null && !existingOfficerByEmail.getId().equals(id)) {
      return ApiResponseUtil.responseApi(HttpStatus.BAD_REQUEST, "El email ya está en uso.");
    }

    return ApiResponseUtil.responseSuccess(HttpStatus.OK, updatedOfficer);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteOfficerById(@PathVariable Long id) {
    boolean deleted = officerService.deleteById(id);
    if (deleted) {
      return ApiResponseUtil.responseSuccess(HttpStatus.NO_CONTENT, "Oficial eliminado con éxito");
    }
    return ApiResponseUtil.responseApi(HttpStatus.NOT_FOUND,
        "No se encontró un oficial con el ID proporcionado");
  }
}
