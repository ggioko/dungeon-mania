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
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;

public class BuildableEntityTest {
    @Test
    public void testCraftingBow() {
        // test for crafting a bow
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");
        
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        assertDoesNotThrow(() -> {
            controller.build("bow");
        });
    }

    @Test
    public void testNotEnoughMatForBow() {
        // test for crafting a bow when you don't have enough material
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        assertThrows(InvalidActionException.class, () -> {
            controller.build("bow");
        });
    }

    @Test
    public void testCraftingShield_Treasure() {
        // test for crafting a shield with treasure
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        assertDoesNotThrow(() -> {
            controller.build("shield");
        });
    }

    @Test
    public void testCraftingShield_Key() {
        // test for crafting a shield with treasure
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);

        assertDoesNotThrow(() -> {
            controller.build("shield");
        });
    }

    @Test
    public void testNotEnoughMatForShield() {
        // test for crafting a Shield when you don't have enough material
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);

        assertThrows(InvalidActionException.class, () -> {
            controller.build("shield");
        });
    }

    // @Test
    // public void testShieldDurability() {
    //     // test for shield durability
    //     DungeonManiaController controller = new DungeonManiaController();

    //     controller.newGame("crafting", "standard");

    //     controller.tick(null, Direction.DOWN);
    //     controller.tick(null, Direction.UP);
    //     controller.tick(null, Direction.LEFT);
    //     controller.tick(null, Direction.UP);
    //     controller.tick(null, Direction.RIGHT);
    //     controller.tick(null, Direction.LEFT);
    //     controller.tick(null, Direction.UP);
    //     controller.tick(null, Direction.LEFT);
    //     controller.tick(null, Direction.LEFT);

    //     assertDoesNotThrow(() -> {
    //         controller.build("shield");
    //         controller.build("bow");
    //     });

    //     controller.tick(null, Direction.LEFT);
    //     controller.tick(null, Direction.LEFT);
    //     controller.tick(null, Direction.LEFT);
    //     controller.tick(null, Direction.LEFT);
    //     controller.tick(null, Direction.LEFT);
    //     controller.tick(null, Direction.RIGHT);

    //     System.out.println(controller.currentDungeon.getBuildableFromInventory("shield").getDurability());


    //     assertTrue(controller.currentDungeon.getItem("shield").equals(null));

    // }
}
