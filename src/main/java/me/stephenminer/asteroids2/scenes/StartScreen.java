package me.stephenminer.asteroids2.scenes;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.Debris;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.alien.AlienDebris;
import me.stephenminer.asteroids2.entity.asteroid.Asteroid;
import me.stephenminer.asteroids2.entity.mothership.ShipBody;
import me.stephenminer.asteroids2.entity.ship.ShipDebris;
import me.stephenminer.asteroids2.scenes.GameScreen;
import me.stephenminer.asteroids2.scenes.sector.event.Invasion;
import me.stephenminer.asteroids2.scenes.sector.guide.GuideScreen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class StartScreen {
    private Text title;
    private Text desc;
    private Button startButton, controlsButton;
    private GameScreen screen;

    private List<Entity> screenObjs;

    public StartScreen(GameScreen screen){
        this.screen = screen;
        screenObjs = new ArrayList<>();
        this.title = loadTitle();
        this.desc = loadDesc();
        this.startButton = startButton();
        this.controlsButton = controlsButton();

    }

    public void start(){
        initBackground();
    }

    private Text loadTitle(){
        Text title = new Text("A Lonely Odyssey");
        title.setX(Asteroids.WIDTH/2d - 60);
        title.setY(300);
        title.setStroke(Color.WHITE);
        Font font = new Font(25);
        title.setFont(font);
        return title;
    }
    private Text loadDesc(){
        Text desc = new Text("Humanity lies on the brink of defeat. " +
                "You are the sole survivor having narrowly escaped destruction. " +
                "You must jump through the galaxy before being caught by the Alien Fleet");
        desc.setWrappingWidth(300);
        desc.setStroke(Color.WHITE);
        desc.setX(Asteroids.WIDTH/20d);
        desc.setY(Asteroids.HEIGHT/2d);
        return desc;
    }

    private Button startButton(){
        Button button = new Button("Start Game");
        button.setTranslateX(Asteroids.WIDTH/2d -10);
        button.setTranslateY(Asteroids.HEIGHT/2d);
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #000000");
        button.setOnMouseClicked((event)-> {
            screen.start();
            clear();
        });
        return button;
    }

    private Button controlsButton(){
        Button button = new Button("Controls");
        button.setTranslateX(Asteroids.WIDTH/2d - button.getWidth()/2d);
        button.setTranslateY(Asteroids.HEIGHT/2d + 25);
        button.setStyle("-fx-background-color: #000000");
        button.setTextFill(Color.WHITE);
        button.setOnMouseClicked((event)->{
            new GuideScreen(screen).start();
            clear();
        });
        return button;
    }

    private void initBackground(){
        int debrisNum = ThreadLocalRandom.current().nextInt(66,80);
        for (int i = 0; i < debrisNum; i++){
            Debris debris = Debris.randDebris(screen, randX(),randY(),0.5f);
            screenObjs.add(debris);
        }
        int asteroids = ThreadLocalRandom.current().nextInt(5,10);
        for (int i = 0; i < asteroids;i++){
            Asteroid asteroid = new Asteroid(screen, randX(),randY(),0.5f,ThreadLocalRandom.current().nextInt(4));
            screenObjs.add(asteroid);
        }
        screen.root().getChildren().add(title);
        screen.root().getChildren().add(desc);
        screen.root().getChildren().add(startButton);
        screen.root().getChildren().add(controlsButton);
        screen.getScene().setFill(Color.BLACK);

    }


    public void clear(){
        for (int i = screenObjs.size()-1; i >= 0; i--){
            Entity entity = screenObjs.get(i);
            entity.setDead(true);
            screenObjs.remove(i);
        }
        screenObjs.clear();
        screen.root().getChildren().remove(title);
        screen.root().getChildren().remove(desc);
        screen.root().getChildren().remove(startButton);
        screen.root().getChildren().remove(controlsButton);
    }

    private double randX(){ return ThreadLocalRandom.current().nextInt(Asteroids.WIDTH); }
    private double randY(){ return ThreadLocalRandom.current().nextInt(Asteroids.GAME_HEIGHT); }

    public GameScreen getScreen(){ return screen; }
}
