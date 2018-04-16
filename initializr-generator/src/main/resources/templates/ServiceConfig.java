package {{packageName}};

import {{packageName}}.domain.{{javaName}};
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class {{javaName}}Config {

    @Bean
    {{javaName}} {{javaNameCamel}}() {
        return new {{javaName}}();
    }
}
