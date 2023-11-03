package com.examen.poo.app.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.poo.app.domain.entities.OfficerEntity;
import com.examen.poo.app.domain.repositories.OfficerRepository;
import com.examen.poo.app.infraestructure.shared.abstractBase.BaseService;

@Service
public class OfficerService extends BaseService<OfficerEntity> {
  @Autowired
  OfficerRepository officerRepository;

  public OfficerEntity updateOfficer(Long officerId, OfficerEntity updatedOfficer) {
    Optional<OfficerEntity> existingOfficer = officerRepository.findById(officerId);

    if (existingOfficer.isPresent()) {
      OfficerEntity officer = existingOfficer.get();

      officer.setName(updatedOfficer.getName());
      officer.setOfficerRank(updatedOfficer.getOfficerRank());
      officer.setIdentification(updatedOfficer.getIdentification());

      return officerRepository.save(officer);
    }
    return null;
  }
}
