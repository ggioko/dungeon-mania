package dungeonmania.entities.collectable;

import org.json.JSONObject;

public class Key extends CollectableEntity {

    public Key(JSONObject entity) {
        super(entity);
        int durability = 1;
        this.setDurability(durability);
    }
    
}
