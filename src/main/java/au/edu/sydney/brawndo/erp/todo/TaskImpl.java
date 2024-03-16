package au.edu.sydney.brawndo.erp.todo;

import au.edu.sydney.brawndo.erp.todo.Task;

import java.time.LocalDateTime;

public class TaskImpl implements Task {

    private int id;
    private LocalDateTime dateTime;
    private String location;
    private String description;
    private boolean completed;
    public TaskImpl(int id, LocalDateTime dateTime, String location, String description) {
        this.id = id;
        this.setDateTime(dateTime);
        this.setLocation(location);
        this.setDescription(description);
        this.completed = false;

    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return The id of this task
     */
    public int getID() {
        return this.id;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return The datetime of this task (may not be null)
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return The location of this task (may not be null, empty, and must be 256 characters or less)
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return The description of this task if present, otherwise null
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return The completion status of this task
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * The new datetime should be returned from any future calls to this Task’s getDateTime method<br>
     *
     * @param dateTime May not be null
     * @throws IllegalArgumentException If the preconditions are violated
     */
    public void setDateTime(LocalDateTime dateTime) throws IllegalArgumentException {
        if (dateTime == null) {
            throw new IllegalArgumentException("Datetime cannot be null.");
        } else {
            this.dateTime = dateTime;
        }
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * The new location should be returned from any future calls to this Task’s getLocation method<br>
     *
     * @param location May not be null or empty, must be 256 characters or less
     * @throws IllegalArgumentException If the preconditions are violated
     */
    public void setLocation(String location) throws IllegalArgumentException {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        } else if (location.equals("")){
            throw new IllegalArgumentException("Location cannot be empty.");
        } else if (location.length() > 256) {
            throw new IllegalArgumentException("Location cannot be greater than 256 characters.");
        } else {
            this.location = location;
        }
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * The new description (or null if set) should be returned from any future calls to this Task’s getDescription method<br>
     *
     * @param description May be null
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * The task will be set to completed and will reflect this in isComplete()<br>
     *
     * @throws IllegalStateException If this task was already complete
     */
    public void complete() throws IllegalStateException {
        if (this.completed) {
            throw new IllegalStateException("Task is already completed.");
        } else {
            this.completed = true;
        }
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param field A mapping to a field stored by this Task (may not be null)
     * @return The string contents of the field mapped by the parameter (with inherited postconditions)
     * @throws IllegalArgumentException If the preconditions are violated
     */
    public String getField(Field field) throws IllegalArgumentException {
        if (field == Field.DESCRIPTION) {
            return this.description;
        } else if (field == Field.LOCATION) {
            return this.location;
        } else {
            throw new IllegalArgumentException("Invalid field");
        }
    }
}