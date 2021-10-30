package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.items.*;

public class RareCollectibleEntitiesTest {
    @Test
    public void testTheOneRing() { // Test if character respawns with full health after getting killed
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "standard");
        controller.currentDungeon.inventory.add(new Item("one_ring_test", "one_ring"));
        controller.currentDungeon.getPlayer().setHealth(1);
        

    }
}