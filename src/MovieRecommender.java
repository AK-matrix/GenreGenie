import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.FileReader;
import com.opencsv.CSVReader;
import java.util.Collections;
import java.util.PriorityQueue;


import java.util.Arrays;
class MovieSimilarity {
    public Movie movie;
    public double similarity;

    public MovieSimilarity(Movie movie, double similarity) {
        this.movie = movie;
        this.similarity = similarity;
    }
}

class Movie {
    String id, title, genres;

    public Movie(String id, String title, String genres) {
        this.id = id;
        this.title = title;
        this.genres = genres;
    }
}

public class MovieRecommender {
    List<Movie> movies = new ArrayList<>();

    public void loadMovies(String filePath) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(filePath));
        String[] nextLine;
        reader.readNext(); // Skip the header
        while ((nextLine = reader.readNext()) != null) {
            movies.add(new Movie(nextLine[0], nextLine[1], nextLine[2]));
        }
        reader.close();
        System.out.println("Loaded " + movies.size() + " movies.");
    }
    public String[] preprocessGenres(String genres) {

        return genres.split(","); // Split genres like "Action|Comedy" into ["Action", "Comedy"]
    }
    public List<Movie> recommend(String title, int topN) {
        Map<String, Double> idfMap = TFIDF.computeIDF(movies);
        Movie targetMovie = movies.stream().filter(m -> m.title.equals(title)).findFirst().orElse(null);

        if (targetMovie == null) return Collections.emptyList();

        double[] targetVector = TFIDF.computeTFIDF(targetMovie.genres.split(","), idfMap);
        PriorityQueue<MovieSimilarity> pq = new PriorityQueue<>((a, b) -> Double.compare(b.similarity, a.similarity));

        for (Movie movie : movies) {
            if (!movie.title.equals(title)) {
                double[] movieVector = TFIDF.computeTFIDF(movie.genres.split(","), idfMap);
                double similarity = Similarity.cosineSimilarity(targetVector, movieVector);
                pq.offer(new MovieSimilarity(movie, similarity));
               // System.out.println(movie.title + ": Similarity = " + similarity);
            }
        }

        List<Movie> recommendations = new ArrayList<>();
        for (int i = 0; i < topN && !pq.isEmpty(); i++) {
            recommendations.add(pq.poll().movie);
        }
        return recommendations;
    }
}
