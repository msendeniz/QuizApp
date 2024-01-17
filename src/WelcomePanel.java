import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

class WelcomePanel extends JPanel {
        private QuizAppFrame parentFrame;

        public WelcomePanel(QuizAppFrame parentFrame) {
            this.parentFrame = parentFrame;
            initializeComponents();
        }

        private void initializeComponents() {
            setLayout(new BorderLayout());

            JLabel welcomeLabel = new JLabel("Welcome to the Quiz App");
            welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
            welcomeLabel.setVerticalAlignment(JLabel.CENTER);

            JButton startButton = new JButton("Start");
            startButton.addActionListener(new StartButtonListener());

            add(welcomeLabel, BorderLayout.CENTER);
            add(startButton, BorderLayout.SOUTH);
        }

        private class StartButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showDifficultyPanel();
            }
        }
    }

