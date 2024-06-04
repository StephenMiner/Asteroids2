package me.stephenminer.asteroids2.entity.background;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.concurrent.ThreadLocalRandom;

public class AlienCruiser{
    protected Double[] xBody, yBody;
    protected Double[] xSlit, ySlit;
    protected Double[] xmGun, ymGun;
    protected Double[] xGuns, yGuns;
    protected Polygon body, slits, mainGun, flaps, guns;
    protected double x,y, ax, ay;
    protected GameScreen screen;

    public AlienCruiser(GameScreen screen,double x, double y){
        this.screen = screen;
        this.x = x;
        this.y = y;
        load();
    }

    public void drift(){
        x+=ax;
        y+=ay;
    }

    public void scale(int factor){
        for (int i = 0; i < xBody.length;i++){
            xBody[i] = xBody[i]*factor;
            yBody[i] = yBody[i]*factor;
        }
        for (int i = 0; i < xmGun.length; i++){
            xmGun[i] = xmGun[i]*factor;
            ymGun[i] = ymGun[i]*factor;
        }
        for (int i = 0; i < xSlit.length; i++){
            xSlit[i] = xSlit[i]*factor;
            ySlit[i] = ySlit[i]*factor;
        }

    }

    public void decelerate(){
        if (ax > 0)
            ax-= ThreadLocalRandom.current().nextDouble(0.01,0.021);
        else ax = 0;
        if (ay > 0)
            ay-=0.02;
        else ay = 0;
    }

    public void updateShapes(){
        updateBody();
        updateMGun();
        updateSlits();
    }

    private void updateBody(){
        body.getPoints().clear();
        for (int i = 0; i < xBody.length; i++){
            body.getPoints().add(x + xBody[i]);
            body.getPoints().add(y + yBody[i]);
        }

    }
    private void updateMGun(){
        mainGun.getPoints().clear();
        for (int i = 0; i < xmGun.length; i++){
            mainGun.getPoints().add(x + xmGun[i]);
            mainGun.getPoints().add(y + ymGun[i]);

        }
    }
    private void updateSlits(){
        slits.getPoints().clear();
        for (int i = 0; i < xSlit.length; i++){
            slits.getPoints().add(x + xSlit[i]);
            slits.getPoints().add(y + ySlit[i]);
        }
    }
    public void tick(){
        drift();
        decelerate();
        updateShapes();
        if (screen.root().getChildren().contains(body)) ;
    }


    public void load(){
        loadBody();
        loadMGun();
        loadSlits();
        mainGun.toBack();
        body.toBack();
        slits.toBack();
        scale(-16);
    }


    private void loadBody(){

        xBody = new Double[]{-3d,-2d,-1d,0.5d,2.5d,3.5d,5d,5d};
        yBody = new Double[]{-1d,0.5d,0.5d,2.5d,2.5d,0.5d,0.5d,-1d};
        body = new Polygon();
        for (int i = 0; i < xBody.length; i++){
            body.getPoints().add(xBody[i] );
            body.getPoints().add(yBody[i] );
        }
        body.setStroke(Color.DARKRED);
        body.setFill(null);
        screen.root().getChildren().add(body);
    }

    private void loadMGun(){
        xmGun = new Double[]{-5d,-1.5d,-2d,-5d};
        ymGun = new Double[]{0.1d,0.1d,-0.5d,-0.5d};
        mainGun = new Polygon();
        for (int i = 0; i < xmGun.length; i++){
            mainGun.getPoints().add(xmGun[i]);
            mainGun.getPoints().add(ymGun[i]);
        }
        screen.root().getChildren().add(mainGun);
        mainGun.setStroke(Color.DARKRED);
        mainGun.setFill(null);
    }

    private void loadSlits(){
        xSlit = new Double[]{-1d,-0.75d,-0.25d,0d,-0.25d,-0.75d,-1d,0d,0.5d,0.75d,1.25d,1.5d,1.25d,0.75d,0.5d,1.5d,2d,2.25d,2.75d,3d,2.75d,2.25d,2d,3d};
        ySlit = new Double[]{0d,0.25d,0.25d,0d,-0.25d,-0.25d,0d,0d,0d,0.25d,0.25d,0d,-0.25d,-0.25d,0d,0d,0d,0.25d,0.25d,0d,-0.25d,-0.25d,0d,0d};
        slits = new Polygon();
        for (int i = 0; i < xSlit.length; i++){
            slits.getPoints().add(xSlit[i]);
            slits.getPoints().add(ySlit[i]);
        }
        slits.setStroke(Color.DARKRED);
        slits.setFill(null);
        screen.root().getChildren().add(slits);
    }


    public void remove(){
        screen.root().getChildren().remove(body);
        screen.root().getChildren().remove(slits);
        screen.root().getChildren().remove(mainGun);
        screen.root().getChildren().remove(flaps);
        screen.root().getChildren().remove(slits);
    }




    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getAx(){ return ax; }
    public double getAy(){ return ay;}

    public void setX(double x){ this.x = x; }
    public void setY(double y){ this.y = y; }
    public void setAx(double ax){ this.ax = ax; }
    public void setAy(double ay){ this.ay = ay; }


}
