package au.edu.sydney.brawndo.erp.todo;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TaskImplTest {

    @Test
    public void testConstructorValid() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
    }

    @Test
    public void testConstructorNullTime() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Task t = new TaskImpl(1,null, "Australia", "Homework");
        });
    }

    @Test
    public void testConstructorNullLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Task t = new TaskImpl(1,LocalDateTime.now(), null, "Homework");
        });
    }

    @Test
    public void testConstructorEmptyLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Task t = new TaskImpl(1,LocalDateTime.now(), "", "Homework");
        });
    }

    @Test
    public void testConstructorLargeLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Task t = new TaskImpl(1,LocalDateTime.now(), "aJ4ZATtclIltbbRd9E0mSxDaIdtxkYdiqMj1MDTRz3wWI1e56iJu4JrEvr4AqEfDdB81k4sYa1QgLi4Gp8PhjCiBA8tijoqJ7D3HO4qmixukubxiV0xffgpX5dK6y8jzLYt7d8CBZm0yWVI92z7nXB1HUrLNcFYP2QIpDzJEIX3gewuwn80SuCSYKIgPQHryANxcewzj7uvkSx2iUw9kh3wlVm1ZlRFwTbzDyidb3TSwK0ZmrLiW0a48TSHABTpbd", "Homework");
        });
    }
    @Test
    public void testConstructorNullDescription() {
        Task t = new TaskImpl(1,LocalDateTime.now(), "Australia", null);
        assertTrue(t.getDescription() == null);
    }

    @Test
    public void testConstructorIsCompleted() {
        Task t = new TaskImpl(1,LocalDateTime.now(), "Australia", "Homework");
        assertTrue(t.isCompleted() == false);
    }

    @Test
    public void testCompleted() {
        Task t = new TaskImpl(1,LocalDateTime.now(), "Australia", "Homework");
        t.complete();
        assertTrue(t.isCompleted() == true);
    }

    @Test
    public void testCompletedTwice() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            Task t = new TaskImpl(1,LocalDateTime.now(), "Australia", "Homework");
            t.complete();
            t.complete();
        });
    }

    @Test
    public void testGetID() {
        Task t = new TaskImpl(1,LocalDateTime.now(), "Australia", "Homework");
        assertEquals(1, t.getID());
    }

    @Test
    public void testGetDateTime() {
        LocalDateTime dt = LocalDateTime.now();
        Task t = new TaskImpl(1,dt, "Australia", "Homework");
        assertEquals(dt,t.getDateTime());
    }

    @Test
    public void testSetDateTime() {
        LocalDateTime dt = LocalDateTime.now();
        Task t = new TaskImpl(1, LocalDateTime.MIN, "Australia", "Homework");
        t.setDateTime(dt);
        assertEquals(dt,t.getDateTime());
    }

    @Test
    public void testSetDateTimeNull() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                    t.setDateTime(null);
        });
    }

    @Test
    public void testGetLocation() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        assertEquals("Australia",t.getLocation());
    }

    @Test
    public void testSetLocation() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        t.setLocation("USA");
        assertEquals("USA",t.getLocation());
    }

    @Test
    public void testSetLocationNull() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            t.setLocation(null);
        });
    }

    @Test
    public void testSetLocationEmpty() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            t.setLocation("");
        });
    }

    @Test
    public void testSetLocationLarge() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            t.setLocation("aJ4ZATtclIltbbRd9E0mSxDaIdtxkYdiqMj1MDTRz3wWI1e56iJu4JrEvr4AqEfDdB81k4sYa1QgLi4Gp8PhjCiBA8tijoqJ7D3HO4qmixukubxiV0xffgpX5dK6y8jzLYt7d8CBZm0yWVI92z7nXB1HUrLNcFYP2QIpDzJEIX3gewuwn80SuCSYKIgPQHryANxcewzj7uvkSx2iUw9kh3wlVm1ZlRFwTbzDyidb3TSwK0ZmrLiW0a48TSHABTpbd");
        });
    }

    @Test
    public void testGetDescription() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        assertEquals("Homework",t.getDescription());
    }

    @Test
    public void testSetDescription() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        t.setDescription("Eat");
        assertEquals("Eat",t.getDescription());
    }

    @Test
    public void testGetFieldLocation() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        assertEquals("Australia",t.getField(Task.Field.LOCATION));
    }
    @Test
    public void testGetFieldDescription() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        assertEquals("Homework",t.getField(Task.Field.DESCRIPTION));
    }
    @Test
    public void testGetFieldInvalid() {
        Task t = new TaskImpl(1, LocalDateTime.now(), "Australia", "Homework");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            t.getField(null);
        });
    }

}