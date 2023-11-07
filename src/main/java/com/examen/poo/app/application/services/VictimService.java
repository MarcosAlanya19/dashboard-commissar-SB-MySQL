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

  public VictimEntity updateVictim(Long victimId, VictimEntity updatedVictim) {
    Optional<VictimEntity> existingVictim = victimRepository.findById(victimId);

    if (existingVictim.isPresent()) {
      VictimEntity victim = existingVictim.get();

      victim.setName(updatedVictim.getName());
      victim.setAddress(updatedVictim.getAddress());
      victim.setPhone(updatedVictim.getPhone());

      return victimRepository.save(victim);
    }
    return null;
  }
}
