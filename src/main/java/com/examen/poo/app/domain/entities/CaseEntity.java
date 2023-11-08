package com.examen.poo.app.domain.entities;

import java.util.Date;

import com.examen.poo.app.infraestructure.shared.abstractBase.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cases")
@Getter
@Setter
@NoArgsConstructor
public class CaseEntity extends BaseEntity {
  @NotBlank(message = "El nombre no puede estar en blanco.")
  @Size(max = 255, message = "El nombre no puede tener más de 255 caracteres.")
  @Column(nullable = false, length = 255)
  private String name;

  @NotBlank(message = "La descripción del caso no puede estar en blanco.")
  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @NotNull(message = "La fecha de inicio no puede ser nula.")
  @Column(nullable = false)
  private Date startDate;

  public enum StatusCase {
    OPEN,
    CLOSE
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private StatusCase status;

  // @NotNull(message = "El oficial del caso no puede ser nulo.")
  // private Long idOfficer;

  // @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
  // CascadeType.MERGE })
  // @JoinColumn(name = "officer_id", referencedColumnName = "id", foreignKey =
  // @ForeignKey(name = "fk_case_officer"))
  // private OfficerEntity officerId;
}
