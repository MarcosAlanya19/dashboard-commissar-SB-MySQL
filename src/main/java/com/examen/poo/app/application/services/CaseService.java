package com.examen.poo.app.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.poo.app.domain.entities.CaseEntity;
import com.examen.poo.app.domain.repositories.CaseRepository;

@Service
public class CaseService {
  @Autowired
  CaseRepository caseRepository;

  public List<CaseEntity> getAll() {
    List<CaseEntity> CaseEntity = (List<CaseEntity>) caseRepository.findAll();
    return CaseEntity;
  }

  public CaseEntity getById(Long id) {
    Optional<CaseEntity> optional = caseRepository.findById(id);
    return optional.orElse(null);
  }

  public CaseEntity create(CaseEntity Object) {
    return caseRepository.save(Object);
  }

  public boolean deleteById(Long id) {
    Optional<CaseEntity> optionalStudent = caseRepository.findById(id);
    if (optionalStudent.isPresent()) {
      caseRepository.delete(optionalStudent.get());
      return true;
    }
    return false;
  }

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
