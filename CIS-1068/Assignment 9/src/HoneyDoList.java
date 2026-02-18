public class HoneyDoList {
    private Task[] tasks;
    private int numTasks;
    private static final int INITIAL_CAPACITY = 10;

    public HoneyDoList() {
        tasks = new Task[INITIAL_CAPACITY];
        numTasks = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numTasks; i++) {
            sb.append(tasks[i]).append("\n");
        }
        return sb.toString();
    }

    public int find(String name) {
        for (int i = 0; i < numTasks; i++) {
            if (tasks[i].getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public void addTask(Task task) {
        if (numTasks == tasks.length) {
            Task[] newTasks = new Task[tasks.length * 2];
            System.arraycopy(tasks, 0, newTasks, 0, tasks.length);
            tasks = newTasks;
        }
        tasks[numTasks++] = task;
    }

    public int totalTime() {
        int total = 0;
        for (int i = 0; i < numTasks; i++) {
            total += tasks[i].getEstMinsToComplete();
        }
        return total;
    }

    public int shortestTime() {
        if (numTasks == 0) return -1;

        int minIndex = 0;
        for (int i = 1; i < numTasks; i++) {
            if (tasks[i].getEstMinsToComplete() < tasks[minIndex].getEstMinsToComplete()) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    public Task completeTask(int index) {
        if (index < 0 || index >= numTasks) return null;

        Task completedTask = tasks[index];
        for (int i = index; i < numTasks - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[--numTasks] = null;
        return completedTask;
    }

    public Task getTask(int index) {
        if (index < 0 || index >= numTasks) return null;
        return tasks[index];
    }

    public Task[] overdueTasks() {
        Task[] overdueTasks = new Task[numTasks];
        int count = 0;
        for (int i = 0; i < numTasks; i++) {
            if (tasks[i].overdue()) {
                overdueTasks[count++] = tasks[i];
            }
        }

        Task[] result = new Task[count];
        System.arraycopy(overdueTasks, 0, result, 0, count);
        return result;
    }
}
