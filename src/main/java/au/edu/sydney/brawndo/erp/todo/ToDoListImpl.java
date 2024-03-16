package au.edu.sydney.brawndo.erp.todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ToDoListImpl implements ToDoList {

    private int currentID = 0;
    private boolean listManagingIDs = false;
    private boolean firstTaskAdded = false;
    private List<Task> tasks = new ArrayList<Task>();;

    public ToDoListImpl() {

    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * If id is null, an integer id is generated which is guaranteed to be unique for this instance of ToDoList if
     * and only if no set-integer tasks have been added - that is, either manage ids externally or allow the ToDoList
     * to manage them, do not mix the two<br>
     * The task added will be available to matching remove and find method calls until it is removed using
     * ToDoList.remove(matching id) or the list is cleared using ToDoList.clear()<br>
     *
     * @param id          The intended id for this task (may be null, may not already be present in the ToDoList)
     * @param dateTime    may not be null
     * @param location    may not be null, empty, or longer than 256 characters
     * @param description Task description (may be null)
     * @return •	A reference to the created (not completed) Task matching the given parameters
     * @throws IllegalStateException    If a null id is passed after a non-null id task has already been added
     * @throws IllegalArgumentException If any other preconditions are violated
     */
    public Task add(Integer id, LocalDateTime dateTime, String location, String description) throws IllegalArgumentException, IllegalStateException {
        if (!firstTaskAdded) {
            firstTaskAdded = true;
            if (id == null) {
                listManagingIDs = true;
            }
        }

        Task newTask = null;
        if (listManagingIDs == true) {
            if (id != null) {
                throw new IllegalStateException("Non-null id added but list is managing IDs.");
            } else {
                newTask = new TaskImpl(this.currentID, dateTime, location, description);
                this.currentID++;
                this.tasks.add(newTask);
            }
        } else {
            if (id != null) {
                if (this.findOne(id) == null) {
                    newTask = new TaskImpl(id, dateTime, location, description);
                    this.tasks.add(newTask);
                } else {
                    throw new IllegalArgumentException("Task ID provided already exists in list.");
                }
            } else {
                throw new IllegalStateException("Null id added but list is not managing IDs");
            }
        }

        return newTask;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * Any task matching the given id will no longer be available to remove and find methods<br>
     *
     * @param id The target task id
     * @return True if a task with a matching id was present in the list – otherwise false
     */
    public boolean remove(int id) {
        Task foundTask = this.findOne(id);
        if (foundTask == null) {
            return false;
        } else {
            this.tasks.remove(foundTask);
            return true;
        }
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param id The target task id
     * @return If a task with a matching id exists in the list, the matching task – otherwise null
     */
    public Task findOne(int id) {
        for (Task t : this.tasks) {
            if (t.getID() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @return A list of all tasks contained by this ToDoList (may be empty)
     * Changes to this list itself will not be reflected in the ToDoList, but changes to the Tasks it contains will be
     */
    public List<Task> findAll() {
        List<Task> tasksCopy = new ArrayList<Task>();
        tasksCopy.addAll(this.tasks);
        return tasksCopy;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param completed The target task completion status
     * @return A list of all tasks contained by this ToDoList which match the completion status given by the parameter
     */
    public List<Task> findAll(boolean completed) {
        List<Task> tasksCopy = new ArrayList<Task>();
        for (Task t : this.tasks) {
            if (t.isCompleted() == completed) {
                tasksCopy.add(t);
            }
        }
        return tasksCopy;
    }

    /**
     * <b>Preconditions:</b><br>
     * The to parameter must be later in time than the from parameter<br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param from      The starting boundary (may be null)
     * @param to        The finishing boundary (may be null)
     * @param completed The target completion status (may be null)
     * @return If completed is null, a list of all tasks contained by this ToDoList whose datetime occurs after from
     * and before to (both exclusive). If either is null their boundary is removed (e.g. if to is null then the method
     * will return a list of all tasks whose datetime occurs after from). If both are removed this yields the same
     * list as ToDoList.findAll(). If completed is not null, the yielded list additionally filtered to those tasks
     * matching the given completion status.
     * @throws IllegalArgumentException If any of the preconditions are violated
     */
    public List<Task> findAll(LocalDateTime from, LocalDateTime to, Boolean completed) throws IllegalArgumentException {
        List<Task> tasksCopy = new ArrayList<Task>();
        LocalDateTime fromBoundary = from;
        LocalDateTime toBoundary = to;

        if (from == null) {
            fromBoundary = LocalDateTime.MIN;
        }
        if (to == null) {
            toBoundary = LocalDateTime.MAX;
        }
        if (from != null && to != null) {
            if (from.isAfter(to)){
                throw new IllegalArgumentException("From date is after to date");
            }
        }

        List<Task> listToFilter;
        if (completed != null) {
            listToFilter = this.findAll(completed);
        } else {
            listToFilter = this.findAll();
        }


        for (Task t : listToFilter) {
            if (t.getDateTime().isAfter(fromBoundary) && t.getDateTime().isBefore(toBoundary)){
                tasksCopy.add(t);
            }
        }

        return tasksCopy;

    }

    /**
     * <b>Preconditions:</b><br>
     * The to parameter must be later in time than the from parameter<br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param params    A map of Task.Fields and values to filter on within those Fields - may be null, but may not
     *                  contain null values
     * @param from      The starting boundary (may be null)
     * @param to        The finishing boundary (may be null)
     * @param completed The target completion status (may be null)
     * @param andSearch Sets the filter status to match all if true, match any if false
     * @return A list of all tasks which match the given filter status based on the following rules:<br>
     * <ul>
     *     <li>from and to filter on dateTime as exclusive boundaries - if either is null their side of the boundary
     *     is opened, if both are null does not filter on dateTime</li>
     *     <li>completed filters on matching completion status if not null</li>
     *     <li>params filters to the matching Task.Field and mapped value (contained string, not equality)</li>
     *     <li>If andSearch is true this requires ALL above filters to match for all returned Tasks. If andSearch
     *     is false any task matching any given filter will be included</li>
     * </ul>
     * @throws IllegalArgumentException If any of the preconditions are violated
     */
    public List<Task> findAll(Map<Task.Field, String> params, LocalDateTime from, LocalDateTime to, Boolean completed, boolean andSearch) throws IllegalArgumentException {
        List<Task> tasksCopy = new ArrayList<Task>();
        List<Task> date_completeFilter = findAll(from,to,completed);

        if (andSearch) {
            for (Task t : date_completeFilter) {
                boolean meetsParams = true;
                if (params != null) {
                    for (Map.Entry<Task.Field, String> e : params.entrySet()) {
                        if (!t.getField(e.getKey()).contains(e.getValue())) {
                            meetsParams = false;
                        }
                    }
                }
                if (meetsParams) {
                    tasksCopy.add(t);
                }
            }
        } else {
//            tasksCopy.addAll(date_completeFilter);
            for (Task t : date_completeFilter) {
//                if (!tasksCopy.contains(t)) {
                if (params != null) {
                    for (Map.Entry<Task.Field, String> e : params.entrySet()) {
                        if (t.getField(e.getKey()).contains(e.getValue())) {
                            tasksCopy.add(t);
                            break;
                        }
                    }
                }
                //}
            }
            if (params == null) {
                tasksCopy.addAll(date_completeFilter);
            }
        }
        return tasksCopy;
    }

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * All tasks in the ToDoList will be removed and no longer available to matching remove and find methods<br>
     */
    public void clear() {
        this.tasks.clear();
    }
}
