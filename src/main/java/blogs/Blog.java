package blogs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class Blog extends ResourceSupport {

    private final String title;
    private final String description;

    @JsonCreator
    public Blog(@JsonProperty("title") String title,
                @JsonProperty("description") String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getdescription() {
        return description;
    }
}
