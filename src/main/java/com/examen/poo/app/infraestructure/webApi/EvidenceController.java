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
import com.examen.poo.app.application.services.EvidenceService;
import com.examen.poo.app.domain.entities.CaseEntity;
import com.examen.poo.app.domain.entities.EvidenceEntity;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/evidence")
public class EvidenceController {
  @Autowired
  EvidenceService evidenceService;

  @Autowired
  CaseService caseService;

  @PostMapping("")
  @Transactional
  public ResponseEntity<?> createEvidence(@Valid @RequestBody EvidenceEntity object, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.createErrorApi(HttpStatus.BAD_REQUEST, bindingResult);
    }

    CaseEntity cases = caseService.getById(object.getIdCase());
    if (cases == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "El caso no existe en la base de datos.");
    }

    object.setCaseId(cases);
    EvidenceEntity createdObject = evidenceService.create(object);
    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, createdObject);
  }

  @GetMapping("")
  public ResponseEntity<?> getEvidence() {
    List<EvidenceEntity> evidenceList = evidenceService.getAll();
    if (evidenceList.isEmpty()) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "No hay evidencia registrada");
    } else {
      return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, evidenceList);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getEvidenceById(@PathVariable Long id) {
    EvidenceEntity evidence = evidenceService.getById(id);
    if (evidence == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,
          "No se encontró evidencia con el ID proporcionado");
    }
    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, evidence);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> putEvidenceById(@PathVariable Long id, @Valid @RequestBody EvidenceEntity object,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.createErrorApi(HttpStatus.BAD_REQUEST, bindingResult);
    }

    EvidenceEntity existingEvidence = evidenceService.getById(id);
    if (existingEvidence == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,
          "No se encontró evidencia con el ID proporcionado.");
    }

    CaseEntity existingCase = caseService.getById(object.getIdCase());
    if (existingCase == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "El caso no existe en la base de datos.");
    }

    EvidenceEntity updatedEvidence = evidenceService.updateEvidence(id, existingEvidence);
    if (updatedEvidence == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "No se pudo actualizar la evidencia");
    }

    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, updatedEvidence);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteEvidenceById(@PathVariable Long id) {
    boolean deleted = evidenceService.deleteById(id);
    if (deleted) {
      return ApiResponseUtil.createSuccessResponse(HttpStatus.NO_CONTENT, "Evidencia eliminada con éxito");
    }
    return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,
        "No se encontró evidencia con el ID proporcionado");
  }
}
