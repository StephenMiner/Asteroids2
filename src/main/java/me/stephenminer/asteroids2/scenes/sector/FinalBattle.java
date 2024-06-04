package me.stephenminer.asteroids2.scenes.sector;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.mothership.ShipBody;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class FinalBattle extends Sector{
    private Button prompter;
    private Text info;
    public FinalBattle(GameScreen screen, Ship ship) {
        super(screen, ship, Color.BLACK, "Final Destination");
        settings.setStarts(0,20,0,0);
        settings.setMaxs(2,25,0,1);
        settings.setRates(10*1000,15*1000,0,25*1000);
    }

    @Override
    public void start(){
        loadNodes();
    }
    private void loadNodes(){
        info = new Text("There is no running away now. The alien mothership is isolated from the main cruiser fleet but is locked onto your location jamming our warpdrive. You must fight our way out, good luck.");
        info.setX(Asteroids.WIDTH/2d);
        info.setY(Asteroids.HEIGHT/4d);
        info.setWrappingWidth(200);
        info.setMouseTransparent(true);
        info.setStroke(Color.WHITE);
        info.setFill(Color.WHITE);
        info.toBack();

        prompter = new Button("Accept");
        prompter.setStyle("-fx-border-color: white;-fx-background-color: transparent;");
        prompter.setTextFill(Color.WHITE);
        prompter.setPrefSize(100,50);
        prompter.setTranslateX(Asteroids.WIDTH/2d);
        prompter.setTranslateY(Asteroids.HEIGHT/2d);
        prompter.setOnMouseClicked((event)->init());

        screen.root().getChildren().add(info);
        screen.root().getChildren().add(prompter);
    }

    private void init(){
        super.start();
        screen.root().getChildren().remove(prompter);
        screen.root().getChildren().remove(info);
        new ShipBody(screen,Asteroids.WIDTH/2d,Asteroids.GAME_HEIGHT/2d,0f).setTarget(ship);
    }

    @Override
    public void stop() {
        super.stop();
    }
}
