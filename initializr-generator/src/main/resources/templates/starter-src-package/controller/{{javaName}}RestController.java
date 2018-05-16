package {{packageName}}.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import {{packageName}}.domain.{{javaName}};
import {{packageName}}.controller.model.GreetingRequestV1;
import {{packageName}}.controller.model.GreetingResponseV1;

import javax.validation.Valid;

@Slf4j
@RestController
public class {{javaName}}RestController {

    @Autowired
    {{javaName}} {{javaNameCamel}};

    @RequestMapping(method = RequestMethod.POST, value = "/greeting")
    public GreetingResponseV1 greeting(@Valid @RequestBody GreetingRequestV1 request) {
        log.debug("Received a GreetingRequest");

        String greeting = {{javaNameCamel}}.greeting(request.getName());

        return GreetingResponseV1.builder()
                .greeting(greeting)
                .build();
    }
}
