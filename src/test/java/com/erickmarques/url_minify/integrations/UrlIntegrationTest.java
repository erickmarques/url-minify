package com.erickmarques.url_minify.integrations;

import com.erickmarques.url_minify.controller.dto.MinifyUrlRequest;
import com.erickmarques.url_minify.factory.Constants;
import com.erickmarques.url_minify.factory.MinifyUrlRequestFactory;
import com.erickmarques.url_minify.factory.MinifyUrlResponseFactory;
import com.erickmarques.url_minify.service.UrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class UrlMinify {

        @Test
        void shouldMinifyUrl() throws Exception {
            // Arrange
            var request = MinifyUrlRequestFactory.createRequestDefault();
            var response = MinifyUrlResponseFactory.createResponseDefault();

            when(urlService.urlMinify(any(MinifyUrlRequest.class), any(HttpServletRequest.class))).thenReturn(response);

            // Act & Assert
            mockMvc.perform(post(Constants.PATH_MINIFY)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.shortUrl").isNotEmpty())
                    .andExpect(jsonPath("$.shortUrl").value(response.shortUrl()));
        }

        @Test
        void shouldHandleExceptionWhenUrlIsBlank() throws Exception {
            // Arrange
            var requestWithUrlIsBlank = MinifyUrlRequest.builder().build();

            // Act & Assert
            mockMvc.perform(post(Constants.PATH_MINIFY)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestWithUrlIsBlank)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldHandleExceptionWhenUrlIsInvalid() throws Exception {
            // Arrange
            var requestWithUrlIsBlank = MinifyUrlRequest
                                        .builder()
                                        .url("URL_INVALID")
                                        .build();

            // Act & Assert
            mockMvc.perform(post(Constants.PATH_MINIFY)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestWithUrlIsBlank)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class Redirect {

        @Test
        void shouldRedirectToCorrectUrl() throws Exception {
            // Arrange
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, Constants.LONG_URL);

            when(urlService.redirect(anyString())).thenReturn(headers);

            // Act & Assert
            mockMvc.perform(get("/" + Constants.ID))
                    .andExpect(status().isFound())
                    .andExpect(header().string(HttpHeaders.LOCATION, Constants.LONG_URL));
        }

        @Test
        void shouldThrowExceptionWhenUrlNotFound() throws Exception {
            // Arrange
            String id = "nonexistentId";

            when(urlService.redirect(anyString())).thenThrow(
                    new ResponseStatusException(HttpStatus.NOT_FOUND)
            );

            // Act & Assert
            mockMvc.perform(get("/" + id))
                    .andExpect(status().isNotFound());
        }
    }
}
