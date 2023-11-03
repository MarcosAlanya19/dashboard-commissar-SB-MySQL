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

import com.examen.poo.app.application.services.WitnessService;
import com.examen.poo.app.domain.entities.WitnessEntity;

@RestController
@RequestMapping("/api/witness")
public class WitnessController {
  @Autowired
  WitnessService witnessService;

  @PostMapping("/")
  public WitnessEntity createEvidence(WitnessEntity object) {
    return witnessService.create(object);
  }

  @GetMapping("/")
  public List<WitnessEntity> getEvidence() {
    return witnessService.getAll();
  }

  @GetMapping("{id}")
  public WitnessEntity getEvidenceById(@PathVariable Long id) {
    return witnessService.getById(id);
  }

  @PutMapping("{id}")
  public WitnessEntity putCaseById(@PathVariable Long id, @RequestBody WitnessEntity object) {
    return witnessService.updateWitness(id, object);
  }

  @DeleteMapping("{id}")
  public void deleteEvidenceById(@PathVariable Long id) {
    witnessService.deleteById(id);
  }
}
