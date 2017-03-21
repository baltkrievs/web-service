package by.darashchonak.mentoring.service.common;

import java.util.List;

public interface Storable {

    public Long create(User user);

    public void update(User user);

    public User get(Long id);

    public void delete(Long id);

    public List<User> getAll();

}
