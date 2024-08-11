package com.erickmarques.url_minify.controller.dto;

import lombok.Builder;

@Builder
public record MinifyUrlResponse (String url) {
}
