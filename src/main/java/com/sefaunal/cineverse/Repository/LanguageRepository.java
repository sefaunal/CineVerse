package com.sefaunal.cineverse.Repository;

import com.sefaunal.cineverse.Model.Language;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
public interface LanguageRepository extends MongoRepository<Language, String> {
    Optional<Language> findByID(String ID);

    Optional<Language> findByLanguageName(String languageName);

    @Query("{ '_id': { $in: ?0 } }")
    List<Language> findAllByIdIn(List<String> languageIds);
}