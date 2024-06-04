package me.stephenminer.asteroids2.scenes.map;

public class SectorPos {
    public static double MAX_DISTANCE_SQUARED = 16*16;
    private double x,y;

    public SectorPos(double x, double y){

    }




    public double distSquared(SectorPos pos){
        double dx = pos.getX() - x;
        double dy = pos.getY() - y;
        return Math.pow(dx,2) + Math.pow(dy,2);
    }

    public boolean canTravel(SectorPos origin){
        return false;
    }


    public double getX(){ return x; }
    public double getY(){ return y; }

}
