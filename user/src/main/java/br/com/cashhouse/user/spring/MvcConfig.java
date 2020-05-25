package br.com.cashhouse.user.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingRespectLayoutTitleStrategy;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

//	@Bean
//	public LayoutDialect layoutDialect() {
//		return new LayoutDialect(new GroupingRespectLayoutTitleStrategy());
//	}
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/webjars/**")
          .addResourceLocations("/webjars/").resourceChain(false);
    }

}
