package org.example.model;

public class Worker extends Entity<Integer> {
    private String name;
    private String password;
    private Boolean isBoss;

    public Worker(String name, String password, Boolean isBoss) {
        this.name = name;
        this.password = password;
        this.isBoss = isBoss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsBoss() {
        return isBoss;
    }

    public void setIsBoss(Boolean isBoss) {
        this.isBoss = isBoss;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isBoss=" + isBoss +
                '}';
    }
}
