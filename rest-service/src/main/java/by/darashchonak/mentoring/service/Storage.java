package by.darashchonak.mentoring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import by.darashchonak.mentoring.service.common.Storable;
import by.darashchonak.mentoring.service.common.User;

public class Storage implements Storable {

    private Map<Long, User> users = new HashMap<>();
    private Long nextId = new Long(1);

    private static volatile Storage instance;

    private synchronized void updateId() {
        ++nextId;
    }

    @Override
    public Long create(User user) {
        Long id = nextId;
        user.setId(id);
        users.put(id, user);
        updateId();

        return id;
    }

    @Override
    public void update(User user) {

        Long id = user.getId();
        users.put(id, user);

    }

    @Override
    public User get(Long id) {

        return users.get(id);
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
    }

    @Override
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
