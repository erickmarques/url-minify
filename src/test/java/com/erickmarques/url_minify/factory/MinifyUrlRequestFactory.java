package com.erickmarques.url_minify.factory;

import com.erickmarques.url_minify.controller.dto.MinifyUrlRequest;

public class MinifyUrlRequestFactory {

    public static MinifyUrlRequest createRequestDefault(){
        return MinifyUrlRequest
                .builder()
                .url(Constants.LONG_URL)
                .build();
    }
}
