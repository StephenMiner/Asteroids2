package me.stephenminer.asteroids2.equipment;

import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class Inventory {
    private final Ship holder;

    private int fuel,scrap,missiles;

    private GameScreen screen;

    public Inventory(Ship holder){
        this(holder, 0, 0, 0);
    }

    public Inventory(Ship holder, int fuel, int scrap, int missiles){
        this.holder = holder;
        this.fuel = fuel;
        this.scrap = scrap;
        this.screen = holder.getScreen();
        this.missiles = missiles;
    }







    public Ship getHolder(){ return holder; }

    public int getItem(Items item){
        return switch (item){
            case FUEL -> fuel;
            case SCRAP -> scrap;
            case MISSILES -> missiles;
        };
    }

    public void setItem(Items item, int num){
        switch (item){
            case FUEL -> setFuel(num);
            case MISSILES -> setMissiles(num);
            case SCRAP -> setScrap(num);
        }
    }

    public void incItem(Items item, int inc){
        switch (item){
            case FUEL -> incFuel(inc);
            case SCRAP -> incScrap(inc);
            case MISSILES -> incMissiles(inc);
        }
    }
    public int getScrap(){ return scrap; }
    public int getFuel(){ return fuel; }
    public int getMissiles(){ return missiles; }

    public void setMissiles(int missiles){this.missiles = missiles;}
    public void setFuel(int fuel){ this.fuel = fuel; }
    public void setScrap(int scrap){ this.scrap = scrap; }

    public void incFuel(int inc){ this.fuel += inc; }
    public void incMissiles(int inc){ this.missiles += inc; }
    public void incScrap(int inc){ this.scrap+=inc; }

    public enum Items{
        SCRAP,
        FUEL,
        MISSILES
    }
}
