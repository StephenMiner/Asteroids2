package me.stephenminer.asteroids2.entity.rogue;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.equipment.Shield;
import me.stephenminer.asteroids2.entity.Shieldable;
import me.stephenminer.asteroids2.equipment.*;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;


public class DroneShip extends Entity implements Shieldable {
    private Entity target;
    private Shield shield;
    private Weapon weapon;
    private boolean shooting;

    public DroneShip(GameScreen screen, double x, double y, float speed){
        this(screen, x, y, speed,null);
    }
    public DroneShip(GameScreen screen, double x, double y, float speed, Entity target){
        super(screen, x, y, speed);
        this.target = target;
        bounce =true;
        initGear();
        useWeaponry();
        generateMovement();
    }

    public DroneShip(GameScreen screen, double x, double y, float speed, boolean dummy){
        super(screen,x,y,speed,dummy);
    }

    @Override
    protected void loadCorners() {
        xCorners = new Double[]{-2.5d,-2d,-1d,1d,2d,0d,-1d,1.5d,-1d,0d,2d,1d,-1d,-2d};
        yCorners = new Double[]{0d,1d,2d,2d,1.5d,1.5d,0.5d,0d,-0.5d,-1.5d,-1.5d,-2d,-2d,-1d};
        scale(5);
    }

    protected void initGear(){
        if (ThreadLocalRandom.current().nextBoolean()){
            this.weapon = Weapon.construct(Weapons.BASIC_LASER,this);
        }else this.weapon = Weapon.construct(Weapons.TWIN_MISSILE, this);
        weapon.setCurrent(0);
        weapon.runCooldown();
        int maxHealth = ThreadLocalRandom.current().nextInt(1,3);
        this.shield = new Shield(this, maxHealth, 20*1000);
        addShield(screen);
    }

    private void generateMovement(){
        angle = ThreadLocalRandom.current().nextDouble(15,361);
        double rads = Math.toRadians(angle);
        ax = Math.cos(rads) * speed;
        ay = Math.sin(rads) * speed;
    }

    protected void useWeaponry(){
        new AnimationTimer(){
            @Override
            public void handle(long l){
                if (dead){
                    stop();
                    return;
                }
                if (weapon == null || target == null || target.isDead() || shooting) return;
                if (!weapon.canShoot()) return;
                if (weapon instanceof Targeter) ((Targeter) weapon).setTarget(target);
                shooting = true;
                angle = targetAngle();
                weapon.shoot(screen);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            shooting = false;
                            generateMovement();
                        });
                    }
                }, 1000);



            }
        }.start();
    }

    @Override
    public boolean drift(){
        if (shooting){
            ax = 0;
            ay = 0;
            return false;
        }else return super.drift();
    }

    private double targetAngle(){
        if (target == null) return 0;
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        return Math.toDegrees(Math.atan2(dy,dx));
    }

    @Override
    public void tick(){
        super.tick();
        if (shield != null) shield.tick();
    }

    @Override
    public void deathEffect() {
        for (int i = 0; i < 4; i++){
            DroneDebris debris = new DroneDebris(screen, this, 0.5f,i);
        }
    }

    @Override
    public boolean onKill(Entity killer) {
        if (!super.onKill(killer)) return false;
        Ship ship = (Ship) killer;
        int scrap = ThreadLocalRandom.current().nextInt(10,20);
        int fuel = Math.max(ThreadLocalRandom.current().nextInt(1,5) - 1,0);
        ship.supplies().incScrap(scrap);
        ship.supplies().incFuel(fuel);
        return true;
    }


    @Override
    public Shield getShield() {
        return shield;
    }

    @Override
    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public Entity getTarget(){ return target; }
    public void setTarget(Entity target){ this.target = target; }
}
