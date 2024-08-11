package com.erickmarques.url_minify.mapper;

import com.erickmarques.url_minify.entity.Url;

public final class UrlMapper {

    // Construtor privado para evitar instâncias desnecessárias
    private UrlMapper() {}

    public static Url toEntity(String id, String fullUrl){
        return Url.builder()
                .id(id)
                .fullUrl(fullUrl)
                .build();
    }
}
