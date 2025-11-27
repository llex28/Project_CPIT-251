// Name: Reham Alwegdani
// ID: 2308074
// Section: EAR
// Group: 2

import java.util.ArrayList;
import java.util.List;
public class Database {
    private List<Message> messages = new ArrayList<>();

    public void storeMessage(Message message) {
        messages.add(message);
        System.out.println("Database: Message stored successfully.");
    }

    public List<Message> retrieveMessages(String recipient) {
        List<Message> recipientMessages = new ArrayList<>();
        for (Message msg : messages) {
            if (msg.recipient.equals(recipient)) {
                recipientMessages.add(msg);
            }
        }
        return recipientMessages;
    }

    public void markAsRead(Message message) {
        message.isRead = true;
        System.out.println("Database: Message marked as read.");
    }
    
}
