package me.stephenminer.asteroids2.scenes.sector;

import javafx.scene.paint.Color;
import me.stephenminer.asteroids2.entity.background.Prop;
import me.stephenminer.asteroids2.entity.background.Star;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AsteroidBelt extends Sector{
    private List<Prop> props;
    public AsteroidBelt(GameScreen screen, Ship ship) {
        super(screen, ship, Color.BLACK, "Asteroid Belt");
        props = new ArrayList<>();
        settings.setStarts(0,30,0,1);
        settings.setRates(0,10*1000,0,20*1000);
        settings.setMaxs(0,40,0,1);
        writeValues();
    }



    private void initProps(){
        int num = ThreadLocalRandom.current().nextInt(40, 50);
        for (int i = 0; i < num; i++){
            Pos pos = randomPos();
            Star star = new Star(screen,2d, Color.SANDYBROWN,pos.getX(), pos.getY(), (float)ThreadLocalRandom.current().nextDouble(2,4.1));
            star.setAx(-1 * star.getSpeed());
            props.add(star);
        }
    }

    @Override
    public void start() {
        super.start();
        initProps();
    }

    @Override
    public void stop() {
        super.stop();
        for (int i = props.size()-1; i>=0; i--){
            Prop prop = props.get(i);
            prop.setRemove(true);
        }
        props.clear();
    }
}
