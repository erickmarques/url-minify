package com.erickmarques.url_minify.factory;

import com.erickmarques.url_minify.controller.dto.MinifyUrlRequest;

public class MinifyUrlRequestFactory {

    private static final String LONG_URL = "https://www.g1.com.br/some/long/url";

    public static MinifyUrlRequest createRequestDefault(){
        return MinifyUrlRequest
                .builder()
                .url(LONG_URL)
                .build();
    }
}
