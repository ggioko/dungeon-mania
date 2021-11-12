package dungeonmania.entities.Moving;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Static.SwampTile;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.Midnight_Armour;
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

    boolean slowed;
    double health;
    double attack;

    public MovingEntity(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
        this.slowed = false;
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

    public boolean isSlowed() {
        return this.slowed;
    }
    public void setSlowed(boolean slowed) {
        this.slowed = slowed;
    }

    @Override
    public void move(Position pos, List<Entity> walls, int width, int height) {
        return;
    }

    @Override
    public void moveAway(Position pos, List<Entity> walls) {
        return;
    }

    public void takeDamage(double playerHP, double playerAD, Dungeon dungeon) {
        if (dungeon.getItem("sword") != null) {
            this.setHealth(this.health - 1);
            Sword.durability -= 1;
            Sword.isBroken(dungeon.getItems());
            // decrease sword durability by 1 // TODO
        }
        //Bow allows player to attack twice
        if (dungeon.getItem("bow") != null) {
            Bow bow = (Bow) dungeon.getItem("bow");
            bow.effect(this, this.health, playerHP, playerAD, dungeon.getItems());
        }
        
        if (dungeon.getItem("midnight_armour") != null) {
            Midnight_Armour m_armour = (Midnight_Armour) dungeon.getItem("midnight_armour");
            this.setHealth(this.health + m_armour.effect(-1, dungeon.getItems()));
        }

        this.setHealth(this.health - ((playerHP * playerAD) / 5));
    }
    
    public boolean isInSwapTile(List<Entity> entities) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        for (Entity entity : entities) {
            if (entity instanceof SwampTile) {
                if (entity.getPosition().getX() == x && entity.getPosition().getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
