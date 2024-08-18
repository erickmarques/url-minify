package com.erickmarques.url_minify.factory;

import com.erickmarques.url_minify.controller.dto.MinifyUrlResponse;

public class MinifyUrlResponseFactory {

    public static MinifyUrlResponse createResponseDefault(){
        return MinifyUrlResponse
                .builder()
                .shortUrl(Constants.BASE_URL.concat("/").concat(Constants.ID))
                .build();
    }
}
