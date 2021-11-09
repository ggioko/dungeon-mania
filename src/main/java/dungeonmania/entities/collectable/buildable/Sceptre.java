package dungeonmania.entities.collectable.buildable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;

public class Sceptre extends Buildable {

    private static final int woodNeeded = 1;
    private static final int arrowsNeeded = 2;
    private static final int keyNeeded = 1;
    private static final int treasureNeeded = 1;
    private static final int sunStoneNeeded = 1;
    private static final List<String> recipe = Arrays.asList("wood", "arrow", "sun_stone", "treasure", "key_1", "key_2");

    public Sceptre(String id, String type) {
        super(id, type);
    }

    @Override
    public boolean isBuildable(List<Entity> inventory) {
        HashMap<String, Integer> materialCount = getRelevantMaterialCount(inventory);
        if (!materialCount.isEmpty()) {
            if (materialCount.get("wood") >= woodNeeded || materialCount.get("arrow") >= arrowsNeeded) {
                if (materialCount.get("key_1") >= keyNeeded || materialCount.get("key_2") >= keyNeeded || materialCount.get("treasure") >= treasureNeeded) {
                    if (materialCount.get("sun_stone") >= sunStoneNeeded) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Map<String, Integer> materialNeeded(List<Entity> inventory) {
        HashMap<String, Integer> materialCount = getRelevantMaterialCount(inventory);
        Map<String, Integer> returnMap = new HashMap<>();
        if (materialCount.get("wood") >= woodNeeded) {
            returnMap.put("wood", 1);
        } else if (materialCount.get("arrow") >= arrowsNeeded) {
            returnMap.put("arrow", 2);
        }

        if (materialCount.get("treasure") >= treasureNeeded) {
            returnMap.put("treasure", 1);
        } else if (materialCount.get("key_1") >= keyNeeded) {
            returnMap.put("key_1", 1);
        } else if (materialCount.get("key_2") >= keyNeeded) {
            returnMap.put("key_2", 1);
        }

        returnMap.put("sun_stone", 1);
        return returnMap;
    }

    @Override
    public HashMap<String, Integer> getRelevantMaterialCount(List<Entity> inventory) {
        HashMap<String, Integer> materialCount = new HashMap<>();

        for (Entity item : inventory) {
            if (recipe.contains(item.getType()) && materialCount.containsKey(item.getType())) {
                materialCount.put(item.getType(), materialCount.get(item.getType()) + 1);
            } else if (recipe.contains(item.getType()) && !materialCount.containsKey(item.getType())) {
                materialCount.put(item.getType(), 1);
            }
        }
        return materialCount;
    }

    @Override
    public void subtractDurability(List<Entity> inventory) {        
    }
    
}
