package me.stephenminer.asteroids2.entity.rogue;

import me.stephenminer.asteroids2.entity.Debris;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class DroneDebris extends Debris {

    public DroneDebris(GameScreen screen, double x, double y, float speed, int piece) {
        super(screen, x, y, speed, piece);
    }
    public DroneDebris(GameScreen screen, DroneShip ship, float speed, int piece) {
        this(screen, ship.getX(), ship.getY(), speed, piece);
    }
    @Override
    protected void loadPiece() {
        switch (piece){
            case 0:
                xCorners = new Double[]{-2.5d,-2d,-1d,1d,2d,0d,-1d};
                yCorners = new Double[]{0d,1d,2d,2d,1.5d,1.5d,0.5d};
                break;
            case 1:
                xCorners = new Double[]{-2.5d,-1d,0d,2d,1d,0d,-1d,-2d};
                yCorners = new Double[]{0d,-0.5d,-1.5d,-1.5d,-2d,-2d,-2d,-1d};
                break;
            case 2:
                xCorners = new Double[]{-2.5d,-1d,-1d};
                yCorners = new Double[]{0d,0.5d,-0.5d};
                break;
            case 3:
                xCorners = new Double[]{-1d,1.5d,-1d};
                yCorners = new Double[]{0.5d,0d,-0.5d};
                break;
        }
    }
}
