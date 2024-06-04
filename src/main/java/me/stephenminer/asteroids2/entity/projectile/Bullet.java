package me.stephenminer.asteroids2.entity.projectile;

import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class Bullet extends Projectile{

    public Bullet(GameScreen screen, Entity shooter, double x, double y, float speed, double angle, double damage) {
        super(screen, shooter, x, y, speed, angle, damage);
    }

    @Override
    protected void loadCorners() {
        xCorners = new Double[]{0d,0.25,0d,-0.25};
        yCorners = new Double[]{0.25,0d,-0.25,0d};
    }


    @Override
    public void onCollide(Entity colliding){
        if (colliding == null) return;
        double xSpeed = xSpeed(colliding);
        double ySpeed = ySpeed(colliding);
        double angle = calculateAngle(xSpeed,ySpeed);
        colliding.setAx(xSpeed);
        colliding.setAy(ySpeed);
        colliding.setangle(angle);
    }



    private double xSpeed(Entity colliding){
        return (colliding.getWeight()*colliding.getAx() + weight*ax) / (colliding.getWeight() + weight);
    }

    private double ySpeed(Entity colliding){
        return (colliding.getWeight()*colliding.getAy() + weight*ay) / (colliding.getWeight() + weight);
    }

    private double calculateAngle(double xSpeed, double ySpeed){
        return Math.toDegrees(Math.atan2(ySpeed,xSpeed));
    }
}
