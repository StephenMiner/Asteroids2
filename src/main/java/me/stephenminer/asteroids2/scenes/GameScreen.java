package me.stephenminer.asteroids2.scenes;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.Shieldable;
import me.stephenminer.asteroids2.entity.background.Prop;
import me.stephenminer.asteroids2.entity.projectile.Bullet;
import me.stephenminer.asteroids2.entity.projectile.Projectile;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.event.ship.MoveShip;
import me.stephenminer.asteroids2.event.ship.WeaponEvents;
import me.stephenminer.asteroids2.scenes.map.Map;
import me.stephenminer.asteroids2.scenes.sector.*;
import me.stephenminer.asteroids2.scenes.sector.event.InvasionTimer;
import me.stephenminer.asteroids2.scenes.sector.store.StoreScreen;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameScreen {

    private Scene scene;
    private Group root;
    private List<Entity> entities;

    private List<Prop> props;

    private Sector sector;
    private boolean close;

    private Ship player;
    private InvasionTimer timer;
    private Map map;

    private EventHandler<KeyEvent> move,shoot;

    public GameScreen(Scene scene){
        this.root = new Group();
        this.scene = scene;
        entities = new ArrayList<>();
        props = new ArrayList<>();
    }

    public void start() {
        map = new Map(this, 30);
        Ship player = new Ship(this, 300, 300, 0.5f);
        this.player = player;
        try {
            //sector = new FinalBattle(this, player);
          //  sector.start();
            sector = selectSector();
            sector.start();
        }catch (Exception ignored){ ignored.printStackTrace();}
        move = new MoveShip(player);
        shoot = new WeaponEvents(player, this);
        scene.addEventHandler(KeyEvent.KEY_PRESSED,move);
        scene.addEventHandler(KeyEvent.KEY_PRESSED,shoot);
        timer = new InvasionTimer(this, 225*1000);
        timer.start();
      //  new DroneShip(this, 400,400,3f, player);
    }

    public void stop(){
        scene.removeEventHandler(KeyEvent.KEY_PRESSED,move);
        scene.removeEventHandler(KeyEvent.KEY_PRESSED,shoot);
        timer.end();
        LoseScreen loseScreen = new LoseScreen(this);
        if (sector != null) {
            sector.stop();
            sector = null;
        }
    }



    public void run(){
        final GameScreen instance = this;

        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if (close )stop();
                int size = entities.size();
                for (int i = size - 1; i >= 0; i--) {
                    Entity entity = entities.get(i);

                    if (entity.isDead()) {
                        entity.remove(i);
                    } else if (entity instanceof Projectile) {
                        Projectile proj = (Projectile) entity;
                        Entity colliding = proj.checkCollison(instance);
                        if (colliding != null){
                            proj.onCollide(colliding);
                        }
                    }else if (entity instanceof Ship){
                        Ship ship = (Ship) entity;
                        Entity colliding = ship.debrisCollision();
                        if (colliding != null){
                            if (ship.canDmg()) ship.remove(i);
                            if (colliding instanceof Shieldable) {
                                if (((Shieldable) colliding).canDmg()) {
                                    colliding.setDead(true);
                                }
                            }else colliding.setDead(true);
                        }
                     }
                    entity.tick();
                }
                int propSize= props.size();
                for (int i = propSize - 1; i >= 0; i--) {
                    Prop prop = props.get(i);
                    if (prop.toRemove()) removeProp(i);
                    else{
                        prop.drift();
                        prop.updateShape();
                    }
                }
            }
        }.start();
    }


    private Sector selectSector() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Class<? extends Sector>> types = sectorTypes();
        Class<? extends Sector> type = types.get(ThreadLocalRandom.current().nextInt(types.size()));
        return type.getConstructor(GameScreen.class, Ship.class).newInstance(this, player);
    }


    private List<Class<? extends Sector>> sectorTypes(){
        List<Class<? extends Sector>> types = new ArrayList<>();
        types.add(LonelyTwilight.class);
        types.add(FreshBattleField.class);
        types.add(BrightVoid.class);
        types.add(AsteroidBelt.class);
        types.add(AlienFrontier.class);
        return types;
    }




    public Group root(){ return root; }
    public void addEntity(Entity entity){
        entities.add(entity);
        if (entity instanceof Shieldable){
            Shieldable shieldable = (Shieldable) entity;
            if (shieldable.getShield() != null) {
                root.getChildren().add(shieldable.getShield().getShape());
            }
        }
        if (sector != null) sector.updateCounts(entity,1);
        root.getChildren().add(entity.getShape());
    }
    public void removeEntity(Entity entity){
        entities.remove(entity);
        root.getChildren().remove(entity.getShape());
    }
    public void removeEntity(int index){
        Entity entity = entities.remove(index);
        if (entity != null) root.getChildren().remove(entity.getShape());
        if (sector != null) sector.updateCounts(entity,-1);
    }

    public void addProp(Prop prop){
        props.add(prop);
        root.getChildren().add(prop.getShape());
    }

    public void removeProp(int index){
        Prop prop = props.remove(index);
        root.getChildren().remove(prop.getShape());
    }
    public void reset(){
        for (int i = entities.size()-1; i>=0; i--){
            Entity entity = entities.get(i);
            if (entity instanceof Ship)continue;
            entity.remove(i,false);
        }
        for (int i = props.size()-1; i>=0; i--){
            Prop prop = props.get(i);
            prop.remove(i);
        }
    }
    public void clear(){
        for (int i = entities.size()-1; i >= 0; i--){
            Entity entity = entities.get(i);
            entity.remove(i,false);
        }
        entities.clear();
        for (int i = props.size()-1; i>=0; i--){
            Prop prop = props.get(i);
            prop.remove(i);
        }
        props.clear();
        root.getChildren().clear();
    }

    public List<Entity> getEntities(){ return entities; }

    public void setClose(boolean close){ this.close = close; }

    public Scene getScene(){ return scene; }

    public void setScene(Scene scene){ this.scene = scene; }

    public boolean danger(){
        return false;
        //return entities.stream().filter(entity -> entity instanceof Sa)
    }

    public boolean closing(){ return close; }
    public Sector getSector(){ return sector; }
    public InvasionTimer getTimer(){ return timer; }
    public Map getMap(){ return map; }

    public void setSector(Sector sector){
        if (this.sector != null) this.sector.stop();
        this.sector = sector;
        sector.start();
    }

    public Ship getPlayer(){ return player; }



}
