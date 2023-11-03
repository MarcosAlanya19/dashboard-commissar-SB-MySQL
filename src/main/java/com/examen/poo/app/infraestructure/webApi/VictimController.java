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

import com.examen.poo.app.application.services.VictimService;
import com.examen.poo.app.domain.entities.VictimEntity;

@RestController
@RequestMapping("/api/victim")
public class VictimController {
  @Autowired
  VictimService victimService;

  @PostMapping("/")
  public VictimEntity createEvidence(VictimEntity object) {
    return victimService.create(object);
  }

  @GetMapping("/")
  public List<VictimEntity> getEvidence() {
    return victimService.getAll();
  }

  @GetMapping("{id}")
  public VictimEntity getEvidenceById(@PathVariable Long id) {
    return victimService.getById(id);
  }

  @PutMapping("{id}")
  public VictimEntity putCaseById(@PathVariable Long id, @RequestBody VictimEntity object) {
    return victimService.updateVictim(id, object);
  }

  @DeleteMapping("{id}")
  public void deleteEvidenceById(@PathVariable Long id) {
    victimService.deleteById(id);
  }
}
