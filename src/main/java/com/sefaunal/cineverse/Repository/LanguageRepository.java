package com.sefaunal.cineverse.Repository;

import com.sefaunal.cineverse.Model.Language;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
public interface LanguageRepository extends MongoRepository<Language, String> {
}