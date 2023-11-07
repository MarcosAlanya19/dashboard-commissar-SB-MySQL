package com.examen.poo.app.domain.entities;

import com.examen.poo.app.infraestructure.shared.objectBase.Person;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "witness")
@Getter
@Setter
@NoArgsConstructor
public class WitnessEntity extends Person {
  @NotNull(message = "El caso asociado al testigo no puede ser nulo.")
  private Long idCase;

  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinColumn(name = "case_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_witness_case"))
  private CaseEntity caseId;
}
