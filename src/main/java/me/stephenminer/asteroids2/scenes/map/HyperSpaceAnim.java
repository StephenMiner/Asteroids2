package me.stephenminer.asteroids2.scenes.map;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.background.Star;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;
import me.stephenminer.asteroids2.scenes.sector.*;
import me.stephenminer.asteroids2.scenes.sector.store.StoreScreen;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HyperSpaceAnim {
    private GameScreen screen;
    private Star prop;
    private boolean stop, incNum;
    private double startRadius, endRadius;
    private double radius;

    private Button lJump,sJump;


    public HyperSpaceAnim(GameScreen screen){
        this.screen = screen;
        startRadius = 4;
        endRadius = 600;
        radius = startRadius;

    }

    private void init(){
        prop = new Star(screen, radius,Asteroids.WIDTH/2d,Asteroids.GAME_HEIGHT/2d);
        prop.setColor(Color.WHITE);
    }

    private void resetShip(){
        Ship ship = screen.getPlayer();
        ship.setWarping(true);
        ship.setAy(0);
        ship.setAx(0);
        ship.setX(Asteroids.WIDTH/2d);
        ship.setY(Asteroids.GAME_HEIGHT/2d);
    }
    public void start(){
        //screen.getSector().setPaused(true);
        screen.reset();
        init();
        screen.getSector().stop();
        resetShip();
        new AnimationTimer(){
            boolean pass = false;
            Double inc = 10.5;
            Sector sector = null;
            @Override
            public void handle(long l){

                if (radius < 1){
                    stop();
                    end();
                    screen.setSector(sector);
                    screen.getPlayer().setWarping(false);
                    screen.getSector().setPaused(false);
                }
                if (screen.getSector() != null && screen.getSector().paused()) return;
                if (incNum){
                    inc = -10.5;
                    incNum = false;
                }
                if (!pass && radius >= endRadius){
                    inc = 0d;
                    pass = true;
                    sector = null;
                    loadButtons();
                    screen.getMap().display();
                    try {
                        sector = selectSector();
                    } catch (Exception ignored){}
                    if (sector == null) return;
                    screen.getScene().setFill(sector.getFill());
                    screen.getSector().cullEntities();
                }
                prop.setRadius(radius);
                radius+=inc;
            }
        }.start();
    }

    private void loadButtons(){
        lJump = new Button("Long Jump");
        sJump = new Button("Short Jump");

        Text ljDesc = new Text("+3 kilo-light-years; Puts you ahead of the alien fleet");
        ljDesc.setWrappingWidth(100);
        ljDesc.setX(Asteroids.WIDTH/2d-100);
        ljDesc.setY(Asteroids.HEIGHT/2d + 50);

        Text sjDesc = new Text("+2 kilo-light-years");
        sjDesc.setWrappingWidth(100);
        sjDesc.setX(Asteroids.WIDTH/2d);
        sjDesc.setY(Asteroids.HEIGHT/2d + 50);


        lJump.setTranslateX(Asteroids.WIDTH/2d-100);
        lJump.setTranslateY(Asteroids.HEIGHT/2d);
        lJump.setPrefSize(100,25);
        lJump.setFocusTraversable(false);
        lJump.setOnMouseClicked((event)-> {
            incNum = true;
            screen.root().getChildren().removeAll(lJump,sJump,sjDesc,ljDesc);
            finJump(true);
        });


        sJump.setTranslateX(Asteroids.WIDTH/2d);
        sJump.setTranslateY(Asteroids.HEIGHT/2d);
        sJump.setPrefSize(100,25);
        sJump.setFocusTraversable(false);
        sJump.setOnMouseClicked((event)-> {
            incNum = true;
            screen.root().getChildren().removeAll(sJump,lJump,sjDesc,ljDesc);
            finJump(false);
        });

        screen.root().getChildren().addAll(sJump,lJump,sjDesc,ljDesc);


    }

    private void longjumpDesc(){

    }

    private void finJump(boolean longJump){
        Map map = screen.getMap();
        if (longJump) map.longJump();
        else map.shortJump();
        map.stopDisplay();
    }

    public void end(){
        prop.setRemove(true);
    }


    private Sector selectSector() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Class<? extends Sector>> types = sectorTypes();
        Class<? extends Sector> type = types.get(ThreadLocalRandom.current().nextInt(types.size()));
        return type.getConstructor(GameScreen.class, Ship.class).newInstance(screen, screen.getPlayer());
    }


    private List<Class<? extends Sector>> sectorTypes(){
        List<Class<? extends Sector>> types = new ArrayList<>();
        types.add(LonelyTwilight.class);
        types.add(FreshBattleField.class);
        types.add(BrightVoid.class);
        types.add(AsteroidBelt.class);
        types.add(AlienFrontier.class);
        types.add(StoreScreen.class);
        return types;
    }




    public GameScreen getScreen(){ return screen; }






}
