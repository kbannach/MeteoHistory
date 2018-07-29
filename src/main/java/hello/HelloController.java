package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(HelloController.BASE_URL)
public class HelloController {

    public static final String BASE_URL = "/service/hello";
    public static final String HELLO_URL = "/hello";

    @GetMapping(HELLO_URL)
    public String hello() {
        return "Hello from MeteoHistoryApplication!";
    }
}
