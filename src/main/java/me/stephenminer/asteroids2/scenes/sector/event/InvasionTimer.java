package me.stephenminer.asteroids2.scenes.sector.event;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class InvasionTimer {
    private List<Node> components;
    private GameScreen screen;
    private Rectangle box,bar;
    private Text label;
    private int height;
    private int width;

    private final int cooldown;
    private int current;

    private boolean end;


    public InvasionTimer(GameScreen screen, int cooldown){
        this.screen = screen;
        this.components = new ArrayList<>();
        this.cooldown = cooldown;
        width = 75;
        height = 25;
    }

    private void initShapes(){
        Rectangle box = new Rectangle();
        double yGap = Asteroids.HEIGHT-Asteroids.GAME_HEIGHT;
        box.setX(Asteroids.WIDTH-width);
        box.setY(Asteroids.GAME_HEIGHT);
        box.setWidth(width);
        box.setHeight(height);
        box.setStroke(Color.WHITE);
        box.setFill(null);
        this.box = box;

        Rectangle bar = new Rectangle();
        bar.setX(box.getX());
        bar.setY(Asteroids.GAME_HEIGHT+5);
        bar.setHeight(height/2d);
        bar.setStroke(Color.WHITE);
        this.bar = bar;

        label = new Text("Alien Pursuit");
        label.setStroke(Color.WHITE);
        label.setFill(Color.WHITE);
        label.setX(box.getX());
        label.setY(Asteroids.GAME_HEIGHT + 45);
        screen.root().getChildren().add(this.label);
        screen.root().getChildren().add(this.box);
        screen.root().getChildren().add(this.bar);
        components.add(box);
        components.add(bar);
        components.add(label);
    }

    public void start(){
        initShapes();
        timer();
    }




    public void timer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (end){
                    cancel();
                    return;
                }
                if (screen.getPlayer().isWarping() || (screen.getSector() != null && screen.getSector().paused()))return;
                float ratio = current / (float) cooldown;
                update(ratio);
                current++;
            }
        }, 1,1);
    }

    private void update(float ratio){
        Platform.runLater(()->{
            double bw = (width-5)*ratio;
            bar.setWidth(bw);
            if (ratio < 1) bar.setFill(Color.RED);
            else bar.setFill(Color.GREEN);
        });
    }

    public void end(){
        end = true;
        for (Node node : components){
            screen.root().getChildren().remove(node);
        }
        components.clear();
    }

    public boolean ending(){ return end;}
    public void setEnding(boolean end){ this.end = end; }

    public int getCurrent(){ return current; }
    public void setCurrent(int current){ this.current = current; }

}
