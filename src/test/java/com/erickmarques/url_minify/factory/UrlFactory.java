package com.erickmarques.url_minify.factory;

import com.erickmarques.url_minify.entity.Url;

public class UrlFactory {

    public static Url createUrlDefault(){
        return Url.builder()
                .id(Constants.ID)
                .fullUrl(Constants.LONG_URL)
                .build();
    }
}
