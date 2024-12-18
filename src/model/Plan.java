package model;

public enum Plan {

    MONTREAL("Montreal");

    private final String name;

    Plan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
