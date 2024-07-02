package me.stephenminer.asteroids2.event.ship;

import javafx.animation.AnimationTimer;
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

                new AnimationTimer(){
                    double target = 0.2;
                    double current = 0;
                    @Override
                    public void handle(long l) {
                        if (current>= target) {
                            stop();
                            return;
                        }
                        ship.rotate(0.07);
                        current+=0.07;
                    }
                }.start();
                //ship.rotate(0.2);
                break;
            case Q:
                new AnimationTimer(){
                    double target = -0.2;
                    double current = 0;
                    @Override
                    public void handle(long l) {
                        if (current< target) {
                            stop();
                            return;
                        }
                        ship.rotate(-0.07);
                        current-=0.07;
                    }
                }.start();
               // ship.rotate(-0.2);
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
