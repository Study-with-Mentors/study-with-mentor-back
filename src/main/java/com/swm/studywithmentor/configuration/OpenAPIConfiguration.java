package com.swm.studywithmentor.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Study With Mentor API",
                version = "${project.version}"
        ),
        servers = {
                @Server(url = "${server.servlet.context-path}")
        }
)
public class OpenAPIConfiguration {
}
