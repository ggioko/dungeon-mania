package dungeonmania.entities.Moving;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.Sword;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.Midnight_Armour;

public class Hydra extends Zombie {
    static int ids;

    public Hydra(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1;
    }
    
    public static Dungeon spawn(Dungeon currentDungeon, Position entry) {
        Hydra ass = new Hydra(new JSONObject("{x:"+ entry.getX() + ",y:"+entry.getY()+",type:hydra}"));
        ass.setId("hydra_" + ((Integer)ids).toString());
        List<Entity> ents = currentDungeon.getEntities();
        ents.add(ass);
        currentDungeon.setEntities(ents);
        ids++;
        return currentDungeon;
    }

    @Override
    public void takeDamage(double playerHP, double playerAD, Dungeon dungeon) {
        if (Math.random() < 0.5 && dungeon.getItem("anduril") != null) {
            this.setHealth(this.health + ((playerHP * playerAD) / 5));
        } else {
            if (dungeon.getItem("anduril") != null) {
                this.setHealth(this.health - 3);
                Sword.durability -= 1;
                Sword.isBroken(dungeon.getItems());
                // decrease sword durability by 1 // TODO
            }
            else if (dungeon.getItem("sword") != null) {
                this.setHealth(this.health - 1);
                Sword.durability -= 1;
                Sword.isBroken(dungeon.getItems());
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
    }
}  
