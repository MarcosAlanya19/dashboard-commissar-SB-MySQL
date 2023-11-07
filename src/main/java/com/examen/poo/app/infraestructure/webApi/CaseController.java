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
import com.examen.poo.app.application.services.OfficerService;
import com.examen.poo.app.domain.entities.CaseEntity;
import com.examen.poo.app.domain.entities.OfficerEntity;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/case")
public class CaseController {
  @Autowired
  CaseService caseService;
  @Autowired
  OfficerService officerService;

  @PostMapping("")
  @Transactional
  public ResponseEntity<?> createCase(@Valid @RequestBody CaseEntity caseEntity, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.createErrorApi(HttpStatus.BAD_REQUEST, bindingResult);
    }

    OfficerEntity officer = officerService.getById(caseEntity.getIdOfficer());
    if (officer == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "El oficial no existe en la base de datos.");
    }

    caseEntity.setOfficerId(officer);
    CaseEntity createdCase = caseService.create(caseEntity);
    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, createdCase);
  }

  @GetMapping("")
  public ResponseEntity<?> getCase() {
    List<CaseEntity> cases = caseService.getAll();
    if (cases.isEmpty()) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "No hay casos registrados");
    } else {
      return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, cases);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getCaseById(@PathVariable Long id) {
    CaseEntity caseEntity = caseService.getById(id);
    if (caseEntity == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,
          "No se encontró un caso con el ID proporcionado");
    }
    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, caseEntity);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> putCaseById(@PathVariable Long id, @Valid @RequestBody CaseEntity object,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.createErrorApi(HttpStatus.BAD_REQUEST, bindingResult);
    }

    CaseEntity existingCase = caseService.getById(id);
    if (existingCase == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,
          "No se encontró un caso con el ID proporcionado");
    }

    CaseEntity updatedCase = caseService.updateCase(id, object);
    if (updatedCase == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo actualizar el caso");
    }

    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, updatedCase);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteCaseById(@PathVariable Long id) {
    boolean success = caseService.deleteById(id);
    if (success) {
      return ApiResponseUtil.createSuccessResponse(HttpStatus.NO_CONTENT, "Caso eliminado con éxito");
    }
    return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "No se encontró un caso con el ID proporcionado");
  }
}
