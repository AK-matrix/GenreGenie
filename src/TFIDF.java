import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;
import java.util.HashMap;


public class TFIDF {
    public static Map<String, Double> computeTF(String[] genres) {
        Map<String, Integer> tfMap = new HashMap<>();

        // Debug: Print the genres array to ensure it's split correctly

        // Count frequency of each genre
        for (String genre : genres) {
            genre = genre.trim();  // Trim any leading/trailing spaces
            tfMap.put(genre, tfMap.getOrDefault(genre, 0) + 1);
        }

        // Debug: Print the frequency map

        int totalTerms = genres.length;  // Total terms (genres) in the current movie

        // Debug: Print the total terms (genres count)

        Map<String, Double> tfNormalizedMap = new HashMap<>();

        // Normalize frequency by total terms (genres count)
        for (Map.Entry<String, Integer> entry : tfMap.entrySet()) {
            double tf = entry.getValue() / (double) totalTerms;  // Normalize frequency
            tfNormalizedMap.put(entry.getKey(), tf);

            // Debug: Print normalized TF for each genre
             }

        return tfNormalizedMap;
    }

    public static Map<String, Double> computeIDF(List<Movie> movies) {
        Map<String, Integer> docCounts = new HashMap<>();
        for (Movie movie : movies) {
            Set<String> uniqueGenres = new HashSet<>(Arrays.asList(movie.genres.split(", ")));
            for (String genre : uniqueGenres) {
                docCounts.put(genre, docCounts.getOrDefault(genre, 0) + 1);

            }
        }
        Map<String, Double> idfMap = new HashMap<>();
        int totalDocs = movies.size();
        for (Map.Entry<String, Integer> entry : docCounts.entrySet()) {
            idfMap.put(entry.getKey(), Math.log((double) totalDocs / entry.getValue()));
        }
        return idfMap;
    }
        public static double[] computeTFIDF(String[] genres, Map<String, Double> idfMap) {
            Map<String, Double> tfMap = computeTF(genres);
            double[] tfidfVector = new double[idfMap.size()];
            int i = 0;
            for (String genre : idfMap.keySet()) {
                tfidfVector[i++] = tfMap.getOrDefault(genre, 0.0) * idfMap.get(genre);

            }
            return tfidfVector;
        }
}
