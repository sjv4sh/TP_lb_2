package Task;

import MoviesPack.GameMovie;
import MoviesPack.Cartoon;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestuctTest {
    private static GameMovie gameMovie;
    private static Cartoon cartoon;

    @BeforeAll
    public static void init() {
        gameMovie = new GameMovie("Гроза",
                500,
                "Бондарчук",
                18);
        cartoon = new Cartoon("Ходячий замок",
                1300,
                Cartoon.CreationMethod.нарисованный,
                8);
    }
    @Test
    public void test1() { assertEquals(9000, gameMovie.calcPaymentToTheFilmCrew()); }
    @Test
    public void test2() {
        assertEquals(10400, cartoon.calcPaymentToTheFilmCrew());
    }
}
