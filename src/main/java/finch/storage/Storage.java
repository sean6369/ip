package finch.storage;

import finch.exception.FinchException;
import finch.task.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);

        // Ensure parent directory exists
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            boolean created = parent.mkdirs();
            if (!created) {
                System.out.println("⚠ Warning: Could not create directory " + parent.getAbsolutePath());
            }
        }

        // Ensure the data file exists
        try {
            if (!file.exists()) {
                boolean createdFile = file.createNewFile();
                if (!createdFile) {
                    System.out.println("⚠ Warning: Could not create file " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create storage file: " + e.getMessage(), e);
        }
    }

    // Save tasks into file
    public void save(TaskList tasks) throws FinchException {
        try (FileWriter fw = new FileWriter(file)) {
            for (int i = 0; i < tasks.size(); i++) {
                fw.write(tasks.getTask(i).encode() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new FinchException("Failed to save tasks: " + e.getMessage());
        }
    }

    // Load tasks from file
    public TaskList load() throws FinchException {
        ArrayList<Task> taskList = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Task task = Task.decode(line);
                taskList.add(task);
            }
        } catch (FileNotFoundException e) {
            // If file doesn't exist, just return an empty TaskList
            return new TaskList();
        } catch (Exception e) {
            throw new FinchException("Failed to load tasks: " + e.getMessage());
        }
        return new TaskList(taskList);
    }
}