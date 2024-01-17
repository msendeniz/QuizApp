    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

    public class QuestionManagerGUI {
        static final String WELCOME_PANEL = "welcome";
        static final String DIFFICULTY_PANEL = "difficulty";
        static final String QUESTIONS_PANEL = "questions";

        public static void main(String[] args) {
            EventQueue.invokeLater(() -> {
                JFrame frame = new QuizAppFrame();
                frame.setTitle("Quiz App");
                frame.setSize(600, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            });
        }
    }

