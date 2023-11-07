package com.examen.poo.app.domain.entities;

import java.util.HashSet;
import java.util.Set;

import com.examen.poo.app.infraestructure.shared.objectBase.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "officer")
@Getter
@Setter
@NoArgsConstructor
public class OfficerEntity extends Person {
  @NotBlank(message = "El rango del oficial no puede estar en blanco.")
  @Size(max = 50, message = "El rango del oficial no puede tener más de 50 caracteres.")
  private String officerRank;

  @NotBlank(message = "La identificación del oficial no puede estar en blanco.")
  @Size(max = 20, message = "La identificación del oficial no puede tener más de 20 caracteres.")
  @Column(unique = true, length = 20)
  private String identification;

  @OneToMany(mappedBy = "officerId")
  @JsonIgnore
  private Set<CaseEntity> cases = new HashSet<>();
}
