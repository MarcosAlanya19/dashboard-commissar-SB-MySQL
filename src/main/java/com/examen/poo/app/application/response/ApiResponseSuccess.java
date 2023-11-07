package com.examen.poo.app.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponseSuccess {
  private int status;
  private Object data;
}
