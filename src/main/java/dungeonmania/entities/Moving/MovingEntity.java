package dungeonmania.entities.Moving;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.Static.Wall;
import java.util.List;

import org.json.JSONObject;

public class MovingEntity extends Entity {

    /* from entity
    String id;
    String type;
    Position position;
    boolean isInteractable;
    */

    int health;
    int attack;

    public MovingEntity(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    public int getHealth() {
        return this.health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return this.attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    @Override
    public void move(Position pos, List<Wall> walls) {
        return;
    }
    
}
