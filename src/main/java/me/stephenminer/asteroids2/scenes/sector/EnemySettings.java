package me.stephenminer.asteroids2.scenes.sector;

public class EnemySettings {
    private int maxAliens,maxAsteroids,maxPirates,maxDrones;
    private int aliens,asteroids,pirates,drones;
    private int alienRate,asteroidRate,pirateRate,droneRate;


    public EnemySettings(){

    }

    public void setStarts(int aliens, int asteroids, int pirates, int drones){
        this.aliens = aliens;
        this.asteroids = asteroids;
        this.pirates = pirates;
        this.drones = drones;
    }
    public void setMaxs(int maxAliens, int maxAsteroids, int maxPirates, int maxDrones){
        this.maxAliens = maxAliens;
        this.maxAsteroids = maxAsteroids;
        this.maxDrones = maxDrones;
        this.maxPirates = maxPirates;
    }
    public void setRates(int alienRate, int asteroidRate, int pirateRate, int droneRate){
        this.alienRate = alienRate;
        this.asteroidRate = asteroidRate;
        this.droneRate = droneRate;
        this.pirateRate = pirateRate;
    }


    public int aliens(){ return aliens; }
    public int asteroids(){ return asteroids; }
    public int pirates(){ return pirates; }
    public int drones(){ return drones; }

    public int maxAliens(){ return aliens; }
    public int maxAsteroids(){ return maxAsteroids; }
    public int maxPirates(){ return maxPirates; }
    public int maxDrones(){ return maxDrones; }

    public int alienRate(){ return alienRate; }
    public int asteroidRate(){ return asteroidRate; }
    public int pirateRate(){ return pirateRate; }
    public int droneRate(){ return droneRate; }

}
