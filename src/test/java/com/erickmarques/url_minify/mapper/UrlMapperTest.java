package com.erickmarques.url_minify.mapper;


import com.erickmarques.url_minify.factory.Constants;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de teste para {@link UrlMapper}.
 */
class UrlMapperTest {

    @Test
    void shouldMapToEntityCorrectly() {
        // Arrange & Act
        var urlEntity = UrlMapper.toEntity(Constants.ID, Constants.LONG_URL);

        // Assert
        assertThat(urlEntity).isNotNull();
        assertThat(urlEntity.getId()).isEqualTo(Constants.ID);
        assertThat(urlEntity.getFullUrl()).isEqualTo(Constants.LONG_URL);
    }

}