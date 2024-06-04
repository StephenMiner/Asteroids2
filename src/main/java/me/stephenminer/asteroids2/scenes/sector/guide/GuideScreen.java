package me.stephenminer.asteroids2.scenes.sector.guide;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.scenes.GameScreen;
import me.stephenminer.asteroids2.scenes.StartScreen;

import java.util.ArrayList;
import java.util.List;

public class GuideScreen {
    private GameScreen screen;

    private List<Node> parts;
    private Color bgColor;



    public GuideScreen(GameScreen screen){
        this.screen = screen;
        parts = new ArrayList<>();
        bgColor = Color.rgb(206,217,237);
    }

    public void start() {
        loadBox();
        loadTitle();
        loadInfo();
        loadBackButton();
        screen.root().getChildren().addAll(parts);
    }

    public void stop(){
        parts.forEach(node -> screen.root().getChildren().remove(node));
    }

    private void loadBox(){
        Rectangle enclosingBox = new Rectangle();
        enclosingBox.setWidth(350);
        enclosingBox.setHeight(350);
        enclosingBox.setX(Asteroids.WIDTH/2d - enclosingBox.getWidth()/2d);
        enclosingBox.setY(Asteroids.HEIGHT/2d-enclosingBox.getHeight()/2d);
        enclosingBox.setFill(bgColor);
       // enclosingBox.setOpacity(0.2);
        enclosingBox.setStroke(Color.WHITE);
        enclosingBox.setStrokeWidth(5);
        parts.add(enclosingBox);
    }

    private void loadTitle(){
        Text title = new Text("Controls");
        title.setX(Asteroids.WIDTH/2d - title.getLayoutBounds().getWidth()/2d);
        title.setY(Asteroids.HEIGHT/4.5d);
        title.setStroke(Color.WHITE);
        title.setFill(Color.WHITE);
        parts.add(title);
    }

    private void loadInfo() {
        Text info = new Text();
        info.setText(
                "D - Accelerate Forwards \n" +
                        "A - Accelerate Backwards \n" +
                        "Q - Rotate counter-clockwise \n" +
                        "E - Rotate clockwise \n" +
                        "W - Kill movement \n" +
                        "# - Select weapon \n" +
                        "Space - Fire weapon \n" +
                        "H - Use Hyperdrive");
        info.setX(Asteroids.WIDTH/2d - info.getLayoutBounds().getWidth()/2d);
        info.setY(Asteroids.GAME_HEIGHT/3d);
        parts.add(info);
    }

    private void loadBackButton(){
        Button button = new Button("Back");
        button.setStyle("-fx-background-color: #000000");
        button.setTranslateX(Asteroids.WIDTH/4d);
        button.setTranslateY(Asteroids.GAME_HEIGHT/4.5d);
        button.setTextFill(Color.WHITE);
        button.setOnMouseClicked(event->{
            new StartScreen(screen).start();
            stop();
        });
        parts.add(button);
    }
}
