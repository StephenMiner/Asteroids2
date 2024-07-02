package me.stephenminer.asteroids2.entity.ship;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import me.stephenminer.asteroids2.entity.Debris;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.equipment.Shield;
import me.stephenminer.asteroids2.entity.Shieldable;
import me.stephenminer.asteroids2.entity.alien.AlienDebris;
import me.stephenminer.asteroids2.entity.asteroid.Asteroid;
import me.stephenminer.asteroids2.equipment.*;
import me.stephenminer.asteroids2.scenes.GameScreen;
import me.stephenminer.asteroids2.scenes.map.HyperSpaceAnim;
import me.stephenminer.asteroids2.scenes.map.HyperSpaceCooldown;

import java.util.ArrayList;
import java.util.List;

public class Ship extends Entity implements Shieldable {
    private List<Weapon> weapons;
    private Inventory supplies;
    private Shield shield;
    private int activeSlot;
    private boolean canJump, warping;
    private HyperSpaceCooldown cooldown;
    public Ship(GameScreen screen, double x, double y, float speed) {
        super(screen, x, y,speed);
        weight = 20;
        weapons = new ArrayList<>();
        activeSlot = -1;
        supplies = new Inventory(this,2,5,2);
        Weapon weapon = Weapon.construct(Weapons.BASIC_LASER,this);
        addWeapon(weapon);
        addWeapon(Weapon.construct(Weapons.BARRAGE_LASER,this));
        addWeapon(Weapon.construct(Weapons.ADVANCED_LASER,this));
        shield = new Shield(this, 3,10*1000);
        addShield(screen);
        cooldown = new HyperSpaceCooldown(screen, this, 15*1000);
        cooldown.start();
    }

    public Ship(GameScreen screen, double x, double y, float speed, boolean dummy){
        super(screen,x,y,speed,dummy);

    }

    @Override
    protected void loadCorners(){
        xCorners = new Double[]{-8d,8d,-8d,-4d};
        yCorners = new Double[]{6d,0d,-6d,0d};
    }


    public void accelerateForwards(){
        ax += Math.cos(Math.toRadians(angle)) * speed;
        ay += Math.sin(Math.toRadians(angle)) * speed;
    }
    public void accelerateBackwards(){
        ax -= Math.cos(Math.toRadians(angle)) * speed;
        ay -= Math.sin(Math.toRadians(angle)) * speed;
    }

    private boolean rotating;
    public void rotate(double increment){
        /*
        if (rotating) return;
        rotating = true;
        new AnimationTimer(){
            final double add = increment/8;
            double current = 0;
            @Override
            public void handle(long l) {
                double next = current + add;
                current = angle;
                angle += Math.toDegrees(add);
                if (next >= increment){
                    rotating = false;
                    stop();
                }

            }
        }.start();

         */
        angle += Math.toDegrees(increment);


    }

    @Override
    public void tick(){
        super.tick();
        if (shield == null) return;
        shield.tick();
    }


    public void setWeapon(int slot){
        if (slot >= weapons.size()) {
            slot = 0;
        }
        activeSlot = slot+1;
    }
    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
        int index = weapons.indexOf(weapon);
        activeSlot = index + 1;
        WeaponDisplay display = new WeaponDisplay(screen, weapon);
        display.start();
        weapon.setDisplay(display);
    }
    public void removeWeapon(Weapon weapon){
        int slot = weaponSlot(weapon);
        if (slot == activeSlot) activeSlot = 1;
        weapon.getDisplay().end();
        weapons.remove(slot-1);
    }


    public void useWeapon(GameScreen screen){
        if (!dead && activeSlot - 1 < weapons.size() && !weapons.isEmpty()){
            Weapon weapon = weapons.get(activeSlot-1);
            weapon.shoot(screen);
        }
    }

    public void useWeapon(GameScreen screen, Weapon weapon){
        FiringCost cost = weapon.getFiringCost();
        boolean shoot = cost == null || cost.extractCosts(supplies);
        if (shoot) weapon.shoot(screen);
    }

    public void lightSpeed(){
        if (!canJump() || dead)return;
        HyperSpaceAnim hyperSpaceAnim = new HyperSpaceAnim(screen);
        hyperSpaceAnim.start();
        canJump = false;
        supplies.incFuel(-1);
        cooldown.start();
    }

    @Override
    public void remove(int index) {
        super.remove(index);
        screen.stop();
    }

    @Override
    public void deathEffect(){
        for (int i = 0; i < 4; i++){
            ShipDebris debris = new ShipDebris(i,this);
        }
    }

    @Override
    public Shield getShield(){ return shield; }
    @Override
    public void setShield(Shield shield){
        removeShield(screen);
        this.shield = shield;
        addShield(screen);
    }

    public Entity debrisCollision(){
        List<Entity> near = nearby(35, true);
        near.remove(this);
        for (Entity entity : near){
            if (entity instanceof Asteroid || entity instanceof Debris){
                Shape inter = Shape.intersect(shape,entity.getShape());

                if (inter.getLayoutBounds().getHeight() > 0 && inter.getLayoutBounds().getWidth() > 0) return entity;
            }
        }
        return null;
    }

    public boolean canJump(){ return supplies.getFuel() > 0 && canJump; }
    public boolean isWarping(){ return warping; }
    public void setCanJump(boolean canJump){  this.canJump = canJump; }
    public void setWarping(boolean warping){ this.warping = warping; }

    public Inventory supplies(){ return supplies;}

    public List<Weapon> getWeapons(){ return weapons; }

    public int weaponSlot(Weapon weapon){
        return weapons.indexOf(weapon) + 1;
    }
    public int getActiveSlot(){ return activeSlot; }

    public HyperSpaceCooldown getHyperSpaceCD(){
        return cooldown;
    }




}
