package com.erickmarques.url_minify.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table( name = "urls")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {
    @Id
    private String id;
    private String fullUrl;

    @CreatedDate
    private LocalDateTime createdAt;
}
