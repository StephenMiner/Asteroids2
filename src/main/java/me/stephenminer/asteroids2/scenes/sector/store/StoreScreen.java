package me.stephenminer.asteroids2.scenes.sector.store;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import me.stephenminer.asteroids2.Asteroids;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.equipment.Weapons;
import me.stephenminer.asteroids2.scenes.GameScreen;
import me.stephenminer.asteroids2.scenes.sector.Sector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class StoreScreen extends Sector {
    private List<StoreIcon> product;
    private List<Node> components;
    private int numProduct;

    private Rectangle window;
    private Polygon nameTab;
    private double cx,cy;
    private boolean selling;

    public StoreScreen(GameScreen screen, Ship player){
        super(screen, player, Color.BLACK,"Store");
        this.numProduct = 6;
        components = new ArrayList<>();
        product = new ArrayList<>();
        settings.setMaxs(0,0,0,0);
        settings.setRates(0,0,0,0);
        writeValues();
    }

    private void loadProduct(){
        Weapons[] options = Weapons.values();
        int width = Asteroids.WIDTH/6;
        int height = 50;
        for (int i = 0; i < numProduct; i++){
            Weapons choice = options[ThreadLocalRandom.current().nextInt(options.length)];
            double x = getX(width, i);
            double y = getY(height, i);
            StoreIcon icon = new StoreIcon(screen,choice.title(),choice, choice.cost(), x,y, width, height);
            icon.show();
            product.add(icon);
        }
    }

    private double getX(int width, int index){
        int i = index % 3;
        return cx + width*i;
    }
    private double getY(int height, int index){
        int i = index/3;
        return cy + height*i;
    }

    public void initParts(){
        cx = Asteroids.WIDTH/2d;
        cy = 100;
        window = new Rectangle();
        window.toBack();
        window.setStrokeWidth(4);
        window.setStroke(Color.WHITE);
        window.setX(cx);
        window.setY(cy);
        window.setWidth(Asteroids.WIDTH/2d);
        window.setHeight(Asteroids.GAME_HEIGHT - Math.abs(Asteroids.HEIGHT-Asteroids.GAME_HEIGHT-2*cy));

        components.add(window);
        screen.root().getChildren().add(window );
    }

    @Override
    public void start() {
        super.start();
        initParts();
        loadProduct();
    }

    @Override
    public void stop(){
        super.stop();
        for (StoreIcon icon : product){
            icon.remove();
        }
        product.clear();
        for (Node node : components){
            screen.root().getChildren().remove(node);
        }
        components.clear();
    }



}
