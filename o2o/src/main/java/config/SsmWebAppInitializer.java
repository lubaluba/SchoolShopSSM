package config;
/**
 * 该类用于代替web.xml
 */
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.google.code.kaptcha.servlet.KaptchaServlet;
public class SsmWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);	//启动servlet
		/**
		 * 配置kaptcha
		 */
		ServletRegistration.Dynamic kaptchaServlet = servletContext.addServlet("kaptcha-servlet", KaptchaServlet.class);//创建一个kaptchaServlet
		kaptchaServlet.addMapping("/kaptcha");	//设置
		//是否有边框
		kaptchaServlet.setInitParameter("kaptcha.border", "no");
		//字体颜色 
		kaptchaServlet.setInitParameter("kaptcha.textproducer.font.color", "red");
		//图片宽度
		kaptchaServlet.setInitParameter("kaptcha.image.width", "135");
		//图片高度
		kaptchaServlet.setInitParameter("kaptcha.image.height", "50");
		//字体颜大小
		kaptchaServlet.setInitParameter("kaptcha.textproducer.font.size", "43");
		//干扰线颜色
		kaptchaServlet.setInitParameter("kaptcha.noise.color", "black");
		//验证码字符个数
		kaptchaServlet.setInitParameter("kaptcha.textproducer.char.length", "5");
		//字体颜大小
		kaptchaServlet.setInitParameter("kaptcha.textproducer.font.names", "Arial");	
	}
	

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String [] {"/"};
	}
	
}
