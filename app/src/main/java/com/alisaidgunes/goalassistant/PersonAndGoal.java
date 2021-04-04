package com.alisaidgunes.goalassistant;

public class PersonAndGoal {
    private int id;
    private String name;
    private String goal;

    public PersonAndGoal() {
    }

    public PersonAndGoal(int id, String name, String goal) {
        this.id = id;
        this.name = name;
        this.goal = goal;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }


}
