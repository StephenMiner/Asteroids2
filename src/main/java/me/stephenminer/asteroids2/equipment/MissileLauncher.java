package me.stephenminer.asteroids2.equipment;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.projectile.Missile;
import me.stephenminer.asteroids2.entity.projectile.Projectile;
import me.stephenminer.asteroids2.entity.ship.FiringCost;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.Timer;
import java.util.TimerTask;

public class MissileLauncher extends Weapon implements Targeter{
    private Entity target;
    public MissileLauncher(Entity holder, int cooldown) {
        this("Missile me.stephenminer.asteroids2.Launcher",holder, cooldown, 1,10, null);
    }
    public MissileLauncher(String name, Entity holder, int cooldown, int volley, int timeBetween, FiringCost firingCost) {
        super(name,holder, Missile.class, cooldown, volley, timeBetween, firingCost);
    }

    @Override
    public void fireProjectile(GameScreen screen) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int currentShots = 0;
            int count = timeBetween;
            @Override
            public void run() {
                if (holder.isDead() || currentShots >= volley) {
                    this.cancel();
                    return;
                }
                if (screen.getSector().paused()) return;
                if (count >= timeBetween){
                    Platform.runLater(()->projectile(screen));
                    count = 0;
                    currentShots++;
                }
                count++;
            }
        }, 0,1);
    }


    @Override
    protected Projectile projectile(GameScreen screen) {
        if (target != null) {
            double angle = launcheAngle(holder);
            return new Missile(screen, holder, holder.getX(),holder.getY(),6f,angle,2);
        }else return new Missile(screen,holder,holder.getX(),holder.getY(), 6f,holder.getAngle(),2);
    }

    @Override
    public Entity getTarget(){ return target; }

    @Override
    public void setTarget(Entity target){ this.target = target; }
}
