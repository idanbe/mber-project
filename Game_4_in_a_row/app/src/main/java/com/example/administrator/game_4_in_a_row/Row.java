package com.example.administrator.game_4_in_a_row;

import java.util.ArrayList;

// TODO : maybe should this class in the future , not delete !!!
public class Row{

    private String Name;
    private int Win;
    private int Loss;
    private int Draws;
    private double Percent_Win;

    // null constructor
    public Row(){

    }

    // constructor
    public Row(String name , int win , int loss , int draws , double percent){
        this.Name = name;
        this.Win = win;
        this.Loss = loss;
        this.Draws = draws;
        this.Percent_Win = percent;
    }


    // geters and seters
    public String getName() {
        return this.Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public int getWin() {
        return this.Win;
    }
    public void setWin(int win) {
        this.Win = win;
    }

    public int getLoss() {
        return this.Loss;
    }
    public void setLoss(int loss) {
        this.Loss = loss;
    }

    public int getDraws() {
        return this.Draws;
    }
    public void setDraws(int standoff) {
        this.Draws = standoff;
    }

    public double getPercent_Win() {
        return this.Percent_Win;
    }
    public void setPercent_Win(double percent_win) {
        this.Percent_Win = percent_win;
    }

}
