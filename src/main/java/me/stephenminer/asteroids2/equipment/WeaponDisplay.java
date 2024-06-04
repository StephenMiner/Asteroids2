package me.stephenminer.asteroids2.equipment;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Shadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.event.ship.WeaponClicks;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class WeaponDisplay {
   // private int slot;
    private Weapon weapon;
    private GameScreen screen;
    private List<Node> parts;

    private Rectangle bar;
    private Button box;
    private Text title;
    private double maxBar = 90;
    private double width = 100;
    private boolean end;


    public WeaponDisplay(GameScreen screen, Weapon weapon){
        this.weapon = weapon;
        this.screen = screen;
        parts = new ArrayList<>();

    }



    private void initParts(){
        double y = Asteroids.HEIGHT - Asteroids.GAME_HEIGHT;
        title = new Text(weapon.getName());
        title.setX(getX());
        title.setY(Asteroids.GAME_HEIGHT + y/4);
        title.setStroke(Color.WHITE);
        title.setMouseTransparent(true);
        title.setFill(Color.WHITE);
        box = new Button();
        box.setTranslateX(getX());
        box.setTranslateY(Asteroids.GAME_HEIGHT);
        box.setStyle("-fx-border-color: white;-fx-background-color: transparent;");
        box.setPrefSize(width,y);
        box.setFocusTraversable(false);
        WeaponClicks weaponEvent = new WeaponClicks(weapon);
        box.addEventHandler(MouseEvent.MOUSE_CLICKED,weaponEvent);
        bar = new Rectangle();
        bar.setMouseTransparent(true);
        bar.setX(getX());
        bar.setY(Asteroids.GAME_HEIGHT + y/2);
        bar.setHeight(25);
        bar.setStroke(Color.WHITE);

        screen.root().getChildren().add(box);
        screen.root().getChildren().add(bar);
        screen.root().getChildren().add(title);
        parts.add(box);
        parts.add(bar);
        parts.add(title);

    }


    public void start(){
        initParts();

        new AnimationTimer(){
            boolean disabled = false;
            @Override
            public void handle(long l) {
                if (end || weapon.getHolder().isDead()){
                    stop();
                    end();
                    return;
                }
                boolean newVal = disabled();
                if (disabled != newVal){
                    if (newVal)
                        box.setStyle("-fx-border-color: red;-fx-background-color: transparent;");
                    else box.setStyle("-fx-border-color: white;-fx-background-color: transparent;");
                }
                disabled = newVal;
                double ratio = weapon.getCurrent()/(double)weapon.getCooldown();
                if (ratio >= 1) bar.setFill(Color.GREEN);
                else bar.setFill(Color.RED);
                double length = ratio * maxBar;
                bar.setWidth(length);
                updatePos();
                if (!disabled) {
                    if (screen.getPlayer().getActiveSlot() == index())
                        box.setStyle("-fx-border-color: yellow;-fx-background-color: transparent;");
                    else box.setStyle("-fx-border-color: white;-fx-background-color: transparent;");
                }
            }
        }.start();
    }

    private void updatePos(){
        double y = Asteroids.HEIGHT - Asteroids.GAME_HEIGHT;
        box.setTranslateX(getX());
        box.setTranslateY(Asteroids.GAME_HEIGHT);
        bar.setX(getX());
        bar.setY(Asteroids.GAME_HEIGHT + y/2);
        title.setX(getX());
        title.setY(Asteroids.GAME_HEIGHT + y/4);
    }

    private boolean disabled(){
        if (weapon.getHolder() instanceof Ship ship){
            return !weapon.getFiringCost().hasItems(ship.supplies());
        }
        return false;
    }

    public void end(){
        end = true;
        for (Node node : parts){
            screen.root().getChildren().remove(node);
        }
        parts.clear();
        box = null;
        bar = null;
        title = null;
    }



    private double getX(){
        return 120 + ((index()-1)*width);
    }

    private int index(){
        return ((Ship) weapon.getHolder()).weaponSlot(weapon);
    }

}
