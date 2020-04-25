package br.com.cashhouse.heroku.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import br.com.cashhouse.transaction.rest.converter.ActionToEnumConverter;
import br.com.cashhouse.transaction.rest.converter.StatusToEnumConverter;
import br.com.cashhouse.util.service.ServiceRequest;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	private ServiceRequest serviceRequest;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(serviceRequest);
		
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new ActionToEnumConverter());
		registry.addConverter(new StatusToEnumConverter());
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}

}
