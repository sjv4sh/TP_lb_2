package sample.Hierarchy;

import java.util.ArrayList;

public class WorkClass {
    private ArrayList<Movie> movies;//лист со всеми объектами

    public WorkClass() {
    }

    public WorkClass(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie worker){
        movies.add(worker);
    }

    public void changeMovie(int id, Movie changedMovie){
        movies.set(id, changedMovie);
    }

    public void removeMovie(Movie worker){
        movies.remove(worker);
    }

    public void removeMovie(int id){
        movies.remove(id);
    }

    public boolean isMovie(Movie worker){
        for (Movie el:
                movies) {
            if (el == worker)
                return true;
        }
        return false;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
