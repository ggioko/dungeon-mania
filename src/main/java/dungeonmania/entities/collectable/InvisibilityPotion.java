package dungeonmania.entities.collectable;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;
import dungeonmania.items.Item;

public class InvisibilityPotion extends CollectableEntity {

    public InvisibilityPotion(JSONObject entity) {
        super(entity);
        int durability = 1;
        this.setDurability(durability);
    }
    
    public static Dungeon addEffects(Dungeon currentDungeon, String itemUsed, Player player, List<Item> inventory) {
        Item invisibility = null;
        
        if (itemUsed != null) {
            if (itemUsed.contains("invisibility_potion")) {
                invisibility = currentDungeon.getItem("invisibility_potion");
                player.setInvisibilityPotionEffect(true);
            }
            if (itemUsed.contains("invisibility_potion") && player.isInvisibilityPotionEffect() == true) {
                inventory.remove(invisibility);
            } 
        }
        currentDungeon.setItems(inventory);
        currentDungeon.setPlayer(player);
        return currentDungeon;
    }
}
