package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.Static.Wall;
import dungeonmania.util.Position;
import java.util.List;

public class Player extends Entity {
    
    double health;
    double attack;
    boolean battling;
    boolean ally;
    boolean invincibilityPotionEffect;
    boolean invisibilityPotionEffect;

    public Player(JSONObject entity) {
        super(entity);
        this.health = 10;
        this.attack = 1;
        this.battling = false;
        this.ally = false;
        this.invincibilityPotionEffect = false;
        this.invisibilityPotionEffect = false;
    }
    
    //getters

    public boolean isInvisibilityPotionEffect() {
        return invisibilityPotionEffect;
    }

    public void setInvisibilityPotionEffect(boolean invisibilityPotionEffect) {
        this.invisibilityPotionEffect = invisibilityPotionEffect;
    }

    public boolean isInvincibilityPotionEffect() {
        return invincibilityPotionEffect;
    }

    public void setInvincibilityPotionEffect(boolean invincibilityPotionEffect) {
        this.invincibilityPotionEffect = invincibilityPotionEffect;
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

    public void setBattling (boolean battling) {
        this.battling = battling;
    }

    public boolean isBattling () {
        return this.battling;
    }

    public void setAlly (boolean ally) {
        this.ally = ally;
    }

    public boolean haveAlly () {
        return this.ally;
    }

}
