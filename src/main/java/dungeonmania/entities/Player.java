package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.entities.Static.Wall;
import dungeonmania.util.Position;
import java.util.List;

public class Player extends Entity {
    
    int health;
    int attack;

    public Player(JSONObject entity) {
        super(entity);
        this.health = 10;
        this.attack = 1;
    }
    
    //getters

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
        boolean move = true;
        for (Wall w : walls) {
            if (w.getPosition().equals(pos)) {
                move = false;
            }
        }
        if (move) {
            this.setPosition(pos);
        }
    }

}
