package me.stephenminer.asteroids2.scenes.sector;

import javafx.scene.paint.Color;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class LonelyTwilight extends Sector{
    public LonelyTwilight(GameScreen screen, Ship ship) {
        super(screen, ship, Color.rgb(27,27,27), "Lonely Twilight");
        settings.setStarts(0,10,1,0);
        settings.setMaxs(1,15,4,0);
        settings.setRates(20*1000,15*1000,20*1000,0);
        writeValues();
    }
}
