package com.erickmarques.url_minify.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record MinifyUrlRequest(@NotBlank(message = "Favor informar o destino!")  String url) {

}
