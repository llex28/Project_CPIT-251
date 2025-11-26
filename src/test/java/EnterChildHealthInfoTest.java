/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Nadaa
 */
public class EnterChildHealthInfoTest {
    @Test
    public void testAddHealthInfoSuccess() {
        ChildHealthManager.Database db = new ChildHealthManager.Database();
        ChildHealthManager.NotificationService ns = new ChildHealthManager.NotificationService();
        ChildHealthManager manager = new ChildHealthManager(db, ns);

        ChildHealthManager.ChildHealthData data =
                new ChildHealthManager.ChildHealthData("child-001", "Has peanut allergy");

        String result = manager.addHealthInfo(data);

        assertEquals("Health info added successfully", result);
        assertEquals("Child child-001: New health update", ns.getLastNotification());
    }

    @Test
    public void testAddHealthInfoInvalidChildId() {
        ChildHealthManager.Database db = new ChildHealthManager.Database();
        ChildHealthManager.NotificationService ns = new ChildHealthManager.NotificationService();
        ChildHealthManager manager = new ChildHealthManager(db, ns);

        ChildHealthManager.ChildHealthData data =
                new ChildHealthManager.ChildHealthData("", "Some notes");

        String result = manager.addHealthInfo(data);

        assertEquals("Error: could not save health info", result);
        assertNull(ns.getLastNotification());
    }
    
}
