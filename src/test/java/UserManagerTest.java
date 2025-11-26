/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
/**
 *
 * @author la690
 */
public class UserManagerTest {
    private File tempInput;// Temporary file used as the input source for testing
    private File tempOutput; // Temporary file used as the output destination for saving changes
    private UserManager manager;// The instance of the UserManager class being tested
    
    @Before
    public void setUp() throws Exception {
        // Copy test-users.txt to a temp input file and ensure the file exists
        URL resource = getClass().getClassLoader().getResource("test-users.txt");
        assertNotNull("test-users.txt not found in src/test/resources", resource);

        File original = new File(resource.toURI());
        // Create temporary input file
        tempInput = File.createTempFile("users-test-input-", ".txt");
        tempInput.deleteOnExit();
        // Create temporary output file
        tempOutput = File.createTempFile("users-test-output-", ".txt");
        tempOutput.deleteOnExit();
        // copy the known test data into the temporary input file, 
        Files.copy(original.toPath(), tempInput.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        // Initialize the UserManager and load the copied test data
        manager = new UserManager();
        manager.loadFromFile(tempInput.getAbsolutePath());
    }
    
    @Test
    public void testLoadFromFile() {
        // We expect 2 users from test-users.txt
        assertEquals("There should be 2 users loaded from file", 2, manager.getUsersCount());
        // Verify the Child user (ID 1001) details
        User child = manager.findUserById(1001);
        assertNotNull("Child should exist", child);
        assertEquals("Child", child.getUserType());
        assertEquals("TestChild", child.getFirstName());
        assertEquals("nuts", child.getAllergyInfo());
        // Verify the Teacher user (ID 2001) details
        User teacher = manager.findUserById(2001);
        assertNotNull("Teacher should exist", teacher);
        assertEquals("Teacher", teacher.getUserType());
        assertEquals("Teacher", teacher.getRole());
        assertEquals("Active", teacher.getStatus());
    }
    
    @Test
    public void testCreateUpdateDeleteAndSave() throws Exception {
        // Create new staff user
        User newStaff = new User(
                3001, "Assistant",
                "Sara", "Helper",
                "Girl", "3/3/1995",
                "sara@test.com", "0550000000", "SARA123",
                "assistant", "Active"
        );
        // Assert creation was successful and count increased
        assertTrue("New user should be created", manager.createUser(newStaff));
        assertEquals(3, manager.getUsersCount());

        // Update an existing user's field (Child ID 1001's password)
        assertTrue(manager.updateUserField(1001, "password", "NEWPASS"));

        // Delete an existing user (Teacher ID 2001)
        assertTrue(manager.deleteUser(2001));
        assertEquals(2, manager.getUsersCount());

        // Save to output file
        manager.saveToFile(tempOutput.getAbsolutePath());

        // Create a new manager instance and load data from the output file to verfiy
        UserManager manager2 = new UserManager();
        manager2.loadFromFile(tempOutput.getAbsolutePath());
        // Verify count matches the saved state
        assertEquals("There should be 2 users after delete", 2, manager2.getUsersCount());
        // Verify Child 1001 has the updated password
        User child2 = manager2.findUserById(1001);
        assertNotNull(child2);
        assertEquals("NEWPASS", child2.getPassword());
        // Verify new Staff 3001 exists and has correct type/role
        User staff2 = manager2.findUserById(3001);
        assertNotNull(staff2);
        assertEquals("Assistant", staff2.getUserType());
        assertEquals("assistant", staff2.getRole());
    }
    
    @Test
    public void testDuplicateIdReturnsFalse() {
        // Attempt to create a new user with ID 1001, which already exists
        User duplicateChild = new User(
                1001, "Child",
                "Other", "Name",
                "Boy", "1/1/2020",
                "other@test.com", "0501111111", "XX",
                "none", "none", "none"
        );

        boolean created = manager.createUser(duplicateChild);
        assertFalse("Creating duplicate ID should fail", created);
    }

    @Test
    public void testUpdateNonExistingUserReturnsFalse() {
        // Attempt to update a user with a non-existent ID (9999)
        boolean updated = manager.updateUserField(9999, "phone", "0501231234");
        assertFalse("Updating non-existing user should return false", updated);
    }
    
    
    
}
