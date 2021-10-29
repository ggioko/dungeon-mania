package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;
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
        this.type = entity.getString("type");
        this.position = new Position(entity.getInt("x"), entity.getInt("y"));
        this.id = this.type + Integer.toString(this.position.getX()) + Integer.toString(this.position.getY()) + LocalTime.now();
        
    }

    public EntityResponse createResponse() {
        return new EntityResponse(this.id, this.type, this.position, this.isInteractable);
    }

    //getters

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getType() {
        return this.type;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    public void move(Position pos, List<Entity> walls) {
        return;
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
