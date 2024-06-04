package me.stephenminer.asteroids2.scenes.sector;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.Entity;
import me.stephenminer.asteroids2.entity.alien.SaucerAlien;
import me.stephenminer.asteroids2.entity.asteroid.Asteroid;
import me.stephenminer.asteroids2.entity.mothership.ShipBody;
import me.stephenminer.asteroids2.entity.rogue.DroneShip;
import me.stephenminer.asteroids2.entity.rogue.PirateFighter;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;
import me.stephenminer.asteroids2.scenes.sector.event.Invasion;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Sector {
    protected GameScreen screen;
    protected String title;
    protected int asteroids, aliens, pirates, drones;
    protected int maxAliens, maxAsteroids, maxPirates, maxDrones;
    protected int alienRate, asteroidRate, pirateRate, droneRate;
    protected Color fill;
    protected boolean starting,paused;

    protected Ship ship;


    protected boolean stop;
    protected Text text;
    protected Text invDisplay;
    protected EnemySettings settings;

    public Sector (GameScreen screen, Ship ship, Color color, String title){
        this.screen = screen;
        this.title = title;
        this.ship = ship;
        this.fill = color;
        settings = new EnemySettings();
    }

    public void start(){
        starting = true;
        for (int i = 0; i < asteroids; i++){
            Pos pos = generatePos();
            spawnAsteroid(pos);
        }
        for (int i = 0; i < aliens; i++){
            Pos pos = generatePos();
            spawnAlien(pos);

        }
        for (int i = 0; i < drones; i++){
            Pos pos = generatePos();
            spawnDrone(pos);

        }
        for (int i = 0; i < pirates; i++){
            Pos pos = generatePos();
            spawnPirate(pos);

        }
        screen.getScene().setFill(fill);
        starting = false;
        this.text = loadText();
        this.invDisplay = loadInvDisplay();
        screen.root().getChildren().add(invDisplay);
        screen.root().getChildren().add(text);
        run();


    }


    public void updateCounts(Entity entity, int modify){
        if (starting)return;
        if (entity instanceof Asteroid) asteroids += modify;
        if (entity instanceof SaucerAlien) aliens += modify;
        if (entity instanceof PirateFighter) pirates += modify;
        if (entity instanceof  DroneShip) drones += modify;
    }

    public void run(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int asteroidCount = 0;
            int alienCount = 0;
            int pirateCount = 0;
            int droneCount = 0;
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (paused) return;
                    if (screen.closing()) cancel();
                    if (stop) cancel();
                    asteroidStatement:
                    {
                        if (asteroidCount >= asteroidRate) {
                            asteroidCount = 0;
                            if (asteroids >= maxAsteroids) break asteroidStatement;
                            Pos pos = generatePos();
                            Asteroid asteroid = new Asteroid(screen, pos.getX(), pos.getY(), 0.5f, ThreadLocalRandom.current().nextInt(4));
                        }
                    }
                    alienStatement:
                    {
                        if (alienCount >= alienRate) {
                            alienCount = 0;
                            if (aliens >= maxAliens) break alienStatement;
                            Pos pos = generatePos();
                            spawnAlien(pos);
                        }
                    }
                    pirateStatement:
                    {
                        if (pirateCount >= pirateRate){
                            pirateCount = 0;
                            if (pirates >= maxPirates) break pirateStatement;
                            Pos pos = generatePos();
                            spawnPirate(pos);
                        }
                    }
                    droneStatement:
                    {
                        if (droneCount >= droneRate){
                            droneCount = 0;
                            if (drones >= maxDrones)break droneStatement;
                            Pos pos = generatePos();
                            spawnDrone(pos);
                        }
                    }
                    invDisplay.setText("Fuel: " + ship.supplies().getFuel() + "; Scrap: " + ship.supplies().getScrap() + "; Missiles: " + ship.supplies().getMissiles());
                    asteroidCount++;
                    alienCount++;
                    pirateCount++;
                    droneCount++;
                });

            }
        }, 1,1);
        /*
        new AnimationTimer(){
            int asteroidCount = 0;
            int alienCount = 0;
            @Override
            public void handle(long l) {
                if (screen.closing()) stop();
                if (stop) stop();
                asteroidStatement:
                {
                    if (asteroidCount >= asteroidRate) {
                        asteroidCount = 0;
                        if (asteroids >= maxAsteroids) break asteroidStatement;
                        Pos pos = generatePos();
                        Asteroid asteroid = new Asteroid(screen, pos.getX(), pos.getY(), 0.5f, ThreadLocalRandom.current().nextInt(4));
                    }
                }
                alienStatement:
                {
                    if (alienCount >= alienRate) {
                        alienCount = 0;
                        if (aliens >= maxAliens) break alienStatement;
                        Pos pos = generatePos();
                        spawnAlien(pos);
                    }
                }

                asteroidCount++;
                alienCount++;
            }
        }.start();

         */
    }

    protected SaucerAlien spawnAlien(Pos pos){
        SaucerAlien alien = new SaucerAlien(screen, pos.getX(),pos.getY(),2f);
        alien.setTarget(ship);
        return alien;
    }
    protected Asteroid spawnAsteroid(Pos pos){
        Asteroid asteroid = new Asteroid(screen, pos.getX(),pos.getY(),0.5f,ThreadLocalRandom.current().nextInt(4));
        return asteroid;
    }
    protected PirateFighter spawnPirate(Pos pos){
        PirateFighter pirate =  new PirateFighter(screen,pos.getX(),pos.getY(),2f);
        pirate.setTarget(ship);
        return pirate;
    }
    protected DroneShip spawnDrone(Pos pos){
        DroneShip drone = new DroneShip(screen,pos.getX(),pos.getY(),2f);
        drone.setTarget(ship);
        return drone;
    }


    private Text loadText(){
        Text text = new Text(title);
        text.setStroke(Color.WHITE);
        text.setX(10);
        text.setY(50);
        return text;
    }

    private Text loadInvDisplay(){
        Text text = new Text("Fuel: " + ship.supplies().getFuel() + "; Scrap: " + ship.supplies().getScrap());
        text.setStroke(Color.WHITE);
        text.setX(10);
        text.setY(75);
        return text;
    }
    protected Pos generatePos(){
        boolean sides = ThreadLocalRandom.current().nextBoolean();
        double x;
        double y;
        if (sides){
            x = ThreadLocalRandom.current().nextBoolean() ? 10 : Asteroids.WIDTH - 10;
            y = ThreadLocalRandom.current().nextDouble(Asteroids.GAME_HEIGHT);
        }else{
            x = ThreadLocalRandom.current().nextDouble(Asteroids.WIDTH);
            y = ThreadLocalRandom.current().nextBoolean() ? 10 : Asteroids.GAME_HEIGHT - 10d;
        }
        return new Pos(x,y);
    }

    protected Pos randomPos(){
        double x = ThreadLocalRandom.current().nextInt(Asteroids.WIDTH);
        double y = ThreadLocalRandom.current().nextInt(Asteroids.GAME_HEIGHT);
        return new Pos(x,y);
    }


    public void stop(){
        stop = true;
        screen.root().getChildren().remove(text);
        screen.root().getChildren().remove(invDisplay);
    }

    public void cullEntities(){
        for (int i = screen.getEntities().size()-1; i>= 0; i--){
            Entity entity = screen.getEntities().get(i);
            if (entity.isDead() || entity instanceof Ship) continue;
            entity.remove(i, false);
        }
    }
    public String getTitle(){ return title; }
    public GameScreen getScreen(){ return screen; }

    public int getAsteroids(){ return asteroids; }


    public void writeValues(){
        aliens = settings.aliens();
        asteroids = settings.asteroids();
        pirates = settings.pirates();
        drones = settings.drones();

        alienRate = settings.alienRate();
        asteroidRate = settings.asteroidRate();
        pirateRate = settings.pirateRate();
        droneRate = settings.droneRate();

        maxAliens = settings.maxAliens();
        maxAsteroids = settings.maxAsteroids();
        maxDrones = settings.maxDrones();
        maxPirates = settings.maxPirates();
    }

    public Color getFill(){ return fill; }
    public boolean paused(){ return paused; }

    public void setPaused(boolean paused){this.paused = paused;}
    public boolean isStop(){ return stop; }


    protected class Pos{
        private final double x,y;
        public Pos(double x, double y){
            this.x = x;
            this.y = y;
        }
        public double getX(){ return x; }
        public double getY(){ return y; }
    }

}
