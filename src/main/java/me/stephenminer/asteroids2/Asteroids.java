package me.stephenminer.asteroids2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;
import me.stephenminer.asteroids2.scenes.StartScreen;

import java.io.IOException;

public class Asteroids extends Application {
    public static int WIDTH = 700;
    public static int HEIGHT = 700;
    public static int GAME_HEIGHT = 650;
    public GameScreen gameScreen;
    public Scene scene;
    public Ship player;
    @Override
    public void start(Stage stage) throws IOException {
        gameScreen = new GameScreen(null);
        Scene scene = new Scene(gameScreen.root(), WIDTH, HEIGHT);
        scene.setFill(Color.BLACK);
        this.scene = scene;
        gameScreen.setScene(scene);
        gameScreen.run();
        StartScreen startScreen = new StartScreen(gameScreen);
        startScreen.start();
        initShipEvents();
        stage.setTitle("A Lonely Odyssey");
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image("Icon.png"));
        stage.setOnCloseRequest(windowEvent -> {
            gameScreen.setClose(true);
            Platform.exit();
            System.exit(0);
        });
    }




    private void initShipEvents(){

    }
    public static void main(String[] args) {
        launch();
        //RouteCipher cipher = new RouteCipher(2,4 );
      //  System.out.println(cipher.encryptMessage("dog"));
    }
}