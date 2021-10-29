package dungeonmania.items.buildable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import dungeonmania.items.Item;

public class Shield extends Buildable {

    private static final int woodNeeded = 2;
    private static final int keyNeeded = 1;
    private static final int treasureNeeded = 1;
    private static final List<String> recipe = Arrays.asList("wood", "treasure", "key");

    public Shield(String id, String type) {
        super(id, type);
    }

    @Override
    public HashMap<String, Integer> getRelevantMaterialCount(List<Item> inventory) {
        HashMap<String, Integer> materialCount = new HashMap<>();

        for (Item item : inventory) {
            if (recipe.contains(item.getType()) && materialCount.containsKey(item.getType())) {
                materialCount.put(item.getType(), materialCount.get(item.getType()) + 1);
            } else if (recipe.contains(item.getType()) && !materialCount.containsKey(item.getType())) {
                materialCount.put(item.getType(), 1);
            }
        }
        return materialCount;
    }

    @Override
    public boolean isBuildable(List<Item> inventory) {

        HashMap<String, Integer> materialCount = getRelevantMaterialCount(inventory);

        if (!materialCount.isEmpty() && materialCount.containsKey("wood") && materialCount.containsKey("key")) {
            if (materialCount.get("wood") >= woodNeeded && (materialCount.get("key") >= keyNeeded)) {
                return true;
            } 
        } else if (!materialCount.isEmpty() && materialCount.containsKey("wood") && materialCount.containsKey("treasure")) {
            if (materialCount.get("wood") >= woodNeeded && (materialCount.get("treasure") >= treasureNeeded)) {
                return true;
            }
        }
            
        return false;
    }

    @Override
    public Map<String, Integer> materialNeeded(List<Item> inventory) {
        
        HashMap<String, Integer> materialCount = getRelevantMaterialCount(inventory);
        Map<String, Integer> returnMap = new HashMap<>();

        if (materialCount.get("treasure") >= keyNeeded) {
            returnMap = Map.of(
                "wood", 2,
                "treasure", 1
            );
        } else {
            returnMap = Map.of(
                "wood", 2,
                "key", 1
            );
        }
        return returnMap;
    }
}
