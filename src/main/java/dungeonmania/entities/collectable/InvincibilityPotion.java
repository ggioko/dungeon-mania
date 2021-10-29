package dungeonmania.entities.collectable;

import org.json.JSONObject;

public class InvincibilityPotion extends CollectableEntity {

    public InvincibilityPotion(JSONObject entity) {
        super(entity);
        int durability = 1;
        this.setDurability(durability);
    }
    
}
