package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.util.*;
import spark.utils.Assert;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.items.Item;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;

public class PotionsTest {
    @Test
    public void healthPotionNotUsedTest() {
        // test for healthPotion not used
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("potions", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        assertTrue(controller.currentDungeon.player.getHealth() == 10);
        // Move to fight enemies
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        assertTrue(controller.currentDungeon.player.getHealth() == 10);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        
        assertTrue(controller.currentDungeon.player.getHealth() != 10);
    }


    @Test
    public void healthPotionUsedTest() {
        // test for healthPotion not used
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("potions", "standard");

        assertTrue(controller.currentDungeon.inventory.isEmpty());
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        assertTrue(controller.currentDungeon.player.getHealth() == 10);
        // Move to fight enemies
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        assertTrue(controller.currentDungeon.player.getHealth() != 10);
        controller.tick("health_potion11", Direction.LEFT);
        assertTrue(controller.currentDungeon.player.getHealth() == 10);

        
    }
}
