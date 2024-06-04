package me.stephenminer.asteroids2.scenes.map;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;
import me.stephenminer.asteroids2.scenes.sector.Sector;
import me.stephenminer.asteroids2.scenes.sector.event.InvasionTimer;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Sector> sectors;
    private GameScreen screen;
    private final int endDist;
    private int distTravelled;
    private List<Node> displayParts;
    private Ship marker;

    public Map(GameScreen screen, int endDist){
        this.screen = screen;
        this.endDist = endDist;
    }



    public void longJump(){
        distTravelled += 3;
        InvasionTimer timer = screen.getTimer();
        timer.setCurrent(Math.max(timer.getCurrent() - 2*1000,0));
    }
    public void shortJump(){
        distTravelled += 2;
    }


    public void display(){
        displayParts = new ArrayList<>();
        loadLabel();
        loadBar();
    }

    private void loadBar(){
        Rectangle line = new Rectangle();
        line.setWidth(200);
        line.setStroke(Color.BLACK);
        line.setHeight(5);
        line.setX(Asteroids.WIDTH/2d-100);
        line.setY(Asteroids.GAME_HEIGHT/2d -5);
        line.setFill(Color.BLACK);
        displayParts.add(line);
        screen.root().getChildren().add(line);
        float ratio = distTravelled/(float)endDist;
        double x = line.getX() + ratio * line.getWidth();
        double y = line.getY() + (line.getHeight()/2d);
        this.marker = new Ship(screen, x,y,0,true);
    }

    private void loadLabel(){
        Text text = new Text(distTravelled + "/" + endDist + "kilo-light-years travelled");
        text.setX(Asteroids.WIDTH/2d-100);
        text.setY(Asteroids.GAME_HEIGHT/2d - 20);
        displayParts.add(text);
        screen.root().getChildren().add(text);
    }



    public void stopDisplay(){
        displayParts.forEach((node)->screen.root().getChildren().remove(node));
        marker.remove(false);
    }
    public int getEndDist(){ return endDist; }
    public int getTravelled(){ return distTravelled; }






}
