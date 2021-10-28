package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.entities.Static.Wall;
import dungeonmania.util.Position;
import java.util.List;

public class Player extends Entity {

    public Player(JSONObject entity) {
        super(entity);
    }
    
    @Override
    public void move(Position pos, List<Wall> walls) {
        boolean move = true;
        for (Wall w : walls) {
            if (w.getPosition().equals(pos)) {
                move = false;
            }
        }
        if (move) {
            this.position = pos;
        }
    }

}
