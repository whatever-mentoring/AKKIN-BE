package com.akkin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .version("alpha")
            .title("아낀거지")
            .description("api 문서");

        Server server = new Server();
        server.setUrl("https://www.seuleuleug.site");

        return new OpenAPI()
            .info(info)
            .servers(List.of(server));
    }
}
