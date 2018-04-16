package {{packageName}}.controller.model;

import lombok.Value;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class GreetingRequestV1 {
    @NotNull @Size(min = 1)
    private String name;
}
