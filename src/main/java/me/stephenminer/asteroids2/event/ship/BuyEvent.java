package me.stephenminer.asteroids2.event.ship;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.sector.store.StoreIcon;

import java.util.Timer;
import java.util.TimerTask;

public class BuyEvent implements EventHandler<MouseEvent> {
    private Ship buyer;
    private StoreIcon icon;
    private Text display;
    public BuyEvent(Ship buyer, StoreIcon clicked){
        this.buyer = buyer;
        this.icon = clicked;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        if (icon.canBuy(buyer)){
            icon.buy(buyer);
        }else {
            notEnoughScrap();
            removeTimer();
        }
    }

    private void notEnoughScrap(){
        removeDisplay();
        Text text = new Text("Not enough Scrap");
        text.setFill(Color.WHITE);
        text.setStroke(Color.DARKRED);
        text.setX(Asteroids.WIDTH/2d-100);
        text.setY(Asteroids.HEIGHT/2d);
        this.display = text;
        icon.getScreen().root().getChildren().add(display);
    }
    private void sellWeapon(){
        removeDisplay();
        Text text = new Text("Right-Click on one of your weapons to get rid of, left click to cancel");
        text.setFill(Color.WHITE);
        text.setStroke(Color.DARKRED);
        text.setX(Asteroids.WIDTH/2d-100);
        text.setY(Asteroids.HEIGHT/2d);
        this.display = text;
        icon.getScreen().root().getChildren().add(display);

    }
    private void removeTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                removeDisplay();
            }
        }, 500);
    }
    private void removeDisplay(){
        Platform.runLater(() -> {
            icon.getScreen().root().getChildren().remove(display);
            display = null;
        });
    }

}
