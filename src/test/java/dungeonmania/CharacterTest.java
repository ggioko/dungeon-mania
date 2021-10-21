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

public class CharacterTest {
    @Test
    public void testItemUsed() {
        // test for invalid items
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        controller.interact("bomb134");
        controller.interact("invincibility_potion1110");
        assertThrows(IllegalArgumentException.class, () -> {
            controller.tick("bomb1", Direction.NONE);
        });
        assertDoesNotThrow(() -> {
            controller.tick("bomb134", Direction.NONE);
            controller.tick("invincibility_potion1110", Direction.NONE);
        });
  
    }

    @Test
    public void testItemNotInInventory() {
        // test for not in inv
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        controller.interact("invincibility_potion1110");
        assertThrows(InvalidActionException.class, () -> {
            controller.tick("bomb134", Direction.NONE);
        });
        assertDoesNotThrow(() -> {
            controller.tick("invincibility_potion1110", Direction.NONE);
        });
  
    }

    @Test
    public void testMovement() {
        // test for not in inv
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse start = controller.newGame("advanced", "standard");
        DungeonResponse end = start;
        //player sshould nto have moved because of walls
        end = controller.tick(null, Direction.UP);
        assertTrue(start.equals(end));
        end = controller.tick(null, Direction.LEFT);
        assertTrue(start.equals(end));
        //player should move
        end = controller.tick(null, Direction.RIGHT);
        assertFalse(start.equals(end));
        end = controller.tick(null, Direction.LEFT);
        assertTrue(start.equals(end));
        end = controller.tick(null, Direction.DOWN);
        assertFalse(start.equals(end));
  
    }
}