package me.stephenminer.asteroids2.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Entity {
    protected GameScreen screen;
    protected double x, y;
    protected float speed;
    protected double ax, ay, angle;
    protected Shape shape;
    protected boolean dead;
    protected boolean bounce;
    protected boolean dummy;
    protected boolean prop;
    protected int weight;
    protected Double[] xCorners,yCorners;

    public Entity(GameScreen screen, double x, double y, float speed){
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.speed = speed;
        dead = false;
        shape = new Polygon();
        init();
    }

    public Entity(GameScreen screen, double x, double y, float speed, boolean dummy){
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.dummy = dummy;
        init();
    }

    protected void init(){
        loadCorners();
        loadPolygon();
        screen.addEntity(this);
    }


    protected abstract void loadCorners();
    protected void loadPolygon(){
        shape = new Polygon();
        for (int i = 0; i < xCorners.length; i++){
            ((Polygon)shape).getPoints().add(xCorners[i]);
            ((Polygon) shape).getPoints().add(yCorners[i]);
        }
    }

    protected void scale(int scalar){
        for (int i = 0; i < xCorners.length; i++){
            xCorners[i] = xCorners[i] * scalar;
            yCorners[i] = yCorners[i] * scalar;
        }
    }

    /**
     *
     * @return true if boundderies hit, false if otherwise
     */
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
            angle = Math.toDegrees(Math.atan2(ay/speed,ax/speed));
        }

        return boundsHit;
    }
    //xp = x-point; yp = y-point
    public void updatePoints(){
        Polygon shape = (Polygon) this.shape;
        shape.getPoints().clear();
        for (int i = 0; i < xCorners.length; i++){
            double xp = xCorners[i] + x;
            double yp = yCorners[i] + y;
            shape.getPoints().add(xp);
            shape.getPoints().add(yp);
        }
        shape.setRotate(angle);
        shape.setStroke(Color.WHITE);
        if (screen.getSector() != null)
            shape.setFill(screen.getSector().getFill());
        else shape.setFill(null);
    }

    public void tick(){
        if (dead || screen.getSector() != null && screen.getSector().paused()) return;
        drift();
        updatePoints();
    }


    public void deathEffect(){

    }

    public boolean onKill(Entity killer){
        return killer instanceof Ship;
    }



    public void remove(int index, boolean deathEffect){
        dead = true;
        if (index == -1) return;
        screen.removeEntity(index);
        if (this instanceof Shieldable){
            Shieldable shieldable = (Shieldable) this;
            shieldable.removeShield(screen);
        }
        if (deathEffect) deathEffect();
    }
    public void remove(){
        remove(true);
    }
    public void remove(int index){
        remove(index,true);
    }

    public void remove(boolean deathEffect){
        remove(screen.getEntities().indexOf(this),deathEffect);
    }


    protected List<Entity> nearby(int dist, boolean useDistSqr){
        if (useDistSqr) {
            double distSqr = dist*dist;
            return screen.getEntities().stream().filter(entity -> distSqr(this, entity) < distSqr && !entity.isDead()).collect(Collectors.toList());
        }return screen.getEntities().stream().filter(entity -> dist(this, entity) < dist && !entity.isDead()).collect(Collectors.toList());
    }
    protected List<Entity> nearby(int dist) {
        return nearby(dist,false);
    }
    protected double dist(Entity e1, Entity e2){
        return Math.sqrt(distSqr(e1,e2));
    }
    protected double distSqr(Entity e1, Entity e2){
        return Math.pow(e2.getX()-e1.getX(),2) + Math.pow(e2.getY()-e1.getY(),2);
    }







    public double getX(){ return x; }
    public double getY(){ return y; }

    public double getAx(){ return ax; }
    public double getAy(){ return ay; }
    public double getAngle(){ return angle; }

    public float getSpeed(){ return speed; }
    public boolean isDead(){ return dead; }
    public boolean bounces(){ return bounce;}
    public boolean isProp(){ return prop; }

    public GameScreen getScreen(){ return screen; }

    public Shape getShape(){ return shape; }

    public void setX(double x){ this.x = x;}
    public void setY(double y){ this.y = y; }
    public void setSpeed(float speed){ this.speed = speed; }
    public void setDead(boolean dead){
        setDead(dead,null);
    }
    public void setDead(boolean dead, Entity killer){
        this.dead = dead;
        onKill(killer);
    }
    public void setBounce(boolean bounce){ this.bounce = bounce; }
    public void setAx(double ax){ this.ax = ax; }
    public void setAy(double ay){ this.ay = ay;}
    public void setangle(double angle){ this.angle = angle; }
    public void setProp(boolean prop){ this.prop = prop; }

    public int getWeight(){ return weight;}
    public void setWeight(int weight){ this.weight = weight; }
}
