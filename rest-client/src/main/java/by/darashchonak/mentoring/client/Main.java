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

import by.darashchonak.mentoring.service.JaxParamBean;
import by.darashchonak.mentoring.service.User;

public class Main {

    public static void main(String[] args) {

        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);

        WebTarget webTarget = client.target("http://localhost:9090/rest").path("user");

        User user1 = new User();
        user1.setName("John Travolta");
        user1.setEmail("John@email.org");
        user1.setAvatar("asad");

        User user2 = new User();
        user2.setName("Uma Thurman");
        user2.setEmail("Uma@email.org");
        user2.setAvatar("asad");

        Gson gson = new Gson();
        String json1 = gson.toJson(user1);
        String json2 = gson.toJson(user2);

        // Create two users
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.put(Entity.entity(json1, MediaType.APPLICATION_JSON), Response.class);
        System.out.println(response.getStatus() + " " + response.getLocation().toString());
        response = invocationBuilder.put(Entity.entity(json2, MediaType.APPLICATION_JSON), Response.class);
        System.out.println(response.getStatus() + " " + response.getLocation().toString());

        // Get user by Id
        webTarget = client.target("http://localhost:9090/rest").path("user").path("2");
        invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        response = invocationBuilder.get();

        User umaThurman = response.readEntity(User.class);

        // Print users' email
        System.out.println(umaThurman.getEmail());

        // Update email
        JaxParamBean params = new JaxParamBean();
        params.setEmail("uma_therman1970@gmail.com");
        webTarget = client.target("http://localhost:9090/rest").path("user").path("2").path("email");
        invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        response = invocationBuilder.post(Entity.entity(gson.toJson(params), MediaType.APPLICATION_JSON),
                Response.class);
        System.out.println(response.getStatus());

        // Get the same user again
        webTarget = client.target("http://localhost:9090/rest").path("user").path("2");
        invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        response = invocationBuilder.get();
        User umaThurmanUpdated = response.readEntity(User.class);

        // Email has been changed
        System.out.println(umaThurmanUpdated.getEmail());

        // Get another user
        webTarget = client.target("http://localhost:9090/rest").path("user").path("1");
        response = invocationBuilder.get();
        User johnTravolta = response.readEntity(User.class);
        System.out.println(johnTravolta.getName());

        // Delete user
        invocationBuilder = webTarget.request();
        response = invocationBuilder.delete();
        System.out.println(response.getStatus());

        // Get user again - no such user status 204
        response = invocationBuilder.get();
        System.out.println(response.getStatus());

    }

}
