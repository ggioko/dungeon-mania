package dungeonmania.goals;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;

public interface Goal {
    public String goalsString();
    public boolean isComplete();
    public boolean add(Goal child);
    public void checkGoalState(List<Entity> entities, Player player);
    public String getName();

}
