package dungeonmania.entities.collectable.buildable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.collectable.CollectableEntity;
import dungeonmania.entities.*;

public abstract class Buildable extends CollectableEntity {

    public Buildable(String id, String type) {
        super(id, type);
    }

    public abstract boolean isBuildable(List<Entity> inventory);
    public abstract Map<String, Integer> materialNeeded(List<Entity> inventory);
    public abstract HashMap<String, Integer> getRelevantMaterialCount(List<Entity> inventory);
    public abstract void subtractDurability(List<Entity> inventory);

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
