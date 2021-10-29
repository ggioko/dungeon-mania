package dungeonmania.entities.collectable;

import org.json.JSONObject;

public class Key extends CollectableEntity {

    int key;

    public Key(JSONObject entity) {
        super(entity);
        int durability = 1;
        this.setDurability(durability);
        this.key = entity.getInt("key");
    }
}
