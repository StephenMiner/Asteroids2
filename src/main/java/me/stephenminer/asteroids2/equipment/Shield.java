package me.stephenminer.asteroids2.equipment;

import javafx.animation.AnimationTimer;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import me.stephenminer.asteroids2.entity.Entity;

import java.util.Timer;
import java.util.TimerTask;

public class Shield {
    private Ellipse circle;
    private Entity holder;
    private int health;
    private int recharge;
    private int maxHealth;

    public Shield(Entity holder, int maxHealth, int recharge){
        this.holder = holder;
        this.maxHealth = maxHealth;
        this.recharge = recharge;
        this.health = maxHealth;
        initModel();
    }

    private void initModel(){
        Shape shape = holder.getShape();
        Bounds bounds = shape.getLayoutBounds();

        circle = new Ellipse(bounds.getWidth(), bounds.getHeight());
        circle.setOpacity(0.5);
        circle.setFill(fromHealth());
    }

    private Color fromHealth(){
        double ratio = ((double) health) /maxHealth;
        if (ratio > (2d/3)) return Color.GREEN;
        else if (ratio <= (1d/3)) return Color.RED;
        else return Color.YELLOW;
    }

    private void updatePos(){
        circle.setCenterX(centerX());
        circle.setCenterY(centerY());
        circle.setFill(fromHealth());
        if (health < 1) circle.setOpacity(0);
        else circle.setOpacity(0.5);
    }

    private double centerX(){
        Bounds bounds = holder.getShape().getLayoutBounds();
        return (bounds.getMaxX()+ bounds.getMinX()) / 2;
    }

    private double centerY(){
        Bounds bounds = holder.getShape().getLayoutBounds();
        return (bounds.getMaxY()+bounds.getMinY())/2;
    }

    public void tick(){
        updatePos();
    }



    public void damageShield(){
        health--;
        circle.setFill(fromHealth());
        regenerateShield();
    }

    public void regenerateShield(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                if (count >= recharge){
                    count = 0;
                    health++;
                    this.cancel();
                    return;
                }
                count++;
            }
        },1,1);

    }

    public boolean disabled(){ return health < 1; }
    public int getHealth(){ return health; }
    public void setHealth(int health){ this.health = health; }
    public Ellipse getShape(){ return circle; }
}
