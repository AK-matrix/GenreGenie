import java.util.List;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        MovieRecommender recommender = new MovieRecommender();
        recommender.loadMovies("src/abc.csv"); // Path to your dataset
        Scanner scanner = new Scanner(System.in);
        List<Movie> recommendations = recommender.recommend(scanner.next(), 5);
        System.out.println("Recommended movies:");
        for (Movie movie : recommendations) {
            System.out.println(movie.title);
        }
    }

}
