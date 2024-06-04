package me.stephenminer.asteroids2.equipment;

import javafx.application.Platform;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.projectile.Bullet;
import me.stephenminer.asteroids2.entity.projectile.Projectile;
import me.stephenminer.asteroids2.entity.ship.FiringCost;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.Timer;
import java.util.TimerTask;

public class ShrapnelLauncher extends Weapon implements Equipment{

    protected double angle;
    protected int projectiles;
    public ShrapnelLauncher(String name, Entity holder, int cooldown, int volley, int timeBetween, FiringCost firingCost){
        super(name, holder, Bullet.class,cooldown,volley,timeBetween,firingCost);

        projectiles = 20;
    }
    @Override
    public void fireProjectile(GameScreen screen) {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            int count = 0;
            int current = 0;

            @Override
            public void run() {
                if (current >= volley){
                    cancel();
                    return;
                }
                if (count >= timeBetween){
                    count = 0;
                    current++;
                    for (double theta = 0; theta <= Math.PI; theta+= Math.PI/projectiles){
                        final double launch = Math.toDegrees(theta);
                        Platform.runLater(()->projectile(screen,launch));
                    }
                }
                count++;
            }
        },0,1);

    }

    @Override
    protected Projectile projectile(GameScreen screen) {

        Bullet bullet = new Bullet(screen,holder,holder.getX(),holder.getY(),2,angle,0.25);
        bullet.updateAcceleration();
        return bullet;
    }

    protected Projectile projectile(GameScreen screen, double launchAngle){
        Bullet bullet = new Bullet(screen, holder, holder.getX(),holder.getY(),20,launchAngle, 0.25);
        bullet.updateAcceleration();
        return bullet;
    }

}
