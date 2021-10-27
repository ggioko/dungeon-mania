package dungeonmania.items.buildable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.items.Item;

public abstract class Buildable extends Item {

    public Buildable(String id, String type) {
        super(id, type);
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

}
