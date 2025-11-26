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
    
    // helper methode to get value after label until next comma 
    private static String get(String line, String label) {
        int start = line.indexOf(label);// Find the start index of the label
        if (start < 0) return null;// Label not found
        start += label.length();// Move the start index past the label itself
        int end = line.indexOf(",", start);// Find the end index: the position of the next comma (separator)
        if (end < 0) end = line.length();// If no comma is found, the value extends to the end of the line
        return line.substring(start, end).trim();// Extract, trim, and return the substring value
    }
    
    
       // Read all users from file 
    public void loadFromFile(String filePath) throws IOException {
        //clear existing data before loading new data
        users.clear();
        
        //Check if the exists or not
        File f = new File(filePath);
        if (!f.exists()) {
            System.out.println("File not found!");
            return; 
        }

        // read all lines into temporary list
       try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;       // skip empty lines
                if (line.startsWith("-")) continue; // skip separator lines

                String lower = line.toLowerCase();

                // we only accept these as user type headers
                if (!lower.equals("child") && !lower.equals("teacher") && !lower.equals("assistant") && !lower.equals("admin")) {
                    continue;// unexpected line ignore and continue
                }

                String typeOriginal = line; // type of the user child,Teacher ....

                // read next non empty data line
                String data;
                while ((data = br.readLine()) != null && data.trim().isEmpty()) {
                    // skip blank lines between type and data
                }
                if (data == null) break; // end of file
                data = data.trim();

                // parse common fields 
                String idStr = get(data, "ID:");
                if (idStr == null) continue;
                int id = Integer.parseInt(idStr);

                String firstName = get(data, "First Name:");
                String lastName = get(data, "Last Name:");
                String gender = get(data, "Gender:");
                String birthDate = get(data, "Birth Date:");
                String email = get(data, "Email:");
                String phone = get(data, "Phone:");
                String password = get(data, "Password:");

                // child only
                String allergy = get(data, "Allergy Information:");
                String chronic = get(data, "Chronic Diseases:");
                String notes = get(data, "General Notes:");

                // staff only
                String role = get(data, "Role:");
                String status = get(data, "Status:");

                User user;
                if (lower.equals("child")) {
                    // Child constructor 
                    user = new User(
                            id, typeOriginal,
                            firstName, lastName,
                            gender, birthDate,
                            email, phone, password,
                            allergy, chronic, notes
                    );
                } else {
                    // Staff constructor 
                    user = new User(
                            id, typeOriginal,
                            firstName, lastName,
                            gender, birthDate,
                            email, phone, password,
                            role, status
                    );
                }

                users.add(user);
            }
        }
    }
    
    // Write users to the outputfile
    public void saveToFile(String filePath) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            //write all users with separator lines
            for (User u : users) {
                // first line user type
                bw.write(u.getUserType());
                bw.newLine();
                // append all fields on one line
                StringBuilder line = new StringBuilder();
                line.append("First Name: ").append(u.getFirstName()).append(", ");
                line.append("Last Name: ").append(u.getLastName()).append(", ");
                line.append("ID:").append(String.format("%03d", u.getId())).append(", ");
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
