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

import com.examen.poo.app.application.services.OfficerService;
import com.examen.poo.app.domain.entities.OfficerEntity;

@RestController
@RequestMapping("/api/officer")
public class OfficerController {
  @Autowired
  OfficerService officerService;

  @PostMapping("")
  public OfficerEntity createOfficer(OfficerEntity object) {
    return officerService.create(object);
  }

  @GetMapping("")
  public List<OfficerEntity> getOfficer() {
    return officerService.getAll();
  }

  @GetMapping("{id}")
  public OfficerEntity getOfficerById(@PathVariable Long id) {
    return officerService.getById(id);
  }

  @PutMapping("{id}")
  public OfficerEntity putOfficerById(@PathVariable Long id, @RequestBody OfficerEntity object) {
    return officerService.updateOfficer(id, object);
  }

  @DeleteMapping("{id}")
  public void deleteOfficerById(@PathVariable Long id) {
    officerService.deleteById(id);
  }
}
