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

  public OfficerEntity getByEmail(String email) {
    return officerRepository.findByEmail(email);
  }

  public OfficerEntity getByDni(String dni) {
    return officerRepository.findByDni(dni);
  }

  public OfficerEntity updateOfficer(Long officerId, OfficerEntity updatedOfficer) {
    Optional<OfficerEntity> existingOfficer = officerRepository.findById(officerId);

    if (existingOfficer.isPresent()) {
      OfficerEntity object = existingOfficer.get();

      object.setName(updatedOfficer.getName());
      object.setAddress(updatedOfficer.getAddress());
      object.setEmail(updatedOfficer.getEmail());
      object.setDni(updatedOfficer.getDni());
      object.setPhone(updatedOfficer.getPhone());
      object.setOfficerRank(updatedOfficer.getOfficerRank());

      return officerRepository.save(object);
    }
    return null;
  }
}
