package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dungeonmania.util.*;
import spark.utils.Assert;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.ResourceBundle.Control;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.entities.*;
import dungeonmania.entities.Moving.Assassin;
import dungeonmania.entities.Moving.Hydra;
import dungeonmania.entities.Moving.Mercenary;


public class HydraTest {
    @Test
    public void HydraSpawnTest() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("interact", "hard");
        //wait 50 ticks for spawn
        for (int i = 0; i <=51; i++) {
            controller.tick(null, Direction.UP);
        }

        //check if it is spawned
        boolean spawned = false;
        for (Entity e : controller.currentDungeon.getEntities()) {
            if (e instanceof Hydra) {
                spawned = true;
            }
        }
        assertTrue(spawned);
    }
    @Test
    public void HydraTest() {
        //take an average of health when fighing hydra
        double wins = 0;
        for (int i = 0; i <=1000; i++) {
            DungeonManiaController controller = new DungeonManiaController();
            controller.newGame("hydra", "hard");
            //when players health is 5 the winrate should be 50%
            controller.currentDungeon.player.setHealth(5);
            //colide with hyrda
            controller.tick(null, Direction.NONE);
            if (controller.currentDungeon != null) {
                wins += 1;
            }
        }
        //check if winrate is 50%. if it is it means the hydra will heal instead of take damage 50% of the time
        assertTrue(wins/1000 > -0.05 && wins/1000 < 0.05);
    }
}
