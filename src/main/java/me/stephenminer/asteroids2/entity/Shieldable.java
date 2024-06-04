package me.stephenminer.asteroids2.entity;

import me.stephenminer.asteroids2.equipment.Shield;
import me.stephenminer.asteroids2.scenes.GameScreen;

public interface Shieldable {

    public default void removeShield(GameScreen screen){
        if (getShield()==null) return;
        screen.root().getChildren().remove(getShield().getShape());
    }

    public default void addShield(GameScreen screen){
        if (getShield() == null)return;
        if (!screen.root().getChildren().contains(getShield().getShape())) {
            screen.root().getChildren().add(getShield().getShape());
        }
    }

    public default boolean canDmg(){
        if (getShield() != null && !getShield().disabled()) {
            getShield().damageShield();
            return false;
        }else return true;
    }

    public Shield getShield();

    public void setShield(Shield shield);

}
