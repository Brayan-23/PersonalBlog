package com.generation.blogpessoal.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  OpenAPI personalBlogOpenAPI() {
    return new OpenAPI().info(
        new Info()
            .title("Project Personal Blog")
            .description("Project Personal Blog - Generation Brazil")
            .version("v0.0.1")
            .license(new License()
                .name("Generattion Brazil")
                .url("https://brazil.generation.org/"))
            .contact(new Contact()
                .name("Generation Brazil")
                .url("httpa://github.com/conteudoGeneration")
                .email("conteudogeneration@generation.org"))
    ).externalDocs(new ExternalDocumentation()
        .description("GitHub")
        .url("https://github.com/conteudoGeneration/"));
  }

  @Bean
  OpenApiCustomizer customizerOpenAPI() {
    return openApi -> {
      openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
          .forEach(operation -> {
            ApiResponses apiResponse = operation.getResponses();

            apiResponse.addApiResponse("200", createApiResponse("Success!!"));
            apiResponse.addApiResponse("201", createApiResponse("Object persisted!!"));
            apiResponse.addApiResponse("204", createApiResponse("Object Excluded!!"));
            apiResponse.addApiResponse("400", createApiResponse("Request Error!!"));
            apiResponse.addApiResponse("401", createApiResponse("Unauthorized access!!"));
            apiResponse.addApiResponse("403", createApiResponse("Prohibited access!!"));
            apiResponse.addApiResponse("404", createApiResponse("Object not found!!"));
            apiResponse.addApiResponse("500", createApiResponse("Application Error!!"));
          }));
    };
  }


  private ApiResponse createApiResponse(String message) {
    return new ApiResponse().description(message);
  }

}
