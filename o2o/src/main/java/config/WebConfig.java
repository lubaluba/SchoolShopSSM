package config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
@Configuration
//@EnableWebMvc//该注解就是开启springMVC的javaConfig,相当于在xml中配置<mvc:annotation-driven>
@ComponentScan("com.pre.zlm.o2o.web")//启动组件扫描,扫描包下含注解的类
public class WebConfig extends WebMvcConfigurationSupport{
	//spring-mvc jsp视图解析器
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver =new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}
/*	@Bean
	protected CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(2097152);
		commonsMultipartResolver.setMaxInMemorySize(0);
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		return commonsMultipartResolver;
	}*/
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}*/
} 
