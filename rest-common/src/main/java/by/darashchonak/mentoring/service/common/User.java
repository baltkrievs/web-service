package by.darashchonak.mentoring.service.common;

public class User extends JaxParamBean {

    private Long id;

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", getName()=" + getName() + ", getEmail()=" + getEmail() + ", getAvatar()="
                + getAvatar() + "]";
    }

}
