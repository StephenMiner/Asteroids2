package me.stephenminer.asteroids2.entity.alien;

import javafx.animation.AnimationTimer;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.equipment.Shield;
import me.stephenminer.asteroids2.entity.Shieldable;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.equipment.BasicLaser;
import me.stephenminer.asteroids2.equipment.Targeter;
import me.stephenminer.asteroids2.equipment.Weapon;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.concurrent.ThreadLocalRandom;

public class SaucerAlien extends Entity implements Shieldable {
    protected Entity target;
    protected Weapon weapon;
    protected Shield shield;


    public SaucerAlien(GameScreen screen,double x, double y, float speed) {
        this(screen, x, y, speed, null);
    }
    public SaucerAlien(GameScreen screen, double x, double y, float speed, Entity target) {
        super(screen,x, y, speed);
        angle = 30;
        this.target = target;
        updateAcceleration();
        bounce = true;
        this.screen = screen;
        initGear();
        useWeaponry();
    }

    protected void initGear(){
        this.weapon = new BasicLaser(this, 5*1000);
        weapon.addWhitelist(Ship.class);
        weapon.setCurrent(0);
    }

    private void updateAcceleration(){
        ax = Math.cos(Math.toRadians(angle)) * speed;
        ay = Math.sin(Math.toRadians(angle)) * speed;
    }
    @Override
    protected void loadCorners() {
        xCorners = new Double[]{ -4d,-2d,-1d,1d,2d,4d,1d,-1d};
        yCorners = new Double[]{ 0d,1d,2d,2d,1d,0d,-1d,-1d};
        scale(-4);
    }

    @Override
    public boolean onKill(Entity killer) {
        if (!super.onKill(killer)) return false;
        Ship ship = (Ship) killer;
        int scrap = ThreadLocalRandom.current().nextInt(5,12);
        int fuel = Math.max(ThreadLocalRandom.current().nextInt(0,4) - 1,0);
        ship.supplies().incScrap(scrap);
        ship.supplies().incFuel(fuel);
        return true;
    }
    @Override
    public void updatePoints(){
        super.updatePoints();
        shape.setRotate(0);
    }

    @Override
    public void deathEffect() {
        for (int i = 0; i < 4; i++){
            AlienDebris alienDebris = new AlienDebris(this,i);
        }
    }

    public void useWeaponry(){
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if (dead) {
                    stop();
                    return;
                }
                if (target == null || target.isDead()) return;
                if (weapon instanceof Targeter){
                    ((Targeter) weapon).setTarget(target);
                    weapon.shoot(screen);
                }
            }
        }.start();
    }

    @Override
    public void tick(){
        super.tick();
        if (shield != null) shield.tick();
    }




    public Entity getTarget(){ return target; }
    public void setTarget(Entity target){ this.target = target; }

    @Override
    public Shield getShield() {
        return shield;
    }

    @Override
    public void setShield(Shield shield) {
        removeShield(screen);
        this.shield = shield;
        addShield(screen);
    }

    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }
}
