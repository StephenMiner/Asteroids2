package me.stephenminer.asteroids2.entity.projectile;

import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.Shieldable;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.List;

public class Missile extends Projectile{
    public Missile(GameScreen screen, Entity shooter, double x, double y, float speed, double angle, double damage) {
        super(screen, shooter, x, y, speed, angle, damage);
        bypassShield = true;
        shootable = true;
        killOffscreen = true;
    }

    @Override
    protected void loadCorners() {
        xCorners = new Double[]{-2d,2d,3d,2d,-2d};
        yCorners = new Double[]{0.25d,0.25d,0d,-0.25d,-0.25d};
        scale(4);

    }

    @Override
    protected void hitEffect() {
        List<Entity> near = nearby(16, true);
        near.remove(this);
        for (Entity entity : near){
            if (entity.isDead()) continue;
            if (entity.getClass().equals(shooter.getClass())) continue;
            if (entity instanceof Projectile && !((Projectile) entity).isShootable())continue;
            if (entity instanceof Shieldable){
                if (((Shieldable) entity).canDmg()) entity.setDead(true);
            }else entity.setDead(true);
        }
        near.clear();
    }
}
