import java.time.LocalDateTime;

public class TaskManager {
    public static void main(String[] args) {

    	HoneyDoList honeydo = new HoneyDoList();


    	honeydo.addTask(new Task("assignment 9", 1, 10));
        honeydo.addTask(new Task("exam 2", 3, 5));
        honeydo.addTask(new Task("water plants", 3, 8));
        honeydo.addTask(new Task("watch mike tyson", 2, 1200));


        honeydo.addTask(new Task("exam 1", 5, 30, LocalDateTime.now().minusDays(10)));
        honeydo.addTask(new Task("exam 3", 4, 45, LocalDateTime.now().plusDays(15)));


        System.out.println("HoneyDo List:\n" + honeydo);


        System.out.println("\nOverdue tasks:");
        Task[] overdueTasks = honeydo.overdueTasks();
        for (Task task : overdueTasks) {
            System.out.println(task);
        }

        String taskName = "exam 1";
        int index = honeydo.find(taskName);
        if (index != -1) {
            System.out.println("\nIs '" + taskName + "' overdue? " + honeydo.getTask(index).overdue());
        } else {
            System.out.println("\nTask '" + taskName + "' not found.");
        }

        taskName = "exam 3";
        index = honeydo.find(taskName);
        if (index != -1) {
            System.out.println("Is '" + taskName + "' overdue? " + honeydo.getTask(index).overdue());
        } else {
            System.out.println("Task '" + taskName + "' not found.");
        }
    }
}