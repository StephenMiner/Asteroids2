package me.stephenminer.asteroids2.equipment;

import me.stephenminer.asteroids2.entity.ship.FiringCost;

public enum Weapons {
    BASIC_LASER(BasicLaser.class,"Basic Laser", 3*1000,2,100, 45, null),
    ADVANCED_LASER(BasicLaser.class, "Advanced Laser", 5*1000,3,100, 70,null),
    RAPID_LASER(BasicLaser.class, "Rapid Laser", 500,1,100, 150,null),
    BARRAGE_LASER(BasicLaser.class, "Barrage Laser", 8*1000,6,100, 95,null),
    FALCON_MISSILE(MissileLauncher.class, "Falcon Missile", 8*1000, 1, 100, 30,new FiringCost(new FiringCost.Pair(Inventory.Items.MISSILES,1))),
    TWIN_MISSILE(MissileLauncher.class, "Twin Missiles", 6*1000,2,200, 55,new FiringCost(new FiringCost.Pair(Inventory.Items.MISSILES,2))),
    ANTI_MISSILE(ShrapnelLauncher.class,"Anti Missile", 5*1000,1,0,60,new FiringCost(new FiringCost.Pair(Inventory.Items.SCRAP,2)));



    private Weapons(Class<? extends Weapon> type, String title, int cooldown, int volley, int timeBetween, int cost, FiringCost firingCost){
        this.type = type;
        this.title = title;
        this.cooldown = cooldown;
        this.volley = volley;
        this.timeBetween = timeBetween;
        this.cost = cost;
        this.firingCost = firingCost;
    }
    private final Class<? extends Weapon> type;
    private final String title;
    private final int cooldown, volley, timeBetween, cost;
    private final FiringCost firingCost;

    public Class<? extends  Weapon> type(){ return type; }
    public String title(){ return title; }
    public int cooldown(){ return cooldown; }
    public int volley(){ return volley; }
    public int timeBetween(){ return timeBetween; }
    public int cost(){ return cost; }
    public FiringCost firingCost(){ return firingCost; }
}
