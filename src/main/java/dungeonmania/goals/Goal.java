package dungeonmania.goals;

public interface Goal {
    public String goalsString();
    public boolean isComplete();
    public boolean add(Goal child);
}
