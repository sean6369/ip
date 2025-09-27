package finch.storage;

import finch.exception.FinchException;
import finch.task.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 * Handles reading from and writing to the storage file for tasks.
 */
public class Storage {
    private final File file;

    // Initialises the Storage with the given file path and ensures that the parent directory and data file exist
    public Storage(String filePath) throws FinchException {
        this.file = new File(filePath);

        // Ensure parent directory exists
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            boolean created = parent.mkdirs();
            if (!created) {
                throw new FinchException("Failed to create directory: " + parent.getAbsolutePath());
            }
        }

        // Ensure the data file exists
        try {
            if (!file.exists()) {
                boolean createdFile = file.createNewFile();
                if (!createdFile) {
                    throw new FinchException("Failed to create file: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            throw new FinchException("Failed to create storage file: " + e.getMessage());
        }
    }

    // Saves all task from TaskList into the storage file
    public void save(TaskList tasks) throws FinchException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < tasks.size(); i++) {
                bw.write(tasks.getTask(i).encode());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new FinchException("Failed to save tasks: " + e.getMessage());
        }
    }

    // Load tasks from the storage file into a TaskList
    public TaskList load() throws FinchException {
        ArrayList<Task> taskList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
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