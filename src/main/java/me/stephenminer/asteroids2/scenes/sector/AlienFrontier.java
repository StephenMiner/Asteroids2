package me.stephenminer.asteroids2.scenes.sector;

import javafx.scene.paint.Color;
import me.stephenminer.asteroids2.equipment.Shield;
import me.stephenminer.asteroids2.entity.alien.AlienBomber;
import me.stephenminer.asteroids2.entity.alien.SaucerAlien;
import me.stephenminer.asteroids2.entity.ship.Ship;
import me.stephenminer.asteroids2.scenes.GameScreen;

public class AlienFrontier extends Sector{

    public AlienFrontier(GameScreen screen, Ship ship) {
        super(screen, ship, Color.rgb(10,20,10), "Alien Frontier");
        settings.setStarts(1,20,0,0);
        settings.setMaxs(1,20,2,0);
        settings.setRates(5*1000,8*1000,10*1000,0);
        writeValues();
    }


    @Override
    protected SaucerAlien spawnAlien(Pos pos) {
        SaucerAlien alien = new AlienBomber(screen, pos.getX(),pos.getY(),1.5f,ship);//super.spawnAlien(pos);
        Shield shield = new Shield(alien,3,15*1000);
        alien.setShield(shield);
        return alien;
    }
}
