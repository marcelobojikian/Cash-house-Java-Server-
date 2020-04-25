package br.com.cashhouse.transaction.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new ActionToEnumConverter());
		registry.addConverter(new StatusToEnumConverter());
	}

}
