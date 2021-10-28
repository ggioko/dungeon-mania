package dungeonmania.entities;

import org.json.JSONObject;

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

    //getters

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getId() {
        return this.getId();
    }
}
