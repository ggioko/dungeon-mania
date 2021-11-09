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
        if (Math.random() < 0.5) {
            this.setHealth(this.health + ((playerHP * playerAD) / 5));
        } else {
            if (dungeon.getItem("sword") != null) {
                this.setHealth(this.health - 1);
                Sword.durability -= 1;
                Sword.isBroken(dungeon.getItems());
            }
            this.setHealth(this.health - ((playerHP * playerAD) / 5));
        }
    }
}  
