package dungeonmania.entities.Moving;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.Static.Wall;
import java.util.List;

import org.json.JSONObject;

public class MovingEntity extends Entity {

    /* from entity
    String id;
    String type;
    Position position;
    boolean isInteractable;
    */

    double health;
    double attack;

    public MovingEntity(JSONObject entity) {
        super(entity);
        //TODO Auto-generated constructor stub
    }

    public double getHealth() {
        return this.health;
    }
    public void setHealth(double health) {
        this.health = health;
    }

    public double getAttack() {
        return this.attack;
    }
    public void setAttack(double attack) {
        this.attack = attack;
    }
    @Override
    public void move(Position pos, List<Entity> walls) {
        return;
    }

   
    public void moveAway(Position pos, List<Entity> walls) {
        //move mercenary towards position pathfinding algorithm
        int xdiff = pos.getX() - this.getPosition().getX();
        int ydiff = pos.getY() - this.getPosition().getY();


        boolean moved = false;
        if (Math.abs(xdiff) > Math.abs(ydiff)) {
            //x++
            if (pos.getX() - this.getPosition().getX() < 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.RIGHT))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.RIGHT));
                    moved = true;
                }
            } 
            if (moved == false && pos.getX() - this.getPosition().getX() > 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.LEFT))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.LEFT));
                    moved = true;
                }
            }
        } 
        if (moved == false) {
            //x++
            if (pos.getY() - this.getPosition().getY() < 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.UP))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.UP));
                    moved = true;
                }
            } 
            if (moved == false && pos.getY() - this.getPosition().getY() > 0 ) {
                boolean move = true;
                for (Entity w : walls) {
                    if (w.getPosition().equals(this.getPosition().translateBy(Direction.DOWN))) {
                        move = false;
                    }
                }
                if (move) {
                    this.setPosition(this.getPosition().translateBy(Direction.DOWN));
                    moved = true;
                }
            }
            if (moved == false) {
                if (pos.getX() - this.getPosition().getX() < 0 ) {
                    boolean move = true;
                    for (Entity w : walls) {
                        if (w.getPosition().equals(this.getPosition().translateBy(Direction.RIGHT))) {
                            move = false;
                        }
                    }
                    if (move) {
                        this.setPosition(this.getPosition().translateBy(Direction.RIGHT));
                        moved = true;
                    }
                } 
                if (moved == false && pos.getX() - this.getPosition().getX() > 0 ) {
                    boolean move = true;
                    for (Entity w : walls) {
                        if (w.getPosition().equals(this.getPosition().translateBy(Direction.LEFT))) {
                            move = false;
                        }
                    }
                    if (move) {
                        this.setPosition(this.getPosition().translateBy(Direction.RIGHT));
                        moved = true;
                    }
                }
            }
        } 
    }
    
}
