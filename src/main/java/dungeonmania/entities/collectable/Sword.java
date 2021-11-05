package dungeonmania.entities.collectable;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

public class Sword extends CollectableEntity {
    public static int durability = 6;

    public Sword(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    public Sword(String id, String type) {
        super(id,type);
    }

    public static void isBroken(List<Entity> inventory) {
        if (durability <= 0) {
            for (Iterator<Entity> it = inventory.iterator(); it.hasNext();){
                Entity anItem = it.next();
                if(anItem.getType().equals("sword")) {
                    it.remove();
                }
            }
        }
    }
    

}
