package me.stephenminer.asteroids2.entity.projectile;

import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class Laser extends Projectile{
    public Laser(GameScreen screen, Entity shooter, double x, double y, float speed, double angle, double damage) {
        super(screen, shooter, x, y, speed, angle, damage);
        killOffscreen = true;
    }

    @Override
    protected void loadCorners() {
        xCorners = new Double[]{-2d,2d,2d,-2d};
        yCorners = new Double[]{1d,1d,-1d,-1d};
    }



}
