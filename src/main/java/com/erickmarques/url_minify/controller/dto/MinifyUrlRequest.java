package com.erickmarques.url_minify.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record MinifyUrlRequest(@URL(message = "Favor informar uma URL válida!") @NotBlank(message = "Favor informar o destino!")  String url) {
}
