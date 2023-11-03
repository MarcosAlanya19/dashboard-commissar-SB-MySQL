package com.examen.poo.app.infraestructure.webApi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.examen.poo.app.application.services.CaseService;
import com.examen.poo.app.domain.entities.CaseEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/case")
public class CaseController {
  @Autowired
  CaseService caseService;

  @PostMapping("")
  public ResponseEntity<?> createActivityLog(@Valid @RequestBody CaseEntity object, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }
    CaseEntity createdCase = caseService.create(object);
    return ResponseEntity.ok(createdCase);
  }

  @GetMapping("")
  public List<CaseEntity> getCase() {
    return caseService.getAll();
  }

  @GetMapping("{id}")
  public CaseEntity getCaseById(@PathVariable Long id) {
    return caseService.getById(id);
  }

  @PutMapping("{id}")
  public ResponseEntity<?> putCaseById(@PathVariable Long id, @Valid @RequestBody CaseEntity object,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
    }
    CaseEntity updatedCase = caseService.updateCase(id, object);
    return ResponseEntity.ok(updatedCase);
  }

  @DeleteMapping("{id}")
  public void deleteCaseById(@PathVariable Long id) {
    caseService.deleteById(id);
  }
}
