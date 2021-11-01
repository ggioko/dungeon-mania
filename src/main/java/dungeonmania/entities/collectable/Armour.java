package dungeonmania.entities.collectable;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.items.Item;

public class Armour extends CollectableEntity {
    public static int durability = (int)Math.floor(Math.random()*(15-4+1)+4);
    public Armour(JSONObject entity) {
        super(entity);
        // this.setDurability(durability);
    }

    public static void isBroken(List<Item> inventory) {
        if (durability <= 0) {
            for (Iterator<Item> it = inventory.iterator(); it.hasNext();){
                Item anItem = it.next();
                if(anItem.getType().equals("armour")) {
                    it.remove();
                }
            }
        }
    }
}
