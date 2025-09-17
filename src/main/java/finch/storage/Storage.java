package finch.storage;

import finch.exception.FinchException;
import finch.task.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    // Load tasks from file
    public ArrayList<Task> load() throws FinchException {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                // Create folder if it doesn't exist
                if (path.getParent() != null) {
                    Files.createDirectories(path.getParent());
                }
                Files.createFile(path);
                return tasks; // return empty list
            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");

                Task task;
                switch (type) {
                case "T":
                    task = new ToDo(parts[2]);
                    break;
                case "D":
                    task = new Deadline(parts[2], parts[3]);
                    break;
                case "E":
                    task = new Event(parts[2], parts[3], parts[4]);
                    break;
                default:
                    throw new FinchException("Corrupted task type in file: " + type);
                }

                if (isDone) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
            reader.close();
        } catch (IOException e) {
            throw new FinchException("Error loading tasks from file: " + e.getMessage());
        }

        return tasks;
    }

    // Save tasks to file
    public void save(ArrayList<Task> tasks) throws FinchException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.toSaveFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new FinchException("Error saving tasks to file: " + e.getMessage());
        }
    }
}
