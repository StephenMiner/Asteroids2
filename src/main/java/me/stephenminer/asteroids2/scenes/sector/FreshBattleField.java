package me.stephenminer.asteroids2.scenes.sector;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.alien.AlienDebris;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.entity.ship.ShipDebris;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FreshBattleField extends Sector{
    private int maxDebris;
    private int debris;
    private int debrisRate;

    public FreshBattleField(GameScreen screen, Ship ship) {
        super(screen, ship, Color.rgb(30,20,20), "Fresh Battlefield");
        maxDebris = 25;
        debris = 50;
        debrisRate = 5*1000;
        settings.setStarts(1,5,1,1);
        settings.setMaxs(3,10,1,1);
        settings.setRates(15*1000,20*1000,20*1000,18*1000);
        writeValues();
    }

    @Override
    public void start() {
        super.start();
        starting = true;
        for (int i = 0; i < debris; i++){
            Pos pos = randomPos();
            spawnDebris(pos);
        }
        starting = false;
    }


    private void spawnDebris(Pos pos){
        boolean shipDebris = ThreadLocalRandom.current().nextBoolean();
        if (shipDebris)
            new ShipDebris(screen,ThreadLocalRandom.current().nextInt(4), pos.getX(),pos.getY(),0.5f);
        else new AlienDebris(screen, pos.getX(),pos.getY(),0.5f,ThreadLocalRandom.current().nextInt(4));
    }

    @Override
    public void updateCounts(Entity entity, int modify) {
        super.updateCounts(entity, modify);
        if (starting) return;
        if (entity instanceof ShipDebris || entity instanceof AlienDebris) debris+=modify;
    }

    @Override
    public void run(){
        super.run();
        new AnimationTimer(){
            private int debrisCount;
            @Override
            public void handle(long l) {
                if (paused) return;
                if (screen.closing() || stop) stop();
                debrisStatement:
                {
                    if (debrisCount >= debrisRate){
                        debrisCount = 0;
                        if (debris >= maxDebris) break debrisStatement;
                        Pos pos = generatePos();
                        spawnDebris(pos);
                    }
                }
                debrisCount++;
            }
        }.start();
    }

    @Override
    public void stop() {
        super.stop();

    }
}
