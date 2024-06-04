package me.stephenminer.asteroids2.entity.mothership;

import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.Shieldable;
import me.stephenminer.asteroids2.equipment.Shield;
import me.stephenminer.asteroids2.scenes.GameScreen;

public abstract class MotherShipPart extends Entity implements Shieldable {
    protected int health,maxHealth;
    protected boolean canHit;
    protected Shield shield;
    public MotherShipPart(GameScreen screen, double x, double y, float speed, int maxHealth) {
        super(screen, x, y, speed);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }
    public MotherShipPart(GameScreen screen, double x, double y, float speed) {
        this(screen, x, y, speed, 5);
    }




    public int getHealth(){ return health; }
    public int getMaxHealth(){ return maxHealth; }

    public void setHealth(int health){ this.health = health;}
    public void setMaxHealth(int maxHealth,boolean reset){
        this.maxHealth = maxHealth;
        if (reset) this.health = maxHealth;
    }

    @Override
    public Shield getShield(){ return shield; }

    @Override
    public boolean canDmg() {
        boolean shieldCheck = Shieldable.super.canDmg();
        if (!shieldCheck) return false;
        health--;
        return health <= 0;
    }

    @Override
    public void setShield(Shield shield){
        removeShield(screen);
        this.shield = shield;
        addShield(screen);
    }




}
