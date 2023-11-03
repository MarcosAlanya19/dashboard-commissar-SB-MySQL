package com.examen.poo.app.infraestructure.shared.abstractBase;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T> extends CrudRepository<T, Long> {}
