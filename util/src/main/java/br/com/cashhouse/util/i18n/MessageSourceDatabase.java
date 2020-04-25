package br.com.cashhouse.util.i18n;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import br.com.cashhouse.core.model.Language;
import br.com.cashhouse.core.repository.LanguageRepository;

@Component("messageSource")
public class MessageSourceDatabase extends AbstractMessageSource {
	
	private static final String DEFAULT_LOCALE_CODE = "en";

	@Autowired
	private LanguageRepository languageRepository;

	@Override
	protected MessageFormat resolveCode(String key, Locale locale) {
		Language language = languageRepository.findByKeyAndLocale(key,locale.getLanguage());
		if (language == null) {
			language = languageRepository.findByKeyAndLocale(key, DEFAULT_LOCALE_CODE);
			if(language == null) {
				language = new Language();
				language.setId(1l);
				language.setKey(key);
				language.setLocale(locale.getLanguage());
				language.setMessage("not_found");
			}
		}
		return new MessageFormat(language.getMessage(), locale);
	}

}
