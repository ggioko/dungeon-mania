package dungeonmania.entities.collectable;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;
import dungeonmania.items.Item;

public class HealthPotion extends CollectableEntity {
    public static int durability = 1;
    public HealthPotion(JSONObject entity) {
        super(entity);
    }
    
    public static Dungeon addEffects(Dungeon currentDungeon, String itemUsed, Player player, List<Item> inventory) {
        Item health = null;
        if (itemUsed != null) {
            if (itemUsed.contains("health_potion")) {
                health = currentDungeon.getItem("health_potion");
            }
        }
        
        if (health != null && itemUsed != null) {
            player.setHealth(10.0);
            HealthPotion.durability -= 1;
            inventory.remove(health);
        }
        currentDungeon.setItems(inventory);
        currentDungeon.setPlayer(player);
        System.out.println(player.getHealth());
        return currentDungeon;
    }
}
