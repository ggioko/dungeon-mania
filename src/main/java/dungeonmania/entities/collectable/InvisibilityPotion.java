package dungeonmania.entities.collectable;

import org.json.JSONObject;

public class InvisibilityPotion extends CollectableEntity {

    public InvisibilityPotion(JSONObject entity) {
        super(entity);
        int durability = 1;
        this.setDurability(durability);
    }
    
}
