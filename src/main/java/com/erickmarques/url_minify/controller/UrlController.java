package com.erickmarques.url_minify.controller;

import com.erickmarques.url_minify.controller.dto.MinifyUrlRequest;
import com.erickmarques.url_minify.controller.dto.MinifyUrlResponse;
import com.erickmarques.url_minify.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping(value = "/url-minify")
    public ResponseEntity<MinifyUrlResponse> urlMinify(@Valid @RequestBody MinifyUrlRequest request,
                                                        HttpServletRequest servletRequest) {
        return ResponseEntity.ok(urlService.urlMinify(request, servletRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.FOUND)
                             .headers(urlService.redirect(id))
                             .build();
    }
}
