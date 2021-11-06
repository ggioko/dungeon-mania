package dungeonmania.entities.Moving;

import dungeonmania.util.Position;

import org.json.JSONObject;

import dungeonmania.entities.Entity;

import dungeonmania.util.Direction;
import java.util.List;
import java.lang.Math;
import java.util.ArrayList;

public class Mercenary extends MovingEntity{
    private boolean bribed;
    private boolean inBattle;

    public Mercenary(JSONObject entity) {
        super(entity);
        this.health = 5;
        this.attack = 1;
        this.bribed = false;
        this.inBattle = true;
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

    @Override
    public void moveAway(Position pos, List<Entity> walls) {
        //move mercenary towards position pathfinding algorithm
        int x1 = pos.getX();
        int y1 = pos.getY();
        int x2 = this.getPosition().getX();
        int y2 = this.getPosition().getY();

        //boolean moved = false;
        if (Math.abs(x1) - Math.abs(x2) >= 0) {
            for (Entity w : walls) {
                if (!w.getPosition().equals(this.getPosition().translateBy(Direction.LEFT))) {
                    this.setPosition(this.getPosition().translateBy(Direction.LEFT));
                }
            }
        }
        if (Math.abs(x1) - Math.abs(x2) < 0) {
            for (Entity w : walls) {
                if (!w.getPosition().equals(this.getPosition().translateBy(Direction.RIGHT))) {
                    this.setPosition(this.getPosition().translateBy(Direction.RIGHT));
                }
            }
        } 

        if (Math.abs(y1) - Math.abs(y2) >= 0) {
            for (Entity w : walls) {
                if (!w.getPosition().equals(this.getPosition().translateBy(Direction.DOWN))) {
                    this.setPosition(this.getPosition().translateBy(Direction.DOWN));
                }
            }
            
        }

        if (Math.abs(y1) - Math.abs(y2) < 0) {
            for (Entity w : walls) {
                if (!w.getPosition().equals(this.getPosition().translateBy(Direction.UP))) {
                    this.setPosition(this.getPosition().translateBy(Direction.UP));
                }
            }
        }
        
    }
    
    public boolean isInBribableRange (Position playerPosition) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x-1, y-1));
        adjacentPositions.add(new Position(x  , y-1));
        adjacentPositions.add(new Position(x+1, y-1));
        adjacentPositions.add(new Position(x+1, y));
        adjacentPositions.add(new Position(x+1, y+1));
        adjacentPositions.add(new Position(x  , y+1));
        adjacentPositions.add(new Position(x-1, y+1));
        adjacentPositions.add(new Position(x-1, y));

        adjacentPositions.add(new Position(x-2, y));
        adjacentPositions.add(new Position(x-2, y-2));
        adjacentPositions.add(new Position(x, y-2));
        adjacentPositions.add(new Position(x+2, y-2));
        adjacentPositions.add(new Position(x+2, y));
        adjacentPositions.add(new Position(x+2, y-2));
        adjacentPositions.add(new Position(x, y+2));
        adjacentPositions.add(new Position(x-2, y+2));

        adjacentPositions.add(new Position(x-1, y-2));
        adjacentPositions.add(new Position(x+1, y-2));
        adjacentPositions.add(new Position(x+2, y-1));
        adjacentPositions.add(new Position(x+2, y+1));
        adjacentPositions.add(new Position(x+1, y+2));
        adjacentPositions.add(new Position(x-1, y+2));
        adjacentPositions.add(new Position(x-2, y+1));
        adjacentPositions.add(new Position(x-2, y-1));

        for (Position i : adjacentPositions) {
            if (playerPosition.getX() == i.getX() && playerPosition.getY() == i.getY()) {
                return true;
            }
        }

        return false;
    }
    public boolean isInBattleRadius (Position playerPosition) {
        int x = playerPosition.getX() - this.getPosition().getX();
        int y = playerPosition.getY() - this.getPosition().getY();

        if (x < 0) {
            x = x*(-1);
        }
        if (y < 0) {
            y = y*(-1);
        }

        if (x <= 3 && y <= 3) {
            return true;
        }
        else return false;
    }
    
    public void setBribed (boolean bribed) {
        this.bribed = bribed;
    }

    public boolean isBribed () {
        return this.bribed;
    }

    public boolean isInBattle() {
        return inBattle;
    }

    public void setInBattle(boolean inBattle) {
        this.inBattle = inBattle;
    }
}
