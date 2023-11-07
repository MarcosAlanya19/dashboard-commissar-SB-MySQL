package com.examen.poo.app.domain.entities;

import java.util.Date;

import com.examen.poo.app.infraestructure.shared.abstractBase.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evidence")
@Getter
@Setter
@NoArgsConstructor
public class EvidenceEntity extends BaseEntity {
  @NotBlank(message = "La descripción de la evidencia no puede estar en blanco.")
  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @NotNull(message = "La fecha de recolección de evidencia no puede ser nula.")
  @Column(nullable = false)
  private Date collectionDate;

  @NotNull(message = "El caso asociado a la evidencia no puede ser nulo.")
  private Long idCase;

  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinColumn(name = "case_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_evidence_case"))
  private CaseEntity caseId;
}
