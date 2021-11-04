package dungeonmania.goals;

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

}  
