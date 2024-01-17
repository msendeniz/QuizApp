import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
class DifficultyPanel extends JPanel {
    private QuizAppFrame parentFrame;

    public DifficultyPanel(QuizAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridLayout(1, 3));

        JButton easyButton = createDifficultyButton("EASY");
        JButton mediumButton = createDifficultyButton("MEDIUM");
        JButton hardButton = createDifficultyButton("HARD");

        add(easyButton);
        add(mediumButton);
        add(hardButton);
    }

    private JButton createDifficultyButton(String difficulty) {
        JButton button = new JButton(difficulty);
        button.addActionListener(new DifficultyButtonListener(difficulty));
        return button;
    }

    private class DifficultyButtonListener implements ActionListener {
        private int difficultyLevel;

        public DifficultyButtonListener(String difficultyLevel) {
            switch (difficultyLevel) {
                case "EASY":
                    this.difficultyLevel = 1;
                    break;
                case "MEDIUM":
                    this.difficultyLevel = 2;
                    break;
                case "HARD":
                    this.difficultyLevel = 3;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown difficulty level: " + difficultyLevel);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Question> questions = loadQuestionsFromCsv("C:\\Users\\Mert\\Desktop\\erdemQuestions.csv", this.difficultyLevel);
            parentFrame.showQuestionsPanel(questions);
        }

        private List<Question> loadQuestionsFromCsv(String csvFilePath, int desiredDifficultyLevel) {
                List<Question> questions = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
            
                        // Check if data array has at least 8 elements (to avoid ArrayIndexOutOfBoundsException)
                        if (data.length >= 8) {
                            int questionNumber = Integer.parseInt(data[0]);
                            int difficultyLevel = Integer.parseInt(data[1]);
                            String questionText = data[2];
                            String[] choices = {data[3], data[4], data[5], data[6]};
                            String correctAnswer = data[7];
            
                            if (difficultyLevel == desiredDifficultyLevel) {
                                Question question = new Question(questionNumber, difficultyLevel, questionText, choices, correctAnswer);
                                questions.add(question);
                            }
                        } else {
                            // Handle the case where the number of columns is not as expected
                            System.out.println("Warning: Incomplete data in line: " + line);
                            System.out.println("Please fix this issue");
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return questions;
            }
    }
}




    

