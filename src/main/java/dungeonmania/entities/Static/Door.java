package dungeonmania.entities.Static;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectable.Key;
import dungeonmania.items.Item;
import dungeonmania.util.Direction;

public class Door extends StaticEntity {

    boolean unlocked;
    int key;

    public Door(JSONObject entity) {
        super(entity);
        this.unlocked = false;
        this.key = entity.getInt("key");
        //TODO Auto-generated constructor stub
    }
    
    public Dungeon unlock(List<Entity> entities, List<Item> inventory, Dungeon current, Player player, Direction direction) {
        String doornum = this.getType().replace("door_", "");
        for (Item i : inventory) {
            if (i.getType().equals("key_1") && doornum.equals("1") && player.getPosition().translateBy(direction).equals(this.getPosition())) {
                //unlock
                this.setType("door_unlocked");
                this.unlocked = true;
                inventory.remove(i);
                break;
            } else if (i.getType().equals("key_2") && doornum.equals("2") && player.getPosition().translateBy(direction).equals(this.getPosition())) {
                //unlock
                this.setType("door_unlocked");
                this.unlocked = true;
                inventory.remove(i);
                break;
            }
        }
        current.setItems(inventory);

        current.setEntities(entities);
        return current;
    }
}
