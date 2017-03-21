package by.darashchonak.mentoring.service.common;

public enum Field {

    NAME("name"), EMAIL("email"), AVATAR("avatar");

    private final String name;

    private Field(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
