package au.edu.sydney.brawndo.erp.todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoFactoryTest {

    @Test
    public void testFactory_CreateToDoList() {
        ToDoFactory f = new ToDoFactory();
        ToDoList l = f.makeToDoList();
        assertFalse(l == null);
        assertTrue(l.findAll().size() == 0);

    }
}