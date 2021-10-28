package dungeonmania.entities.Moving;

import org.json.JSONObject;

import dungeonmania.entities.Static.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Spider extends MovingEntity {

    boolean spawned;
    int pathnum;
    List<Direction> directions = new ArrayList<Direction>(
        Arrays.asList(Direction.LEFT, Direction.DOWN, Direction.DOWN, Direction.RIGHT, Direction.RIGHT, Direction.UP, Direction.UP, Direction.LEFT)
    );
    

    public Spider(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1; 
        this.spawned = true;
        this.pathnum = 0;
        //TODO Auto-generated constructor stub
    }

    @Override
    public void move(Position pos, List<Wall> walls) {
        if (this.spawned) {
            this.setPosition(this.getPosition().translateBy(Direction.UP));
            this.spawned = false;
            return;
        }
        //move in circle
        this.setPosition(this.getPosition().translateBy(directions.get(pathnum)));
        this.pathnum++;
        if (this.pathnum == 8) {
            this.pathnum = 0;
        }
    }

}
