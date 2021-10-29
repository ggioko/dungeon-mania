package dungeonmania.entities.Static;

import org.json.JSONObject;

import dungeonmania.DungeonManiaController;
import dungeonmania.entities.Moving.Zombie;
import dungeonmania.Dungeon;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Position;

import java.util.List;
import dungeonmania.entities.Entity;

public class Spawner extends StaticEntity {

    int ticks;
    int currentTick;

    public Spawner(JSONObject entity, int ticks) {
        super(entity);
        this.ticks = ticks;
        this.currentTick = 0;
    }
    
    public Dungeon spawn(Dungeon current) {
        this.currentTick++;
        if (this.currentTick == this.ticks) {
            JSONObject obj = new JSONObject();
            obj.put("x", this.getPosition().getX());
            obj.put("y", this.getPosition().getY());
            obj.put("type", "zombie_toast");
            Zombie zombie = new Zombie(obj);
            List<Entity> list = current.getEntities();
            list.add(zombie);
            current.setEntities(list);
            this.currentTick = 0;
        }
        return current;
    }

}
