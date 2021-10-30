package dungeonmania.items.buildable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.items.Item;

public abstract class Buildable extends Item {

    private int durability;

    public Buildable(String id, String type, int durability) {
        super(id, type);
        this.durability = durability;
    }

    public abstract boolean isBuildable(List<Item> inventory);
    public abstract Map<String, Integer> materialNeeded(List<Item> inventory);
    public abstract HashMap<String, Integer> getRelevantMaterialCount(List<Item> inventory);

    public static Buildable getBuildable(String type) {
        Buildable buildable = null;
        if (type.equalsIgnoreCase("shield")) {
            buildable = new Shield("shield", "shield");
        } else if (type.equalsIgnoreCase("bow")) {
            buildable = new Bow("bow", "bow");
        }
        return buildable;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return this.durability;
    }

    public void subtractDurability(List<Item> inventory) {
        this.durability -= 1;
        if (this.durability == 0) {
            inventory.remove(this);
        }
    }

}
