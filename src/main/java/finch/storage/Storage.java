package finch.storage;

import finch.exception.FinchException;
import finch.task.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 * Handles reading from and writing to the storage file for tasks.
 * <p>
 * The {@code Storage} class ensures persistence by saving tasks to a file
 * and loading them back when the Finch application starts.
 */
public class Storage {
    private final File file;

    /**
     * Constructs a {@code Storage} object with the specified file path.
     * <p>
     * This constructor ensures that the parent directory exists (creating it
     * if necessary) and that the data file is present. If the directory or
     * file cannot be created, a {@link FinchException} is thrown.
     *
     * @param filePath the path to the data file
     * @throws FinchException if the directory or file cannot be created
     */
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

    /**
     * Saves all tasks from the given {@link TaskList} to the storage file.
     * <p>
     * Each task is written in its save format (as returned by
     * {@link Task#toSaveFormat()}) on a separate line. This format is designed
     * to be reloadable by {@link #load()} to reconstruct the task list.
     *
     * @param tasks the {@link TaskList} containing all tasks to be saved
     * @throws FinchException if an I/O error occurs during saving
     */
    public void save(TaskList tasks) throws FinchException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            // Loop through all tasks in the list and write their save format
            for (int i = 0; i < tasks.size(); i++) {
                bw.write(tasks.getTask(i).toSaveFormat());
                bw.newLine(); // Write each task on a new line
            }
        } catch (IOException e) {
            throw new FinchException("Failed to save tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file into a {@link TaskList}.
     * <p>
     * Each line in the file is decoded into a {@link Task} using
     * {@link Task#decode(String)}. If the file is empty or missing,
     * an empty {@link TaskList} is returned.
     *
     * @return a {@link TaskList} containing tasks loaded from the file
     * @throws FinchException if an error occurs while reading or decoding tasks
     */
    public TaskList load() throws FinchException {
        ArrayList<Task> taskList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                // Decode each line into a Task object
                Task task = Task.decode(line);

                // Add the decoded Task to the in-memory list
                taskList.add(task);
            }
        } catch (FileNotFoundException e) {
            // If file doesn't exist, just return an empty TaskList
            return new TaskList();
        } catch (Exception e) {
            // Catch any decoding or I/O issues and wrap them in a FinchException
            throw new FinchException("Failed to load tasks: " + e.getMessage());
        }

        // Return all successfully loaded tasks as a TaskList
        return new TaskList(taskList);
    }
}