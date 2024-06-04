package me.stephenminer.asteroids2.entity.mothership;

import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.equipment.BroadsideLaser;
import me.stephenminer.asteroids2.equipment.Weapon;
import me.stephenminer.asteroids2.equipment.Weapons;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ShipBody extends MotherShipPart{
    private List<GunBattery> batteryList;
    private List<GunBroadside> broadsideList;
    private ShieldGenerator shieldGen;

    private List<MotherShipPart> parts;
    private Entity target;
    private double rot;

    public ShipBody(GameScreen screen, double x, double y, float speed){
        super(screen,x,y,speed);
        setMaxHealth(9999,true);
        parts = new ArrayList<>();
        loadBatteries();
        loadBroadside();
    }


    private void loadBatteries(){
        int batteries = 8;
        double currentX = x-220;
        batteryList = new ArrayList<>();
        for (int i= 0; i < batteries; i++){
            GunBattery battery = new GunBattery(screen,currentX,y,0);
            currentX += 60;
            Weapon weapon = batteryWeapon(battery);
            weapon.setCurrent(0);
            battery.setWeapon(weapon);
            battery.setTarget(target);
            battery.startWeapons();
            batteryList.add(battery);
            battery.getShape().toBack();
        }
        shape.toBack();
        parts.addAll(batteryList);
    }
    private void loadBroadside(){
        broadsideList = new ArrayList<>();
        double adder = -3*40;
        for (int i = 1; i < 4; i++){
            double x = this.x+adder;
            adder += 3*40;
            System.out.println(adder);
            GunBroadside up = new GunBroadside(screen,x, y - (2.4*40),0f,true);
            GunBroadside down = new GunBroadside(screen,x, y + (2.4*40),0f,false);
            up.getShape().toBack();
            up.startWeapon();
            down.startWeapon();
            down.getShape().toBack();
            broadsideList.add(up);
            broadsideList.add(down);
        }
        parts.addAll(broadsideList);
    }

    private Weapon batteryWeapon(GunBattery battery){
        Weapons[] selection = new Weapons[]{Weapons.ADVANCED_LASER,Weapons.BARRAGE_LASER,Weapons.TWIN_MISSILE,Weapons.FALCON_MISSILE};
        Weapons type = selection[ThreadLocalRandom.current().nextInt(selection.length)];
        return Weapon.construct(type,battery);
    }


    @Override
    protected void loadCorners() {
        xCorners = new Double[]{-8d,-7d, -6d,0d,6d,7d,8d,7d,6d,0d,-6d,-7d,-8d,-7d,-6d,-5d,0d,5d,6d,6.5d,6d,5d,0d,-5d,-6d,-6.5d,-6d};
        yCorners = new Double[]{0d,1d,1.5d,1.5d,1.5d,1d,0d,-1d,-1.5d,-1.5d,-1.5d,-1d,0d,1d,1.5d,2.5d,2.5d,2.5d,1.5d,0d,-1.5d,-2.5d,-2.5d,-2.5d,-1.5d,0d,1.5d};
        scale(40);

    }

    @Override
    public void updatePoints() {
        super.updatePoints();
        shape.setRotate(rot);

    }

    @Override
    public void remove(int index, boolean deathEffect) {
        super.remove(index, deathEffect);
        parts.forEach(part->part.remove(false));
    }

    public Entity getTarget(){ return target; }
    public void setTarget(Entity target){
        this.target = target;
        batteryList.forEach(battery->battery.setTarget(target));
    }

    public double getRot(){ return rot; }
    public void setRot(double rot){ this.rot = rot;}
}
