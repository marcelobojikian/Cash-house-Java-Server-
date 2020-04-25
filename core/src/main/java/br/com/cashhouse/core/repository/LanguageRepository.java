package br.com.cashhouse.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cashhouse.core.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
	
	Language findByKeyAndLocale(String key, String locale);

}
