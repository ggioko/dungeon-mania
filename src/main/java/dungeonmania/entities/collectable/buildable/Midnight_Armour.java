package dungeonmania.entities.collectable.buildable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Moving.Zombie;

public class Midnight_Armour extends Buildable {

    private static final List<String> recipe = Arrays.asList("armour", "sun_stone");



    public Midnight_Armour(String id, String type) {
        super(id, type);
    }

    @Override
    public boolean isBuildable(List<Entity> inventory) {
        return false;
    }

    public boolean isBuildable(List<Entity> inventory, List<Entity> entities) {
        HashMap<String, Integer> materialCount = getRelevantMaterialCount(inventory);

        if (!materialCount.isEmpty()) {
            if (materialCount.containsKey("armour") && materialCount.containsKey("sun_stone") && !Zombie.zombieExistOnMap(entities)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Integer> materialNeeded(List<Entity> inventory) {
        Map<String, Integer> returnMap = Map.of(
            "armour", 1,
            "sun_stone", 1
        );

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
