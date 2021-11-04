package dungeonmania.entities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.items.Item;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;
import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.Static.Spawner;
import dungeonmania.entities.Static.StaticEntity;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.collectable.Armour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalTime;

public class Entity {
    private String id;
    private String type;
    private Position position;
    private boolean isInteractable;

    public Entity(JSONObject entity) {
        if (entity.getString("type").equals("key") || entity.getString("type").equals("door")) {
            this.type = entity.getString("type") + "_" + entity.getInt("key");
        } else {
            this.type = entity.getString("type");
        }
        this.position = new Position(entity.getInt("x"), entity.getInt("y"));
        this.id = entity.getString("type") + Integer.toString(this.position.getX()) + Integer.toString(this.position.getY());
        if (this instanceof Mercenary || this instanceof Spawner) {
            this.isInteractable = true;
        } else {
            this.isInteractable = false;
        }
    }

    public EntityResponse createResponse() {
        return new EntityResponse(this.id, this.type, this.position, this.isInteractable);
    }

    public Item createItem() {
        return new Item(this.id, this.type);
    }

    //getters

    public String getId() {
        return this.id;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    public boolean isInteractable() {
        return this.isInteractable;
    }

    public void move(Position pos, List<Entity> walls) {
        return;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCollectable() {
        List<String> collectables = new ArrayList<String>();
        collectables.addAll(Arrays.asList("armour", "arrow","bomb", "health_potion", "invincibility_potion", "invisibility_potion", "key_1", "key_2", "sword", "treasure", "wood"));
        if (collectables.contains(this.type)) {
            return true;
        }
        return false;
    }
}
