/* // test change

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author la690
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Input and output file
        String inputPath = "inputFile.txt";   
        String outputPath = "output.txt"; 
        // Initialize object to handel user operatein
        UserManager manager = new UserManager();
        // Try and catch to take care about the input file
        try {
            manager.loadFromFile(inputPath);
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }
        // Initialize Scanner object to read from user
        Scanner in = new Scanner(System.in);
        //Loop to display the menu option
        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) List all users");
            System.out.println("2) Create new CHILD");
            System.out.println("3) Create new STAFF");
            System.out.println("4) Update existing user");
            System.out.println("5) Delete user");
            System.out.println("0) Save and Exit");
            System.out.print("Choice: ");
            //Take user input and validate it is an integer
            int choice;
            try {
                choice = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice.");
                continue;
            }
            // Handle save and exit option
            if (choice == 0) {
                // save all changes and exit
                try {
                    manager.saveToFile(outputPath);
                    System.out.println("All changes saved to " + outputPath);
                } catch (IOException e) {
                    System.out.println("Error writing output file: " + e.getMessage());
                }
                break;
            }
            // Switch to handel other menu options
            switch (choice) {
                // This option to list all users
                case 1:
                    for (User u : manager.getAllUsers()) {
                        System.out.println(u);
                    }
                    break;
                //This option to create new child user 
                case 2: 
                    //Take all input informations from the user
                    System.out.print("ID (int): ");
                    int cid = Integer.parseInt(in.nextLine());
                    System.out.print("First name: ");
                    String cfn = in.nextLine();
                    System.out.print("Last name: ");
                    String cln = in.nextLine();
                    System.out.print("Gender: ");
                    String cgender = in.nextLine();
                    System.out.print("Birth date: ");
                    String cbd = in.nextLine();
                    System.out.print("Email: ");
                    String cemail = in.nextLine();
                    System.out.print("Phone: ");
                    String cphone = in.nextLine();
                    System.out.print("Password: ");
                    String cpw = in.nextLine();
                    System.out.print("Allergy info: ");
                    String callergy = in.nextLine();
                    System.out.print("Chronic diseases: ");
                    String cchronic = in.nextLine();
                    System.out.print("General notes: ");
                    String cnotes = in.nextLine();
                    //Create new object
                    User child = new User(
                            cid, "Child",
                            cfn, cln,
                            cgender, cbd,
                            cemail, cphone, cpw,
                            callergy, cchronic, cnotes
                    );
                    //attempt to add the new user, if the user already exists will not added
                    if (manager.createUser(child)) {
                        System.out.println("Child created.");
                    } else {
                        System.out.println("ID already exists!");
                    }
                    break;
                    
                    //This option to create sttaf user
                case 3: 
                    //Take all input informations from the user
                    System.out.print("ID (int): ");
                    int sid = Integer.parseInt(in.nextLine());
                    System.out.print("Staff type (Teacher/Assistant/Admin): ");
                    String stype = in.nextLine();
                    System.out.print("First name: ");
                    String sfn = in.nextLine();
                    System.out.print("Last name: ");
                    String sln = in.nextLine();
                    System.out.print("Gender: ");
                    String sgender = in.nextLine();
                    System.out.print("Birth date: ");
                    String sbd = in.nextLine();
                    System.out.print("Email: ");
                    String semail = in.nextLine();
                    System.out.print("Phone: ");
                    String sphone = in.nextLine();
                    System.out.print("Password: ");
                    String spw = in.nextLine();
                    System.out.print("Role: ");
                    String srole = in.nextLine();
                    System.out.print("Status: ");
                    String sstatus = in.nextLine();
                    //Create new object
                    User staff = new User(
                            sid, stype,
                            sfn, sln,
                            sgender, sbd,
                            semail, sphone, spw,
                            srole, sstatus
                    );
                    //attempt to add the new user, if the user already exists will not added
                    if (manager.createUser(staff)) {
                        System.out.println("Staff created.");
                    } else {
                        System.out.println("ID already exists!");
                    }
                    break;
                //This option for update user information   
                case 4: 
                    System.out.print("Enter user ID to update: ");
                    int uid = Integer.parseInt(in.nextLine());
                    System.out.print("Field (firstname, lastname, gender, birthdate, email, phone, password, allergyinfo, chronicdiseases, generalnotes, role, status): ");
                    String field = in.nextLine();
                    System.out.print("New value: ");
                    String newVal = in.nextLine();
                    //Attempt to ubdate the specified field for the given user ID
                    if (manager.updateUserField(uid, field, newVal)) {
                        System.out.println("User updated.");
                    } else {
                        System.out.println("User not found or invalid field.");
                    }
                    break;
                //This option for delet user
                case 5: 
                    System.out.print("Enter user ID to delete: ");
                    int did = Integer.parseInt(in.nextLine());
                    //Attempt to delete the user for the given user ID
                    if (manager.deleteUser(did)) {
                        System.out.println("User deleted.");
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
        // nada code
        ChildHealthManager.Database db = new ChildHealthManager.Database();
        ChildHealthManager.NotificationService ns = new ChildHealthManager.NotificationService();
        ChildHealthManager managerr = new ChildHealthManager(db, ns);
        
        ChildHealthManager.ChildHealthData data = new ChildHealthManager.ChildHealthData("child-001", "Has peanut allergy");
        
        String result = managerr.addHealthInfo(data);

        
        System.out.println("Parent Message: " + result);
        System.out.println("Teacher Last Notification: " + ns.getLastNotification());
        
        in.close();
    }
    
}
