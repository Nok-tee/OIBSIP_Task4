/*
@authour Nokuthula Thokozani Mbuyisa
@version Java Development Task4
*/

import java.util.*;

public class TestOnlineExamination {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Session session = new Session();  // Session to manage login and logout
        boolean running = true;

        // Create a list of students for the system
        List<StudentDetails> students = Arrays.asList(
            new StudentDetails("Hope Adams", "58974623", "TYpo1485"),
            new StudentDetails("Fana Dew", "69584125", "lk85opgp"),
            new StudentDetails("Aya Nhlapo", "95147826", "12345678"),
            new StudentDetails("Wami", "32589412", "45poppy56")
        );

        while (running) {
            // Display the menu
            System.out.println("\t~ Hello, welcome to your online Maths examination ~");
            System.out.println("\t~ Menu ~");
            System.out.println("1. Login");
            System.out.println("2. Update password");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            // Get user choice
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline left-over

            switch (choice) {
                case 1:
                    // Login option
                    if (!session.isLoggedIn()) {
                        System.out.println("Enter your Student ID: ");
                        String studentId = scanner.nextLine();
                        
                        System.out.println("Enter your Password: ");
                        String password = scanner.nextLine();

                        // Validate login for each student
                        StudentDetails loggedInStudent = null;
                        for (StudentDetails student : students) {
                            if (student.login(studentId, password)) {
                                loggedInStudent = student;
                                break;
                            }
                        }

                        if (loggedInStudent != null) {
                            session.login();  // Session login
                            System.out.println("Login successful! Welcome to your exam.");
                        } else {
                            System.out.println("Invalid Student ID or Password.");
                        }

                    } else {
                        System.out.println("You are already logged in.");
                    }
                    break;
                case 2:
                    // Update password option
                    if (!session.isLoggedIn()) {
                        // If not logged in, prompt login first
                        System.out.println("You must be logged in to update your password.");
                        System.out.println("Please log in first.");

                        System.out.println("Enter your Student ID: ");
                        String studentId = scanner.nextLine();
                        
                        System.out.println("Enter your Password: ");
                        String password = scanner.nextLine();

                        // Validate login for each student
                        StudentDetails loggedInStudent = null;
                        for (StudentDetails student : students) {
                            if (student.login(studentId, password)) {
                                loggedInStudent = student;
                                break;
                            }
                        }

                        if (loggedInStudent != null) {
                            session.login();  // Session login
                            System.out.println("Login successful! You can now update your password.");
                        } else {
                            System.out.println("Invalid Student ID or Password.");
                            break; // Exit the password update if login fails
                        }
                    }

                    // Proceed with password update after successful login
                    System.out.println("Enter your old password: ");
                    String oldPassword = scanner.nextLine();

                    System.out.println("Enter your new password: ");
                    String newPassword = scanner.nextLine();

                    // Assuming only one student is logged in
                    for (StudentDetails student : students) {
                        if (student.updatePassword(oldPassword, newPassword)) {
                            System.out.println("Password updated successfully.");
                        } else {
                            System.out.println("Password update failed.");
                        }
                    }
                    break;
                case 3:
                    // Exit option
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    if (session.isLoggedIn()) {
                        session.logout();
                    }
                    break;
                default:
                    // Invalid choice
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }

            // Simulate Exam logic only if logged in
            if (session.isLoggedIn()) {
                Exam exam = new Exam(1);  // 1 minute for testing

                // Adding sample questions with correct answers embedded
                List<String> options1 = Arrays.asList("1", "2", "3", "4");
                List<String> options2 = Arrays.asList("Paris", "Berlin", "Madrid", "Rome");

                exam.addQuestion(new Question("What is 2+2?", options1, 4));  // Correct answer: "4" (option 4)
                exam.addQuestion(new Question("What is the capital of France?", options2, 1));  // Correct answer: "Paris" (option 1)

                exam.startExam();
                session.logout();  // Automatically log out after exam
            }
        }

        scanner.close();  // Close scanner resource
    }
}

class StudentDetails {
    private String name;
    private String studentID;
    private String password;

    public StudentDetails(String name, String studentID, String password) {
        this.name = name;
        this.studentID = studentID;
        this.password = password;
    }

    public boolean login(String studentId, String password) {
        return this.studentID.equals(studentId) && this.password.equals(password);
    }

    public boolean updatePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword) && newPassword.length() >= 8) {
            this.password = newPassword;
            return true;
        }
        return false;
    }
}

class Session {
    private boolean isLoggedIn = false;

    public void login() {
        isLoggedIn = true;
    }

    public void logout() {
        isLoggedIn = false;
        System.out.println("You have successfully logged out.");
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}

class Question {
    private String question;
    private List<String> options;
    private int correctAnswerIndex;  // Index of the correct answer

    public Question(String question, List<String> options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public void displayQuestion() {
        System.out.println(question);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));  // Display options as numbers or names
        }
    }

    public boolean checkAnswer(int answer) {
        return answer == correctAnswerIndex;
    }
}

class Exam {
    private List<Question> questions = new ArrayList<>();
    private int timeLimit;  // Time limit in minutes

    public Exam(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void startExam() {
        System.out.println("Starting the exam...");

        // Simulate timer (you can add actual timer logic here)
        long endTime = System.currentTimeMillis() + timeLimit * 60 * 1000;

        Scanner scanner = new Scanner(System.in);

        for (Question question : questions) {
            question.displayQuestion();
            System.out.print("Your answer (enter the option number): ");
            int answer = scanner.nextInt();
            if (question.checkAnswer(answer)) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect.");
            }

            if (System.currentTimeMillis() > endTime) {
                System.out.println("Time is up!");
                break;
            }
        }

        System.out.println("Exam finished.");
    }
}
