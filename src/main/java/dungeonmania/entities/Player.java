package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.entities.Moving.Mercenary;
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
    public void move(Position pos, List<Entity> walls) {
        boolean move = true;
        boolean moveMercenary = false;
        for (Entity w : walls) {
            if (w.getPosition().equals(pos)) {
                if (w instanceof Mercenary) {
                    moveMercenary = true;
                }
                else {
                    move = false;
                }
            }
        }
        if (move) {
            Position old = this.getPosition();
            this.setPosition(pos);
            if (moveMercenary) {
                for (Entity entity : walls) {
                    if (entity instanceof Mercenary) {
                        Mercenary m = (Mercenary) entity;
                        m.move(old, walls);
                    }
                }
            }
        }
    }

}
