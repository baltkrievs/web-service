package by.darashchonak.mentoring.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import by.darashchonak.mentoring.service.common.JaxParamBean;
import by.darashchonak.mentoring.service.common.Storable;
import by.darashchonak.mentoring.service.common.User;
import by.darashchonak.mentoring.service.common.Users;

@Path("/rest")
public class WebService {

    private final Storable storage = Storage.getInstance();

    /**
     *
     * @return list of all users available
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user")
    public Users getUsers() {

        Users usersList = new Users();
        usersList.setUsersList(storage.getAll());

        return usersList;
    }

    /**
     * Creates a new user
     *
     * @param user
     * @return
     * @throws URISyntaxException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/user")
    public Response putUser(User user) throws URISyntaxException {
        Long id = storage.create(user);
        return Response.created(new URI("/rest/user/" + id)).build();
    }

    /**
     * Updates existing user
     *
     * @param user
     * @return
     * @throws URISyntaxException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/user")
    public Response updateUser(User user) throws URISyntaxException {

        if (user.getId() == null) {
            return Response.status(400).entity("No user ID found. Uset PUT method instead").build();
        }

        storage.update(user);
        return Response.ok().contentLocation(new URI("/rest/user/" + user.getId())).build();

    }

    /**
     *
     * @param id
     * @return Returns users by it's id or 204 No Content if user doesn't exist
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{id}")
    public User getUserById(@PathParam("id") String id) {

        return storage.get(Long.parseLong(id));
    }

    /**
     * Removes user
     *
     * @param id
     * @return
     */

    @DELETE
    @Path("/user/{id}")
    public Response delete(@PathParam("id") String id) {

        storage.delete(Long.parseLong(id));

        return Response.ok().build();
    }

    /**
     * Updates user data
     *
     * @param bean
     * @param id
     * @param field
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws URISyntaxException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/user/{id}/{field}")
    public Response updateUserDetails(final JaxParamBean bean, @PathParam("id") String id,
            @PathParam("field") String field)
            throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, URISyntaxException {

        User user = storage.get(Long.parseLong(id));

        if (null == user) {
            return Response.status(204).build();
        }

        if ("name".equals(field)) {
            user.setName(bean.getName());
        } else if ("email".equals(field)) {
            user.setEmail(bean.getEmail());
        } else if ("avatar".equals(field)) {
            user.setAvatar(bean.getAvatar());
        } else {
            return Response.status(204).build();
        }

        return Response.ok().contentLocation(new URI("/rest/user/" + user.getId())).build();
    }

}
