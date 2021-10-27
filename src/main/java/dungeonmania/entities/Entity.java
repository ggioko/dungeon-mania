package dungeonmania.entities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.items.Item;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class Entity {
    String id;
    String type;
    Position position;
    boolean isInteractable;

    public Entity(JSONObject entity) {
        this.type = entity.getString("type");
        this.position = new Position(entity.getInt("x"), entity.getInt("y"));
        this.id = this.type + Integer.toString(this.position.getX()) + Integer.toString(this.position.getY());
        if (this.type.equals("wall")) {
            this.isInteractable = false;
        } else {
            this.isInteractable = true;
        }
    }

    public EntityResponse createResponse() {
        return new EntityResponse(this.id, this.type, this.position, this.isInteractable);
    }

    public Item createItem() {
        return new Item(this.id, this.type);
    }

    //getters

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isCollectable() {
        List<String> collectables = new ArrayList<String>();
        collectables.addAll(Arrays.asList("armour", "arrow","bomb", "health_potion", "invincibility_potion", "invisibility_potion", "key", "sword", "treasure", "wood"));
        if (collectables.contains(this.type)) {
            return true;
        }
        return false;
    }
}
