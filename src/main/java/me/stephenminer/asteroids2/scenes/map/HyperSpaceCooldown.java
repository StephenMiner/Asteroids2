package me.stephenminer.asteroids2.scenes.map;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HyperSpaceCooldown {
    private int duration;
    private int current;
    private final Ship ship;
    private final GameScreen screen;

    private final int maxLength = 100;

    private List<Node> parts;

    private Rectangle bar;
    private Button box;

    public HyperSpaceCooldown(GameScreen screen, Ship ship, int duration){
        this.screen = screen;
        this.ship = ship;
        parts = new ArrayList<>();
        this.duration = duration;
        init();
    }

    private void init(){
        double y = Asteroids.HEIGHT -Asteroids.GAME_HEIGHT;
        box = new Button();
        box.setTranslateX(0);
        box.setTranslateY(Asteroids.GAME_HEIGHT);
        box.setPrefSize(120,y);
        box.setStyle("-fx-border-color: white;-fx-background-color: transparent;");
        box.addEventHandler(MouseEvent.MOUSE_CLICKED,e->ship.lightSpeed());
        box.setFocusTraversable(false);
        Rectangle bar = new Rectangle();
        bar.setX(0);
        bar.setY(Asteroids.GAME_HEIGHT + (y/2));
        bar.setHeight(y/2);
        bar.setWidth(0);
        bar.setFill(Color.RED);
        bar.setMouseTransparent(true);
        bar.setStroke(Color.WHITE);
        this.bar = bar;
        Text text = new Text("Warpdrive Cooldown");
        text.setY(Asteroids.GAME_HEIGHT + (y/4));
        text.setX(0);
        text.setFill(Color.WHITE);
        text.setStroke(Color.WHITE);
        text.setMouseTransparent(true);
        parts.add(text);
        parts.add(box);
        parts.add(bar);
        screen.root().getChildren().add(box);
        screen.root().getChildren().add(bar);
        screen.root().getChildren().add(text);
    }

    public void start(){
        Timer timer = new Timer();
        current = 0;
        bar.setFill(Color.RED);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (ship.isDead()){
                        cancel();
                        return;
                    }
                    if (ship.isWarping()) return;
                    if (screen.getSector() != null && screen.getSector().paused()) return;
                    if (current >= duration){
                        ship.setCanJump(true);
                        bar.setFill(Color.GREEN);
                        cancel();
                        return;
                    }
                    double ratio = current / (double) duration;
                    double length = ratio*maxLength;
                    bar.setWidth(length);
                    current++;
                });
            }
        },1,1);
    }

    public void stop(){
        for (Node node : parts){
            screen.root().getChildren().remove(node );
        }
        parts.clear();
    }






}
