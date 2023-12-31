package com.examen.poo.app.domain.repositories;

import com.examen.poo.app.domain.entities.OfficerEntity;
import com.examen.poo.app.domain.entities.VictimEntity;
import com.examen.poo.app.infraestructure.shared.abstractBase.BaseRepository;

public interface VictimRepository extends BaseRepository<VictimEntity> {
  VictimEntity findByEmail(String email);

  VictimEntity findByDni(String dni);
}
