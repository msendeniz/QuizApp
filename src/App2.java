

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

public class App2 extends JFrame {

    private int currentRow = 0;
    private int currentCol = 0;
    private static int score = 0;
    private int secondsLeft;
    private JPanel[][] grid;
    private JLabel scoreboard;
    private JLabel timerLabel;
    private Timer timer;
    private int rowNum = 6;
    private int colNum = 6;
    private Random random = new Random();
    private ImageIcon cursorIcon;
    private ImageIcon targetIcon;
    private JLabel[][] cellLabels;

    public App2(int initialTime) {
        setTitle("Mermaid Game");
        setSize(400, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        requestFocusInWindow(); 
        cursorIcon = new ImageIcon("C:\\Users\\Mert\\Desktop\\cursorImage.png"); //Our cursor image declaration, path
        targetIcon = new ImageIcon("C:\\Users\\Mert\\Desktop\\mermaidImage.png");////Mermaid image declaration, path
        this.secondsLeft = initialTime;

        grid = new JPanel[rowNum][colNum];
        initializeGrid();

        scoreboard = new JLabel("Score: " + score);
        scoreboard.setFont(new Font("Arial", Font.PLAIN, 20)); //Score label font

        timerLabel = new JLabel("Time: " + secondsLeft);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 20)); //Time label font

        JPanel hbox = new JPanel();
        hbox.add(scoreboard);
        hbox.add(timerLabel);

        add(createGridPanel(), BorderLayout.CENTER);
        add(hbox, BorderLayout.NORTH);

        timer = new Timer(1000, new ActionListener()//creating instance of timer class
        		{
            @Override
            public void actionPerformed(ActionEvent e)//It is called every time the timer "ticks"
            {
                updateTimer();
            }
        });
        timer.setRepeats(true); //timer continues to fire its action events repeatedly
        timer.start();

       addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                handleKeyPress(evt);
            }
        });
    }

    private void initializeGrid() {
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                grid[row][col] = creatingaVisibleCell();
            }
        }
        setRandomNumberCellColors(); //after the 6x6 matrix has been created, this method is invoked for creating random number of cells
        settingdesiredCursorStyle(currentRow, currentCol);
    }

    private JPanel creatingaVisibleCell() {
        JPanel cell = new JPanel();
        cell.setPreferredSize(new Dimension(60, 60)); //setting the size of the cell panel to 60*60 pixels
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //setting border
        cell.setBackground(Color.LIGHT_GRAY); //setting background of the cell
        return cell;
    }

    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(rowNum, colNum)); //creating a new panel with a grid layout
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                gridPanel.add(grid[row][col]); //adding components of the two-dimensional grid array to the gridPanel
            }
        }
        return gridPanel;
    }

    private void settingdesiredCursorStyle(int row, int col) {
        
        for (int i = 0; i < rowNum; i++) { 
            for (int j = 0; j < colNum; j++) {
                grid[i][j].removeAll(); // Removing all components from the cell at position [i][j]
                if (grid[i][j].getBackground() == Color.GREEN) {               
                    JButton greenButton = new JButton();
                    greenButton.setIcon(targetIcon); //setting mermaid icon to this green button
                    grid[i][j].add(greenButton); // green button is added to the current cell
                } else {
                    grid[i][j].setBackground(Color.LIGHT_GRAY);
                }               
                grid[i][j].revalidate();
                grid[i][j].repaint();  //for refreshing the cell's appearance
            }
        }
    
        //cursor style at its new position
        grid[row][col].setBackground(Color.MAGENTA);
        JButton cursorButton = new JButton(); //creating instance which is named as cursor button
        cursorButton.setIcon(cursorIcon); //setting or adding curson icon to the cursor button
        grid[row][col].add(cursorButton); // cursorbutton is added to the current cell
        grid[row][col].revalidate();
        grid[row][col].repaint();
    }
    
    

    private void setRandomNumberCellColors() //function of this method is to create random number of cells every time after cursor touches mermaid
    {
        int numberOfGreenCells = random.nextInt(rowNum/2)+1; //specifying and limiting the number of cells that will be turned green
    
        for (int i = 0; i < numberOfGreenCells; i++) //for loop repeats itself 'added green cells' times and in each iteration random cell
        	//will be selected and properties of it will be changed
        {
            int randomRow = random.nextInt(rowNum);
            int randomCol = random.nextInt(colNum);
            grid[randomRow][randomCol].setBackground(Color.GREEN);
    
            grid[randomRow][randomCol].removeAll(); //resetting the cell before adding new components
            	JButton button = new JButton();
            button.setIcon(targetIcon); // new button is created and mermaid image is set on this icon
            
            grid[randomRow][randomCol].add(button); //adding latest created button to the cell
            
            //updating cell'S appearance
            grid[randomRow][randomCol].revalidate();         
            grid[randomRow][randomCol].repaint();
        }
    }
    

    private void handleKeyPress(KeyEvent evt) //function of this method is to define keyboard inputs to our matrix game
    {
        if (timer.isRunning()) //actions are only continued if a certain timer exists{
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_UP:
                    movetheCursor(-1, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    movetheCursor(1, 0);
                    break;
                case KeyEvent.VK_LEFT:
                    movetheCursor(0, -1);
                    break;
                case KeyEvent.VK_RIGHT:
                    movetheCursor(0, 1);
                    break;
            }
        }
    
    private void movetheCursor(int rowChange, int colChange) {
        int newRow = currentRow + rowChange;
        int newCol = currentCol + colChange;
    
        if (newRow >= 0 && newRow < rowNum && newCol >= 0 && newCol < colNum) // ensuring the new position is within the boundaries of the matrix grid.
        {
            if (grid[newRow][newCol].getBackground() == Color.GREEN) {
               score += 100;  // if the cursor moves to a cell with a green background, meaning saving mermaid, the user's score will increase by 100
                scoreboard.setText("Score: " + score);  //updating scoreboard
              grid[newRow][newCol].setBackground(Color.LIGHT_GRAY); // background color of the mermaid's cell will be turned to gray, meaning saving operation completed
                setRandomNumberCellColors(); //addition of new mermaids in random cells and with random number
            }
            currentRow = newRow;
            currentCol = newCol; //cursor's new position is updated
            settingdesiredCursorStyle(currentRow, currentCol);  //updating the cursor's appearance or style at its new position.
        }
    }
    

    private void updateTimer() //countdown timer for our mermaid game  
    {
        if (secondsLeft > 0) {
            secondsLeft--;
            timerLabel.setText("Time: " + secondsLeft);
        } else {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game completed! Your score: " + score); //if there is no time left, message box will appear on the screen
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App2(score).setVisible(true)); //starting the GUI application, mermaid game
    }
}
