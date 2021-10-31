package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.Static.Wall;
import dungeonmania.util.Position;
import java.util.List;

public class Player extends Entity {
    
    double health;
    double attack;
    boolean invincibility_potion_effect;

    public Player(JSONObject entity) {
        super(entity);
        this.health = 10;
        this.attack = 1;
        this.invincibility_potion_effect = false;
    }
    
    //getters

    public boolean isInvincibility_potion_effect() {
        return invincibility_potion_effect;
    }

    public void setInvincibility_potion_effect(boolean invincibility_potion_effect) {
        this.invincibility_potion_effect = invincibility_potion_effect;
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
