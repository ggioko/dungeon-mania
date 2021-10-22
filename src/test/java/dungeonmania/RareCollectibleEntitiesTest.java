package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RareCollectibleEntitiesTest {
    @Test
    public void testCharacterHasTheOneRingAfterBattleWin() { // Test if character gets the one ring after winning battle
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
    }

    @Test
    public void testTheOneRingCharacterRespawn() { // Test if character respawns with full health after getting killed
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");   
    }

    @Test
    public void testTheOneRingDiscardAfterUse() { // Test if the one ring discards after one use
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
    }
}