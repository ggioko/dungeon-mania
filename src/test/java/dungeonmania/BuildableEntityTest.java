package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.util.*;
import spark.utils.Assert;

import java.io.IOException;
import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;

public class BuildableEntityTest {
    @Test
    public void testCraftingBow() {
        // test for crafting a bow
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse d = controller.newGame("advanced-2", "standard");

        System.out.println(d.getInventory());

        controller.interact("wood1111");
        controller.interact("arrow1113");
        controller.interact("arrow1114");
        controller.interact("arrow1214");

        System.out.println(d.getInventory());
        System.out.println(d.getBuildables());



        assertDoesNotThrow(() -> {
            controller.build("bow");
        });

        System.out.println(d.getInventory());

    }

    @Test
    public void testNotEnoughMatForBow() {
        // test for crafting a bow when you don't have enough material
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("advanced-2", "standard");

        controller.interact("wood1111");
        controller.interact("arrow1113");
        controller.interact("arrow1114");

        assertThrows(InvalidActionException.class, () -> {
            controller.build("bow");
        });
    }

    @Test
    public void testCraftingShield_Treasure() {
        // test for crafting a shield with treasure
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("advanced-2", "standard");

        controller.interact("wood1111");
        controller.interact("wood1112");
        controller.interact("treasure710");

        controller.build("shield");

        assertThrows(InvalidActionException.class, () -> {
            controller.tick("wood1111", Direction.NONE);
            controller.tick("wood1112", Direction.NONE);
            controller.tick("treasure710", Direction.NONE);
        });
        assertDoesNotThrow(() -> {
            controller.tick("shield", Direction.NONE);
        });
    }

    @Test
    public void testCraftingShield_Key() {
        // test for crafting a shield with treasure
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("advanced-2", "standard");

        controller.interact("wood1111");
        controller.interact("wood1112");
        controller.interact("key1110");

        controller.build("shield");

        assertThrows(InvalidActionException.class, () -> {
            controller.tick("wood1111", Direction.NONE);
            controller.tick("wood1112", Direction.NONE);
            controller.tick("key1110", Direction.NONE);
        });
        assertDoesNotThrow(() -> {
            controller.tick("shield", Direction.NONE);
        });
    }

    @Test
    public void testNotEnoughMatForShield() {
        // test for crafting a Shield when you don't have enough material
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("advanced-2", "standard");

        controller.interact("wood1112");
        controller.interact("key1110");

        assertThrows(InvalidActionException.class, () -> {
            controller.build("shield");
        });
    }
}
