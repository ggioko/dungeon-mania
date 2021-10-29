package dungeonmania.entities.Moving;

import dungeonmania.util.Position;
import dungeonmania.entities.Moving.MovingEntity;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.Static.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.List;
import java.lang.Math;

public class Mercenary extends MovingEntity{
    private boolean bribed;

    public Mercenary(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1;
        this.bribed = false;
        this.setInteractable(true);
    }

    @Override
    public void move(Position pos, List<Entity> walls) {
        //move mercenary towards position pathfinding algorithm
        int xdiff = pos.getX() - this.getPosition().getX();
        int ydiff = pos.getY() - this.getPosition().getY();


        boolean moved = false;
        if (Math.abs(xdiff) > Math.abs(ydiff)) {
            //x++
            if (pos.getX() - this.getPosition().getX() < 0 ) {
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
            if (moved == false && pos.getX() - this.getPosition().getX() > 0 ) {
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
                        if (w.getPosition().equals(this.getPosition().translateBy(Direction.LEFT))) {
                            move = false;
                        }
                    }
                    if (move) {
                        this.setPosition(this.getPosition().translateBy(Direction.LEFT));
                        moved = true;
                    }
                } 
                if (moved == false && pos.getX() - this.getPosition().getX() > 0 ) {
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
            }
        } 
    }
    
    public boolean isInBribableRange (Position playerPosition) {
        int x = this.getPosition().getX() - playerPosition.getX();
        int y = this.getPosition().getY() - playerPosition.getY();
        if (Math.abs(x) >= 2 && Math.abs(y) >= 2){
            return true;
        }
        else {
            return false;
        }
    }
       
    public void setBribed (boolean bribed) {
        this.bribed = bribed;
    }

    public boolean getBribed () {
        return this.bribed;
    }
}
