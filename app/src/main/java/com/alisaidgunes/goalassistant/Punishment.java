package com.alisaidgunes.goalassistant;

public class Punishment {

    private int id;
    private String name;
    private int punishmentopday;

    public Punishment() {
    }

    public Punishment(int id, String name, int punishmentopday) {
        this.id = id;
        this.name = name;
        this.punishmentopday = punishmentopday;
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

    public int getPunishmentopday() {
        return punishmentopday;
    }

    public void setPunishmentopday(int punishmentopday) {
        this.punishmentopday = punishmentopday;
    }





}
