package com.examen.poo.app.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.poo.app.domain.entities.EvidenceEntity;
import com.examen.poo.app.domain.repositories.EvidenceRepository;
import com.examen.poo.app.infraestructure.shared.abstractBase.BaseService;

@Service
public class EvidenceService extends BaseService<EvidenceEntity> {
  @Autowired
  EvidenceRepository evidenceRepository;

  public EvidenceEntity updateEvidence(Long evidenceId, EvidenceEntity updatedEvidence) {
    Optional<EvidenceEntity> existingEvidence = evidenceRepository.findById(evidenceId);

    if (existingEvidence.isPresent()) {
      EvidenceEntity evidence = existingEvidence.get();

      evidence.setDescription(updatedEvidence.getDescription());
      evidence.setCollectionDate(updatedEvidence.getCollectionDate());

      return evidenceRepository.save(evidence);
    }
    return null;
  }
}
