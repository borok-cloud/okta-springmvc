package org.mars.demookta.springmvc;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Application implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);  // Register your application config
        context.register(SecurityConfig.class); // Register security config
        context.register(Application.class);
        context.setServletContext(servletContext);
        //context.scan("org.mars.demookta.springmvc"); // Change to your package with controllers
        //context.refresh();

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        // Register the Spring Security filter (DelegatingFilterProxy)
        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"));
        securityFilter.addMappingForUrlPatterns(null, false, "/*");  // Map filter to all URL patterns
    }

    public static void main(String[] args) {
        // You can add custom initialization logic here if needed
        System.out.println("Spring Web MVC Application started.");
    }
}

