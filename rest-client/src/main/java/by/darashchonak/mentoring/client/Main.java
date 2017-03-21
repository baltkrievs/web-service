package by.darashchonak.mentoring.client;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import by.darashchonak.mentoring.service.common.Field;
import by.darashchonak.mentoring.service.common.User;

public class Main {

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, IOException {

        RestClient rc = new RestClient("http://localhost:9090/rest", User.class);

        User user1 = new User();
        user1.setName("John Travolta");
        user1.setEmail("John@email.org");
        user1.setAvatar("avatar/images-3.jpeg");

        User user2 = new User();
        user2.setName("Uma Thurman");
        user2.setEmail("Uma@email.org");
        user2.setAvatar("avatar/images-2.jpeg");

        // Create two users
        Long user1Id = rc.create(user1);
        Long user2Id = rc.create(user2);

        User umaThurman = rc.get(user2Id);
        System.out.println(umaThurman);
        // Update email
        rc.updateProperty(user2Id, Field.EMAIL, "uma_thurman1970@gmail.com");
        // Get the same user again
        umaThurman = rc.get(user2Id);

        System.out.println(umaThurman);
        // Get another user
        User johnTravolta = rc.get(user1Id);
        System.out.println(johnTravolta);
        // Delete user
        rc.delete(user1Id);
        // Get user again - no such user status 204
        johnTravolta = rc.get(user1Id);
        System.out.println(johnTravolta);

        List<User> list = rc.getAll();
        // List all
        list.stream().forEach(System.out::println);

        // Update
        umaThurman.setAvatar("");
        rc.update(umaThurman);

        System.out.println(rc.get(user2Id));
    }

}
