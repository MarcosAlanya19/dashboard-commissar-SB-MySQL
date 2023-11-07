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
import com.examen.poo.app.application.services.VictimService;
import com.examen.poo.app.domain.entities.CaseEntity;
import com.examen.poo.app.domain.entities.VictimEntity;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/victim")
public class VictimController {
  @Autowired
  VictimService victimService;

  @Autowired
  CaseService caseService;

  @PostMapping("")
  @Transactional
  public ResponseEntity<?> createVictim(@Valid @RequestBody VictimEntity object, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.createErrorApi(HttpStatus.BAD_REQUEST, bindingResult);
    }

    CaseEntity cases = caseService.getById(object.getIdCase());
    if (cases == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "El caso no existe en la base de datos.");
    }

    object.setCaseId(cases);
    VictimEntity createdObject = victimService.create(object);
    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, createdObject);
  }

  @GetMapping("")
  public ResponseEntity<?> getVictims() {
    List<VictimEntity> victims = victimService.getAll();
    if (victims.isEmpty()) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "No hay víctimas registradas");
    } else {
      return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, victims);
    }
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getVictimById(@PathVariable Long id) {
    VictimEntity victim = victimService.getById(id);
    if (victim == null) {
      return ResponseEntity.notFound().build();
    }
    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, victim);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> putVictimById(@PathVariable Long id, @Valid @RequestBody VictimEntity object,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ApiResponseUtil.createErrorApi(HttpStatus.BAD_REQUEST, bindingResult);
    }

    VictimEntity existingVictim = victimService.getById(id);
    if (existingVictim == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND,
          "No se encontró evidencia con el ID proporcionado.");
    }

    CaseEntity existingCase = caseService.getById(object.getIdCase());
    if (existingCase == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "El caso no existe en la base de datos.");
    }

    VictimEntity updatedVictim = victimService.updateVictim(id, existingVictim);
    if (updatedVictim == null) {
      return ApiResponseUtil.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
          "No se pudo actualizar la victima");
    }

    return ApiResponseUtil.createSuccessResponse(HttpStatus.OK, updatedVictim);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteVictimById(@PathVariable Long id) {
    boolean deleted = victimService.deleteById(id);
    if (deleted) {
      return ApiResponseUtil.createSuccessResponse(HttpStatus.NO_CONTENT, "Víctima eliminada con éxito");
    }
    return ApiResponseUtil.createErrorResponse(HttpStatus.NOT_FOUND, "No se pudo encontrar la víctima para eliminar");
  }
}
