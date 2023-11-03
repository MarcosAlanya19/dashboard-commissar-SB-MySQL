package com.examen.poo.app.infraestructure.webApi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.poo.app.application.services.EvidenceService;
import com.examen.poo.app.domain.entities.EvidenceEntity;

@RestController
@RequestMapping("/api/evidence")
public class EvidenceController {
  @Autowired
  EvidenceService evidenceService;

  @PostMapping("/")
  public EvidenceEntity createCase(EvidenceEntity caseCreate) {
    return evidenceService.create(caseCreate);
  }

  @GetMapping("/")
  public List<EvidenceEntity> getCase() {
    return evidenceService.getAll();
  }

  @GetMapping("{id}")
  public EvidenceEntity getCaseById(@PathVariable Long id) {
    return evidenceService.getById(id);
  }

  @PutMapping("{id}")
  public EvidenceEntity putCaseById(@PathVariable Long id, @RequestBody EvidenceEntity evidence) {
    return evidenceService.updateEvidence(id, evidence);
  }

  @DeleteMapping("{id}")
  public void deleteCaseById(@PathVariable Long id) {
    evidenceService.deleteById(id);
  }
}
