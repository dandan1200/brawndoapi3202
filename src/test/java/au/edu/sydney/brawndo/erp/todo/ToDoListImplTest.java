package au.edu.sydney.brawndo.erp.todo;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListImplTest {
    private ToDoList tdl;
    @BeforeEach
    public void createToDoList() {
        tdl = new ToDoListImpl();
    }
    @Test
    public void testAddValid() {

        LocalDateTime time = LocalDateTime.now();
        Task newTask = tdl.add(null, time, "Australia", "Homework");

//        assertEquals(0,newTask.getID());
        assertEquals(time,newTask.getDateTime());
        assertEquals("Australia", newTask.getLocation());
        assertEquals("Homework", newTask.getDescription());
        assertEquals(false, newTask.isCompleted());
    }

    @Test
    public void testAddNullIDWhenNotManagingIDs() {
        LocalDateTime time = LocalDateTime.now();
        Task newTask = tdl.add(1, time, "Australia", "Homework");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            tdl.add(null, time, "Australia", "Class");
        });
    }

    @Test
    public void testAddNonNullIDWhenManagingIDs() {
        LocalDateTime time = LocalDateTime.now();
        Task newTask = tdl.add(null, time, "Australia", "Homework");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            tdl.add(1, time, "Australia", "Class");
        });
    }

    @Test
    public void testAddNullDateTime() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tdl.add(1, null, "Australia", "Class");
        });
    }

    @Test
    public void testAddNullLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tdl.add(1, LocalDateTime.now(), null, "Class");
        });
    }

    @Test
    public void testAddEmptyLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tdl.add(1, LocalDateTime.now(), "", "Class");
        });
    }

    @Test
    public void testAddLargeLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tdl.add(1, LocalDateTime.now(), "aJ4ZATtclIltbbRd9E0mSxDaIdtxkYdiqMj1MDTRz3wWI1e56iJu4JrEvr4AqEfDdB81k4sYa1QgLi4Gp8PhjCiBA8tijoqJ7D3HO4qmixukubxiV0xffgpX5dK6y8jzLYt7d8CBZm0yWVI92z7nXB1HUrLNcFYP2QIpDzJEIX3gewuwn80SuCSYKIgPQHryANxcewzj7uvkSx2iUw9kh3wlVm1ZlRFwTbzDyidb3TSwK0ZmrLiW0a48TSHABTpbd", "Class");
        });
    }

    @Test
    public void testAddDuplicateID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tdl.add(1, LocalDateTime.now(), "Australia", "Homework");
            tdl.add(1, LocalDateTime.now(), "Australia", "Homework");
        });
    }

    @Test
    public void testAddValidMultipleManagingIDs() {

        LocalDateTime time = LocalDateTime.now();
        Task newTask = tdl.add(null, time, "Australia", "Homework");

//        assertEquals(0,newTask.getID());
        assertEquals(time,newTask.getDateTime());
        assertEquals("Australia", newTask.getLocation());
        assertEquals("Homework", newTask.getDescription());
        assertEquals(false, newTask.isCompleted());

        Task newTask2 = tdl.add(null, time, "USA", "Homework");

//        assertEquals(1,newTask2.getID());
        assertEquals(time,newTask2.getDateTime());
        assertEquals("USA", newTask2.getLocation());
        assertEquals("Homework", newTask2.getDescription());
        assertEquals(false, newTask2.isCompleted());
    }

    @Test
    public void testAddValidMultipleNotManagingIDs() {

        LocalDateTime time = LocalDateTime.now();
        Task newTask = tdl.add(1, time, "Australia", "Homework");

        assertEquals(1,newTask.getID());
        assertEquals(time,newTask.getDateTime());
        assertEquals("Australia", newTask.getLocation());
        assertEquals("Homework", newTask.getDescription());
        assertEquals(false, newTask.isCompleted());

        Task newTask2 = tdl.add(3, time, "USA", "Homework");

        assertEquals(3,newTask2.getID());
        assertEquals(time,newTask2.getDateTime());
        assertEquals("USA", newTask2.getLocation());
        assertEquals("Homework", newTask2.getDescription());
        assertEquals(false, newTask2.isCompleted());
    }

    @Test
    public void testRemoveInvalidID() {
        assertEquals(false, tdl.remove(10));
        tdl.add(1, LocalDateTime.now(), "Australia", "Homework");

        assertEquals(false, tdl.remove(10));
    }

    @Test
    public void testRemoveValidID() {
        tdl.add(1, LocalDateTime.now(), "Australia", "Homework");
        assertEquals(true, tdl.remove(1));
        assertEquals(null, tdl.findOne(1));
    }

    @Test
    public void testFindOne() {
        LocalDateTime time = LocalDateTime.now();
        tdl.add(1, time, "Australia", "Homework");

        Task foundTask = tdl.findOne(1);
        assertEquals(1, foundTask.getID());
        assertEquals(time,foundTask.getDateTime());
        assertEquals("Australia", foundTask.getLocation());
        assertEquals("Homework", foundTask.getDescription());
        assertEquals(false, foundTask.isCompleted());

        tdl.add(2, time, "USA", "Homework");

        foundTask = tdl.findOne(2);
        assertEquals(2, foundTask.getID());
        assertEquals(time,foundTask.getDateTime());
        assertEquals("USA", foundTask.getLocation());
        assertEquals("Homework", foundTask.getDescription());
        assertEquals(false, foundTask.isCompleted());

    }

    @Test
    public void testFindAllEmpty() {
        assertTrue(tdl.findAll().isEmpty());
    }

    @Test
    public void testFindAllValid() {
        LocalDateTime time = LocalDateTime.now();
        tdl.add(1, time, "Australia", "Homework");

        Task foundTask = tdl.findAll().get(0);
        assertEquals(1, foundTask.getID());
        assertEquals(time,foundTask.getDateTime());
        assertEquals("Australia", foundTask.getLocation());
        assertEquals("Homework", foundTask.getDescription());
        assertEquals(false, foundTask.isCompleted());

        tdl.add(2, time, "USA", "Homework");
        assertEquals(2, tdl.findAll().size());

        tdl.remove(2);
        assertEquals(1, tdl.findAll().size());
    }

    @Test
    public void testFindAllCompleted() {
        tdl.add(1, LocalDateTime.now(), "Australia", "Homework");
        tdl.add(2,LocalDateTime.now(), "USA", "Eat");

        assertEquals(0,tdl.findAll(true).size());

        tdl.findOne(1).complete();
        assertEquals(1,tdl.findAll(false).size());
        assertEquals(2,tdl.findAll(false).get(0).getID());
        assertEquals(1,tdl.findAll(true).get(0).getID());

        tdl.findOne(2).complete();
        assertEquals(0,tdl.findAll(false).size());
        assertEquals(2,tdl.findAll(true).size());

    }

    @Test
    public void testFindAllDateTimeFilterInvalidDates() {
        LocalDateTime time = LocalDateTime.now();
        tdl.add(1, time, "Australia", "Homework");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
           tdl.findAll(LocalDateTime.of(2000,Month.JANUARY,1,0,0), LocalDateTime.of(1999,Month.JANUARY, 1, 0,0), null);
        });

    }
    @Test
    public void testFindAllDateTimeFilter() {
        LocalDateTime t1 = LocalDateTime.of(2000, Month.JANUARY, 1,0,0);
        LocalDateTime t2 = LocalDateTime.of(2001,Month.JANUARY,1,0,0);
        LocalDateTime t3 = LocalDateTime.of(2002,Month.JANUARY,1,0,0);

        tdl.add(0,t2,"Australia", "Homework");
        List<Task> tasks = tdl.findAll(t1,t3,null);
        assertEquals(1,tasks.size());
        assertEquals(0,tasks.get(0).getID());

        tasks = tdl.findAll(t1,LocalDateTime.of(2000,Month.FEBRUARY,1,0,0),null);
        assertEquals(0,tasks.size());

        tdl.add(1,t3, "USA", "Eat");
        tasks = tdl.findAll(t1,t3,null);
        assertEquals(1,tasks.size());
        assertEquals(0,tasks.get(0).getID());

        tasks = tdl.findAll(t1,t3,false);
        assertEquals(1,tasks.size());
        assertEquals(0,tasks.get(0).getID());

        tasks = tdl.findAll(t1,t3,true);
        assertEquals(0,tasks.size());

        tdl.findOne(0).complete();
        tasks = tdl.findAll(t1,t3,true);
        assertEquals(1,tasks.size());
        assertEquals(0,tasks.get(0).getID());

    }

    @Test
    public void testFindAllDateTimeFilterNullDates() {
        LocalDateTime t1 = LocalDateTime.of(2000, Month.JANUARY, 1,0,0);
        LocalDateTime t2 = LocalDateTime.of(2001,Month.JANUARY,1,0,0);
        LocalDateTime t3 = LocalDateTime.of(2002,Month.JANUARY,1,0,0);

        tdl.add(0,t2,"Australia", "Homework");
        List<Task> tasks = tdl.findAll(null,t3,null);
        assertEquals(1,tasks.size());
        assertEquals(0,tasks.get(0).getID());

        tasks = tdl.findAll(t1,null,null);
        assertEquals(1,tasks.size());
        assertEquals(0,tasks.get(0).getID());
    }

    @Test
    public void testFindAllAllFiltersNullValues() {
        LocalDateTime t1 = LocalDateTime.of(2000, Month.JANUARY, 1,0,0);
        LocalDateTime t2 = LocalDateTime.of(2001,Month.JANUARY,1,0,0);
        LocalDateTime t3 = LocalDateTime.of(2002,Month.JANUARY,1,0,0);
        Task task1 = tdl.add (0, t1, "Australia", "Homework");
        Task task2 = tdl.add(1, t2, "USA", "Eat");
        Task task3 = tdl.add(2, t3, "Canada", "Drive");

        task1.complete();

        List<Task> tasks = tdl.findAll(null, null, null, null,false);
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
        assertTrue(tasks.contains(task3));

        tasks = tdl.findAll(null, null, null, null,true);
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
        assertTrue(tasks.contains(task3));
    }

    @Test
    public void testFindAllAllFiltersOrSearches() {
        LocalDateTime t1 = LocalDateTime.of(2000, Month.JANUARY, 1,0,0);
        LocalDateTime t2 = LocalDateTime.of(2001,Month.JANUARY,1,0,0);
        LocalDateTime t3 = LocalDateTime.of(2002,Month.JANUARY,1,0,0);
        LocalDateTime t4 = LocalDateTime.of(2003,Month.JANUARY,1,0,0);

        Task task1 = tdl.add(0, t1, "Australia", "Homework");
        Task task2 = tdl.add(1, t2, "USA", "Eat");
        Task task3 = tdl.add(2, t3, "Canada", "Drive");
        Task task4 = tdl.add(3, t4, "London", "Flight");

        task4.complete();

        Map<Task.Field, String> params = new HashMap<Task.Field, String>();

        params.put(Task.Field.LOCATION, "A");

        List<Task> tasks = tdl.findAll(params, t2.minusDays(1), t4, null,false);
        assertTrue(tasks.contains(task2));
        assertEquals(1, tasks.size());

        tasks = tdl.findAll(null, t2, null, null,false);
        assertTrue(tasks.contains(task3));
        assertTrue(tasks.contains(task4));
        assertEquals(2,tasks.size());

        params.put(Task.Field.DESCRIPTION, "Flig");

        tasks = tdl.findAll(params, t2, t4.plusDays(1), null,false);
        assertTrue(tasks.contains(task4));
        assertEquals(1, tasks.size());


//        tasks = tdl.findAll(params, t2, t4, true,false);
//        assertTrue(tasks.contains(task1));
//        assertTrue(tasks.contains(task2));
//        assertTrue(tasks.contains(task3));
//        assertTrue(tasks.contains(task4));
//        assertEquals(4, tasks.size());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
           tdl.findAll(null, t2, t1, null, false);
        });
    }

    @Test
    public void testFindAllAllFiltersAndSearches() {
        LocalDateTime t1 = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0);
        LocalDateTime t2 = LocalDateTime.of(2001, Month.JANUARY, 1, 0, 0);
        LocalDateTime t3 = LocalDateTime.of(2002, Month.JANUARY, 1, 0, 0);
        LocalDateTime t4 = LocalDateTime.of(2003, Month.JANUARY, 1, 0, 0);

        Task task1 = tdl.add(0, t1, "Australia", "Homwork");
        Task task2 = tdl.add(1, t2, "USA", "eat");
        Task task3 = tdl.add(2, t3, "Canada", "Drive");
        Task task4 = tdl.add(3, t4, "London", "Flight");

        Map<Task.Field, String> params = new HashMap<Task.Field, String>();

        params.put(Task.Field.LOCATION, "A");

        List<Task> tasks = tdl.findAll(params, t1, t4, null, true);
        assertTrue(tasks.contains(task2));
        assertEquals(1, tasks.size());

        params.put(Task.Field.DESCRIPTION, "ive");

        tasks = tdl.findAll(params, t1, t4, null, true);
        assertEquals(0, tasks.size());

        params.put(Task.Field.DESCRIPTION, "e");
        tasks = tdl.findAll(params, t1, t4, null, true);
        assertTrue(tasks.contains(task2));
        assertEquals(1, tasks.size());


        tasks = tdl.findAll(params, t1, t4, false, true);
        assertTrue(tasks.contains(task2));
        assertEquals(1, tasks.size());

        tasks = tdl.findAll(params, t1, t4, true, true);
        assertEquals(0, tasks.size());

        params.put(Task.Field.LOCATION, "a");
        tasks = tdl.findAll(params, null, null, false, true);
        assertTrue(tasks.contains(task3));
        assertEquals(1, tasks.size());

    }

    @Test
    public void testClear() {
        LocalDateTime t1 = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0);
        LocalDateTime t2 = LocalDateTime.of(2001, Month.JANUARY, 1, 0, 0);
        LocalDateTime t3 = LocalDateTime.of(2002, Month.JANUARY, 1, 0, 0);
        LocalDateTime t4 = LocalDateTime.of(2003, Month.JANUARY, 1, 0, 0);

        Task task1 = tdl.add(0, t1, "Australia", "Homwork");
        Task task2 = tdl.add(1, t2, "USA", "eat");
        Task task3 = tdl.add(2, t3, "Canada", "Drive");
        Task task4 = tdl.add(3, t4, "London", "Flight");

        tdl.clear();

        assertEquals(0, tdl.findAll().size());
    }
}

