import java.time.LocalDateTime;

public class Task {
    private String name;
    private int priority;
    private int estMinsToComplete;
    private LocalDateTime whenDue;

    public Task(String name, int priority, int estMinsToComplete) {
        this.name = name;
        this.priority = priority;
        this.estMinsToComplete = estMinsToComplete;
    }

    public Task(String name, int priority, int estMinsToComplete, LocalDateTime whenDue) {
        this.name = name;
        this.priority = priority;
        this.estMinsToComplete = estMinsToComplete;
        this.whenDue = whenDue;
    }

    public String getName() { return name; }
    public int getPriority() { return priority; }
    public int getEstMinsToComplete() { return estMinsToComplete; }
    public LocalDateTime getWhenDue() { return whenDue; }

    public void setName(String name) { this.name = name; }
    public void setEstMinsToComplete(int estMinsToComplete) { this.estMinsToComplete = estMinsToComplete; }
    public void setWhenDue(LocalDateTime whenDue) { this.whenDue = whenDue; }

    public void increasePriority(int amount) {
        if (amount > 0) {
            priority += amount;
        }
    }

    public void decreasePriority(int amount) {
        if (amount > 0) {
            priority = Math.max(priority - amount, 0);
        }
    }

    public boolean overdue() {
        return whenDue != null && LocalDateTime.now().isAfter(whenDue);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                ", estMinsToComplete=" + estMinsToComplete +
                ", whenDue=" + whenDue +
                '}';
    }
}