package dungeonmania.entities.Static;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.Key;
import dungeonmania.items.Item;

public class Door extends StaticEntity {

    boolean unlocked;
    int key;

    public Door(JSONObject entity) {
        super(entity);
        this.unlocked = false;
        this.key = entity.getInt("key");
        //TODO Auto-generated constructor stub
    }
    
    public Dungeon unlock(List<Entity> entities, List<Item> inventory, Dungeon current) {
        for (Entity e : entities) {
            if (e instanceof Door) {
                String doornum = ((Door)e).getType().replace("door_", "");
                for (Item i : inventory) {
                    if (i.getType().equals("key_1") && doornum.equals("1")) {
                        //unlock
                        ((Door)e).setType("door_unlocked");
                        this.unlocked = true;
                        inventory.remove(i);
                        break;
                    } else if (i.getType().equals("key_2") && doornum.equals("2")) {
                        //unlock
                        ((Door)e).setType("door_unlocked");
                        this.unlocked = true;
                        inventory.remove(i);
                        break;
                    }
                }
            }
        }
        current.setItems(inventory);
        current.setEntities(entities);
        return current;
    }
}
