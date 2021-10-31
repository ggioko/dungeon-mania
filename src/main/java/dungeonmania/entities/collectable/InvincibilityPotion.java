package dungeonmania.entities.collectable;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.Dungeon;
import dungeonmania.entities.Player;
import dungeonmania.items.Item;

public class InvincibilityPotion extends CollectableEntity {

    public InvincibilityPotion(JSONObject entity) {
        super(entity);
        int durability = 1;
        this.setDurability(durability);
    }

    public static Dungeon addEffects(Dungeon currentDungeon, String itemUsed, Player player, List<Item> inventory) {
        Item invincibility = null;
        
        if (itemUsed != null) {
            if (itemUsed.contains("invincibility_potion")) {
                invincibility = currentDungeon.getItem("invincibility_potion");
                player.setInvincibilityPotionEffect(true);
            }
            if (itemUsed.contains("invincibility_potion") && player.isInvincibilityPotionEffect() == true) {
                inventory.remove(invincibility);
            } 
        }
        currentDungeon.setItems(inventory);
        currentDungeon.setPlayer(player);
        return currentDungeon;
    }
    
}
