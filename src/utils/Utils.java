package src.utils;

import java.util.*;
import src.objects.*;

public class Utils {
    public static Set<String> dictionary;
    public static void setDictionary(Set<String> dict){
        dictionary = dict;
    }
    
    public static List<String> getNeighbors(String word) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char oldChar = chars[i];
            for (char c = 'A'; c <= 'Z'; c++) {
                if (c != oldChar) {
                    chars[i] = c;
                    String newWord = new String(chars);
                    if (dictionary.contains(newWord)) {
                        neighbors.add(newWord);
                    }
                }
            }
            chars[i] = oldChar;
        }
        return neighbors;
    }
    public static int heuristic(String word, String target) {
        int diff = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != target.charAt(i)) {
                diff++;
            }
        }
        return diff;
    }

    public static List<String> buildPath(Node endNode) {
        List<String> path = new LinkedList<>();
        Node current = endNode;
        while (current != null) {
            path.add(0, current.word); // Add to the front
            current = current.parent;
        }
        return path;
    }
}
