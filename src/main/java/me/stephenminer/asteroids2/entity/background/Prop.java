package me.stephenminer.asteroids2.entity.background;

import javafx.scene.shape.Shape;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.scenes.GameScreen;

public abstract class Prop {
    protected Shape shape;
    protected GameScreen screen;
    protected double x,y,angle;
    protected double ax, ay;
    protected float speed;
    protected boolean bounce;
    protected boolean remove;


    public Prop(GameScreen screen,double x, double y, float speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.screen = screen;
        initShape();
        screen.addProp(this);


    }

    public boolean drift(){
        x += ax;
        y += ay;
        boolean boundsHit = false;
        if (x >= Asteroids.WIDTH) {
            if (!bounce) x = 1;
            else ax *=-1;
            boundsHit = true;
        }
        if (x <= 0){
            if (!bounce) x = Asteroids.WIDTH-1;
            else ax *=-1;
            boundsHit = true;
        }
        if (y >= Asteroids.GAME_HEIGHT) {
            if (!bounce) y = 1;
            else ay *= -1;
            boundsHit = true;
        }
        if (y <= 0) {
            if (!bounce) y = Asteroids.GAME_HEIGHT-1;
            else ay *= -1;
            boundsHit = true;
        }
        if (boundsHit && bounce) {
            angle += 180;
        }

        return boundsHit;
    }


    public void remove(int i){
        remove = true;
        screen.removeProp(i);
    }

    public abstract void initShape();
    public abstract void updateShape();


    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getAx(){ return ax; }
    public double getAy(){ return ay; }
    public float getSpeed(){ return speed; }
    public boolean bounces(){ return bounce; }
    public double getAngle(){ return angle; }
    public boolean toRemove(){ return remove; }
    public Shape getShape(){ return shape; }

    public void setX(double x){ this.x = x; }
    public void setY(double y){ this.y = y; }
    public void setAx(double ax){ this.ax = ax; }
    public void setAy(double ay){ this.ay = ay; }
    public void setSpeed(float speed){ this.speed = speed;}
    public void setBounce(boolean bounce){ this.bounce = bounce; }
    public void setRemove(boolean remove){ this.remove = remove; }
    public void setAngle(double angle){ this.angle = angle; }
}
