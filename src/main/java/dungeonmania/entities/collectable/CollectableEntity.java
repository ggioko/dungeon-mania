package dungeonmania.entities.collectable;

import dungeonmania.response.models.ItemResponse;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

public class CollectableEntity extends Entity {
    private int durability;
    public CollectableEntity(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    public CollectableEntity(String id, String type) {
        super(id, type);
    }
    
    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public ItemResponse createItemResponse() {
        return new ItemResponse(this.getId(), this.getType());
    }
}
