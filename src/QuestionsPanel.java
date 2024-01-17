
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


class QuestionsPanel extends JPanel {
    private QuizAppFrame parentFrame; // Reference to the parent frame, likely used to interact with other panels or frames in the application.
    private List<Question> questions; // A list to store the questions for the quiz
    private int currentQuestionIndex; // Tracking the index of the current question being displayed.
    private int score; // User score

    private JLabel scoreLabel; // Displaying the current score.
    private JLabel questionTextLabel; // Displaying current question 
    private JButton[] choiceButtons; // Array of buttons for the multiple-choice answers.
    private Timer questionTimer;

    public QuestionsPanel(QuizAppFrame parentFrame)// Allowing QuestionsPanel to interact with the parent frame, 
    {
        this.parentFrame = parentFrame;
        this.score = 0;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton skipButton = new JButton("Skip to Matrix Game"); //Creating a button for skipping to part 2
        skipButton.addActionListener(e -> parentFrame.startMermaidGame(score*3)); // If the button is pressed, Part 2 will begin and part 1 ends

        JPanel topPanel = new JPanel();
        topPanel.add(skipButton);
        topPanel.add(scoreLabel);
        

        questionTextLabel = new JLabel();
        questionTextLabel.setHorizontalAlignment(JLabel.CENTER);
        questionTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel choicesPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        choiceButtons = new JButton[4]; //Creating arrays for choices
        for (int i = 0; i < 4; i++) {
            choiceButtons[i] = new JButton();
            choiceButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            choiceButtons[i].addActionListener(new ChoiceButtonListener());
            choicesPanel.add(choiceButtons[i]);
        }
        //Handling the replacement of choices, questions and toppanel in the GUI 
        add(topPanel, BorderLayout.NORTH);
        add(questionTextLabel, BorderLayout.CENTER);
        add(choicesPanel, BorderLayout.SOUTH);
    }




    public void setQuestions(List<Question> questions) // For initializing the QuestionsPanel with a list of questions.
    {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        displayCurrentQuestion();
    }

    private void displayCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionTextLabel.setText("Q" + currentQuestion.getQuestionNumber() + ": " + currentQuestion.getQuestionText());

            String[] choices = currentQuestion.getChoices();
            for (int i = 0; i < choices.length; i++) {
                choiceButtons[i].setText(choices[i]);
                choiceButtons[i].setBackground(null); // Reseting background color
                choiceButtons[i].setEnabled(true); // Enabling the button for the next question
            }
        } else {
            // Handle end of quiz
            JOptionPane.showMessageDialog(this, "Quiz completed! Your score: " + score);
            parentFrame.showDifficultyPanel(); // Showing difficulty panel again or handle the quiz end
            currentQuestionIndex = 0; // Reseting for next time
            parentFrame.startMermaidGame(score*3);
        }
    }

    public class ChoiceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            Question currentQuestion = questions.get(currentQuestionIndex);

            // Updating the score if the answer is correct
            if (selectedButton.getText().equals(currentQuestion.getCorrectAnswer())) {
                selectedButton.setBackground(Color.GREEN); // Correct answer declaration
                updateScore(currentQuestion.getDifficultyLevel());
            } else {
                selectedButton.setBackground(Color.RED); // Incorrect answer alert
                // Find and mark the correct answer button
                for (JButton button : choiceButtons) {
                    if (button.getText().equals(currentQuestion.getCorrectAnswer())) {
                        button.setBackground(Color.GREEN);
                        break;
                    }
                }
            }

            // Disabling all choice buttons after a choice is made
            for (JButton button : choiceButtons) {
                button.setEnabled(false);
            }

            // Moving to the next question after a short delay
            if (questionTimer != null && questionTimer.isRunning()) {
                questionTimer.stop();
            }
            questionTimer = new Timer(1000, ae -> {
                currentQuestionIndex++;
                displayCurrentQuestion();
            });
            questionTimer.setRepeats(false);
            questionTimer.start();
        }
    }

    private void updateScore(int difficultyLevel) {
        switch (difficultyLevel) {
            case 1: // Easy
                score += 10;
                break;
            case 2: // Moderate
                score += 30;
                break;
            case 3: // Hard
                score += 50;
                break;
        }
        scoreLabel.setText("Score: " + score);
    }
    public void startMermaidGame(int score) //if there will be no question to show in the panel, the user will automatically pass to the mermaid game
    {
        this.dispose(); // Close the current frame
        App2 mermaidGame = new App2(score * 3); // Triple the score as the initial time
        mermaidGame.setVisible(true); // Start the mermaid game
    }
    private void dispose() {
    }

}
