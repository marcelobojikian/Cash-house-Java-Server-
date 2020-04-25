package br.com.cashhouse.util.service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class I18nService extends AcceptHeaderLocaleResolver {

	private static final List<Locale> LOCALES = Arrays.asList(new Locale("en"), new Locale("es"), new Locale("pt"));

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private MessageSource messageSource;

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String language = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
		log.debug("language parameter: " + language);
		if (language == null || language.isEmpty()) {
			log.debug("language(default): " + Locale.getDefault());
			return Locale.getDefault();
		}
		List<Locale.LanguageRange> list = Locale.LanguageRange.parse(language);
		Locale locale = Locale.lookup(list, LOCALES);
		log.debug("Locale: " + locale);
		return locale;
	}

	public String getMessage(String code) {
		return messageSource.getMessage(code, null, resolveLocale(request));
	}

	public String getMessage(String code, Object... args) {
		return messageSource.getMessage(code, args, code, resolveLocale(request));
	}

}
