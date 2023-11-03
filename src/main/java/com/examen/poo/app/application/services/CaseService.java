package com.examen.poo.app.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.poo.app.domain.entities.CaseEntity;
import com.examen.poo.app.domain.repositories.CaseRepository;
import com.examen.poo.app.infraestructure.shared.abstractBase.BaseService;

@Service
public class CaseService extends BaseService<CaseEntity> {
  @Autowired
  CaseRepository caseRepository;

  public CaseEntity updateCase(Long caseId, CaseEntity updatedCase) {
    Optional<CaseEntity> existingCase = caseRepository.findById(caseId);

    if (existingCase.isPresent()) {
      CaseEntity caseObject = existingCase.get();

      caseObject.setDescription(updatedCase.getDescription());
      caseObject.setStartDate(updatedCase.getStartDate());
      caseObject.setStatus(updatedCase.getStatus());

      return caseRepository.save(caseObject);
    }
    return null;
  }
}
