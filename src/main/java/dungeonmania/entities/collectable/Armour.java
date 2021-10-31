package dungeonmania.entities.collectable;

import org.json.JSONObject;

public class Armour extends CollectableEntity {
    public static int durability = (int)Math.floor(Math.random()*(15-4+1)+4);
    public Armour(JSONObject entity) {
        super(entity);
        // this.setDurability(durability);
    }
}
