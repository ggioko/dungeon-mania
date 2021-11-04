package dungeonmania.entities.buildable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;

public abstract class Buildable extends Entity {

    public Buildable(String id, String type) {
        super(id, type);
    }

    public abstract boolean isBuildable(List<Entity> inventory);
    public abstract Map<String, Integer> materialNeeded(List<Entity> inventory);
    public abstract HashMap<String, Integer> getRelevantMaterialCount(List<Entity> inventory);
    public abstract void subtractDurability(List<Entity> inventory);
    public abstract int getDurability();

    public static Buildable getBuildable(String type) {
        Buildable buildable = null;
        if (type.equalsIgnoreCase("shield")) {
            buildable = new Shield("shield", "shield");
        } else if (type.equalsIgnoreCase("bow")) {
            buildable = new Bow("bow", "bow");
        }
        return buildable;
    }

    // public void setDurability(int durability) {
    //     this.durability = durability;
    // }

    // public int getDurability() {
    //     return this.durability;
    // }


}
