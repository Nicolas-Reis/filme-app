package com.grupo.catalogoFilme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

	@Bean
	public Cloudinary cloudinary(@Value("${CLOUDINARY_URL}") String url) {
		return new Cloudinary(url);
	}
}
