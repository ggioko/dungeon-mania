package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dungeonmania.util.*;
import spark.utils.Assert;

import java.io.IOException;
import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.items.*;

public class InteractTest {
    @Test
    public void entityIdNotValid() {
        // test for invalid entity Id
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "standard");
        assertThrows(IllegalArgumentException.class, () -> {
            controller.interact("dog");
        });
    }

    @Test
    public void playerNotWithinRangeOfMercenary() {
        // test for mercenary within 2 cardinal tiles of player
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "standard");
        assertThrows(InvalidActionException.class, () -> {
            controller.interact("mercenary51");
        });
    }

    @Test
    public void playerNotCardinallyAdjacentOfSpawner() {
        // test for spawner cadinally adjacent to player
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "standard");
        assertThrows(InvalidActionException.class, () -> {
            controller.interact("zombie_toast_spawner41");
        });
    }

    @Test
    public void playerDoesNotHaveGold() {
        // test for if player has enough gold to bribe
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "standard");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertThrows(InvalidActionException.class, () -> {
            controller.interact("mercenary51");
        });
    }

    @Test
    public void playerDoesNotHaveWeapon() {
        // test for if player has a weapon to break spawner
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "standard");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertThrows(InvalidActionException.class, () -> {
            controller.interact("zombie_toast_spawner14");
        });
    }

    @Test
    public void playerBribesMercenary() {
        // test for if player bribes mercenary
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "standard");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.currentDungeon.inventory.add(new Item("treasure_test", "treasure"));
        assertDoesNotThrow(() -> {
            controller.interact("mercenary51");
        });
        // check if gold is gone from inventory
        assertTrue(controller.currentDungeon.inventory.isEmpty());
    }

    @Test
    public void playerDestroysSpawner() {
        // test for if player breaks spawner
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "standard");
        
        assertThrows(InvalidActionException.class, () -> {
            controller.interact("zombie_toast_spawner14");
        });

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);

        for (Item i : controller.currentDungeon.inventory){
            System.out.println(i.getType());
        }

        assertDoesNotThrow(() -> {
            controller.interact("zombie_toast_spawner14");
        });

        assertTrue(controller.currentDungeon.getEntity("zombie_toast_spawner14") == null);
    }
}
