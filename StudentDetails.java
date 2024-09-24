/*
@authour Nokuthula Thokozani Mbuyisa
@version Java Development Task 4
*/
import java.util.*;

public class StudentDetails 
{
    private String name;
    private String studentID;
    private String password;
    
    private static ArrayList<StudentDetails> registeredStudents = new ArrayList<>();
	private boolean isLoggedIn = false;

    public StudentDetails(String name, String studentID, String password) 
	{
        setName(name);
        setStudentID(studentID);
        setPassword(password);
        // Add the student to the registered list upon creation
        registeredStudents.add(this);
    }

    public void setName(String name) 
	{
        this.name = name;
    }

    public void setStudentID(String studentID) 
	{
        if (studentID.length() != 8) {
            throw new IllegalArgumentException("Student ID must consist of 8 digits.");
        } else {
            for (int i = 0; i < studentID.length(); i++) {
                if (!Character.isDigit(studentID.charAt(i))) {
                    throw new IllegalArgumentException("Student ID must contain only digits.");
                }
            }
            this.studentID = studentID;
        }
    }

    public void setPassword(String password) 
	{
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password should be at least 8 characters long.");
        } else {
            this.password = password;
        }
    }

    public String getStudentID() 
	{
        return studentID;
    }

    public String getPassword() 
	{
        return password;
    }

    public String getName() 
	{
        return name;
    }

    // Validate login by checking if student ID and password match
    public static StudentDetails login() 
	{
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your Student ID: ");
        String enteredID = input.nextLine();

        System.out.println("Enter your password: ");
        String enteredPassword = input.nextLine();

        // Search for the student in the registered students list
        for (StudentDetails student : registeredStudents) 
		{
            if (student.getStudentID().equals(enteredID) && student.getPassword().equals(enteredPassword)) 
			{
                System.out.println("Login successful. Welcome " + student.getName() + "!");
                return student; // Successful login
            }
        }

        // If no match is found
        System.out.println("Invalid Student ID or Password.");
        return null; // Login failed
    }
	
	
    public void logout() 
	{
        isLoggedIn = false;
        System.out.println("You have successfully logged out.");
    }

    public boolean isLoggedIn() 
	{
        return isLoggedIn;
    }


    // Update password method
    public boolean updatePassword() 
	{
        Scanner old = new Scanner(System.in);
        System.out.println("Enter your old password: ");
        String oldPassword = old.nextLine();

        // Validate the old password
        if (oldPassword.equals(password)) {
            Scanner change = new Scanner(System.in);
            System.out.println("Enter your new password: ");
            String newPassword = change.nextLine();

            // Set the new password
            setPassword(newPassword);
            System.out.println("Password updated successfully.");
            return true;
        } else {
            System.out.println("Incorrect password. Password update failed.");
            return false;
        }
    }

    public String toString() 
	{
        return String.format("Hello %s, welcome to your online exam.", name);
    }

    // Register a new student (optional for demonstration purposes)
    public static void registerStudent(String name, String studentID, String password)
	{
        new StudentDetails(name, studentID, password);
    }
	
	
}	