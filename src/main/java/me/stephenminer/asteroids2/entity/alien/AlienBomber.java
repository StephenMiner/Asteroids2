package me.stephenminer.asteroids2.entity.alien;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.equipment.MissileLauncher;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class AlienBomber extends SaucerAlien{
    private Double[] xExtra;
    private Double[] yExtra;
    private Shape extra;

    private double extraRot;
    public AlienBomber(GameScreen screen, double x, double y, float speed, Entity target) {
        super(screen, x, y, speed, target);
    }

    @Override
    protected void init() {
        super.init();
        loadExtra();
        screen.root().getChildren().add(extra);
    }

    @Override
    protected void initGear() {
        MissileLauncher launcher = new MissileLauncher(this, 8*1000);
        launcher.addWhitelist(Ship.class);
        launcher.setCurrent(0);
        launcher.runCooldown();
        this.weapon = launcher;
    }
    protected void loadExtra(){
        extra = new Polygon();
        for (int i = 0; i < xExtra.length; i++){
            ((Polygon)extra).getPoints().add(xExtra[i]);
            ((Polygon) extra).getPoints().add(yExtra[i]);
        }
    }

    @Override
    protected void loadCorners() {
        super.loadCorners();
        yExtra = new Double[]{-0.5d,0.5d,0.5d,-0.5d};
        xExtra = new Double[]{-0.5d,-0.5d,-3d,-3d};
        scaleExtra(-4);
    }

    private void scaleExtra(int scale){
        for (int i = 0; i < xExtra.length; i++){
            xExtra[i] = xExtra[i] * scale;
            yExtra[i] = yExtra[i] * scale;
        }
    }

    @Override
    public boolean drift() {
        extraRot = targetRot();
        return super.drift();
    }

    @Override
    public void deathEffect() {
        super.deathEffect();
    }

    @Override
    public void remove(int index, boolean deathEffect) {
        super.remove(index, deathEffect);
        screen.root().getChildren().remove(extra);
    }

    protected double targetRot(){
        if (target == null) return 0;
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        return Math.toDegrees(Math.atan2(dy,dx));
    }

    @Override
    public void updatePoints() {
        super.updatePoints();
        if (extra == null) return;
        Polygon extra = (Polygon) this.extra;
        extra.getPoints().clear();
        for (int i = 0; i < xExtra.length; i++){
            double xp = xExtra[i] + x;
            double yp = yExtra[i] + y;
            extra.getPoints().add(xp);
            extra.getPoints().add(yp);
        }
        extra.setRotate(extraRot);
        extra.setStroke(Color.WHITE);
        if (screen.getSector() != null)
            extra.setFill(screen.getSector().getFill());
        else extra.setFill(null);
    }
}
