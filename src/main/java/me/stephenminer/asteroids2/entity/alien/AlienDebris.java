package me.stephenminer.asteroids2.entity.alien;

import me.stephenminer.asteroids2.entity.Debris;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.concurrent.ThreadLocalRandom;

public class AlienDebris extends Debris {
    public AlienDebris(GameScreen screen, double x, double y, float speed, int piece){
        super(screen,x,y,speed, piece);
    }

    public AlienDebris(SaucerAlien alien, int piece){
        this(alien.getScreen(),alien.getX(),alien.getY(),0.5f,piece);
    }



    @Override
    public void updatePoints() {
        super.updatePoints();
        shape.setRotate(rot);
    }

    @Override
    public boolean drift() {
        rot += rotInc;
        return super.drift();
    }



    protected void loadPiece(){
        switch (piece){
            case 0:
                xCorners = new Double[]{-4d,-2d,-1d};
                yCorners = new Double[]{0d,1d,-1d};
                break;
            case 1:
                xCorners = new Double[]{-2d,-1d,1d,2d};
                yCorners = new Double[]{1d,2d,2d,1d};
                break;
            case 2:
                xCorners = new Double[]{2d,4d,1d};
                yCorners = new Double[]{1d,0d,-1d};
            case 3:
                xCorners = new Double[]{-2d,2d,1d,-1d};
                yCorners = new Double[]{1d,1d,-1d,-1d};
        }
    }


    public double getRot(){ return rot; }
    public double getRotInc(){ return rotInc; }

}
