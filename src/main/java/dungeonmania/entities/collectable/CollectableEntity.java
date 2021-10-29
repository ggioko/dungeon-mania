package dungeonmania.entities.collectable;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

public class CollectableEntity extends Entity {
    private int durability;
    public CollectableEntity(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }
    
    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
