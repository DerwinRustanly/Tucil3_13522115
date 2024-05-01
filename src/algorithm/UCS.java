package src.algorithm;

import java.util.*;
import src.objects.*;
import src.utils.*;
public class UCS implements Algo{
        public Solution solve(String start, String end) {
        Queue<Node> pqueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Map<String, Integer> visited = new HashMap<>();
        pqueue.add(new Node(start, null, 0));
    
        while (!pqueue.isEmpty()) {
            Node current = pqueue.poll();
    
            if (current.word.equals(end)) {
                List<String> path = Utils.buildPath(current);
                int visited_words = visited.size();
                return new Solution(path, visited_words);
            }
    
            visited.put(current.word, current.cost);
    
            for (String neighbor : Utils.getNeighbors(current.word)) {
                int newCost = current.cost + 1;
                if (!visited.containsKey(neighbor) || visited.get(neighbor) > newCost) {
                    visited.put(neighbor, newCost);
                    pqueue.add(new Node(neighbor, current, newCost));
                }
            }
        }
    
        return new Solution();
    }
}
