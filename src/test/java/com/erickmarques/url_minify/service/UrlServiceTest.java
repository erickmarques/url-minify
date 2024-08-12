package com.erickmarques.url_minify.service;

import com.erickmarques.url_minify.controller.dto.MinifyUrlRequest;
import com.erickmarques.url_minify.entity.Url;
import com.erickmarques.url_minify.factory.MinifyUrlRequestFactory;
import com.erickmarques.url_minify.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


/**
 * Classe de teste para {@link UrlService}.
 */
@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private HttpServletRequest servletRequest;

    @InjectMocks
    private UrlService urlService;

    private static final int MIN_ID_LENGTH = 5;
    private static final int MAX_ID_LENGTH = 10;
    private static final String BASE_URL = "http://localhost:8080/";
    private static final String ENDPOINT = "url-minify";

    @Nested
    class UrlMinify {

        private final MinifyUrlRequest request = MinifyUrlRequestFactory.createRequestDefault();

        @Test
        void shouldMinifyUrl() {
            // Arrange
            when(servletRequest.getRequestURL()).thenReturn(new StringBuffer(BASE_URL.concat(ENDPOINT)));
            when(urlRepository.existsById(anyString())).thenReturn(false);

            // Act
            var response = urlService.urlMinify(request, servletRequest);

            // Assert
            verify(urlRepository, times(1)).save(any(Url.class));
            assertThat(response.url()).contains(BASE_URL);
        }

        @Test
        void shouldGenerateUniqueId() {
            // Arrange
            when(servletRequest.getRequestURL()).thenReturn(new StringBuffer(BASE_URL.concat(ENDPOINT)));
            when(urlRepository.existsById(anyString())).thenReturn(false);

            // Act
            var response = urlService.urlMinify(request, servletRequest);

            // Assert
            var id = response.url().replace(BASE_URL, "");
            assertThat(id.length()).isBetween(MIN_ID_LENGTH, MAX_ID_LENGTH);
        }
    }
}
