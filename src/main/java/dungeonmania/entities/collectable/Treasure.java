package dungeonmania.entities.collectable;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

public class Treasure extends CollectableEntity {

    public Treasure(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    public static boolean treasureOnMap(List<Entity> entities) {
        for (Entity e : entities) {
            if (e.getType().equals("treasure")) {
                return true;
            }
        }
        return false;
    }
    
}
