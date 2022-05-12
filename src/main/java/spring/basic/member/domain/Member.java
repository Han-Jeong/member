package spring.basic.member.domain;

public class Member {

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private String name;
}
