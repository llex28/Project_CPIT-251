// Name: Reham Alwegdani
// ID: 2308074
// Section: EAR
// Group: 2
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;
public class MessagingSystemTest {
    private Database db;
    private MessagingSystem system;
    private Message msg1;
    private Message msg2;

    @Before
    public void setUp() {
        db = new Database();
        system = new MessagingSystem(db);
        msg1 = new Message("Hello", "Alice", "Bob");
        msg2 = new Message("Hi", "Charlie", "Bob");
    }

    // ----- Database Tests -----
    @Test
    public void testStoreMessage() {
        db.storeMessage(msg1);
        List<Message> messages = db.retrieveMessages("Bob");
        assertEquals(1, messages.size());
        assertEquals("Hello", messages.get(0).content);
    }

    @Test
    public void testRetrieveMessages() {
        db.storeMessage(msg1);
        db.storeMessage(msg2);
        List<Message> messages = db.retrieveMessages("Bob");
        assertEquals(2, messages.size());
    }

    @Test
    public void testMarkAsRead() {
        db.storeMessage(msg1);
        db.markAsRead(msg1);
        assertTrue(msg1.isRead);
    }

    // ----- MessagingSystem Tests -----
    @Test
    public void testSendMessage() {
        system.sendMessage("Hello Bob", "Alice", "Bob");
        List<Message> messages = db.retrieveMessages("Bob");
        assertEquals(1, messages.size());
        assertEquals("Hello Bob", messages.get(0).content);
        assertEquals("Alice", messages.get(0).sender);
        assertTrue(messages.get(0).isRead);
    }

    @Test
    public void testDisplayMessagesMarksAsRead() {
        system.sendMessage("Hi Bob", "Alice", "Bob");
        system.displayMessages("Bob");
        List<Message> messages = db.retrieveMessages("Bob");
        assertTrue(messages.get(0).isRead);
    }
    
    
}
