package by.darashchonak.mentoring.client;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.Gson;

import by.darashchonak.mentoring.service.common.Field;
import by.darashchonak.mentoring.service.common.ImageUtils;
import by.darashchonak.mentoring.service.common.JaxParamBean;
import by.darashchonak.mentoring.service.common.Serviceable;
import by.darashchonak.mentoring.service.common.User;
import by.darashchonak.mentoring.service.common.Users;

public class RestClient implements Serviceable {

    private final Client client = ClientBuilder.newClient(new ClientConfig());
    private final Gson gson = new Gson();
    private final String path;
    private WebTarget webTarget;
    private Invocation.Builder invocationBuilder;
    private Response response;

    private final String endpoint;

    public RestClient(String endpoint, Class<?> type) {
        this.endpoint = endpoint;
        this.path = type.getSimpleName().toLowerCase();
    }

    @Override
    public List<User> getAll() {

        webTarget = client.target(endpoint).path(path);
        invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        response = invocationBuilder.get();

        Users users = response.readEntity(Users.class);

        return users.getUsersList();
    }

    @Override
    public Long create(User user) {

        try {
            user.setAvatar(ImageUtils.encodeImage(user.getAvatar()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        webTarget = client.target(endpoint).path(path);
        invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        response = invocationBuilder.put(Entity.entity(gson.toJson(user), MediaType.APPLICATION_JSON), Response.class);
        String[] strArray = response.getLocation().toString().split("/");

        return Long.parseLong(strArray[strArray.length - 1]);

    }

    @Override
    public User get(Long id) {
        webTarget = client.target(endpoint).path(path).path(id.toString());
        invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        response = invocationBuilder.get();

        User user = response.readEntity(User.class);

        return user;
    }

    @Override
    public void delete(Long id) {

        webTarget = client.target(endpoint).path(path).path(id.toString());
        invocationBuilder = webTarget.request();
        response = invocationBuilder.delete();
    }

    @Override
    public void updateProperty(Long userId, Field field, String value) {

        JaxParamBean params = new JaxParamBean();
        String fieldName = field.toString();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        // Black Magic of reflection here (((
        Method method = null;
        try {
            method = params.getClass().getMethod(methodName, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        try {
            method.invoke(params, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        webTarget = client.target(endpoint).path(path).path(userId.toString()).path(field.toString());
        invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        response = invocationBuilder.post(Entity.entity(gson.toJson(params), MediaType.APPLICATION_JSON),
                Response.class);
    }

    @Override
    public void update(User user) {

        webTarget = client.target(endpoint).path(path);
        invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        response = invocationBuilder.post(Entity.entity(gson.toJson(user), MediaType.APPLICATION_JSON), Response.class);

    }

}
