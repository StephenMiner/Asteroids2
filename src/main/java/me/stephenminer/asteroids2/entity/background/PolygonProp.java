package me.stephenminer.asteroids2.entity.background;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class PolygonProp extends Prop{
    private Double[] xCorners,yCorners;
    private Polygon shape;

    public PolygonProp(GameScreen screen, double x, double y, float speed, Double[] xCorners, Double[] yCorners) {
        super(screen, x, y, speed);
        this.xCorners = xCorners;
        this.yCorners = yCorners;
        scale(4);
    }


    @Override
    public void initShape() {

    }

    private void scale(int factor){
        for (int i = 0; i < xCorners.length; i++){
            xCorners[i] = xCorners[i] * factor;
            yCorners[i] = yCorners[i] * factor;
        }
    }

    @Override
    public void updateShape() {
        shape.getPoints().clear();
        for (int i = 0; i < xCorners.length; i++){
            double xp = xCorners[i] + x;
            double yp = yCorners[i] + y;
            shape.getPoints().add(xp);
            shape.getPoints().add(yp);
        }
        shape.setStroke(Color.WHITE);
    }
}
