package by.darashchonak.mentoring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import by.darashchonak.mentoring.service.common.User;

public class Storage {

    private Map<Long, User> users = new HashMap<>();
    private Long nextId = new Long(1);

    private static volatile Storage instance;

    private synchronized void updateId() {
        ++nextId;
    }

    public Long create(User user) {
        Long id = nextId;
        user.setId(id);
        users.put(id, user);
        updateId();

        return id;
    }

    public void update(User user) {

        Long id = user.getId();
        users.put(id, user);

    }

    public User get(Long id) {

        return users.get(id);
    }

    public void delete(Long id) {
        users.remove(id);
    }

    public List<User> getAll() {
        return users.values().stream().collect(Collectors.toList());
    }

    public static Storage getInstance() {

        Storage localInstance = instance;
        if (localInstance == null) {
            synchronized (Storage.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Storage();
                }
            }
        }
        return localInstance;
    }

}
