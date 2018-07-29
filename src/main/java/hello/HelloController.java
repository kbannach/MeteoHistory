package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(HelloController.BASE_URL)
public class HelloController {

    public static final String BASE_URL = "/service/hello";

    @GetMapping
    public String hello() {
        return "Hello from MeteoHistoryApplication!";
    }
}
