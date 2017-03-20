package by.darashchonak.mentoring.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rest")
public class WebService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user")
    public User getIt() {
        return new User("name", "email");
    }

}
