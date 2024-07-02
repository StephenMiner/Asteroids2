package me.stephenminer.asteroids2.entity.rogue;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.equipment.Shield;
import me.stephenminer.asteroids2.entity.Shieldable;
import me.stephenminer.asteroids2.equipment.Targeter;
import me.stephenminer.asteroids2.equipment.Weapon;
import me.stephenminer.asteroids2.equipment.Weapons;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class PirateFighter extends Entity implements Shieldable {
    private Shield shield;
    private Entity target;
    private Weapon weapon;
    private boolean fleeing;
    private boolean shooting;

    public PirateFighter(GameScreen screen, double x, double y, float speed) {
        this(screen, x, y, speed,null);
    }
    public PirateFighter(GameScreen screen,  double x, double y, float speed,Entity target) {
        super(screen, x, y, speed);
        this.target = target;
        initGear();
        useWeaponry();
        ax = Math.cos(Math.toRadians(angleToTarget()))*speed;
        ay = Math.sin(Math.toRadians(angleToTarget()))*speed;
        bounce = true;
        weight = 80;
    }
    public PirateFighter(GameScreen screen, double x, double y, float speed, boolean dummy){
        super(screen,x,y,speed,dummy);
    }
    private void initGear(){
        weapon = Weapon.construct(Weapons.ADVANCED_LASER,this);
        weapon.setCurrent(0);
        weapon.runCooldown();
    }
    @Override
    protected void loadCorners() {
        xCorners = new Double[]{-3d,-1.5d,0d,0d,3.5d,0.5d,0.5d,1.5d,4d,1.5d,0.5d,0.5d,3.5d,0d,0d,-1.5d};
        yCorners = new Double[]{0d,0.5d,1d,1.5d,1.5d,1.3d,1d,1d,0d,-1d,-1d,-1.3d,-1.5d,-1.5d,-1d,-0.5d};
        scale(4);
    }

    @Override
    public boolean drift(){
        if (shooting) return false;
        boolean borders = super.drift();
        if (target == null) return borders;
        double dist = dist(this, target);
        if (!fleeing) {
            ax = Math.cos(Math.toRadians(angle)) * speed;
            ay = Math.sin(Math.toRadians(angle)) * speed;
            if (dist < 60) {
                fleeing = true;
                angle = ThreadLocalRandom.current().nextDouble(this.angle + 90,this.angle + 180);
                double rot = Math.toRadians(angle);
                ax = Math.cos(rot) * speed;
                ay = Math.sin(rot) * speed;
               // angle = Math.toDegrees(rot);
                System.out.println(22);
                //  angle = Math.toDegrees(Math.atan2(ay/speed,ax/speed));
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        fleeing = false;
                        angle = angleToTarget();
                        double rads = Math.toRadians(angle);
                        ax = Math.cos(rads) * speed;
                        ay = Math.sin(rads) * speed;
                    }
                }, 1500);
            }
        }
        return borders;
    }

    @Override
    public void deathEffect() {
        for (int i = 0; i < 4; i++){
            PirateDebris debris = new PirateDebris(screen, this,0.5f,i);
        }
    }

    public void useWeaponry(){
        if (weapon == null) return;
        new AnimationTimer(){
            @Override
            public void handle(long l){
                if (dead){
                    stop();
                    return;
                }
                if (weapon == null || target == null || target.isDead() || shooting || fleeing) return;
                if (!weapon.canShoot()) return;
                if (weapon instanceof Targeter) ((Targeter) weapon).setTarget(target);
                shooting = true;
                angle = angleToTarget();
                weapon.shoot(screen);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            shooting = false;
                        //    angle = Math.toDegrees(Math.atan2(ay/speed,ax/speed));
                        });
                    }
                }, 1000);
            }
        }.start();
    }

    @Override
    public boolean onKill(Entity killer) {
        if (!super.onKill(killer)) return false;
        Ship ship = (Ship) killer;
        int scrap = ThreadLocalRandom.current().nextInt(8,17);
        int fuel = Math.max(ThreadLocalRandom.current().nextInt(0,3) - 1,0);
        ship.supplies().incScrap(scrap);
        ship.supplies().incFuel(fuel);
        return true;
    }

    private double angleToTarget(){
        if (target == null) return 0;
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        double angle = Math.toDegrees(Math.atan2(dy,dx));
        System.out.println(angle);
        return angle;
    }




    @Override
    public Shield getShield(){ return shield; }
    @Override
    public void setShield(Shield shield){ this.shield = shield; }
    public Entity getTarget(){ return target; }
    public void setTarget(Entity target){ this.target = target;}


}
