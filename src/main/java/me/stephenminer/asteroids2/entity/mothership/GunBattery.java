package me.stephenminer.asteroids2.entity.mothership;

import javafx.application.Platform;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.equipment.Targeter;
import me.stephenminer.asteroids2.equipment.Weapon;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.Timer;
import java.util.TimerTask;

public class GunBattery extends MotherShipPart {
    private Weapon weapon;
    private Entity target;

    private boolean disabled;

    public GunBattery(GameScreen screen, double x, double y, float speed, int maxHealth){
        super(screen,x,y,speed);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public GunBattery(GameScreen screen, double x, double y, float speed){
        this(screen,x,y,speed,5);
    }

    @Override
    protected void loadCorners() {
        xCorners = new Double[]{-2d,-2d,2d,2d,2d,-2d,-2d,-1d,-1d,-0.1d,-0.1d,0.1d,0.1d,1d,1d,2d};
        yCorners = new Double[]{0d,2d,2d,0d,-2d,-2d,0d,0d,-3d,-3d,0d,0d,-3d,-3d,0d,0d};
        scale(10);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean drift(){
        if (!disabled) angle = calcRotAngle();
        return super.drift();
    }

    public void startWeapons(){
        Timer timer = new Timer();
        weapon.runCooldown();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (disabled || weapon == null || (screen.getSector() != null && screen.getSector().paused())) return;
                if (dead) {
                    cancel();
                    return;
                }
                Platform.runLater(()->weapon.shoot(screen));
            }
        }, 1,250);
    }

    private double calcRotAngle(){
        if (target == null || target.isDead())return 0;
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        return Math.toDegrees(Math.atan2(dy,dx)) + 90;
    }




    public Weapon getWeapon(){ return weapon; }
    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
        if (weapon instanceof Targeter) ((Targeter) weapon).setTarget(target);
    }

    public Entity getTarget(){ return target; }
    public void setTarget(Entity target){
        this.target = target;
        if (weapon instanceof Targeter) ((Targeter) weapon).setTarget(target);
    }

    public boolean isDisabled(){ return disabled; }
    public void setDisabled(boolean disabled){ this.disabled = disabled; }



}
