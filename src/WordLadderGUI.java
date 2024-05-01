package src;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import src.objects.*;

public class WordLadderGUI extends JFrame {
    private JTextField startWordField;
    private JTextField endWordField;
    private JButton ucsButton;
    private JButton gbfsButton;
    private JButton aStarButton;
    private JTextArea resultArea;
    private WordLadderSolver solver;

    public WordLadderGUI() {
        initUI();
        solver = createSolver("dictionary.txt");
        if (solver == null) {
            System.exit(1); // Terminates the application
        }
        setTitle("Word Ladder Solver");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 400); // Adjusted for better layout display
        setLocationRelativeTo(null);
        setResizable(true); // Allow resizing
    }

    private void initUI() {
        Font commonFont = new Font("Monospaced", Font.PLAIN, 16); // Create a common font object
    
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 10, 0)); // Changed to 1 row, 2 columns
        getContentPane().add(mainPanel);
    
        // Panel that will center the leftPanel contents vertically and horizontally
        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.setBackground(new Color(45, 45, 45));
        mainPanel.add(centeringPanel);
    
        // Left Panel for inputs and buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(45, 45, 45)); // Dark background for the left panel
        centeringPanel.add(leftPanel); // Add leftPanel to the centering panel
    
        // Input panel with labels
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBackground(new Color(45, 45, 45)); // Dark background for the input panel
        JLabel startWordLabel = new JLabel("<html><font color='white'>Start Word:</font></html>");
        startWordLabel.setFont(commonFont);
        inputPanel.add(startWordLabel);
        startWordField = new JTextField();
        startWordField.setFont(commonFont);
        inputPanel.add(startWordField);
        JLabel endWordLabel = new JLabel("<html><font color='white'>End Word:</font></html>");
        endWordLabel.setFont(commonFont);
        inputPanel.add(endWordLabel);
        endWordField = new JTextField();
        endWordField.setFont(commonFont);
        inputPanel.add(endWordField);
    
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(45, 45, 45)); // Dark background for the button panel
        ucsButton = new JButton("UCS");
        gbfsButton = new JButton("GBFS");
        aStarButton = new JButton("A*");
        // Set font for buttons
        ucsButton.setFont(commonFont);
        gbfsButton.setFont(commonFont);
        aStarButton.setFont(commonFont);
        // Set foreground and background for buttons
        ucsButton.setForeground(Color.WHITE);
        gbfsButton.setForeground(Color.WHITE);
        aStarButton.setForeground(Color.WHITE);
        ucsButton.setBackground(new Color(70, 70, 70));
        gbfsButton.setBackground(new Color(70, 70, 70));
        aStarButton.setBackground(new Color(70, 70, 70));
        buttonPanel.add(ucsButton);
        buttonPanel.add(gbfsButton);
        buttonPanel.add(aStarButton);
    
        // Adding a rigid area for spacing before the input panel
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(inputPanel);
        leftPanel.add(buttonPanel);
        leftPanel.add(Box.createVerticalGlue()); // Adding space after the button panel
    
        // Result area
        resultArea = new JTextArea(10, 20);
        resultArea.setEditable(false);
        resultArea.setFont(commonFont); // Set font for the text area
        resultArea.setBackground(new Color(30, 30, 30)); // Dark background for the text area
        resultArea.setForeground(new Color(255, 255, 255)); // White text
        JScrollPane scrollPane = new JScrollPane(resultArea);
        mainPanel.add(scrollPane); // Add result area to the main panel as the second column
    
        // Setup buttons with actions
        ucsButton.addActionListener(e -> solve("ucs"));
        gbfsButton.addActionListener(e -> solve("gbfs"));
        aStarButton.addActionListener(e -> solve("a*"));
    }

    private void solve(String algorithm) {
        String start = startWordField.getText().trim().toUpperCase(); // Ensure upper case
        String end = endWordField.getText().trim().toUpperCase(); // Ensure upper case
        try {
            long startTime = System.currentTimeMillis();
            Solution solution = solver.solve(start, end, algorithm);
            long endTime = System.currentTimeMillis();
            resultArea.setText("Path:\n");
            solution.path.forEach(word -> resultArea.append(word + "\n"));
            resultArea.append("Visited words: " + solution.visited_words + "\n");
            resultArea.append("Duration: " + (endTime - startTime) + " ms\n");
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    private WordLadderSolver createSolver(String dictionaryPath) {
        try {
            return new WordLadderSolver(dictionaryPath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load dictionary: " + e.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordLadderGUI().setVisible(true));
    }
}
