package me.stephenminer.asteroids2.event.ship;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class WeaponEvents implements EventHandler<KeyEvent> {
    private final Ship ship;
    private final GameScreen screen;
    public WeaponEvents(Ship ship, GameScreen screen){
        this.ship = ship;
        this.screen = screen;

    }
    @Override
    public void handle(KeyEvent event) {
        if (event.getCode().isDigitKey()){
            int num = Integer.parseInt(event.getCode().getName());
            ship.setWeapon(num-1);
        }
        if (event.getCode() == KeyCode.SPACE){
            ship.useWeapon(screen);
        }
    }



}
