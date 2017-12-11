package MapNavigation;

import Entity.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class SearchEngine {

        DirectoryController dc;

        public SearchEngine(DirectoryController dc) { this.dc = dc; }

        private List<Node> places(){
            TreeMap<String,ObservableList<Node>> directory = dc.getDirectory();
            List<Node> locations = new ArrayList<>();
            Collection<ObservableList<Node>> nodeCollection = directory.values();
            locations = directory.get("All");
            return locations;
        }
        //put list of list into lists of names

        private  int LevenshteinDistance(String src, String dest)
        {
            int[][] d = new int[(src.length() + 1)] [(dest.length() + 1)];
            int i, j, cost;
            char[] str1 = src.toCharArray();
            char[] str2 = dest.toCharArray();

            for (i = 0; i <= str1.length; i++)
            {
                d[i][0] = i;
            }
            for (j = 0; j <= str2.length; j++)
            {
                d[0][j] = j;
            }
            for (i = 1; i <= str1.length; i++)
            {
                for (j = 1; j <= str2.length; j++)
                {

                    if (str1[i - 1] == str2[j - 1])
                        cost = 0;
                    else
                        cost = 1;

                    d[i][ j] = Math.min(d[i - 1][j] + 1,Math.min(d[i][j - 1] + 1,d[i - 1][ j - 1] + cost));

                    if ((i > 1) && (j > 1) && (str1[i - 1] ==
                            str2[j - 2]) && (str1[i - 2] == str2[j - 1]))
                    {
                        d[i][j] = Math.min(d[i][j], d[i - 2][j - 2] + cost);
                    }
                }
            }

            return d[str1.length][ str2.length];
        }

        public  ObservableList<Node> Search(String wd){
            String word = wd.toLowerCase();
            List<Node> places = places();
            List<String> wordList =  new ArrayList<>();

            for(Node n: places){
                wordList.add(n.getShortName().toLowerCase());
            }
            //System.out.println(wordList);
            List<Node> results = new ArrayList<>();
            double fuzzyness = 0.5;//basically the allowed error - adjust as needed
            List<Integer> matches = new ArrayList<Integer>();
            while (results.size() < 6) {
                for (int i = 0; i < wordList.size(); i++) {
                    String s = wordList.get(i);
                    // Calculate the Levenshtein-distance:
                    int levenshteinDistance = LevenshteinDistance(word, s);
                    // Length of the longer string:
                    int length = Math.max(word.length(), s.length());
                    // Calculate the score:
                    double score = 1.0 - (double) levenshteinDistance / length;
                    if (score > fuzzyness & (!matches.contains(i))) {
                        matches.add(i);
                    }
                }
                if(fuzzyness > 0.3 ){
                    fuzzyness -= 0.1;
                }
                else break;
            }
            for ( int i = 0; i < 6; i++){
                if (matches.size() > i) {
                    int m = matches.get(i);
                    Node node = places.get(m);
                    results.add(node);
                }
            }
            List<Node> letterStarts = new ArrayList<>();
            List<Integer> remove = new ArrayList<>();

            for (int i = 0; i < results.size();i++){
                Node n = results.get(i);
                if(n.getShortName().toLowerCase().contains(word)){
                    letterStarts.add(n);
                    remove.add(i);
                }
            }

            List<Node> answer = new LinkedList<>();

            for (int j = 0; j < letterStarts.size();j++){
                answer.add(letterStarts.get(j));
            }
            for (int k = 0; k < results.size(); k++){
                if(!remove.contains(k)){
                    answer.add(results.get(k));
                }
            }
            ObservableList FXresults = FXCollections.observableArrayList();
            FXresults.addAll(answer);
            return FXresults;
        }


}
