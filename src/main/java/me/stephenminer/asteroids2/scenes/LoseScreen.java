package me.stephenminer.asteroids2.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.Asteroids;

public class LoseScreen {
    private GameScreen screen;

    private Text text;
    private Button button;
    private Button titleButton;

    public LoseScreen(GameScreen screen){
        this.screen = screen;
        init();
    }

    private void init(){
        text = loadText();
        button = getButton();
        titleButton = loadTitleButton();
        screen.root().getChildren().add(button);
        screen.root().getChildren().add(text);
        screen.root().getChildren().add(titleButton);
    }

    public void restart(){
        screen.clear();
        screen.start();
    }

    public void titleScreen(){
        screen.clear();
        StartScreen startScreen = new StartScreen(screen);
        startScreen.start();
    }

    private Text loadText(){
        Text text = new Text("Your ship shatters, drifting for eternity");
        text.setFill(Color.WHITE);
        text.setX(Asteroids.WIDTH/2d);
        text.setY(Asteroids.HEIGHT/2d);
        return text;
    }

    private Button getButton(){
        Button button = new Button("Restart?");
        button.setTranslateX(Asteroids.WIDTH/2d);
        button.setTranslateY(Asteroids.HEIGHT/2d - 50);
        button.setOnMouseClicked((event) ->  restart() );
        return button;
    }

    private Button loadTitleButton(){
        Button button = new Button("Title Screen");
        button.setTranslateX(Asteroids.WIDTH/2d);
        button.setTranslateY(Asteroids.HEIGHT/2d - 100);
        button.setOnMouseClicked((event)->titleScreen());
        return button;
    }


}
