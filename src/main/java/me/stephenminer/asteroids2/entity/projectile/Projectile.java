package me.stephenminer.asteroids2.entity.projectile;

import javafx.scene.shape.Shape;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.Shieldable;
import me.stephenminer.asteroids2.entity.alien.SaucerAlien;
import me.stephenminer.asteroids2.entity.mothership.MotherShipPart;
import me.stephenminer.asteroids2.entity.mothership.ShipBody;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Projectile extends Entity {
    protected final Entity shooter;
    protected double damage;
    protected boolean killOffscreen, bypassShield, shootable;

    //Specified which objects this projectile can target
    protected Set<Class<? extends Entity>> whitelist;

    public Projectile(GameScreen screen, Entity shooter, double x, double y, float speed, double angle, double damage){
        super(screen, x,y,speed);
        this.angle = angle;
        this.shooter = shooter;
        this.damage = damage;
        this.whitelist = new HashSet<>();
        initAcceleration();
    }

    protected void initAcceleration(){
        double rads = Math.toRadians(angle);
        ax = Math.cos(rads)*speed;
        ay = Math.sin(rads)*speed;
    }

    @Override
    public boolean drift() {
        boolean hitEdge = super.drift();
        if (hitEdge && !bounce && killOffscreen){
            dead = true;
        }
        return hitEdge;
    }

    public void updateAcceleration(){
        ax = Math.cos(Math.toRadians(angle)) * speed;
        ay = Math.sin(Math.toRadians(angle)) * speed;
    }

    public Entity checkCollison(GameScreen screen){
        List<Entity> entities = nearby(17, true);
        entities.remove(this);
        for (int i = entities.size()-1; i >= 0; i --){
            Entity entity = entities.get(i);
            Shape inter = Shape.intersect(shape,entity.getShape());
            if (shooter instanceof SaucerAlien && entity instanceof SaucerAlien) continue;
            if (shooter instanceof MotherShipPart && entity instanceof MotherShipPart) continue;
            if (entity instanceof ShipBody)continue;
            if (inter.getLayoutBounds().getHeight() > 0 && inter.getLayoutBounds().getWidth() > 0 && !shooter.equals(entity)) {
                if (entity instanceof Projectile && !((Projectile) entity).isShootable()) continue;
                hitEffect();
                return entity;
            }
            //if (shape.getLayoutBounds().intersects((entity.getShape().getLayoutBounds())) && !shooter.equals(entity)) return entity;
        }
        return null;
    }

    public void onCollide(Entity colliding){
        if (colliding != null){
            this.setDead(true);
            if (colliding instanceof Shieldable){
                Shieldable shieldable = (Shieldable) colliding;
                if (shieldable.canDmg()) colliding.setDead(true, this.getShooter());
            }else colliding.setDead(true, this.getShooter());
        }
    }

    protected void hitEffect(){

    }




    public void addWhitelist(Class<? extends Entity> type){
        this.whitelist.add(type);
    }
    public void removeWhitelist(Class<? extends Entity> type){
        this.whitelist.remove(type);
    }

    public Set<Class<? extends Entity>> getWhitelist(){ return whitelist; }

    public boolean bypassShield(){ return bypassShield; }
    public boolean isShootable(){ return shootable;
    }
    public void setBypassShield(boolean bypassShield){ this.bypassShield = bypassShield; }
    public void setShootable(boolean shootable){ this.shootable = shootable; }

    public Entity getShooter(){ return shooter; }
}
