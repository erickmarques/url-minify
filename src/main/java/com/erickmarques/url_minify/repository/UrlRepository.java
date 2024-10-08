package com.erickmarques.url_minify.repository;

import com.erickmarques.url_minify.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, String> {
}
