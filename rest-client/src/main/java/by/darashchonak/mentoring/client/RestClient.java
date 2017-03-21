package by.darashchonak.mentoring.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.Gson;

import by.darashchonak.mentoring.service.common.User;

public class RestClient {

    private final Client client = ClientBuilder.newClient(new ClientConfig());
    private final Gson gson = new Gson();
    private WebTarget webTarget;
    private Response response;

    public Response createUser(User user) {

        webTarget = client.target("http://localhost:9090/rest").path("user");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        response = invocationBuilder.put(Entity.entity(gson.toJson(user), MediaType.APPLICATION_JSON), Response.class);

        return response;

    }

}
