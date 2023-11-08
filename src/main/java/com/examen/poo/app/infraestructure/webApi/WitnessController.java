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
import com.examen.poo.app.application.services.CaseService;
import com.examen.poo.app.application.services.WitnessService;
import com.examen.poo.app.domain.entities.CaseEntity;
import com.examen.poo.app.domain.entities.WitnessEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/witness")
public class WitnessController {
  @Autowired
  CaseService caseService;

  @Autowired
  WitnessService witnessService;

  @PostMapping("")
  public ResponseEntity<?> createWitness(@Valid @RequestBody WitnessEntity object, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.responseError(HttpStatus.BAD_REQUEST, bindingResult);
    }

    CaseEntity cases = caseService.getById(object.getIdCase());
    if (cases == null) {
      return ApiResponseUtil.responseApi(HttpStatus.NOT_FOUND, "El caso no existe en la base de datos.");
    }

    WitnessEntity existingWitnessByDNI = witnessService.getByDni(object.getDni());
    if (existingWitnessByDNI != null) {
      return ApiResponseUtil.responseApi(HttpStatus.BAD_REQUEST, "El DNI ya está en uso.");
    }

    WitnessEntity existingWitnessByEmail = witnessService.getByEmail(object.getEmail());
    if (existingWitnessByEmail != null) {
      return ApiResponseUtil.responseApi(HttpStatus.BAD_REQUEST, "El email ya está en uso.");
    }

    object.setCaseId(cases);
    WitnessEntity createdObject = witnessService.create(object);
    return ApiResponseUtil.responseSuccess(HttpStatus.OK, createdObject);
  }

  @GetMapping("")
  public ResponseEntity<?> getWitnesses() {
    List<WitnessEntity> witnesses = witnessService.getAll();
    if (witnesses.isEmpty()) {
      return ApiResponseUtil.responseApi(HttpStatus.NOT_FOUND, "No hay testigos registrados");
    } else {
      return ApiResponseUtil.responseSuccess(HttpStatus.OK, witnesses);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getWitnessById(@PathVariable Long id) {
    WitnessEntity witness = witnessService.getById(id);
    if (witness == null) {
      return ApiResponseUtil.responseApi(HttpStatus.NOT_FOUND, "No se encontró el testigo solicitado");
    }
    return ApiResponseUtil.responseSuccess(HttpStatus.OK, witness);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> putWitnessById(@PathVariable Long id, @Valid @RequestBody WitnessEntity object,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.responseError(HttpStatus.BAD_REQUEST, bindingResult);
    }

    WitnessEntity existingWitness = witnessService.getById(id);
    if (existingWitness == null) {
      return ApiResponseUtil.responseApi(HttpStatus.NOT_FOUND,
          "No se encontró testigo con el ID proporcionado.");
    }

    CaseEntity existingCase = caseService.getById(object.getIdCase());
    if (existingCase == null) {
      return ApiResponseUtil.responseApi(HttpStatus.NOT_FOUND, "El caso no existe en la base de datos.");
    }

    WitnessEntity existingWitnessByDNI = witnessService.getByDni(object.getDni());
    if (existingWitnessByDNI != null && !existingWitnessByDNI.getId().equals(id)) {
      return ApiResponseUtil.responseApi(HttpStatus.BAD_REQUEST, "El DNI ya está en uso.");
    }

    WitnessEntity existingVictimByEmail = witnessService.getByEmail(object.getEmail());
    if (existingVictimByEmail != null && !existingVictimByEmail.getId().equals(id)) {
      return ApiResponseUtil.responseApi(HttpStatus.BAD_REQUEST, "El email ya está en uso.");
    }

    WitnessEntity updatedVictim = witnessService.updateWitness(id, existingWitness);
    if (updatedVictim == null) {
      return ApiResponseUtil.responseApi(HttpStatus.INTERNAL_SERVER_ERROR,
          "No se pudo actualizar el testigo");
    }

    return ApiResponseUtil.responseSuccess(HttpStatus.OK, updatedVictim);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteWitnessById(@PathVariable Long id) {
    boolean deleted = witnessService.deleteById(id);
    if (deleted) {
      return ApiResponseUtil.responseSuccess(HttpStatus.NO_CONTENT, "Testigo eliminado con éxito");
    }
    return ApiResponseUtil.responseApi(HttpStatus.NOT_FOUND, "No se encontró el testigo para eliminar");
  }
}
