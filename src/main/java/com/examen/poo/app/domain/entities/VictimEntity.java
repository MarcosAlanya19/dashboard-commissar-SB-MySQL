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
@Table(name = "victim")
@Getter
@Setter
@NoArgsConstructor
public class VictimEntity extends Person {
  @NotNull(message = "El caso asociado a la víctima no puede ser nulo.")
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "case_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_victim_case"))
  private CaseEntity caseId;
}
