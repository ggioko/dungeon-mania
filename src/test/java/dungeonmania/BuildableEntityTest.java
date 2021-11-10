package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.util.*;
import spark.utils.Assert;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectable.buildable.Bow;
import dungeonmania.entities.collectable.buildable.Shield;
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
    public void testCraftingSceptre() {
        // test for crafting a sceptre
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");
        
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);

        assertDoesNotThrow(() -> {
            controller.build("sceptre");
        });
    }

    @Test
    public void testCraftingMidnightArmour_withoutZombie() {
        // test for crafting a midnight armour
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");
        
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);

        assertDoesNotThrow(() -> {
            controller.build("midnight_armour");
        });
    }

    @Test
    public void testCraftingMidnightArmour_withZombie() {
        // test for crafting a midnight armour when zombie exists on map
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting_zombie", "standard");
        
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);

        assertThrows(InvalidActionException.class, () -> {
            controller.build("midnight_armour");
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

    @Test
    public void testBuildableDurability() {
        // test for buildable items durability
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("crafting", "standard");

        // collect items for shield and bow
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        // create shield and bow
        assertDoesNotThrow(() -> {
            controller.build("shield");
            controller.build("bow");
        });

        Bow bow = (Bow) controller.currentDungeon.getItem("bow");
        bow.setDurability(1);
        Shield shield = (Shield) controller.currentDungeon.getItem("shield");
        shield.setDurability(1);
        
        // look for and fight enemies        
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        
        // shield durability == 0 && not in inventory
        assertNull(controller.currentDungeon.getItem("shield"));

        // bow durability == 0 && not in inventory
        assertNull(controller.currentDungeon.getItem("bow")); 
        
    }

    @Test
    public void testShieldEffect() {
        // Test if shield halves incoming damage
        DungeonManiaController controller1 = new DungeonManiaController();
        DungeonManiaController controller2 = new DungeonManiaController();
        
        controller1.newGame("crafting", "standard");
        controller2.newGame("crafting", "standard");

        controller1.tick(null, Direction.UP);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.LEFT);

        assertDoesNotThrow(() -> {
            controller1.build("shield");
        });

        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);

        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);

        assertTrue(controller1.currentDungeon.player.getHealth() > controller2.currentDungeon.player.getHealth());
    }

    @Test
    public void testMidnight_ArmourDefenceEffect() {
        DungeonManiaController controller1 = new DungeonManiaController();
        DungeonManiaController controller2 = new DungeonManiaController();

        controller1.newGame("crafting", "standard");
        controller2.newGame("crafting", "standard");

        controller1.tick(null, Direction.DOWN);
        controller1.tick(null, Direction.LEFT);
        controller1.tick(null, Direction.DOWN);

        assertDoesNotThrow(() -> {
            controller1.build("midnight_armour");
        });

        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);
        controller1.tick(null, Direction.RIGHT);

        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);
        controller2.tick(null, Direction.RIGHT);

        assertTrue(controller1.currentDungeon.player.getHealth() > controller2.currentDungeon.player.getHealth());
    }
}
