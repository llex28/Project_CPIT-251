/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nadaa
 */
public class ChildHealthManager {
    
    // inner class (this class holds the child's health information)
        public static class ChildHealthData {
        private String childId;
        private String notes;

        //creates a new health record
        public ChildHealthData(String childId, String notes) {
            this.childId = childId;
            this.notes = notes;
        }

        public String getChildId() {
            return childId;
        }

        public String getNotes() {
            return notes;
        }
    }

   // inner class(This class simulates saving data to a database)
    public static class Database {
        //tries to save the data
        public boolean storeHealthInfo(ChildHealthData data) {
            if (data == null || data.getChildId() == null || data.getChildId().isEmpty()) {
                return false; 
            }
            System.out.println("Saving health info for: " + data.getChildId());
            return true; 
        }
    }

    // inner class (this class simulates sending a notification to the teacher)
    public static class NotificationService {
        private String lastNotification = null;

        public void notifyTeacher(String childId, String message) {
            lastNotification = "Child " + childId + ": " + message;
            System.out.println("Teacher Notification: " + lastNotification);
        }

       
        public String getLastNotification() {
            return lastNotification;
        }
    }

    //main Manager Class

    private Database database;
    private NotificationService notificationService;

    // connects the manager with database and notification service
    public ChildHealthManager(Database database, NotificationService notificationService) {
        this.database = database;
        this.notificationService = notificationService;
    }

    //main function that adds health info anf return simple massge back to parent
    public String addHealthInfo(ChildHealthData data) {

        boolean saved = database.storeHealthInfo(data);

        if (!saved) {
            return "Error: could not save health info";
        }

        notificationService.notifyTeacher(data.getChildId(), "New health update");

        return "Health info added successfully";
    }

   
    public NotificationService getNotificationService() {
        return notificationService;
    }
    
}
