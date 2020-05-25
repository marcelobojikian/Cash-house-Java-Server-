package br.com.cashhouse.transaction.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.cashhouse.transaction.converter.ActionToEnumConverter;
import br.com.cashhouse.transaction.converter.StatusToEnumConverter;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/webjars/**")
          .addResourceLocations("/webjars/").resourceChain(false);
    }

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new ActionToEnumConverter());
		registry.addConverter(new StatusToEnumConverter());
	}

}
