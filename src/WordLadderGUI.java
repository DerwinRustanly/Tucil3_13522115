package src;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

import java.awt.*;
import java.io.IOException;
import java.util.stream.IntStream;

import src.objects.*;

public class WordLadderGUI extends JFrame {
    private JTextField startWordField;
    private JTextField endWordField;
    private JButton ucsButton;
    private JButton gbfsButton;
    private JButton aStarButton;
    private JTextPane resultArea;
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
        resultArea = new JTextPane();
        resultArea.setEditorKit(new HTMLEditorKit());
        resultArea.setContentType("text/html");
        resultArea.setEditable(false);
        String initialHtml = "<html><body style='color:white; background-color:rgb(70,70,70); font-family:Monospace; font-size:16pt;'>";
        resultArea.setText(initialHtml);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        mainPanel.add(scrollPane); // Add result area to the main panel as the second column

        // Setup buttons with actions
        ucsButton.addActionListener(e -> solve("ucs"));
        gbfsButton.addActionListener(e -> solve("gbfs"));
        aStarButton.addActionListener(e -> solve("a*"));
    }

    private void solve(String algorithm) {
        String start = startWordField.getText().trim().toUpperCase();
        String end = endWordField.getText().trim().toUpperCase();
        try {
            long startTime = System.nanoTime();
            Solution solution = solver.solve(start, end, algorithm);
            long endTime = System.nanoTime();
            displayResults(solution.visited_words, solution.path, startTime, endTime);
        } catch (Exception e) {
            resultArea.setText("<html><body style='font-family:Monospace; font-size:16pt; color:white; background-color:rgb(70,70,70);'><p>Error: " + e.getMessage() + "</p></body></html>");
        }
    }
    

    private void displayResults(int visitedNodesCount, java.util.List<String> ladder, long startTime, long endTime) {
        long duration = (endTime - startTime) / 1000000; // Convert to milliseconds

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<html><body style='font-family:Monospace; font-size:16pt; color:white; background-color:rgb(70,70,70); text-align:center;'>");

        if (ladder != null && !ladder.isEmpty()) {
            sb.append(
                    "<h2 style='color: #AAA; font-weight: bold; margin-bottom: 10px; border-bottom: 2px solid #666; display: inline-block; padding-bottom: 5px;'>Shortest Ladder</h2>");
            sb.append("<div style='margin:auto; width:fit-content;'>");
            sb.append("<table style='border-collapse:collapse; margin:auto;'>");
            sb.append("<tr><th style='padding: 5px;'>Step</th><th>Word</th></tr>");
            sb.append("<tr><td>1</td><td>").append(formatWord(ladder.get(0), ladder.get(0))).append("</td></tr>");
            for (int i = 0; i < ladder.size() - 1; i++) {
                sb.append("<tr><td>")
                        .append(i + 2)
                        .append("</td><td>")
                        .append(formatWord(ladder.get(i), ladder.get(i + 1)))
                        .append("</td></tr>");
            }
            sb.append("</table></div>");
        } else {
            sb.append("<p>No ladder found.</p>");
        }

        sb.append("<p style='margin-top:20px;'>Visited words: ").append(visitedNodesCount).append("</p>");
        sb.append("<p>Execution time: ").append(duration).append(" ms</p>");
        sb.append("</body></html>");

        resultArea.setText(sb.toString());
    }

    private String formatWord(String word1, String word2) {
        StringBuilder formatted = new StringBuilder("<table style='margin: auto;'><tr>");
        for (int i = 0; i < word1.length(); i++) {
            if (i < word2.length()) {
                String bgColor = word1.charAt(i) == word2.charAt(i) ? "lightgray" : "red";
                String color = "white";
                formatted.append("<td style='width:20px; height:20px; background-color:")
                        .append(bgColor)
                        .append("; color:")
                        .append(color)
                        .append("; text-align:center; border:1px solid black;'>")
                        .append(word2.charAt(i))
                        .append("</td>");
            }
        }
        formatted.append("</tr></table>");
        return formatted.toString();
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
