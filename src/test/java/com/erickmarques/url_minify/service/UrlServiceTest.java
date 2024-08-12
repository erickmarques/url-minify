package com.erickmarques.url_minify.service;

import com.erickmarques.url_minify.controller.dto.MinifyUrlRequest;
import com.erickmarques.url_minify.entity.Url;
import com.erickmarques.url_minify.factory.Constants;
import com.erickmarques.url_minify.factory.MinifyUrlRequestFactory;
import com.erickmarques.url_minify.factory.UrlFactory;
import com.erickmarques.url_minify.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private static final String ENDPOINT = "url-minify";

    @Nested
    class UrlMinify {

        private final MinifyUrlRequest request = MinifyUrlRequestFactory.createRequestDefault();

        @Test
        void shouldMinifyUrl() {
            // Arrange
            when(servletRequest.getRequestURL()).thenReturn(new StringBuffer(Constants.BASE_URL.concat(ENDPOINT)));
            when(urlRepository.existsById(anyString())).thenReturn(false);

            // Act
            var response = urlService.urlMinify(request, servletRequest);

            // Assert
            verify(urlRepository, times(1)).save(any(Url.class));
            assertThat(response.url()).contains(Constants.BASE_URL);
        }

        @Test
        void shouldGenerateUniqueId() {
            // Arrange
            when(servletRequest.getRequestURL()).thenReturn(new StringBuffer(Constants.BASE_URL.concat(ENDPOINT)));
            when(urlRepository.existsById(anyString())).thenReturn(false);

            // Act
            var response = urlService.urlMinify(request, servletRequest);

            // Assert
            var id = response.url().replace(Constants.BASE_URL, "");
            assertThat(id.length()).isBetween(MIN_ID_LENGTH, MAX_ID_LENGTH);
        }
    }

    @Nested
    class Redirect {
        @Test
        void shouldRedirectToCorrectUrl() {
            // Arrange
            var url = UrlFactory.createUrlDefault();

            when(urlRepository.findById(url.getId())).thenReturn(Optional.of(url));

            // Act
            var headers = urlService.redirect(url.getId());

            // Assert
            assertThat(headers.getLocation()).isEqualTo(URI.create(url.getFullUrl()));
        }

        @Test
        void shouldThrowExceptionWhenUrlNotFound() {
            // Arrange
            var id = "nonexistentId";
            when(urlRepository.findById(id)).thenReturn(Optional.empty());

            // Act
            ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
                urlService.redirect(id);
            });

            // Assert
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(exception.getReason()).isEqualTo("Não existe URL para o ID informado!");
        }

    }
}
