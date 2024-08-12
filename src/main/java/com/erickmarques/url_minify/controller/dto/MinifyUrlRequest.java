package com.erickmarques.url_minify.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MinifyUrlRequest(@NotBlank(message = "Favor informar o destino!")  String url) {
}
