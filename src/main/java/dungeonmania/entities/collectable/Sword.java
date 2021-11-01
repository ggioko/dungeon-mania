package dungeonmania.entities.collectable;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.items.Item;

public class Sword extends CollectableEntity {
    public static int durability = (int)Math.floor(Math.random()*(5-2+1)+2);

    public Sword(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    public static void isBroken(List<Item> inventory) {
        if (durability <= 0) {
            for (Iterator<Item> it = inventory.iterator(); it.hasNext();){
                Item anItem = it.next();
                if(anItem.getType().equals("sword")) {
                    it.remove();
                }
            }
        }
    }
    

}
