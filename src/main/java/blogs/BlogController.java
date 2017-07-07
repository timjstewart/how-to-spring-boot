package blogs;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class BlogController {

    @RequestMapping(path = "/blogs", method = RequestMethod.GET)
    public List<Blog> index() {
        List<Blog> blogs = new ArrayList<>();
        blogs.add(new Blog("Bits 'n Bytes", "Random musings of a programmer."));
        return blogs;
    }

    @RequestMapping(path = "/blogs", method = RequestMethod.POST)
    public Blog createBlog(@RequestBody Blog blog) {
        System.out.println(blog);
        return blog;
    }

    @RequestMapping(path = "/blogs/{blogTitle}", method = RequestMethod.GET)
    public Blog getBlogByTitle(@PathVariable String blogTitle) {
        System.out.println(blogTitle);
        return new Blog(blogTitle, "No description");
    }

}
