package me.stephenminer.asteroids2.equipment;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.projectile.Laser;
import me.stephenminer.asteroids2.entity.projectile.Projectile;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.Timer;
import java.util.TimerTask;

public class BasicLaser extends Weapon implements Targeter{

    private Entity target;
    public BasicLaser(Entity holder, int cooldown) {
        this("Basic Laser",holder, cooldown, 2, 10);
    }
    public BasicLaser(String name,Entity holder, int cooldown, int volley, int timeBetween) {
        super(name,holder, Laser.class, cooldown, volley, timeBetween);
    }

    @Override
    public void fireProjectile(GameScreen screen) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int currentShots = 0;
            int count = timeBetween;
            @Override
            public void run() {
                if (holder.isDead() || currentShots >= volley){
                    this.cancel();
                    return;
                }
                if (screen.getSector() != null && screen.getSector().paused()) return;
                if (count >= timeBetween ){
                    Platform.runLater(()->projectile(screen));
                    count = 0;
                    currentShots++;
                }
                count++;
            }
        }, 0,1);
    }


    @Override
    protected Projectile projectile(GameScreen screen){
        Laser laser = new Laser(screen, holder,holder.getX(),holder.getY(),5.5f, holder.getAngle(),1);
        if (target != null){
            double angle = launcheAngle(holder);
            laser.setangle(angle);
            laser.updateAcceleration();
        }
        whitelist.forEach(laser::addWhitelist);
        return laser;
    }

    @Override
    public void setTarget(Entity target){ this.target = target; }

    @Override
    public Entity getTarget(){ return target; }

}
