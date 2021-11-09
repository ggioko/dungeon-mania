package dungeonmania.entities;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Moving.Mercenary;
import dungeonmania.entities.Moving.MovingEntity;
import dungeonmania.entities.Static.Wall;
import dungeonmania.entities.collectable.Armour;
import dungeonmania.entities.collectable.buildable.Midnight_Armour;
import dungeonmania.entities.collectable.buildable.Shield;
import dungeonmania.util.Position;

import java.util.ArrayList;
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
    public void move(Position pos, List<Entity> walls, int width, int height) {
        boolean move = true;
        boolean moveMercenary = false;
        List<Entity> temp = new ArrayList<Entity>();
        for (Entity e : walls) {
            if (!(e instanceof MovingEntity)) {
                temp.add(e);
            }
        }
        walls = temp;
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
                        m.move(old, walls, width, height);
                    }
                }
            }
        }
    }

    public void setBattling (boolean battling) {
        this.battling = battling;
    }

    public void takeDamage(double enemyHP, double enemyAD, Dungeon dungeon, MovingEntity enemy) {
        //Armour cuts enemy damage to half
        if (dungeon.getItem("armour") != null) {
            enemyAD = enemyAD/2;
            Armour.durability -= 1;
            Armour.isBroken(dungeon.getItems());
            // decrease armour durability by 1 // TODO
        }
        //Shield cuts enemy damage to half
        //If player has shield and armour, 75% of damage is negated.
        if (dungeon.getItem("shield") != null) {
            Shield shield = (Shield) dungeon.getItem("shield");
            enemyAD = shield.effect(enemyAD, dungeon.getItems());
        }

        if (dungeon.getItem("midnight_armour") != null) {
            Midnight_Armour m_armour = (Midnight_Armour) dungeon.getItem("midnight_armour");
            enemyAD = m_armour.effect(enemyAD, dungeon.getItems());
        }

        this.setHealth(this.health - ((enemyHP * enemyAD) / 10));
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
