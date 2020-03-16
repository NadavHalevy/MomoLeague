package com.example.momoleague;

public class ListItem {
    private int points;
    private int gamse;
    private String email;

    public ListItem(){}

    public ListItem(int points, int gamse, String email) {
        this.points = points;
        this.gamse = gamse;
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGamse() {
        return gamse;
    }

    public void setGamse(int gamse) {
        this.gamse = gamse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
