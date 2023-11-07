package com.examen.poo.app.infraestructure.shared.objectBase;

import com.examen.poo.app.infraestructure.shared.abstractBase.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Person extends BaseEntity {
  @NotBlank(message = "El nombre no puede estar en blanco.")
  @Size(max = 255, message = "El nombre no puede tener más de 255 caracteres.")
  @Column(nullable = false, length = 255)
  private String name;

  @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres.")
  @Column(length = 255)
  private String address;

  @Size(max = 20, message = "El contacto no puede tener más de 20 caracteres.")
  @Column(length = 20)
  private String email;

  @Size(max = 20, message = "El DNI no puede tener más de 20 caracteres.")
  @Column(length = 20)
  private String dni;

  @Size(max = 20, message = "El teléfono no puede tener más de 20 caracteres.")
  @Column(length = 20)
  private String phone;
}
