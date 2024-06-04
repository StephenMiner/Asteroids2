package me.stephenminer.asteroids2.event.ship;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import me.stephenminer.asteroids2.entity.ship.Ship;

public class MoveShip implements EventHandler<KeyEvent> {
    private final Ship ship;
    public MoveShip(Ship ship){
        this.ship = ship;
    }
    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        if (ship.isDead() || ship.isWarping()) return;
        switch (code){
            case D:
                ship.accelerateForwards();
                break;
            case A:
                ship.accelerateBackwards();
                break;
            case E:
                ship.rotate(1);
                break;
            case Q:
                ship.rotate(-1);
                break;
            case W:
                ship.setAx(0);
                ship.setAy(0);
                break;
            case H:
                ship.lightSpeed();
                break;
        }
    }

    public Ship getShip(){ return ship; }
}
