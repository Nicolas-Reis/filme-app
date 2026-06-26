package com.grupo.catalogoFilme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	private static final String SECURITY_SCHEME = "bearerAuth";

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Catálogo de Filmes API")
						.version("v1")
						.description("API de avaliação de filmes. Faça login em /auth/login, copie o token e informe-o no botão Authorize."))
				.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME))
				.components(new Components().addSecuritySchemes(SECURITY_SCHEME,
						new SecurityScheme()
								.name(SECURITY_SCHEME)
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
								.description("Informe apenas o token JWT (sem o prefixo 'Bearer ').")));
	}
}
