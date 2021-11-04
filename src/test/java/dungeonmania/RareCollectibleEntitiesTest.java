package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.*;
import dungeonmania.util.*;

public class RareCollectibleEntitiesTest {
    @Test
    public void testTheOneRing() { // Test if character respawns with full health after getting killed
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "standard");
        controller.currentDungeon.inventory.add(new Entity("one_ring_test", "one_ring"));
        controller.currentDungeon.getPlayer().setHealth(0.01);
        controller.tick(null, Direction.DOWN);
        //player runs into hostile entity
        //Check if player respawns
        assertTrue(controller.currentDungeon.getPlayer().getHealth() > 9);
        //Check if one ring is one use
        assertTrue(controller.currentDungeon.inventory.isEmpty());
    }
}