package {{packageName}}.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class {{javaName}}Test {

    private final {{javaName}} {{javaNameCamel}} = new {{javaName}}();

    @Test
    public void testGreetingGivesCorrectGreeting() {
        assertEquals("Hello Mr. Boaty McBoatface!", {{javaNameCamel}}.greeting("Mr. Boaty McBoatface"));
        assertEquals("Hello Coffee!", {{javaNameCamel}}.greeting("Coffee"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGreetingWithNullNameCausesExpectedException() {
        {{javaNameCamel}}.greeting(null);
    }
}
