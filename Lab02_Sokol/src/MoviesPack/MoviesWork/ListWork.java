package MoviesPack.MoviesWork;

import MoviesPack.Movie;

import java.util.ArrayList;

public class ListWork {
    private ArrayList<Movie> movies;//лист со всеми объектами

    public ListWork(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    /* Добавление объекта */
    public void addMovie(Movie movieForAdding){
        movies.add(movieForAdding);
    }

    /* Изменение объекта */
    public void changeMovie(int idForChanging, Movie changedMovie){
        movies.set(idForChanging, changedMovie);
    }

    /* Запрос */
    public double executeRequest(int id){
        Request request = movies.get(id);
        return request.calcPaymentToTheFilmCrew();
    }

    /* Вывод всех объектов*/
    public String printMoviesList(){
        int count = 0;
        String result = "";
        for (Movie mov:
                movies) {
            count++;
            result += count + ") " + mov.toString() + "\n";
        }
        return result;
    }
}
