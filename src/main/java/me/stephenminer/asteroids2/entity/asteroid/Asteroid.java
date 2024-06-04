package me.stephenminer.asteroids2.entity.asteroid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.concurrent.ThreadLocalRandom;

public class Asteroid extends Entity {
    protected double rot;
    protected double rotInc;
    protected int piece;
    public Asteroid(GameScreen screen, double x, double y, float speed, int piece) {
        super(screen, x, y, speed);
        initVals();
        this.piece = piece;
        initPieces();
    }


    private void initVals(){
        angle = ThreadLocalRandom.current().nextDouble(10,360);
        rotInc = ThreadLocalRandom.current().nextDouble(0.1,0.6);
        if (ThreadLocalRandom.current().nextBoolean()) rotInc*=-1;
        double rads = Math.toRadians(angle);
        ax = Math.cos(rads) * speed;
        ay = Math.sin(rads) * speed;

    }

    @Override
    public void deathEffect() {
        int shards = ThreadLocalRandom.current().nextInt(1,5);
        for (int i = 0; i < shards; i++){
            SmallAstroid smallAstroid = new SmallAstroid(screen, x,y,0.5f,ThreadLocalRandom.current().nextInt(6));
        }
    }
    @Override
    public boolean onKill(Entity killer) {
        if (!super.onKill(killer)) return false;
        Ship ship = (Ship) killer;
        int scrap = ThreadLocalRandom.current().nextInt(0,3);
        int fuel = 0;
        ship.supplies().incScrap(scrap);
        ship.supplies().incFuel(fuel);
        return true;
    }

    @Override
    protected void loadCorners() {
        initPieces();
    }

    protected void initPieces(){
        switch (piece){
            case 0:
                xCorners = new Double[]{-1.5d,1d,2d,-1d,-2d,-2.5d};
                yCorners = new Double[]{2d,1d,-1d,-2.5d,-1d,0d};
                scale(-8);
                break;
            case 1:
                xCorners = new Double[]{-4d,-3d,-1d,1d,2d,2d,3d,0d,-1.5d,-3d};
                yCorners = new Double[]{0d,2d,4d,3d,1d,0d,-2d,-3d,-2.5d,-2d};
                scale(-4);
                break;
            case 2:
                xCorners = new Double[]{-4d,-3d,-1d,0d,1d,2d,-1.5d,-3d,-3.5d};
                yCorners = new Double[]{0d,2d,3d,4d,3d,0d,-2d,-3d,-2d,-1d};
                scale(-4);
                break;
            case 3:
                xCorners = new Double[]{-4d,-3d,-1d,0d,2d,3d,4d,3d,2d,0d,-2d,-3d};
                yCorners = new Double[]{0d,3d,3.5d,4d,2.5d,2d,-1d,-2d,-3d,-4d,-3d,-1d};
                scale(-4);
                break;
        }
    }



    @Override
    public boolean drift() {
        rot += rotInc;
        return super.drift();
    }

    @Override
    public void updatePoints() {
        super.updatePoints();
        shape.setRotate(rot);


    }

    public double getRot(){ return rot; }
    public double getRotInc(){ return rotInc; }
}
