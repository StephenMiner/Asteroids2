package me.stephenminer.asteroids2.entity;

import me.stephenminer.asteroids2.entity.alien.AlienDebris;
import me.stephenminer.asteroids2.entity.rogue.DroneDebris;
import me.stephenminer.asteroids2.entity.rogue.PirateDebris;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.entity.ship.ShipDebris;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Debris extends Entity{

    public static Debris randDebris(GameScreen screen,double x, double y,float speed){
        int selection = ThreadLocalRandom.current().nextInt(4);
        int pieceRoll = ThreadLocalRandom.current().nextInt(4);
        switch (selection){
            case 0:
                return new ShipDebris(screen,pieceRoll,x,y,speed);
            case 1:
                return new AlienDebris(screen, x, y, speed, pieceRoll);
            case 2:
                return new PirateDebris(screen, x, y, speed, pieceRoll);
            case 3:
                return new DroneDebris(screen,x,y,speed, pieceRoll);
        };
        return null;
    }
    protected double rot, rotInc;
    protected int piece, maxPieces;
    public Debris(GameScreen screen, double x, double y, float speed, int piece) {
        super(screen, x, y, speed);
        this.piece = piece;
        initVals();
        loadCorners();
        weight = ThreadLocalRandom.current().nextInt(0,5) * 20;
    }

    protected void initVals(){
        angle = ThreadLocalRandom.current().nextDouble(10,360);
        double rads = Math.toRadians(angle);
        ax = Math.cos(rads)*speed;
        ay = Math.sin(rads)*speed;
        rotInc = ThreadLocalRandom.current().nextDouble(0.1,0.5);
        if (ThreadLocalRandom.current().nextBoolean()) rotInc*=-1;
    }

    @Override
    public void updatePoints() {
        super.updatePoints();
        shape.setRotate(rot);
    }

    @Override
    protected void loadCorners() {
        loadPiece();
        scale(-4);
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
    public boolean drift() {
        rot += rotInc;
        return super.drift();
    }

    protected abstract void loadPiece();

    public double getRot(){ return rot; }
    public double getRotInc(){ return rotInc; }




}
