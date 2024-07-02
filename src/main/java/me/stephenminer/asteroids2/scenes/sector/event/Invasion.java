package me.stephenminer.asteroids2.scenes.sector.event;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.Debris;
import me.stephenminer.asteroids2.entity.alien.AlienBomber;
import me.stephenminer.asteroids2.entity.alien.SaucerAlien;
import me.stephenminer.asteroids2.entity.background.AlienCruiser;
import me.stephenminer.asteroids2.equipment.Inventory;
import me.stephenminer.asteroids2.equipment.MissileLauncher;
import me.stephenminer.asteroids2.equipment.Weapon;
import me.stephenminer.asteroids2.equipment.Weapons;
import me.stephenminer.asteroids2.scenes.GameScreen;
import me.stephenminer.asteroids2.scenes.sector.Sector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Invasion {

    private final GameScreen screen;
    private boolean end;
    private final List<AlienCruiser> background;
    private Button prompter;
    private Text text;
    private final int aliens;
    public Invasion( GameScreen screen, int aliens){
        this.screen = screen;
        background = new ArrayList<>();
        this.aliens = aliens;
    }


    public void start(){
        if (screen.getSector() != null)
            screen.getSector().setPaused(true);
        text = new Text("You've spent too much time in this sector! The alien fleet has caught up to you! Your warpdrive engines were hacked and now need to reset, good luck");
        text.setStroke(Color.WHITE);
        text.setMouseTransparent(true);
        text.setX(Asteroids.WIDTH / 4d);
        text.setWrappingWidth(150);
        text.setY(Asteroids.HEIGHT/2d);
        prompter = new Button("...Understood");
        prompter.setPrefSize(125,25);
        prompter.setTranslateX(Asteroids.WIDTH/2d);
        prompter.setTranslateY(Asteroids.HEIGHT/2d);
        screen.root().getChildren().add(text);
        screen.root().getChildren().add(prompter);
        prompter.setOnMouseClicked((event)->invade());
    }

    public void end(){
        end = true;
        background.clear();

    }

    private void invade(){
        for (int i = 0; i < 10; i++){
            AlienCruiser cruiser = new AlienCruiser(screen,randX(),randY());
            double acceleration = 1 * ThreadLocalRandom.current().nextDouble(1,4.5);
            cruiser.setAx(acceleration);
            background.add(cruiser);
        }
        animation();
        screen.root().getChildren().remove(text);
        screen.root().getChildren().remove(prompter);
        text = null;
        prompter = null;
        screen.getSector().setPaused(false);
        for (int i = 0; i < aliens; i++){
            spawnAlien();
        }
    }

    private void spawnAlien(){
        Weapons weapons = select();
        SaucerAlien alien;
        Pair<Double,Double> pos = generatePos();
        if (weapons.firingCost() != null && weapons.firingCost().getCost(Inventory.Items.MISSILES) != null){
            alien = new AlienBomber(screen,pos.getKey(),pos.getValue(),1,screen.getPlayer());
        }else alien = new SaucerAlien(screen, pos.getKey(), pos.getValue(),2,screen.getPlayer());
        Weapon weapon = Weapon.construct(weapons,alien);
        weapon.setCurrent(0);
        weapon.runCooldown();
        alien.setWeapon(weapon);

    }
    protected Pair<Double,Double> generatePos(){
        boolean sides = ThreadLocalRandom.current().nextBoolean();
        double x;
        double y;
        if (sides){
            x = ThreadLocalRandom.current().nextBoolean() ? 1 : Asteroids.WIDTH - 1;
            y = ThreadLocalRandom.current().nextDouble(Asteroids.GAME_HEIGHT);
        }else{
            x = ThreadLocalRandom.current().nextDouble(Asteroids.WIDTH);
            y = ThreadLocalRandom.current().nextBoolean() ? 1 : Asteroids.GAME_HEIGHT - 1;
        }
        return new Pair<>(x, y);
    }

    private Weapons select(){
        Weapons[] options = new Weapons[]{Weapons.ADVANCED_LASER,Weapons.BASIC_LASER,Weapons.TWIN_MISSILE};
        return options[ThreadLocalRandom.current().nextInt(options.length)];
    }
    public double randX(){
        return ThreadLocalRandom.current().nextInt(0, 75);
    }


    public double randY(){ return ThreadLocalRandom.current().nextDouble(5, Asteroids.GAME_HEIGHT/10) * 10; }

    private void animation(){
        new AnimationTimer(){
            @Override
            public void handle(long l){
                if (end || (screen.getSector() != null &&screen.getSector().isStop())){
                    stop();
                    background.forEach(AlienCruiser::remove);
                    background.clear();
                }
                background.forEach(AlienCruiser::tick);
            }
        }.start();
    }

    public void setEnd(boolean end){ this.end = end; }
    public boolean ending(){ return end; }
}
