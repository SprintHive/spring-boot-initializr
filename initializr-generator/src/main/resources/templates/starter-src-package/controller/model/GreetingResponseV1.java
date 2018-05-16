package {{packageName}}.controller.model;

import lombok.Value;
import lombok.Builder;

@Value
@Builder
public class GreetingResponseV1 {
    private String greeting;
}
