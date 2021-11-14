package dungeonmania.entities.collectable;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

public class Sword extends CollectableEntity {
    public Sword(JSONObject entity) {
        super(entity);
        setDurability(6);
        //TODO Auto-generated constructor stub
    }

    public Sword(String id, String type) {
        super(id,type);
    }

    public void isBroken(List<Entity> inventory) {
        if (this.getDurability() <= 0) {
            for (Iterator<Entity> it = inventory.iterator(); it.hasNext();){
                Entity anItem = it.next();
                if(anItem.getType().equals("sword")) {
                    it.remove();
                }
            }
        }
    }
    

}
