package blogs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class BlogControllerTest {

    @Autowired
    private BlogController controller;

    @Autowired
	private TestRestTemplate restTemplate;

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void canCreateBlog() throws Exception {
	   assertThat(restTemplate.postForEntity(
	            "/blogs",
                new Blog("title", "description"),
                Blog.class
		).getBody().getTitle())
			.contains("title");
	}
}
