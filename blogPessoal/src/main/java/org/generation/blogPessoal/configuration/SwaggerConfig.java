package org.generation.blogPessoal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	/**
	 * Define a package onde estão as classes do tipo @RestController, para que o
	 * Swagger mapeie todas as classes e seus respectivos endpoints para montar a
	 * documentação do projeto.
	 * @author Will
	 * @return Docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("org.generation.blogPessoal.controller"))
				.paths(PathSelectors.any()).build().apiInfo(metadata()).useDefaultResponseMessages(false);
	}

	/**
	 * (.title)Define o titulo da sua aplicação que será exibida na documentação.
	 * (.description)Cria uma descrição para a sua aplicação.
	 * (.version)Define a versão da sua aplicação
	 * (.license)Define o tipo de licença da sua aplicação.
	 * (.licenseUrl)Informa o link de acesso da licença da sua aplicação (geralmente se aplica a licença no
	 *	Github).
	 *	Define os dados para contato com o desenvolvedor inseridos no método contact().
	 * @author Will
	 * @return ApiInfo
	 */
	public static ApiInfo metadata() {
		return new ApiInfoBuilder().title("API - Blog Pessoal").description("Projeto API Spring - Blog Pessoal")
				.version("1.0.0").license("Apache License Version 2.0").licenseUrl("https://github.com/WillianX47/BlogPessoal.git")
				.contact(contact()).build();
	}

	/**
	 * Define os dados do Desenvolvedor (Nome, Website e o E-mail).
	 * @return Contact
	 */
	private static Contact contact() {
		return new Contact("Willian Souza", "https://github.com/WillianX47", "willianx47@gmail.com");
	}

	/**
	 * Define as mensagens personalizadas para os códigos de Resposta do protocolo http (http
	 * Response) para todos os verbos (GET, POST, PUT e DELETE). Cada linha é referente a um
	 * Status Code.
	 * @author Will
	 * @return
	 */
	/*private static List<Response> responseMessage() {
		return new ArrayList<Response>() {
			private static final long serialVersionUID = 1L;
			{
				add(new ResponseBuilder().code("200").description("Sucesso!").build());
				add(new ResponseBuilder().code("201").description("Criado!").build());
				add(new ResponseBuilder().code("400").description("Erro na requisição!").build());
				add(new ResponseBuilder().code("401").description("Não Autorizado!").build());
				add(new ResponseBuilder().code("403").description("Proibido!").build());
				add(new ResponseBuilder().code("404").description("Não Encontrado!").build());
				add(new ResponseBuilder().code("500").description("Erro!").build());
			}
		};
	}*/
}
