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

  @Size(max = 50, message = "El contacto no puede tener más de 50 caracteres.")
  @Column(length = 50, unique = true)
  private String email;

  @Size(max = 8, message = "El DNI no puede tener más de 8 caracteres.")
  @Column(length = 8, unique = true)
  private String dni;

  @Size(max = 9, message = "El teléfono no puede tener más de 9 caracteres.")
  @Column(length = 9)
  private String phone;
}
