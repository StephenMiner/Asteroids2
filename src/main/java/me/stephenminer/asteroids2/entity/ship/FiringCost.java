package me.stephenminer.asteroids2.entity.ship;

import me.stephenminer.asteroids2.equipment.Inventory;

import java.util.Collection;

public class FiringCost {
    private final Pair[] costs;

    public FiringCost(Pair... pairs){
        costs = pairs;
    }
    public FiringCost(Collection<Pair> costs){
        this.costs = new Pair[costs.size()];
        int index = 0;
        for (Pair pair : costs){
            this.costs[index] = pair;
            index++;
        }
    }

    public boolean extractCosts(Inventory inv){
        boolean hasItems = hasItems(inv);
        System.out.println(1);
        if (hasItems){
            for (Pair pair : costs){
                inv.incItem(pair.item,-1*pair.cost);
                System.out.println(4);
            }
        }
        return hasItems;
    }

    public Pair[] getCosts(){ return costs; }
    public Pair getCost(Inventory.Items type){
        for (Pair pair : costs){
            if (pair.item() == type) return pair;
        }
        return null;
    }

    public boolean hasItems(Inventory inv){
        int passed = 0;
        for (Pair pair : costs){
            if (inv.getItem(pair.item()) >= pair.cost()) passed++;
        }
        return passed >= costs.length;
    }






    public record Pair(Inventory.Items item, int cost){

    }
}
