package dungeonmania.entities.Moving;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.util.Position;
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

    public void takeDamage(double playerHP, double playerAD, Dungeon dungeon) {
        if (dungeon.getItem("sword") != null) {
            this.setHealth(this.health - 1);
            Sword.durability -= 1;
            Sword.isBroken(dungeon.getItems());
            // decrease sword durability by 1 // TODO
        }
        this.setHealth(this.health - ((playerHP * playerAD) / 5));
    }
   
    
}
