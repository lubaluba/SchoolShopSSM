package config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.pre.zlm.o2o.web.interceptor.ShopInterceptor;
import com.pre.zlm.o2o.web.interceptor.ShopPermissionInterceptor;
@Configuration
//@EnableWebMvc//该注解就是开启springMVC的javaConfig,相当于在xml中配置<mvc:annotation-driven>
@ComponentScan("com.pre.zlm.o2o.web")	//启动组件扫描,扫描包下含注解的类
@EnableAspectJAutoProxy
public class WebConfig extends WebMvcConfigurationSupport{
	
	/**
	 * 注册自定义的拦截器
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration interceptor1  = registry.addInterceptor(new ShopInterceptor());
		//该拦截器拦截shopAdminController下面的所有接口
		interceptor1.addPathPatterns("/shopadmin/**");
		InterceptorRegistration interceptor2  = registry.addInterceptor(new ShopPermissionInterceptor());
		interceptor2.addPathPatterns("/shopadmin/updateshop");
		super.addInterceptors(registry);
	}
	
	//spring-mvc jsp视图解析器
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver =new InternalResourceViewResolver();
		resolver.setPrefix("/");	//自动添加前缀,也就是我们访问前端页面时不用写前缀
		resolver.setSuffix(".html");				//自动添加后缀,访问前端页面(jsp文件)不用写文件后置	
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}
	
	/**
	 *	文件上传解析器,如果不配置的话,会导致request里面的参数无法解析
	 */
	@Bean
	protected CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(2097152);
		commonsMultipartResolver.setMaxInMemorySize(2097152);
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		return commonsMultipartResolver;
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}*/
} 
