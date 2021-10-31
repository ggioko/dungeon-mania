package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.entities.Static.Wall;
import dungeonmania.util.Position;
import java.util.List;

public class Player extends Entity {
    
    double health;
    double attack;

    public Player(JSONObject entity) {
        super(entity);
        this.health = 10;
        this.attack = 1;
    }
    
    //getters

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
        boolean move = true;
        for (Entity w : walls) {
            if (w.getPosition().equals(pos)) {
                move = false;
            }
        }
        if (move) {
            this.setPosition(pos);
        }
    }

}
