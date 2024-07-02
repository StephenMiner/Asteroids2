package me.stephenminer.asteroids2.entity.asteroid;

import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.concurrent.ThreadLocalRandom;

public class SmallAstroid extends Asteroid{

    public SmallAstroid(GameScreen screen, double x, double y, float speed, int piece) {
        super(screen, x, y, speed, piece);
        weight = 0;
    }

    @Override
    public void deathEffect(){

    }

    @Override
    public boolean onKill(Entity killer) {
        if (!super.onKill(killer)) return false;
        Ship ship = (Ship) killer;
        int scrap = ThreadLocalRandom.current().nextInt(0,1);
        int fuel = 0;
        ship.supplies().incScrap(scrap);
        ship.supplies().incFuel(fuel);
        return true;
    }
    @Override
    protected void initPieces() {
        switch (piece){
            case 0:
                xCorners = new Double[]{-2d,-1d,1d,2d,0.5d};
                yCorners = new Double[]{0d,2d,1.5d,-1d,-2d};
                break;
            case 1:
                xCorners = new Double[]{-2d,-1d,1d,2d,0.5d,-1d};
                yCorners = new Double[]{0d,2d,1.5d,-1d,-2d,-1d};
                break;
            case 2:
                xCorners = new Double[]{-1.5d,-1d,0d,1d,2d,3d,2d,0d,-1d};
                yCorners = new Double[]{0d,2d,3d,2.5d,2d,-1d,-1.5d,-2d,-1d};
                break;
            case 3:
                xCorners = new Double[]{-2d,-1d,1d,2d,1d,-1d};
                yCorners = new Double[]{0d,1.5d,2d,1d,-1d,-2d};
                break;
            case 4:
                xCorners = new Double[]{-2d,-1d,1d,2d,1.5d,0d,-1d};
                yCorners = new Double[]{0d,1.5d,1d,0d,-1.5d,-2d,-1d};
                break;
            case 5:
                xCorners = new Double[]{-3d,-2d,-1d,1d,1.5d,3d,2d,0d,-1d,-2.5d};
                yCorners = new Double[]{0d,2d,3d,2d,1d,0.5d,-1d,-2.5d,-2d,-1d};
                break;
        }
        scale(-2);
    }

}
