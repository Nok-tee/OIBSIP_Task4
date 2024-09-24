/*
@authour Nokuthula Thokozani Mbuyisa
@version Java Development Task 4
*/
import java.util.*;
import java.util.concurrent.*;

class Question 
{
    private String questionText;
    private List<String> options;
    private int correctAnswer;

    public Question(String questionText, List<String> options, int correctAnswer) 
	{
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public void displayQuestion() 
	{
        System.out.println(questionText);
        for (int i = 0; i < options.size(); i++) 
		{
            System.out.println((i + 1) + ": " + options.get(i));
        }
    }

    public boolean checkAnswer(int selectedAnswer) 
	{
        return selectedAnswer == correctAnswer;
    }
}

class Exam 
{
    private List<Question> questions = new ArrayList<>();
    private Map<Question, Integer> studentAnswers = new HashMap<>();
    private int score = 0;
    private int duration; // in seconds
    private boolean isSubmitted = false;

    public Exam(int durationInMinutes) 
	{
        this.duration = durationInMinutes * 60; // convert to seconds
    }

    public void addQuestion(Question question) 
	{
        questions.add(question);
    }

    public void startExam() 
	{
        Scanner input = new Scanner(System.in);
        System.out.println("Your exam has started. You have " + duration / 10 + " minutes.");

        // Start a separate thread for the timer
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(this::autoSubmit, duration, TimeUnit.SECONDS);

        // Display and answer each question
        for (Question question : questions) 
		{
            question.displayQuestion();
            System.out.print("Select your answer (1-4): ");
            int answer = input.nextInt();
            studentAnswers.put(question, answer);
        }

        if (!isSubmitted) 
		{
            submitExam();  // Submit manually if timer hasn't triggered auto-submit
        }

        scheduler.shutdown();  // Stop the timer thread when exam is submitted
    }

    public void autoSubmit() 
	{
        if (!isSubmitted) 
		{
            System.out.println("\nTime's up! Auto-submitting your exam.");
            submitExam();
        }
    }

    public void submitExam() 
	{
        isSubmitted = true;
        System.out.println("\nSubmitting your answers...");

        for (Map.Entry<Question, Integer> entry : studentAnswers.entrySet()) 
		{
            Question question = entry.getKey();
            int studentAnswer = entry.getValue();
            if (question.checkAnswer(studentAnswer)) 
			{
                score++;
            }
        }

        System.out.println("Exam submitted. Your score is: " + score + "/" + questions.size());
        System.out.println("Session is closed. Logging out...");
    }
}

class Session 
{
    private boolean isLoggedIn = false;

    public void login() 
	{
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Student ID: ");
        String studentID = input.nextLine();
        System.out.println("Enter Password: ");
        String password = input.nextLine();

        // Simulate login validation (could be expanded to check against a database)
        if (studentID.equals("12345678") && password.equals("password123")) 
		{
            isLoggedIn = true;
            System.out.println("Login successful! Welcome to your exam.");
        } else {
            System.out.println("Invalid credentials.");
        }
    }
}