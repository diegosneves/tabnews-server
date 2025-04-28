package br.com.diegosneves.tabnewsserver.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("development")
public class OpenApiConfig {

    /**
     * Retorna uma instância personalizada do {@link OpenAPI}.
     * <p>
     * Este método configura informações detalhadas e tags que serão exibidas na documentação Swagger/OpenAPI.
     *
     * @return a instância personalizada do {@link OpenAPI}
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(getInfo());
    }


    /**
     * Busca informações sobre a API.
     *
     * @return Uma instância da classe {@link Info} contendo versão, título, descrição e detalhes de contato
     */
    private Info getInfo() {
        return new Info()
                .version("v1.0.0")
                .title("TabNews API")
                .description("TabNews é um projeto em desenvolvimento voltado para a agregação de notícias.")
                .contact(new Contact().email("neves.diegoalex@outlook.com").url("https://github.com/diegosneves/tabnews-server").name("Diego Neves"));
    }

}
