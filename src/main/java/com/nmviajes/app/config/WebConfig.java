package com.nmviajes.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
//	@Value("${jobsapp.path.imgs}")
//	private String rutaImages;
//	
//	@Value("${jobsapp.path.cv}")
//	private String rutaCv; 
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) { 
		registry.addResourceHandler("/logos/**").addResourceLocations("file:c:/nmviajes/img-vuelos/");
		//registry.addResourceHandler("/logos/**").addResourceLocations(rutaImages);
		//registry.addResourceHandler("/cv/**").addResourceLocations("file:" + rutaCv);
		registry.addResourceHandler("/hoteles/**").addResourceLocations("file:c:/nmviajes/img-hoteles/");
		}

}
