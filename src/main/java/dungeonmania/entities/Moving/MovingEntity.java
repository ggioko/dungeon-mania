package dungeonmania.entities.Moving;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
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

    double health;
    double attack;

    public MovingEntity(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    public double getHealth() {
        return this.health;
    }
    public void setHealth(double health) {
        this.health = health;
    }

    public double getAttack() {
        return this.attack;
    }
    public void setAttack(double attack) {
        this.attack = attack;
    }
    @Override
    public void move(Position pos, List<Entity> walls) {
        return;
    }

   
    
}
