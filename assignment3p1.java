import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;



public class assignment3p1 {

    //creating a global connection object so it can be used in methods without passing it as an argument
    //Scanner is global so it doesnt need to be recreated in each method
    static Connection conn;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        //Connecting to DB code from slides
        String url = "jdbc:postgresql://localhost/Assignment 03";
        String user = "--YOURUSER--";
        String password = "--YOURPASSWORD--";
        try { 
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                //If it connects it prints a message to the user and then calls the controlFlow method
                System.out.println("Connected to PostgreSQL successfully!");
                controlFlow();
            } else {
                System.out.println("Failed to establish connection.");
            } 
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to get all students from the students table
    private static void getAllStudents(){

        try {
            //Using the global connection object to create a statement and execute a query to get all students
            Statement stmt = conn.createStatement();
            //Query to get all students
            String sql = "SELECT * FROM students";
            //Executing the query and then getting the result set
            stmt.executeQuery(sql);
            ResultSet rs = stmt.getResultSet();
            //Printing out the result set in a formatted way
            while (rs.next()) {
                System.out.print("Student id : " + rs.getInt("student_id"));
                System.out.print(" | Student name : " + rs.getString("first_name") + " " +  rs.getString("last_name"));
                System.out.print(" | Student email : " + rs.getString("email"));
                System.out.print(" | Enrollment data : " + rs.getString("enrollment_date") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //Method to add a student to the students table
    //Takes in first name, last name, email, and enrollment date as arguments
    private static void addStudent(String fn, String ln, String email, String ed){

        try {
            //Using the global connection object to create a statement and execute a query to add a student
            Statement stmt = conn.createStatement();
            //Creating the query to add a student
            String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES ('" + fn + "', '" + ln + "', '" + email + "', '" + ed + "')";
            //Executing the query
            stmt.executeUpdate(sql);
            //Printing a message to the user
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    

    //Method to delete a student from the students table
    //Takes in the student id as an argument
    private static void deleteStudent(String id){

        try {
            //Using the global connection object to create a statement and execute a query to delete a student
            Statement stmt = conn.createStatement();
            //Creating the query to delete a student
            String sql = "DELETE FROM students WHERE student_id = " + id;
            //Executing the query
            stmt.executeUpdate(sql);
            //Printing a message to the user
            System.out.println("Student deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } 

    }

    //Method to update a student's email in the students table
    private static void updateStudentEmail(String id, String email){

        try {
            //Using the global connection object to create a statement and execute a query to update a student's email
            Statement stmt = conn.createStatement();
            //Creating the query to update a student's email
            String sql = "UPDATE students SET email = '" + email + "' WHERE student_id = " + id;
            //Executing the query
            stmt.executeUpdate(sql);
            //Printing a message to the user
            System.out.println("Student email updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();

        }      

    }

    //Method to control the flow of the program
    private static void controlFlow(){
        //Creates a choice variable and sets it to some value other than 5 so the while loop runs
        int choice = 99;
        
        //While loop to keep the program running until the user chooses to exit
        while(choice != 5){
            
            //Printing out the menu to the user
            System.out.println("\n1. Get all students");
            System.out.println("2. Add student");
            System.out.println("3. Update student email");
            System.out.println("4. Delete student");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");
            //Getting the user's choice
            choice = scanner.nextInt();
            //Clearing the scanner buffer
            scanner.nextLine();

            //Switch statement to call the appropriate method based on the user's choice
            switch (choice) {
                case 1:
                    getAllStudents();
                    break;
                case 2:
                //Getting the user's input for the new student's first name, last name, email, and enrollment date
                    System.out.println("Enter first name: ");
                    String fn = scanner.next();
                    System.out.println("Enter last name: ");
                    String ln = scanner.next();
                    System.out.println("Enter email: ");
                    String email = scanner.next();
                    System.out.println("Enter enrollment date: ");
                    String ed = scanner.next();
                    //Calling the addStudent method with the user's input as arguments
                    addStudent(fn, ln, email, ed);
                    break;
                case 3:
                //Getting the user's input for the student id and the new email
                    System.out.println("Enter student id: ");
                    String id = scanner.next();
                    System.out.println("Enter new email: ");
                    String newEmail = scanner.next();
                    //Calling the updateStudentEmail method with the user's input as arguments
                    updateStudentEmail(id, newEmail);
                    break;
                case 4:
                //Getting the user's input for the student id
                    System.out.println("Enter student id: ");
                    String id2 = scanner.next();
                    //Calling the deleteStudent method with the user's input as an argument
                    deleteStudent(id2);
                    break;
                case 5:
                //Printing a goodbye message and breaking out of the while loop
                    System.out.println("Goodbye!");
                    break;
                default:
                //If the user enters an invalid choice, it prints a message to the user
                    System.out.println("Invalid choice");
                    break;


                
            }

        }


    }



}
