# References

- [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)

# Requirements

- You must have maven 3 installed.
- You must have the Java 8 SDK installed.

# Create the pom.xml file

Put the following in your pom.xml file.

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

        <modelVersion>4.0.0</modelVersion>

        <!-- Information about your RESTful API -->
        <groupId>com.timjstewart</groupId>
        <artifactId>cool-rest-api</artifactId>
        <version>0.1.0</version>

        <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>1.5.2.RELEASE</version>
        </parent>

        <dependencies>
            <!-- Main dependency for SpringBoot apps -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>

            <!-- For testing SpringBoot web applications -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-devtools</artifactId>
                <optional>true</optional>
            </dependency>
        </dependencies>

        <properties>
            <java.version>1.8</java.version>
        </properties>

        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                    <!-- When classes on the CLASSPATH change, load the changes. -->
                    <dependencies>
                        <dependency>
                            <groupId>org.springframework</groupId>
                            <artifactId>springloaded</artifactId>
                            <version>1.2.6.RELEASE</version>
                        </dependency>
                    </dependencies>
                </plugin>
            </plugins>
        </build>
    </project>

# Create basic directory structure

    $ mkdir -p src/{test,main}/java/blogs

# Create Controller source file

This should go in src/main/java/blogs/BlogsController.java.

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

# Create the Application source file

This should go in src/main/java/blogs/Application.java

    package blogs;

    import java.util.Arrays;

    import org.springframework.boot.CommandLineRunner;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.Bean;

    @SpringBootApplication
    public class Application {

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }

# Run the application

    $ mvn spring-boot:run

When your application boots up, you will be able to run the following curl command:

    $ curl http://localhost:8080/blogs

# Create your first resource

Put the following code in src/main/java/blogs/Blog.java

    package blogs;

    public class Blog {

        private final String name;
        private final String description;

        public Blog(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getdescription() {
            return description;
        }
    }

# Make the /blogs route return some Blogs

Modify BlogController.java by adding these imports:

    import java.util.List;
    import java.util.ArrayList;

and changing the index method to:

    @RequestMapping("/blogs")
    public List<Blog> index() {
        List<Blog> blogs = new ArrayList<>();
        blogs.add(new Blog("Bits 'n Bytes", "Random musings of a programmer."));
        return blogs;
    }

I had to restart the application before the following curl command returned the correct JSON.

    $ curl localhost:8080/blogs

Resulting JSON:

    [
        {
            "name":"Bits 'n Bytes",
             "description":"Random musings of a programmer."
        }
    ]

# Create a route for creating a Blog

## First modify the Blog resource

Here we have a choice between making our Blog resource a Bean with getters and
setters (which makes our immutable Blog object mutable), or we can add some
attributes to our BlogObject so that Spring/Jackson can deserialize JSON into
Blog objects.  I like immutability more than I mind adding annotations.

Add the following imports to Blog.java

    import com.fasterxml.jackson.annotation.JsonCreator;
    import com.fasterxml.jackson.annotation.JsonProperty;

Then tag the Blog constructor and constructor parameters with JsonCreator and
JsonProperty annotations:

    @JsonCreator
    public Blog(@JsonProperty("name") String name,
                @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }

## Modify the controller

We'll be adding another route that has the same /blogs path so we need to
disambiguate our routes by specifying HTTP methods.  When we add the HTTP
method specification, we need to explicitly name our path property for the
RequestMapping annotation.  Add the imports for those annotations:

    import com.fasterxml.jackson.annotation.JsonCreator;
    import com.fasterxml.jackson.annotation.JsonProperty;

Change the annotation for the index() method to:

    @RequestMapping(path = "/blogs", method = RequestMethod.GET)

Add the new POST route:

    @RequestMapping(path = "/blogs", method = RequestMethod.POST)
    public Blog createBlog(@RequestBody Blog blog) {
        System.out.println("Blog created");
        return blog;
    }