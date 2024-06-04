package me.stephenminer.asteroids2.entity.background;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class Star extends Prop {
    private double radius;
    private Color color;

    public Star(GameScreen screen,double radius, Color color, double x, double y, float speed) {
        super(screen, x, y, speed);
        this.radius = radius;
        this.color = color;
    }
    public Star(GameScreen screen,double radius, Color color, double x, double y){
        this(screen,radius, color, x, y, 0f);
    }
    public Star(GameScreen screen,double radius, double x, double y, float speed){
        this(screen,radius, Color.WHITE, x, y, speed);
    }
    public Star(GameScreen screen,double radius, double x, double y){
        this(screen,radius, Color.WHITE, x, y, 0f);
    }



    @Override
    public void initShape() {
        Circle circle = new Circle(radius, color);
        this.shape = circle;
    }

    @Override
    public void updateShape() {
        Circle circle = (Circle) shape;
        if (circle.getRadius() != radius) circle.setRadius(radius);
        if (circle.getFill() != color) circle.setFill(color);
        circle.setCenterX(x);
        circle.setCenterY(y);
    }


    public double getRadius(){ return radius; }
    public Color getColor(){ return color; }

    public void setRadius(double radius){ this.radius = radius; }
    public void setColor(Color color){ this.color = color; }
}
