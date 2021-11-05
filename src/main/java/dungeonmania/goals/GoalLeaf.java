package dungeonmania.goals;

import dungeonmania.entities.Player;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Static.Boulder;
import dungeonmania.entities.Static.FloorSwitch;
import dungeonmania.entities.collectable.Treasure;

import java.util.List;

public class GoalLeaf implements Goal {
    private String name;
    private boolean complete;
    
    public GoalLeaf (String name, boolean complete) {
        this.name = name;
        this.complete = complete;
    }

    @Override
    public String goalsString() {
        return this.name;
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public boolean add(Goal child) {
        return false;
    }

    public void setComplete() {
        this.complete = true;
    }

    public void setIncomplete() {
        this.complete = false;
    }

    @Override
    public String nameString() {
        return this.name;
    }

    public boolean checkComplete(List<Entity> entities, Player player) {
        if (this.name.equals("treasure")) {
            if (Treasure.treasureOnMap(entities)) {
                return false;
            } else {
                return true;
            }
        } else if (this.name.equals("enemies")) {
            if (Entity.enemiesOnMap(entities)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // public void checkComplete(List<Entity> entities, Player player) {
    //     if (this.name.equals("exit")) {
    //         for (Entity e : entities) {
    //             if (e.getType().equals("exit") && e.getPosition().equals(player.getPosition())) {
    //                 this.complete = true;
    //                 break;
    //             } else {
    //                 this.complete = false;
    //             }
    //         }
    //     } else if (this.name.equals("treasure")) {
    //         for (Entity e : entities) {
    //             if (e.getType().equals("treasure")) {
    //                 this.complete = false;
    //                 break;
    //             }
    //         }
    //     } else if (this.name.equals("enemies")) {
    //         for (Entity e : entities) {
    //             if (e.getType().equals("mercenary") || e.getType().equals("spider") || e.getType().equals("zombie_toast")) {
    //                 this.complete = false;
    //                 break;
    //             }
    //         }
    //     } else if (this.name.equals("boulders")) {
    //         for (Entity e : entities) {
    //             if (e.getType().equals("wtich")) {
    //                 if (!((FloorSwitch)e).getTriggered()) {
    //                     this.complete = false;
    //                     break;
    //                 }
    //             }
    //         }
    //     }
    // }

    

}  
