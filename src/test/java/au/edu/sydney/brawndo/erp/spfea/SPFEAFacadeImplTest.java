package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.todo.Task;
import au.edu.sydney.brawndo.erp.todo.TaskImpl;
import au.edu.sydney.brawndo.erp.todo.ToDoList;
import au.edu.sydney.brawndo.erp.todo.ToDoListImpl;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SPFEAFacadeImplTest {

    private SPFEAFacade spfeaFacadeImpl;
    ToDoList tdlMock;

    @BeforeEach
    public void setup() {
        spfeaFacadeImpl = new SPFEAFacadeImpl();
        tdlMock = mock(ToDoListImpl.class);
    }

    @Test
    public void testCallMethodsWithNullProvider() {
        spfeaFacadeImpl.setToDoProvider(null);

        //Test addNewTask with null provider
        assertThrows(IllegalStateException.class, () -> {
            spfeaFacadeImpl.addNewTask(LocalDateTime.now(), "Desc", "HOME OFFICE");
        });

        //Test completeTask with null provider
        assertThrows(IllegalStateException.class, () -> {
            spfeaFacadeImpl.completeTask(1);
        });

        //Test getAllTasks with null provider
        assertThrows(IllegalStateException.class, () -> {
            spfeaFacadeImpl.getAllTasks();
        });

    }

    @Test
    public void testCompleteTaskInvalid() {
        spfeaFacadeImpl.setToDoProvider(tdlMock);
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.completeTask(99));
        Task t = spfeaFacadeImpl.addNewTask(LocalDateTime.MAX, "Desc", "HOME OFFICE");
        spfeaFacadeImpl.completeTask(t.getID());
        assertThrows(IllegalStateException.class, () -> spfeaFacadeImpl.completeTask(t.getID()));

    }

    @Test
    public void testSetToDoProvider() {
        assertDoesNotThrow(() -> spfeaFacadeImpl.setToDoProvider(tdlMock));

    }

    @Test
    public void testAddNewTaskInvalidInputs() {
        spfeaFacadeImpl.setToDoProvider(tdlMock);

        //Null datetime
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addNewTask(null, "Desc", "HOME OFFICE"));

        //Invalid datetime
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addNewTask(LocalDateTime.of(2000,1,1,0,0), "Desc", "HOME OFFICE"));

        //Null description
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addNewTask(LocalDateTime.MAX, null, "HOME OFFICE"));

        //Empty description
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addNewTask(LocalDateTime.MAX, "", "HOME OFFICE"));

        //Null location
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addNewTask(LocalDateTime.MAX, "Desc", null));

        //Empty location
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addNewTask(LocalDateTime.MAX, "Desc", ""));

        //Invalid location
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addNewTask(LocalDateTime.MAX, "Desc", "home"));


    }

//    @Test
//    public void testAddNewTaskValidInputs() {
//        spfeaFacadeImpl.setToDoProvider(tdlMock);
//        LocalDateTime time = LocalDateTime.MAX;
//        Task taskMock = mock(TaskImpl.class);
//
//        when(taskMock.getDateTime()).thenReturn(time);
//        when(taskMock.getDescription()).thenReturn();
//
//        when(tdlMock.add(any(Integer.class),any(LocalDateTime.class),any(String.class),any(String.class))).thenReturn();
//
//        Task task = spfeaFacadeImpl.addNewTask(time, "Desc", "HOME OFFICE");
//        assertEquals(spfeaFacadeImpl);
//    }

    @Test
    public void testAddCustomerInvalidInputs() {
        //Null firstname
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer(null, "Doe", null,"@" ));

        //Empty firstname
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("", "Doe", null,"@" ));

        //Null lastname
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", null, null,"@" ));

        //Empty lastname
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", "", null,"@" ));

        //Empty phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", "Doe", "","@" ));

        //Invalid phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", "Doe", "abc",null ));

        //Invalid phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", "Doe", "123abc",null ));

        //Invalid phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", "Doe", "+123-",null ));

        //Invalid phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", "Doe", "+123456(a)",null ));

        //Empty email
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", "Doe", null,"" ));

        //Invalid email
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", "Doe", null,"invalid" ));

        //Invalid email
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", "Doe", null,"123" ));

        //Duplicate names
        assertThrows(IllegalArgumentException.class, () -> {
            spfeaFacadeImpl.addCustomer("Jane", "Doe", null,"@" );
            spfeaFacadeImpl.addCustomer("Jane", "Doe", null,"@" );
        });

        //Null email and phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.addCustomer("Jane", "Doe", null, null ));
    }

    @Test
    public void testAddCustomerValidInputs() {

        assertDoesNotThrow(() -> spfeaFacadeImpl.addCustomer("Jane" , "Doe", "12345678", "email@email.com"));
        assertDoesNotThrow(() -> spfeaFacadeImpl.addCustomer("Jane" , "Doe", "+12345678", "email@email.com"));
        assertDoesNotThrow(() -> spfeaFacadeImpl.addCustomer("Jane" , "Doe", "12345678", "@"));
        assertDoesNotThrow(() -> spfeaFacadeImpl.addCustomer("Jane" , "Doe", "+1(2)2345678", "email@email.com"));
        assertDoesNotThrow(() -> spfeaFacadeImpl.addCustomer("Jane" , "Doe", null, "email@email.com"));
        assertDoesNotThrow(() -> spfeaFacadeImpl.addCustomer("Jane" , "Doe", "12345678", null));
        assertDoesNotThrow(() -> spfeaFacadeImpl.addCustomer("Jane" , "Doe", null, null));

    }

    @Test
    public void testGetCustomerIDInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.getCustomerID(null, "Doe"));
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.getCustomerID("Jane", null));
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.getCustomerID(null, ""));
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.getCustomerID("", null));
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.getCustomerID(null, null));
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.getCustomerID("", ""));
    }

    @Test
    public void testGetCustomerIDValidInputs() {
        int id = spfeaFacadeImpl.addCustomer("Jane" , "Doe", "12345678", "email@email.com");
        assertEquals(id, spfeaFacadeImpl.getCustomerID("Jane", "Doe"));

        id = spfeaFacadeImpl.addCustomer("John" , "Doe", "12345678", "email@email.com");
        assertEquals(id, spfeaFacadeImpl.getCustomerID("John", "Doe"));

        assertEquals(null, spfeaFacadeImpl.getCustomerID("A", "A"));
    }

    @Test
    public void testGetAllCustomersNull() {
        assertNotEquals(null, spfeaFacadeImpl.getAllCustomers());
    }

    @Test
    public void testGetAllCustomers() {
        spfeaFacadeImpl.addCustomer("Jane" , "Doe", "12345678", "email@email.com");

        assertTrue(spfeaFacadeImpl.getAllCustomers().contains("Doe,Jane"));
        assertTrue((spfeaFacadeImpl.getAllCustomers().size() == 1));

        spfeaFacadeImpl.addCustomer("John" , "Doe", "12345678", "email@email.com");
        assertTrue(spfeaFacadeImpl.getAllCustomers().contains("Doe,Jane"));
        assertTrue(spfeaFacadeImpl.getAllCustomers().contains("Doe,John"));
        assertTrue((spfeaFacadeImpl.getAllCustomers().size() == 2));

        int id = spfeaFacadeImpl.addCustomer("Jack" , "Doe", "12345678", "email@email.com");
        assertTrue(spfeaFacadeImpl.getAllCustomers().contains("Doe,Jane"));
        assertTrue(spfeaFacadeImpl.getAllCustomers().contains("Doe,John"));
        assertTrue(spfeaFacadeImpl.getAllCustomers().contains("Doe,Jack"));
        assertTrue((spfeaFacadeImpl.getAllCustomers().size() == 3));

        spfeaFacadeImpl.removeCustomer(id);

        spfeaFacadeImpl.addCustomer("John" , "Doe", "12345678", "email@email.com");
        assertTrue(spfeaFacadeImpl.getAllCustomers().contains("Doe,Jane"));
        assertTrue(spfeaFacadeImpl.getAllCustomers().contains("Doe,John"));
        assertTrue((spfeaFacadeImpl.getAllCustomers().size() == 2));


    }

    @Test
    public void testGetCustomerPhone() {
        int id = spfeaFacadeImpl.addCustomer("Jane" , "Doe", "12345678", "email@email.com");
        assertEquals("12345678", spfeaFacadeImpl.getCustomerPhone(id));

        id = spfeaFacadeImpl.addCustomer("John" , "Doe", "+12345678", "email@email.com");
        assertEquals("+12345678", spfeaFacadeImpl.getCustomerPhone(id));

        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.getCustomerPhone(Integer.MAX_VALUE));

    }

    @Test
    public void testSetCustomerPhone() {
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerPhone(Integer.MAX_VALUE, "12345678"));

        int id = spfeaFacadeImpl.addCustomer("Jane" , "Doe", "12345678", "email@email.com");
        spfeaFacadeImpl.setCustomerPhone(id, "87654321");
        assertEquals("87654321", spfeaFacadeImpl.getCustomerPhone(id));

        spfeaFacadeImpl.setCustomerPhone(id, null);
        assertEquals(null, spfeaFacadeImpl.getCustomerPhone(id));

        //Invalid phone
        int finalId = id;
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerPhone(finalId, "" ));

        //Invalid phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerPhone(finalId, "abc" ));

        //Invalid phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerPhone(finalId, "123abc" ));

        //Invalid phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerPhone(finalId, "+123-" ));

        //Invalid phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerPhone(finalId, "+123456(a)" ));

        id = spfeaFacadeImpl.addCustomer("John" , "Doe", "12345678", null);

        int finalId1 = id;
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerPhone(finalId1, null));

    }

    @Test
    public void testGetCustomerEmail() {
        int id = spfeaFacadeImpl.addCustomer("Jane" , "Doe", "12345678", "email@email.com");
        assertEquals("email@email.com", spfeaFacadeImpl.getCustomerEmail(id));

        id = spfeaFacadeImpl.addCustomer("John" , "Doe", "+12345678", "email@email.com");
        assertEquals("email@email.com", spfeaFacadeImpl.getCustomerEmail(id));

        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.getCustomerEmail(Integer.MAX_VALUE));

    }

    @Test
    public void testSetCustomerEmail() {
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerEmail(Integer.MAX_VALUE, "email@email.com"));

        int id = spfeaFacadeImpl.addCustomer("Jane" , "Doe", "12345678", "email@email.com");
        spfeaFacadeImpl.setCustomerEmail(id, "email1@email.com");
        assertEquals("email1@email.com", spfeaFacadeImpl.getCustomerEmail(id));

        spfeaFacadeImpl.setCustomerEmail(id, null);
        assertEquals(null, spfeaFacadeImpl.getCustomerEmail(id));

        //Invalid phone
        int finalId = id;
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerEmail(finalId, "" ));

        //Invalid phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerEmail(finalId, "invalid" ));

        //Invalid phone
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerEmail(finalId, "123" ));

        id = spfeaFacadeImpl.addCustomer("John" , "Doe", null, "email@email.com");

        int finalId1 = id;
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerEmail(finalId1, null));
    }

    @Test
    public void testRemoveCustomer() {
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.removeCustomer(Integer.MAX_VALUE));

        int id = spfeaFacadeImpl.addCustomer("Jane", "Doe", "12345678", "email@email.com");
        spfeaFacadeImpl.removeCustomer(id);
        assertEquals(null, spfeaFacadeImpl.getCustomerID("Jane","Doe"));
        assertFalse(spfeaFacadeImpl.getAllCustomers().contains("Doe,Jane"));
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.getCustomerEmail(id));
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.getCustomerPhone(id));
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerEmail(id, "hello@hello.com"));
        assertThrows(IllegalArgumentException.class, () -> spfeaFacadeImpl.setCustomerPhone(id, "12345678"));

    }
}












