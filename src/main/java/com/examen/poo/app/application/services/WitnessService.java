package com.examen.poo.app.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.poo.app.domain.entities.WitnessEntity;
import com.examen.poo.app.domain.repositories.WitnessRepository;
import com.examen.poo.app.infraestructure.shared.abstractBase.BaseService;

@Service
public class WitnessService extends BaseService<WitnessEntity> {
  @Autowired
  WitnessRepository witnessRepository;

  public WitnessEntity getByEmail(String email) {
    return witnessRepository.findByEmail(email);
  }

  public WitnessEntity getByDni(String dni) {
    return witnessRepository.findByDni(dni);
  }

  public WitnessEntity updateWitness(Long witnessId, WitnessEntity updatedWitness) {
    Optional<WitnessEntity> existingWitness = witnessRepository.findById(witnessId);

    if (existingWitness.isPresent()) {
      WitnessEntity object = existingWitness.get();

      object.setName(updatedWitness.getName());
      object.setAddress(updatedWitness.getAddress());
      object.setEmail(updatedWitness.getEmail());
      object.setDni(updatedWitness.getDni());
      object.setPhone(updatedWitness.getPhone());

      return witnessRepository.save(object);
    }
    return null;
  }
}
