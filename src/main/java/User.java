/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author la690
 */
public class User {
    // common fields for both child and staff
    private int id;                 
    private String userType;        
    private String firstName;
    private String lastName;
    private String gender;
    private String birthDate;
    private String email;
    private String phone;
    private String password;

    // fields for child only
    private String allergyInfo;
    private String chronicDiseases;
    private String generalNotes;

    // Fields for staff only
    private String role;            
    private String status;          
    // Constract to create new child user
    public User(int id, String userType,String firstName, String lastName,String gender, String birthDate,String email, String phone, String password,String allergyInfo, String chronicDiseases, String generalNotes) {
        //common fields
        this.id = id;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.password = password;

        // child fields
        this.allergyInfo = allergyInfo;
        this.chronicDiseases = chronicDiseases;
        this.generalNotes = generalNotes;

        // staff fields are NULL
        this.role = null;
        this.status = null;
    }
    
    //Constract to create new staff user
    public User(int id, String userType,String firstName, String lastName,String gender, String birthDate,String email, String phone, String password,String role, String status) {

        this.id = id;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.password = password;

        // staff fields
        this.role = role;
        this.status = status;

        // child fields are NULL
        this.allergyInfo = null;
        this.chronicDiseases = null;
        this.generalNotes = null;
    }
    
    // Here all the getters for all fields
    public int getId(){
        return id;
    }
    public String getUserType(){
        return userType;
    }
    public String getFirstName(){
        return firstName; 
    }
    public String getLastName(){
        return lastName;
    }
    public String getGender(){
        return gender;
    }
    public String getBirthDate(){
        return birthDate;
    }
    public String getEmail(){
        return email;
    }
    public String getPhone(){
        return phone;
    }
    public String getPassword(){
        return password; 
    }
    public String getAllergyInfo(){
        return allergyInfo;
    }
    public String getChronicDiseases(){
        return chronicDiseases;
    }
    public String getGeneralNotes(){
        return generalNotes;
    }
    public String getRole(){
        return role;
    }
    public String getStatus(){
        return status;
    }
    
    
    // here all setters for all fields
    public void setFirstName(String firstName) { 
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName; 
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }
    public void setEmail(String email){
        this.email = email; 
    }
    public void setPhone(String phone){
        this.phone = phone; 
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setAllergyInfo(String allergyInfo){
        this.allergyInfo = allergyInfo; 
    }
    public void setChronicDiseases(String chronicDiseases){
        this.chronicDiseases = chronicDiseases; 
    }
    public void setGeneralNotes(String generalNotes){
        this.generalNotes = generalNotes;
    }
    public void setRole(String role){
        this.role = role; 
    }
    public void setStatus(String status){
        this.status = status; 
    }
    
    //return string representation of the user object including all fields
    @Override
    public String toString() {
        return "User{" + "ID=" + String.format("%03d", id) + ", Type='" + userType + '\'' +", Name='" + firstName + " " + lastName + '\''+", Gender='" + gender + '\'' +", BirthDate='" + birthDate + '\'' +
                ", Email='" + email + '\'' +", Phone='" + phone + '\'' +
                ", AllergyInfo='" + allergyInfo + '\'' +", Chronic Diseases='" + chronicDiseases + '\'' +", Notes='" + generalNotes + '\'' +", Role='" + role + '\'' +", Status='" + status + '\'' + '}';
    }
}










