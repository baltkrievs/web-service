package by.darashchonak.mentoring.service.common;

public interface Serviceable extends Storable {

    public void updateProperty(Long userId, Field field, String value);
}
