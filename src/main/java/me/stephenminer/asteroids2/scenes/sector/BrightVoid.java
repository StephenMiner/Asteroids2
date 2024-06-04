package me.stephenminer.asteroids2.scenes.sector;

import javafx.scene.paint.Color;
import me.stephenminer.asteroids2.entity.background.Prop;
import me.stephenminer.asteroids2.entity.background.Star;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BrightVoid extends Sector{
    private List<Prop> stars;

    public BrightVoid(GameScreen screen, Ship ship) {
        super(screen, ship, Color.BLACK, "Bright Void");
        stars = new ArrayList<>();
        settings.setStarts(0,15,0,1);
        settings.setMaxs(2,20,0,1);
        settings.setRates(20*1000,10*1000,0,20*1000);
        writeValues();
    }

    @Override
    public void start(){
        super.start();
        initStars();
    }
    private void initStars(){
        int numStars = ThreadLocalRandom.current().nextInt(50,60);
        for (int i = 0; i < numStars; i++){
            Pos pos = randomPos();
            Star star = new Star(screen,1.5d,pos.getX(),pos.getY(),0f);
        }
    }

    @Override
    public void stop() {
        super.stop();
        for (int i = stars.size()-1; i>=0; i--){
            Prop prop = stars.get(i);
            prop.setRemove(true);
        }
        stars.clear();
    }
}
