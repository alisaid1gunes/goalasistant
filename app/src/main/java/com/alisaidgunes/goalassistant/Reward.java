package com.alisaidgunes.goalassistant;

public class Reward {
    private int id;
    private String name;
    private int rewardopday;

    public Reward() {
    }

    public Reward(int id, String name, int rewardopday) {
        this.id = id;
        this.name = name;
        this.rewardopday = rewardopday;
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

    public int getRewardopday() {
        return rewardopday;
    }

    public void setRewardopday(int rewardopday) {
        this.rewardopday = rewardopday;
    }
}
