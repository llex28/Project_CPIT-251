// Name: Reham Alwegdani
// ID: 2308074
// Section: EAR
// Group: 2

import java.util.ArrayList;
import java.util.List;
public class MessagingSystem {
    private Database db;

    public MessagingSystem(Database db) {
        this.db = db;
    }

    public void sendMessage(String content, String sender, String recipient) {
        if (content == null || content.isEmpty()) {
            System.out.println("System: Message Not Sent - Content is empty");
            return;
        }

        Message message = new Message(content, sender, recipient);
        db.storeMessage(message);
        System.out.println("System: Message Sent Confirmation");

        // Notify recipient
        notifyRecipient(recipient, message);
    }

    private void notifyRecipient(String recipient, Message message) {
        System.out.println("System: Notifying " + recipient + " of new message...");
        displayMessages(recipient);
    }

    public void displayMessages(String recipient) {
        List<Message> messages = db.retrieveMessages(recipient);
        System.out.println("Messages for " + recipient + ":");
        for (Message msg : messages) {
            System.out.println("From: " + msg.sender + " | Content: " + msg.content + " | Read: " + msg.isRead);
            db.markAsRead(msg); // optional read acknowledgment
        }
    }
    
}
