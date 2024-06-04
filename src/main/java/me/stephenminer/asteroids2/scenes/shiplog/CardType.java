package me.stephenminer.asteroids2.scenes.shiplog;

import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.alien.AlienBomber;
import me.stephenminer.asteroids2.entity.alien.SaucerAlien;
import me.stephenminer.asteroids2.entity.rogue.DroneShip;

public enum CardType {
    SAUCER(SaucerAlien.class, "Alien Saucer", "The basic fighter of the alien fleet \nEquiped with a basic laser and rarely a shield\nMoves and shoots"),
    BOMBER(AlienBomber.class, "Alien Bomber","Slightly modified version of Alien Saucer \n Equipped with missiles \nEquipped with a Falcon Missile and most often shielded by 3 shield layers. \nMoves slows and shoots"),
    DRONE(DroneShip.class,"Drone Patroller","Automated attacak ship \n Equipped with lasers or missiles");
    //PIRATE_FIGHTER();


    private CardType(Class<? extends Entity> type, String title, String desc){
        this.type = type;
        this.title = title;
        this.desc = desc;
    }



    private final Class<? extends Entity> type;
    private final String title;
    private final String desc;

    public Class<? extends Entity> type(){ return type; }
    public String title(){ return title; }
    public String desc(){ return desc; }
}
