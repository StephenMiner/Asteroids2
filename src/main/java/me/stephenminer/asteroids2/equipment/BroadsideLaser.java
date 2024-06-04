package me.stephenminer.asteroids2.equipment;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.projectile.Projectile;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.Timer;
import java.util.TimerTask;

public class BroadsideLaser extends BasicLaser{
    private double shotSpacing;
    private int shotDelay;
    private int volleys;
    private boolean up;
    public BroadsideLaser(String name, Entity holder, int cooldown, int volley, int timeBetween, int shotSpacing, int shotDelay, int volleys, boolean up ) {
        super(name, holder, cooldown, volley, timeBetween);
        this.shotSpacing = shotSpacing;
        this.shotDelay = shotDelay;
        this.volleys = volleys;
        this.up = up;
    }

    private double cx;


    @Override
    public void fireProjectile(GameScreen screen){
        cx = holder.getX()-36;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int volleysFired = 0;
            int volleyCount;
            int shots;
            int shotCount = timeBetween;
            @Override
            public void run() {
                if (volleysFired >= volleys){
                    this.cancel();
                    cx = holder.getX();
                    return;
                }
                if (volleyCount >= shotDelay){
                    volleyCount = shotDelay;
                    if (shots >= volley) {
                        volleysFired++;
                        volleyCount = 0;
                        cx+=shotSpacing;
                        shots = 0;
                        return;
                    }
                    if (shotCount > timeBetween){
                        Platform.runLater(()->projectile(screen));
                        shotCount = 0;
                        shots++;
                    }
                    shotCount++;
                    return;
                }
                volleyCount++;
            }
        }, 0,1);
    }

    @Override
    protected Projectile projectile(GameScreen screen) {
        Projectile projectile = super.projectile(screen);
        projectile.setX(cx);
        projectile.setangle(up ? -90 : 90);
        projectile.updateAcceleration();
        return projectile;
    }

    public double getSpacing(){ return shotSpacing; }
    public double getShotDelay(){ return shotDelay; }
    public int getVolleys(){ return volleys; }


}
