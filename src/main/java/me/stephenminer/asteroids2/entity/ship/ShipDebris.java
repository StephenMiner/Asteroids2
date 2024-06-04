package me.stephenminer.asteroids2.entity.ship;

import me.stephenminer.asteroids2.entity.Debris;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.concurrent.ThreadLocalRandom;

public class ShipDebris extends Debris {
    private Ship ship;

    public ShipDebris(int piece, Ship holder){
        this(holder.getScreen(), piece,holder.getX(), holder.getY(),0.5f);
        this.ship = holder;


    }
    public ShipDebris(GameScreen screen, int piece, double x, double y, float speed){
        super(screen, x, y, speed, piece);

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



    @Override
    protected void loadPiece(){
        switch (piece){
            case 0:
                xCorners = new Double[]{-2d,0.3d,-1d};
                yCorners = new Double[]{1.5d,0.8d,0d};
                break;
            case 1:
                xCorners = new Double[]{-1d,0.3d,2d,0.7d};
                yCorners = new Double[]{0d,0.8d,0d,-0.8d};
                break;
            case 2:
                xCorners = new Double[]{-0.2d,0d,0.7d};
                yCorners = new Double[]{-1d,-0.2d,-0.7d};
                break;
            case 3:
                xCorners = new Double[]{-1d,0d,-0.2d,-2d};
                yCorners = new Double[]{0d,-0.2d,-1d,-1.5d};
                break;
        }

    }

    public int getPiece(){ return piece; }
    public void setPiece(int piece){ this.piece = piece; }
    public Ship getShip(){ return ship; }
}
