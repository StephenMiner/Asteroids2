package me.stephenminer.asteroids2.scenes.shiplog;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import me.stephenminer.asteroids2.entity.Entity;

import java.util.List;

public class ShipCard {
    private List<Node> components;
    private Entity entity;
    private final double cWidth, cHeight;
    private double x, y;

    public ShipCard(Entity dummy, double x, double y){
        cWidth = 100;
        cHeight = 25;
        this.entity = dummy;
        this.x = x;
        this.y = y;
    }

    public void initDisplay(){
        Rectangle rectangle = new Rectangle();
        //rectangle.set
    }

    public double cWidth(){ return cWidth; }
    public double cHeight(){ return cHeight; }
    public double getX(){ return x; }
    public double getY(){ return y; }
    public Entity entity(){ return entity; }

    public void setX(double x){ this.x = x;}
    public void setY(double y){ this.y = y; }

}
