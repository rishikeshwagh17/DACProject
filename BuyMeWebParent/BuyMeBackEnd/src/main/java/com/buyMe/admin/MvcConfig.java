package com.buyMe.admin;



import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		//to expose directory on the file system aceesible for clients
		String dirName = "user-photos";
		Path userPhotosDir = Paths.get(dirName);
		
		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + userPhotosPath + "/");
		
		//add method to expose directory on file system accessible for client for categories
		String categoryImageDirName = "../category-images";
		Path categoryImagesDir = Paths.get(categoryImageDirName);
		
		String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/category-images/**").addResourceLocations("file:/" + categoryImagesPath + "/");
		
	}
	
}
