package dungeonmania.goals;

public interface Goal {
    public String goalsString();
    public String nameString();
    public boolean isComplete();
    public boolean add(Goal child);
}
