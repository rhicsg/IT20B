package to.pkgdo.list;

import java.awt.Dimension;
import javax.swing.*;
import java.util.*;

public class ToDoList {

    private static final List<String> toDoList = new LinkedList<>();
    private static final List<String> completedTasks = new LinkedList<>();
    private static final Stack<String> undoStack = new Stack<>();

    public static void main(String[] args) {
        String[] options = {"Add Task", "Mark as Done", "Undo", "View To-Do List", "View Completed Tasks", "Exit"};
        Map<Integer, Runnable> actions = new HashMap<>() {{
            put(0, ToDoList::addTask);
            put(1, ToDoList::markAsDone);
            put(2, ToDoList::undo);
            put(3, () -> viewList(toDoList, "To-Do List"));
            put(4, () -> viewList(completedTasks, "Completed Tasks"));
            put(5, ToDoList::exit);
        }};

        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Choose an option", "To-Do List App", JOptionPane.DEFAULT_OPTION, 
                                                       JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            Optional.ofNullable(actions.get(choice)).ifPresent(Runnable::run);
        }
    }

    private static void addTask() {
        String task = JOptionPane.showInputDialog("Enter the task:");
        if (task != null && !task.trim().isEmpty()) {
            toDoList.add(task);
            undoStack.push("add:" + task);
            JOptionPane.showMessageDialog(null, "Task added successfully!");
        }
    }

    private static void markAsDone() {
        if (toDoList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tasks available to mark as done.");
            return;
        }
        String taskList = createTaskList(toDoList);
        String taskNumberStr = JOptionPane.showInputDialog(taskList + "\nEnter the task number to mark as done:");
        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            if (taskNumber >= 1 && taskNumber <= toDoList.size()) {
                String task = toDoList.remove(taskNumber - 1);
                completedTasks.add(task);
                undoStack.push("done:" + task);
                JOptionPane.showMessageDialog(null, "Task marked as done!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid task number!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.");
        }
    }

   private static void undo() {
    if (undoStack.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No actions to undo.");
        return;
    }

    int confirmation = JOptionPane.showConfirmDialog(null, 
        "Are you sure you want to undo the last action?", 
        "Undo Action", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.WARNING_MESSAGE
    );

    if (confirmation == JOptionPane.NO_OPTION) {
        JOptionPane.showMessageDialog(null, "Undo action canceled.");
        return; 
    }

    String lastAction = undoStack.pop();
    String[] parts = lastAction.split(":");  
    String task = parts[1];

    switch (parts[0]) {
        case "add":
            toDoList.remove(task);
            JOptionPane.showMessageDialog(null, "Undone: Task \"" + task + "\" removed.");
            break;
        case "done":
            completedTasks.remove(task);
            toDoList.add(task);
            JOptionPane.showMessageDialog(null, "Undone: Task \"" + task + "\" moved back to To-Do List.");
            break;
        default:
            JOptionPane.showMessageDialog(null, "Unknown action type in undo.");
    }
}


    private static void viewList(List<String> list, String title) {
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, title + " is empty.");
        } else {
            StringBuilder sb = new StringBuilder(title + ":\n");
            list.forEach(task -> sb.append("- ").append(task).append("\n"));
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    private static void exit() {
        if (JOptionPane.showConfirmDialog(null, "Gusto gyud ka mo exit Maam?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Pasara mi maam!");
            System.exit(0);
        }
    }

    private static String createTaskList(List<String> list) {
        StringBuilder taskList = new StringBuilder("To-Do List:\n");
        for (int i = 0; i < list.size(); i++) {
            taskList.append((i + 1) + ". " + list.get(i) + "\n");
        }
        return taskList.toString();
    }
}
