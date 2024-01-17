import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionManager {

    private List<Question> questions;
    private List<Question> easyQuestions;
    private List<Question> mediumQuestions;
    private List<Question> hardQuestions;

    public QuestionManager(String csvFilePath) {
        questions = loadQuestionsFromCsv(csvFilePath);
        separateQuestionsByDifficulty();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Question> getEasyQuestions() {
        return easyQuestions;
    }

    public List<Question> getMediumQuestions() {
        return mediumQuestions;
    }

    public List<Question> getHardQuestions() {
        return hardQuestions;
    }

    private void separateQuestionsByDifficulty() {
        easyQuestions = new ArrayList<>();
        mediumQuestions = new ArrayList<>();
        hardQuestions = new ArrayList<>();

        for (Question question : questions) {
            int difficultyLevel = question.getDifficultyLevel();
            switch (difficultyLevel) {
                case 1:
                    easyQuestions.add(question);
                    break;
                case 2:
                    mediumQuestions.add(question);
                    break;
                case 3:
                    hardQuestions.add(question);
                    break;
                
                default:
                    throw new IllegalArgumentException("Invalid difficulty level: " + difficultyLevel);
            }
        }
    }

    private List<Question> loadQuestionsFromCsv(String csvFilePath) {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                
                int questionNumber = Integer.parseInt(data[0]);
                int difficultyLevel = Integer.parseInt(data[1]);
                String questionText = data[2];
                String[] choices = {data[3], data[4], data[5], data[6]};
                String correctAnswer = data[7];

                Question question = new Question(questionNumber, difficultyLevel, questionText, choices, correctAnswer);
                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }
    
    public List<Question> getQuestionsByDifficulty(int difficultyLevel) {
        switch (difficultyLevel) {
            case 1:
                return getEasyQuestions();
            case 2:
                return getMediumQuestions();
            case 3:
                return getHardQuestions();
            default:
                throw new IllegalArgumentException("Invalid difficulty level: " + difficultyLevel);
        }
    }
}
