package me.stephenminer.asteroids2.event.ship;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.equipment.Weapon;
import me.stephenminer.asteroids2.scenes.GameScreen;
import me.stephenminer.asteroids2.scenes.sector.Sector;
import me.stephenminer.asteroids2.scenes.sector.store.StoreScreen;

import java.util.Timer;
import java.util.TimerTask;

public class WeaponClicks implements EventHandler<MouseEvent> {
    private final Weapon weapon;
    private final GameScreen screen;
    private Button sellButton;
    public WeaponClicks(Weapon weapon){
        this.weapon = weapon;
        this.screen = weapon.getHolder().getScreen();
    }

    @Override
    public void handle(MouseEvent event){
        Sector sector = screen.getSector();
        if (sector instanceof StoreScreen){
            StoreScreen store = (StoreScreen)  sector;
            if (event.getButton() == MouseButton.SECONDARY){
                loadSell();
                removeTimer();
            }else removeButton();
        }else weapon.shoot(screen);

    }



    public void loadSell(){
        screen.root().getChildren().remove(sellButton);
        Button button = new Button("Sell for " + weapon.getCost()/3);
        button.setTextFill(Color.WHITE);
        button.setTranslateX(Asteroids.WIDTH/2d-200);
        button.setTranslateY(Asteroids.GAME_HEIGHT/2d);
        button.setPrefSize(100,50);
        button.setStyle("-fx-border-color: white;-fx-background-color: transparent;");
        button.setOnMouseClicked((event)->{
            weapon.sell();
            removeButton();
        });
        this.sellButton = button;
        screen.root().getChildren().add(sellButton);
    }

    private void removeTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (sellButton == null) return;
                removeButton();
            }
        }, 2000);
    }


    public void removeButton(){
        Platform.runLater(()->{
            screen.root().getChildren().remove(sellButton );
            sellButton = null;
        });
    }






    public Weapon getWeapon(){ return weapon;}
}
