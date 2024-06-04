package me.stephenminer.asteroids2.scenes.sector.store;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.equipment.Equipment;
import me.stephenminer.asteroids2.equipment.Weapon;
import me.stephenminer.asteroids2.equipment.Weapons;
import me.stephenminer.asteroids2.event.ship.BuyEvent;
import me.stephenminer.asteroids2.scenes.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class StoreIcon {
    private List<Node> parts;
    private Button box;
    private Text title;
    private int cost;
    private double x, y;
    private double width,height;
    private boolean soldout;
    private String name;
    private GameScreen screen;
    private Weapons product;


    public StoreIcon(GameScreen screen, String name, Weapons product, int cost, double x, double y, double width, double height){
        this.screen = screen;
        this.cost = cost;
        this.product = product;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        parts = new ArrayList<>();
        initParts();
    }

    public void show(){
        initParts();
    }

    public void remove(){
        for (Node node : parts){
            screen.root().getChildren().remove(node );
        }
        parts.clear();
        title = null;
        box = null;
    }


    private void initParts(){
        title = new Text(name);
        title.setX(x);
        title.setY(y + height/4);
        title.setMouseTransparent(true);
        title.setFill(Color.WHITE);
        box = new Button(cost + " Scrap");
        box.setStyle("-fx-border-color: white;-fx-background-color: transparent;");
        box.setTextFill(Color.WHITE);
        box.setPrefSize(width,height);
        box.setTranslateX(x);
        box.setTranslateY(y);
        box.setFocusTraversable(false);
        box.setOnMouseClicked(new BuyEvent(screen.getPlayer(),this));

        screen.root().getChildren().add(title);
        screen.root().getChildren().add(box);
        parts.add(box);
        parts.add(title);
    }

    public void updateParts(){
        title.setX(x);
        title.setY(y + height/4);
        box.setTranslateX(x);
        box.setTranslateY(y);
    }

    public void buy(Ship buyer){
        if (!canBuy(buyer)) return;
        buyer.supplies().incScrap(-1*cost);
        buyer.addWeapon(Weapon.construct(product,buyer));
        title.setText("SOLDOUT");
        title.setStroke(Color.DARKRED);
        soldout = true;
    }

    public boolean canBuy(Ship buyer){
        return !soldout && buyer.supplies().getScrap() >= cost && buyer.getWeapons().size() < 3;
    }






    public GameScreen getScreen(){ return screen; }
    public int getCost(){ return cost; }
    public Button getBox(){ return box; }
    public Weapons getProduct(){ return product; }

    public double getWidth(){ return width; }
    public double getHeight(){ return height; }
    public double getX(){ return x; }
    public double getY(){ return y;}
    public boolean isSoldout(){ return soldout; }

    public void setWidth(double width){ this.width = width; }
    public void setHeight(double height){ this.height = height; }
    public void setSoldout(boolean soldout){ this.soldout = soldout; }

    public void setPos(double x, double y){
        this.x = x;
        this.y = y;
        updateParts();
    }
}
