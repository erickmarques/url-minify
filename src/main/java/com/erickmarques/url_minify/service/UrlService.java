package com.erickmarques.url_minify.service;

import com.erickmarques.url_minify.controller.dto.MinifyUrlRequest;
import com.erickmarques.url_minify.controller.dto.MinifyUrlResponse;
import com.erickmarques.url_minify.entity.Url;
import com.erickmarques.url_minify.mapper.UrlMapper;
import com.erickmarques.url_minify.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;


@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    private static final String PATH = "url-minify";
    private static final int MIN_ID_LENGTH = 5;
    private static final int MAX_ID_LENGTH = 10;

    public MinifyUrlResponse urlMinify(MinifyUrlRequest request, HttpServletRequest servletRequest) {

        var id = generateUniqueId();

        save(id, request.url());

        return MinifyUrlResponse
                .builder()
                .shortUrl(generateNewUrl(id, servletRequest))
                .build();
    }

    public HttpHeaders redirect(String id) {
        var url = findById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.getFullUrl()));

        return headers;
    }

    @Transactional(readOnly = true)
    private Url findById(String id){
        return urlRepository.findById(id)
                            .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe URL para o ID informado!"));
    }

    @Transactional(readOnly = true)
    private String generateUniqueId() {
        String id;
        do {
            id = RandomStringUtils.randomAlphanumeric(MIN_ID_LENGTH, MAX_ID_LENGTH);
        } while (urlRepository.existsById(id));
        return id;
    }

    @Transactional
    private void save(String id, String fullUrl){
        var url = UrlMapper.toEntity(id, fullUrl);
        urlRepository.save(url);
    }

    private String generateNewUrl(String id, HttpServletRequest servletRequest){
        return servletRequest.getRequestURL().toString().replace(PATH, id);
    }
}
