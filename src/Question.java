
public class Question {
    private int difficultyLevel;
    private int questionNumber;
    private String questionText;
    private String[] choices; 
    private String correctAnswer;
    

    public Question(int questionNumber, int difficultyLevel, String questionText, String[] choices, String correctAnswer) {
        this.difficultyLevel = difficultyLevel;
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }
}
