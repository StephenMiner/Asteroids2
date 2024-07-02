package me.stephenminer.asteroids2.equipment;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.stage.Screen;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.projectile.Projectile;
import me.stephenminer.asteroids2.entity.ship.FiringCost;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Weapon implements Equipment{
    protected final Entity holder;
    protected final Class<? extends Projectile> type;
    protected FiringCost firingCost;
    //How long it takes for the weapon to charge;
    protected int cooldown;
    //Weapons current charge status
    protected int current;
    //Amount of projectiles per volley
    protected int volley;
    //Time between each shot
    protected int timeBetween;
    protected int cost;
    protected final Set<Class<? extends Entity>> whitelist;
    protected String name;
    protected WeaponDisplay display;

    public Weapon(String name, Entity holder, Class<? extends Projectile> type, int cooldown, int volley, int timeBetween){
        this(name, holder, type, cooldown, volley, timeBetween, null);
    }
    public Weapon(String name, Entity holder, Class<? extends Projectile> type, int cooldown, int volley, int timeBetween, FiringCost firingCost){
        this.whitelist = new HashSet<>();
        this.holder = holder;
        this.type = type;
        this.cooldown = cooldown;
        this.volley = volley;
        this.timeBetween = timeBetween;
        this.name = name;
        this.firingCost = firingCost;
        current = cooldown;
    }

    public static Weapon construct(Weapons weaponType, Entity holder){
        Class<? extends Weapon> type = weaponType.type();
        try {
            Constructor<? extends Weapon> constructor = type.getConstructor(String.class, Entity.class, int.class, int.class, int.class, FiringCost.class);
            Weapon weapon = constructor.newInstance(weaponType.title(),holder,weaponType.cooldown(),weaponType.volley(),weaponType.timeBetween(), weaponType.firingCost());
            weapon.setCost(weaponType.cost());
            return weapon;
        }catch (Exception e){
            try{
                Constructor<? extends Weapon> constructor = type.getConstructor(String.class, Entity.class, int.class, int.class, int.class);
                Weapon weapon = constructor.newInstance(weaponType.title(),holder,weaponType.cooldown(),weaponType.volley(),weaponType.timeBetween());
                weapon.setCost(weaponType.cost());
                return weapon;
            }catch (Exception ignored){}
        }
        return null;
    }

    public boolean shoot(GameScreen screen){
        if (!canShoot()) return false;
        boolean shoot = firingCost == null || !(holder instanceof Ship ship) || (firingCost != null && firingCost.hasItems(ship.supplies()));
        System.out.println(5);
        if (shoot) {
            System.out.println(2);
            if (firingCost != null && holder instanceof Ship) firingCost.extractCosts(((Ship) holder).supplies());
            fireProjectile(screen);
            System.out.println(3);
            current = 0;
            runCooldown();
            return true;
        }
        return false;
    }

    public abstract void fireProjectile(GameScreen screen);

    protected abstract Projectile projectile(GameScreen screen);

    public void runCooldown(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (current >= cooldown) {
                    current = cooldown;
                    cancel();
                    return;
                }
                current++;
            }
        },1,1);

    }

    public void sell(){
        if (holder instanceof Ship){
            Ship ship = (Ship) holder;
            ship.removeWeapon(this);
            ship.supplies().incScrap(cost/3);
        }
    }


    public Entity getHolder(){
        return holder;
    }
    public String getName(){ return name; }
    public Class<? extends Projectile> getType(){ return type; }
    public int getCooldown(){ return cooldown; }
    public int getCurrent(){ return current; }
    public int getVolley(){ return volley; }
    public int getTimeBetween(){ return timeBetween; }
    public int getCost(){ return cost; }
    public boolean canShoot(){ return current >= cooldown; }
    public WeaponDisplay getDisplay(){ return display; }

    public void addWhitelist(Class<? extends Entity> type){
        whitelist.add(type);
    }
    public void removeWhitelist(Class<? extends Entity> type){
        whitelist.remove(type);
    }
    public Set<Class<? extends Entity>> getWhitelist(){ return whitelist; }
    public void setDisplay(WeaponDisplay display){this.display = display;}
    public void setCost(int cost){ this.cost = cost; }
    public void setCurrent(int current){ this.current = current; }

    public FiringCost getFiringCost(){ return firingCost; }
    public void setFiringCost(FiringCost firingCost){ this.firingCost = firingCost; }

    public boolean hasFiringCost(){ return firingCost != null; }


}
