package dungeonmania.entities.collectable;

import org.json.JSONObject;

public class HealthPotion extends CollectableEntity {

    public HealthPotion(JSONObject entity) {
        super(entity);
        int durability = 1;
        this.setDurability(durability);
    }
    
}
