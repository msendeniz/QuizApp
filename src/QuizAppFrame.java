
import javax.swing.*;
import java.awt.*;

import java.util.List;
class QuizAppFrame extends JFrame // to create a window, we should extend JFrame
	{
    private CardLayout cardLayout;
    private JPanel cardPanel;   //variables are used to manage the layout and content of the frame. 

    public QuizAppFrame() {
        initializeComponents(); //constructor calls the initializeComponents method to set up the GUI components.
    }

    private void initializeComponents() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        WelcomePanel welcomePanel = new WelcomePanel(this);
        DifficultyPanel difficultyPanel = new DifficultyPanel(this);
        QuestionsPanel questionsPanel = new QuestionsPanel(this);  // panels are created

        cardPanel.add(welcomePanel, QuestionManagerGUI.WELCOME_PANEL);
        cardPanel.add(difficultyPanel, QuestionManagerGUI.DIFFICULTY_PANEL);
        cardPanel.add(questionsPanel, QuestionManagerGUI.QUESTIONS_PANEL); // panels are added to cardpanel with specific identifiers which are used to switch between panels. 

        add(cardPanel);
        cardLayout.show(cardPanel, QuestionManagerGUI.WELCOME_PANEL); // The cardPanel is added to the QuizAppFrame, and the initial panel (welcome panel) is now visible.
    }

    public void showDifficultyPanel() //switching to the difficulty panel 
    {
        cardLayout.show(cardPanel, QuestionManagerGUI.DIFFICULTY_PANEL);
    }

    public void showQuestionsPanel(List<Question> questions) //switching to the questions panel which are retrieved from csv file
    {
       QuestionsPanel questionsPanel = (QuestionsPanel) cardPanel.getComponent(2);//retrieving the third component from cardPanel which is QuestionsPanel
        questionsPanel.setQuestions(questions); //necessary for updating the questions
        cardLayout.show(cardPanel, QuestionManagerGUI.QUESTIONS_PANEL); //displaying questions panel
    }
    public void startMermaidGame(int score)// passing to the Part 2
    {
        this.dispose(); //close the frame
        App2 mermaidGame = new App2(score);
        mermaidGame.setVisible(true); // Start matrix, mermaid, game
    }
}
