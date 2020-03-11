package com.example.momoleague;

public class TableRow implements Comparable<TableRow>{
    private int games = 0,victory = 0,draw = 0,lose = 0,goalAgainst = 0,goalsFor = 0;
    public TableRow(){}

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getVictory() {
        return victory;
    }

    public void setVictory(int victory) {
        this.victory = victory;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getGoalAgainst() {
        return goalAgainst;
    }

    public void setGoalAgainst(int goalAgainst) {
        this.goalAgainst = goalAgainst;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getDiffrence(){
        return goalsFor - goalAgainst;
    }

    public int getPoints(){
        return victory*3 + draw;
    }

    @Override
    public int compareTo(TableRow o) {
        int res = o.getPoints() - getPoints();
        return res == 0 ? o.getDiffrence() - getDiffrence() : res;
    }
}
