package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;
import dungeonmania.entities.Static.StaticEntity;
import dungeonmania.entities.Static.Wall;
import java.util.List;
import java.time.LocalTime;

public class Entity {
    String id;
    String type;
    Position position;
    boolean isInteractable;

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

    public void setType(String type) {
        this.type = type;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    public void move(Position pos, List<Wall> walls) {
        return;
    }
}
