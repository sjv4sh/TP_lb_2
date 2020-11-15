package Task;

import MoviesPack.GameMovie;
import MoviesPack.Cartoon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {
    @Test
    public void test1(){
        GameMovie gameMovie = new GameMovie("Гроза",
                500,
                "Бондарчук",
                18);
        assertEquals(9000, gameMovie.calcPaymentToTheFilmCrew());
    }
    @Test
    public void test2(){
        Cartoon cartoon = new Cartoon("Ходячий замок",
                1300,
                Cartoon.CreationMethod.нарисованный,
                8);
        assertEquals(10400, cartoon.calcPaymentToTheFilmCrew());
    }
}
