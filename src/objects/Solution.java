package src.objects;
import java.util.*;

public class Solution {
    public List<String> path;
    public int visited_words;

    public Solution(){
        this.path = new ArrayList<>();
        this.visited_words = 0;
    }
    public Solution (List<String> path, int visited_words){
        this.path = path;
        this.visited_words = visited_words;
    }
}
