package com.examen.poo.app.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.poo.app.domain.entities.VictimEntity;
import com.examen.poo.app.domain.repositories.VictimRepository;
import com.examen.poo.app.infraestructure.shared.abstractBase.BaseService;

@Service
public class VictimService extends BaseService<VictimEntity> {
  @Autowired
  VictimRepository victimRepository;

  public VictimEntity getByEmail(String email) {
    return victimRepository.findByEmail(email);
  }

  public VictimEntity getByDni(String dni) {
    return victimRepository.findByDni(dni);
  }

  public VictimEntity updateVictim(Long victimId, VictimEntity updatedVictim) {
    Optional<VictimEntity> existingVictim = victimRepository.findById(victimId);

    if (existingVictim.isPresent()) {
      VictimEntity object = existingVictim.get();

      object.setName(updatedVictim.getName());
      object.setAddress(updatedVictim.getAddress());
      object.setEmail(updatedVictim.getEmail());
      object.setDni(updatedVictim.getDni());
      object.setPhone(updatedVictim.getPhone());

      return victimRepository.save(object);
    }
    return null;
  }
}
