package dungeonmania.entities.collectable;

import org.json.JSONObject;

public class HealthPotion extends CollectableEntity {
    public static int durability = 1;
    public HealthPotion(JSONObject entity) {
        super(entity);
    }
    
}
