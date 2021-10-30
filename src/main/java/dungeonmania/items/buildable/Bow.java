package dungeonmania.items.buildable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.items.Item;

public class Bow extends Buildable {

    private static final List<String> recipe = Arrays.asList("wood", "arrow");
    private static final int woodNeeded = 1;
    private static final int arrowNeeded = 3;

    private int durability;

    public Bow(String id, String type) {
        super(id, type);
        this.durability = 20;
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

        if (!materialCount.isEmpty() && materialCount.containsKey("wood") && materialCount.containsKey("arrow")) {
            if (materialCount.get("wood") >= woodNeeded && (materialCount.get("arrow") >= arrowNeeded)) {
                return true;
            } 
        }
            
        return false;
    }

    @Override
    public Map<String, Integer> materialNeeded(List<Item> inventory) {
        
        Map<String, Integer> returnMap = Map.of(
            "wood", 1,
            "arrow", 3
        );

        return returnMap;
    }    

    @Override
    public void subtractDurability(List<Item> inventory) {
        this.durability -= 1;
        if (this.durability == 0) {
            inventory.remove(this);
        }
    }

    public int getDurability() {
        return durability;
    }
}
