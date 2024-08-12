package com.erickmarques.url_minify.controller;

import com.erickmarques.url_minify.factory.MinifyUrlRequestFactory;
import com.erickmarques.url_minify.factory.MinifyUrlResponseFactory;
import jakarta.servlet.http.HttpServletRequest;

import com.erickmarques.url_minify.controller.dto.MinifyUrlRequest;
import com.erickmarques.url_minify.controller.dto.MinifyUrlResponse;
import com.erickmarques.url_minify.service.UrlService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * Classe de teste para {@link UrlController}.
 */
@ExtendWith(MockitoExtension.class)
class UrlControllerTest {

    @Mock
    private UrlService urlService;

    @Mock
    private HttpServletRequest servletRequest;

    @InjectMocks
    private UrlController urlController;

    @Nested
    class UrlMinify {

        private final MinifyUrlRequest request = MinifyUrlRequestFactory.createRequestDefault();
        private final MinifyUrlResponse response = MinifyUrlResponseFactory.createResponseDefault();

        @Test
        void shouldReturnMinifiedUrlResponse() {
            // Arrange
            when(urlService.urlMinify(any(MinifyUrlRequest.class), any(HttpServletRequest.class)))
                    .thenReturn(response);

            // Act
            var result = urlController.urlMinify(request, servletRequest);

            // Assert
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).isNotNull();
            assertThat(result.getBody().url()).isEqualTo(response.url());
        }
    }
}
