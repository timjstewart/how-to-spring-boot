package blogs;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class BlogsController {

    @RequestMapping("/blogs")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
