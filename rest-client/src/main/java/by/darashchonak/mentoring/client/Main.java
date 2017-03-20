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

import by.darashchonak.mentoring.service.User;

public class Main {

    public static void main(String[] args) {

        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget webTarget = client.target("http://localhost:9090/rest").path("user");

        User user = new User();
        user.setName("Meganame");
        user.setEmail("asdasds@emaik.org");
        user.setAvatar("asad");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        System.out.println(json);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.put(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);

        System.out.println(response.getStatus());

        webTarget = client.target("http://localhost:9090/rest").path("user").path("2");
        invocationBuilder = webTarget.request();
        response = invocationBuilder.delete();

        System.out.println(response.getStatus());

    }

}
