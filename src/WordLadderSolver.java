package src;
import java.io.*;
import java.util.*;

import src.objects.*;
import src.utils.*;
import src.algorithm.*;
import java.time.*;
import java.time.temporal.ChronoField;

public class WordLadderSolver {
    private Set<String> dictionary;

    public WordLadderSolver(String dictionaryFile) throws IOException {
        loadDictionary(dictionaryFile);
    }

    private void loadDictionary(String filename) throws IOException {
        dictionary = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line.trim().toUpperCase());
            }
        }
        Utils.setDictionary(dictionary);
    }

    public Solution solve(String start, String end, String algorithm) {
        start = start.toUpperCase(); end = end.toUpperCase();
        if (!dictionary.contains(start) || !dictionary.contains(end)) {
            throw new IllegalArgumentException("Start or end word not in dictionary.");
        } else if (start.length() != end.length()){
            throw new IllegalArgumentException("Start and end word must have the same length.");
        }
        Algo Solver;
        switch (algorithm.toLowerCase()) {
            case "ucs":
                Solver = new UCS();
                return Solver.solve(start, end);
            case "gbfs":
                Solver = new GBFS();
                return Solver.solve(start, end);
            case "a*":
                Solver = new Astar();
                return Solver.solve(start, end);
            default:
                throw new IllegalArgumentException("Unknown algorithm type.");
        }
    }
    
    public static void main(String[] args) {
        try {
            WordLadderSolver solver = new WordLadderSolver("words_alpha.txt");
            int start = LocalTime.now().get( ChronoField.MILLI_OF_SECOND );
            Solution solution = solver.solve("idea", "plan", "A*");
            if (solution.path.isEmpty()) {
                System.out.println("No solution found.");
            } else {
                solution.path.forEach(System.out::println);
                System.out.println("Visited words: " + solution.visited_words);
            }
            int end = LocalTime.now().get( ChronoField.MILLI_OF_SECOND );
            System.out.println("Duration: " + (end-start) + " ms");
        } catch (IOException e) {
            System.err.println("Failed to load dictionary: " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
