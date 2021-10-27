package dungeonmania.entities.Moving;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.Static.Wall;
import java.util.List;

public class MovingEntity extends Entity {

    public MovingEntity(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }
    @Override
    public void move(Position pos, List<Wall> walls) {
        return;
    }
    
}
