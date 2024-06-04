package me.stephenminer.asteroids2.equipment;

import me.stephenminer.asteroids2.entity.Entity;

public interface Targeter {

    public default double launcheAngle(Entity source){
        Entity target = getTarget();
        if (target == null) return 0;
        double dx = target.getX() - source.getX();
        double dy = target.getY() - source.getY();
        return Math.toDegrees(Math.atan2(dy,dx));
    }

    public void setTarget(Entity entity);
    public Entity getTarget();
}
