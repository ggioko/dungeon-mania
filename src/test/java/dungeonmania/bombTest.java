package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dungeonmania.util.*;

import java.io.IOException;
import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.Test;

public class bombTest {
    @Test
    public void testBomb() {
        // test for crafting a bow
        DungeonManiaController controller = new DungeonManiaController();

        controller.newGame("bombTest", "standard");
        
        //Picks up bomb
        controller.tick(null, Direction.DOWN);
        assertTrue(!(controller.currentDungeon.getItem("bomb") == null));
        assertTrue(controller.currentDungeon.getEntity("bomb12") == null);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        //Place bomb down
        controller.tick("bomb12", Direction.NONE);
        assertTrue(controller.currentDungeon.getItem("bomb") == null);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        //Push boudler into switch
        controller.tick(null, Direction.RIGHT);
        //Walls within one radius of bomb should be removed
        assertTrue(controller.currentDungeon.getEntity("wall41") == null);
        assertTrue(controller.currentDungeon.getEntity("wall51") == null);
        assertTrue(controller.currentDungeon.getEntity("wall52") == null);
        assertTrue(controller.currentDungeon.getEntity("wall53") == null);
        assertTrue(controller.currentDungeon.getEntity("wall43") == null);
        assertTrue(controller.currentDungeon.getEntity("boulder22") == null);
        assertTrue(controller.currentDungeon.getEntity("switch32") == null);
        assertTrue(controller.currentDungeon.getEntity("bomb12") == null);
    }

}
