/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author la690
 */
public class UserManager {
    
    //ArryList to handel all user objects
    private final ArrayList<User> users = new ArrayList<>();
    
    // ArrayList to handel lines before the first user block (intro text)
    private final ArrayList<String> headerLines = new ArrayList<>();
    // ArrayList to handel lines after the last user block (summary text)
    private final ArrayList<String> footerLines = new ArrayList<>();
    
    

    // helper methode to get value after label until next comma 
    private static String get(String line, String label) {
        int start = line.indexOf(label);// Find the start index of the label
        if (start < 0) return null;// Label not found
        start += label.length();// Move the start index past the label itself
        int end = line.indexOf(",", start);// Find the end index: the position of the next comma (separator)
        if (end < 0) end = line.length();// If no comma is found, the value extends to the end of the line
        return line.substring(start, end).trim();// Extract, trim, and return the substring value
    }
    
    
       // Read all users from file keeps header and footer
    public void loadFromFile(String filePath) throws IOException {
        //clear existing data before loading new data
        users.clear();
        headerLines.clear();
        footerLines.clear();
        //Check if the exists or not
        File f = new File(filePath);
        if (!f.exists()) {
            System.out.println("File not found!");
            return; 
        }

        // read all lines into temporary list
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        boolean foundFirstUser = false;// Flag to track if we have started parsing user blocks
        boolean inFooter = false;// Flag to track if we have reached the footer section
        int i = 0;// Current line index

        while (i < lines.size()) {
            String line = lines.get(i);
            String trimmed = line.trim();
            String lower = trimmed.toLowerCase();
            // Check if the current line is a known user type, indicating the start of a user block
            boolean isType = lower.equals("child")
                    || lower.equals("teacher")
                    || lower.equals("assistant")
                    || lower.equals("admin");

            if (!foundFirstUser) {
                // still in header part
                if (!isType) {
                    headerLines.add(line);
                    i++;
                    continue;
                } else {
                    // first user starts type we found
                    foundFirstUser = true;
                    // Parse the entire user block and update 'i' to the next line after the user's data
                    i = parseUserBlock(lines, i, trimmed, lower); // consumes type+data
                    continue;
                }
            }

            // After first user has been found
            if (inFooter) {
                // already in footer section: keep adding all subsequent lines to the footer
                footerLines.add(line);
                i++;
                continue;
            }

            if (isType) {
                // Found another user block,parse it
                i = parseUserBlock(lines, i, trimmed, lower);
                continue;
            }

            // not a type line
            if (trimmed.isEmpty() || trimmed.startsWith("-")) {
                // blank line or separator line between users
                i++;
                continue;
            }

            // any other non empty, non type line after user blocks means we in the footer
            inFooter = true;
            footerLines.add(line);
            i++;
        }
    }
    
    
        
     //Parses one complete user block type line + data line starting at the given index.
     
    private int parseUserBlock(List<String> lines, int index, String typeOriginal, String typeLower) {
        int i = index + 1;

        // skip blank lines between type and data
        while (i < lines.size() && lines.get(i).trim().isEmpty()) {
            i++;
        }
        if (i >= lines.size()) return i;// Reached end of the file

        String data = lines.get(i).trim();

        // parse common fields 
        String idStr= get(data, "ID:");
        if (idStr == null) return i + 1;  //ID field is missing or broken
        int id= Integer.parseInt(idStr);// Convert ID string to integer

        String firstName  = get(data, "First Name:");
        String lastName   = get(data, "Last Name:");
        String gender     = get(data, "Gender:");
        String birthDate  = get(data, "Birth Date:");
        String email      = get(data, "Email:");
        String phone      = get(data, "Phone:");
        String password   = get(data, "Password:");

        // child-only
        String allergy    = get(data, "Allergy Information:");
        String chronic    = get(data, "Chronic Diseases:");
        String notes      = get(data, "General Notes:");

        // staff-only
        String role       = get(data, "Role:");
        String status     = get(data, "Status:");

        User user;
        if (typeLower.equals("child")) {
            // use child constructor
            user = new User(
                    id, typeOriginal,
                    firstName, lastName,
                    gender, birthDate,
                    email, phone, password,
                    allergy, chronic, notes
            );
        } else {
            // Teacher / Assistant / Admin -> staff constructor
            user = new User(
                    id, typeOriginal,
                    firstName, lastName,
                    gender, birthDate,
                    email, phone, password,
                    role, status
            );
        }

        users.add(user);
        return i + 1; // next line after data
    }


    
    
    // Write header,users and footer to the outputfile
    public void saveToFile(String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            // write header exactly as it was
            for (String h : headerLines) {
                bw.write(h);
                bw.newLine();
            }

            // if header does not end with a blank line, add one
            if (!headerLines.isEmpty()) {
                String last = headerLines.get(headerLines.size() - 1).trim();
                if (!last.isEmpty()) {
                    bw.newLine();
                }
            }

            //write all users with separator lines
            for (User u : users) {
                // first lineuser type
                bw.write(u.getUserType());
                bw.newLine();
                // The comma-separated data lineappend all common fields
                StringBuilder line = new StringBuilder();
                line.append("First Name: ").append(u.getFirstName()).append(", ");
                line.append("Last Name: ").append(u.getLastName()).append(", ");
                line.append("ID:").append(u.getId()).append(", ");
                line.append("Gender: ").append(u.getGender()).append(", ");
                line.append("Birth Date: ").append(u.getBirthDate()).append(", ");
                line.append("Email: ").append(u.getEmail()).append(", ");
                line.append("Phone: ").append(u.getPhone()).append(", ");
                //append specific type fields
                if (u.getUserType().equalsIgnoreCase("child")) {
                    line.append("Allergy Information: ").append(u.getAllergyInfo()).append(", ");
                    line.append("Chronic Diseases: ").append(u.getChronicDiseases()).append(", ");
                    line.append("General Notes: ").append(u.getGeneralNotes()).append(", ");
                    line.append("Password: ").append(u.getPassword());
                } else {
                    line.append("Role: ").append(u.getRole()).append(", ");
                    line.append("Status: ").append(u.getStatus()).append(", ");
                    line.append("Password: ").append(u.getPassword());
                }

                
                bw.write(line.toString());
                bw.newLine();
                bw.write("---------------------------------------------------------------------------------");
                bw.newLine();
            }

            //Write footer lines exactly as it was
            for (String fLine : footerLines) {
                bw.write(fLine);
                bw.newLine();
            }
        }
    }

    
    // Method to find the user by ID
    public User findUserById(int id) {
        for (User u : users) {
            if (u.getId() == id) return u;
        }
        return null;
    }
    //Method to return the current number of users in memory 
    public int getUsersCount() {
        return users.size();
    }
    //Methode ro return a copy of list of all users
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // add new user if the ID unique
    public boolean createUser(User newUser) {
        // check if the ID already exists
        if (findUserById(newUser.getId()) != null) {
            return false; 
        }
        users.add(newUser);
        return true;
    }
    
    // remove a user from list by ID
    public boolean deleteUser(int id) {
        User u = findUserById(id);
        if (u == null) return false;
        users.remove(u);
        return true;
    }

    // Updates a single specified field for an existing user.
    public boolean updateUserField(int id, String fieldName, String newValue) {
        User u = findUserById(id);
        if (u == null || fieldName == null) return false;

        switch (fieldName.toLowerCase()) {
            case "firstname":       u.setFirstName(newValue);       break;
            case "lastname":        u.setLastName(newValue);        break;
            case "gender":          u.setGender(newValue);          break;
            case "birthdate":       u.setBirthDate(newValue);       break;
            case "email":           u.setEmail(newValue);           break;
            case "phone":           u.setPhone(newValue);           break;
            case "password":        u.setPassword(newValue);        break;
            case "allergyinfo":     u.setAllergyInfo(newValue);     break;
            case "chronicdiseases": u.setChronicDiseases(newValue); break;
            case "generalnotes":    u.setGeneralNotes(newValue);    break;
            case "role":            u.setRole(newValue);            break;
            case "status":          u.setStatus(newValue);          break;
            default:
                return false;// Field name was not recognized
        }
        return true;
    }
    
}
