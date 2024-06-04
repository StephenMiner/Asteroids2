package me.stephenminer.asteroids2.entity.mothership;

import me.stephenminer.asteroids2.equipment.BroadsideLaser;
import me.stephenminer.asteroids2.equipment.Weapon;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.Timer;
import java.util.TimerTask;

public class GunBroadside extends MotherShipPart{
    private boolean disabled;
    private boolean up;
    private Weapon weapon;

    public GunBroadside(GameScreen screen, double x, double y, float speed, boolean up){
        super(screen,x,y,speed);
        this.up = up;
        initWeapon();
        loadCorners();
    }

    private void initWeapon(){
        BroadsideLaser laser = new BroadsideLaser("BroadSide Laser",this,10*1000,3,400,24,100,4, up);
        this.weapon = laser;
        laser.setCurrent(0);
        laser.runCooldown();
    }

    public void startWeapon(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (disabled || weapon == null || (screen.getSector() != null && screen.getSector().paused())) return;
                if (dead){
                    cancel();
                    return;
                }
                weapon.shoot(screen);
            }
        }, 1,1000);
    }

    @Override
    protected void loadCorners() {
        xCorners = new Double[]{-5d,-5d,-4d,-4d,-2d,-2d,-1d,-1d,1d,1d,2d,2d,4d,4d,5d,5d};
        yCorners = new Double[]{0d,3d,3d,0.3d,0.3d,3d,3d,0.3d,0.3d,3d,3d,0.3d,0.3d,3d,3d,0d};
        if (up) scale(-8);
        else scale(8);
    }



    public boolean isDisabled(){ return disabled; }
    public void setDisabled(boolean disabled){ this.disabled = disabled; }





}
