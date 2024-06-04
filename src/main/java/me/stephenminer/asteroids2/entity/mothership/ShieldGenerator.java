package me.stephenminer.asteroids2.entity.mothership;

import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class ShieldGenerator extends MotherShipPart {
    public ShieldGenerator(GameScreen screen, double x, double y, float speed) {
        super(screen, x, y, speed);
        setMaxHealth(4,true);
    }


    @Override
    protected void loadCorners() {

    }

}
