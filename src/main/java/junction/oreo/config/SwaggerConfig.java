package junction.oreo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import junction.oreo.enums.EnvironmentType;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private final ConfigurableEnvironment env;

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("https://donelist.ga");
        Server localhostServer = new Server();
        localhostServer.setUrl("http://localhost:8080");

        // Local 환경
        List<Server> serverList = List.of(localhostServer, server);
        if (!env.getActiveProfiles()[0].equalsIgnoreCase(EnvironmentType.LOCAL.name())) { // Cloud 환경
            serverList = List.of(server, localhostServer);
        }

        return new OpenAPI().servers(serverList);
    }
}
