package src.algorithm;
import java.util.*;
import src.objects.*;
import src.utils.*;

public class GBFS implements Algo {
    public Solution solve(String start, String end) {
        Queue<Node> pqueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.heuristic));
        Set<String> visited = new HashSet<>();
        pqueue.add(new Node(start, null, 0, Utils.heuristic(start, end)));
    
        while (!pqueue.isEmpty()) {
            Node current = pqueue.poll();
    
            if (current.word.equals(end)) {
                List<String> path = Utils.buildPath(current);
                int visited_words = visited.size();
                return new Solution(path, visited_words);
            }
    
            visited.add(current.word);
    
            for (String neighbor : Utils.getNeighbors(current.word)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    pqueue.add(new Node(neighbor, current, 0, Utils.heuristic(neighbor, end)));
                }
            }
        }
    
        return new Solution();
    }
}
