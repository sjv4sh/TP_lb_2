package Menu;

import InputInterface.InputInterface;
import MoviesPack.Cartoon;
import MoviesPack.GameMovie;
import MoviesPack.Movie;
import MoviesPack.MoviesWork.ListWork;

import java.util.ArrayList;

import static MoviesPack.Cartoon.CreationMethod.*;

public class Menu {
    public static void start(){

        ArrayList<Movie> movies = new ArrayList<>();

        ListWork work = new ListWork(movies);

        work.addMovie(new Cartoon("Мультфильм1", 60000,
                нарисованный, 10));

        work.addMovie(new GameMovie("Игровой фильм", 100000,
                "Режиссеров Р. Р.", 20));


        boolean flag = false; // переменная для цикла. Если = true, то выход из программы
        while (!flag) {
            System.out.println("\nВыберите задание: \n[1] - добавить\n" +
                    "[2] - изменить\n[3] - просмотр\n" +
                    "[4] - запрос\n[5] - выход");

            switch (InputInterface.inputInteger("")) {
                case 1://добавить
                    work.addMovie(fill());
                    break;
                case 2://изменить
                    work.changeMovie(findMovie(movies), fill());
                    break;
                case 3://просмотр
                    System.out.println("Все фильмы:");
                    System.out.println(work.printMoviesList());
                    break;
                case 4://запрос
                    System.out.println("Оплата съемочной группе = "
                            + work.executeRequest(findMovie(movies)));
                    break;
                case 5://выxод
                    System.out.println("[Выход]");
                    flag = true;
                    break;
                default:
                    System.out.println("Ошибка. Повторите ввод.");
                    break;
            }

        }
    }

    /*Ввод объекта*/
    public static Movie fill(){
        String name = InputInterface.inputString("Название фильма: ");
        double pay = InputInterface.inputDouble("Оплата одному участнику съемок: ");
        int type = InputInterface.inputInteger("Нажмите [1], если это игровой фильм, [2] - мультфильм: ");
        if (type == 1){
            String producer = InputInterface.inputString("Режиссер: ");
            int number = InputInterface.inputInteger("Число человек в съемочной группе: ");
            return new GameMovie(name, pay, producer, number);

        } else {
            Cartoon.CreationMethod creationMethod = нарисованный;
            switch (InputInterface.inputInteger("[1] - кукольный [2] - рисованный [3] - пластилиновый")){
                case 1:
                    creationMethod = кукольный;
                    break;
                case 2:
                    creationMethod = нарисованный;
                    break;
                case 3:
                    creationMethod = пластилиновый;
                    break;
                default:
                    System.out.println("Ошибка. Повторите ввод.");
                    break;
            }

            int number = InputInterface.inputInteger("Количество художников: ");
            return new Cartoon(name, pay, creationMethod, number);
        }
    }

    /* Поиск растения */
    public static int findMovie(ArrayList<Movie> movies){
        int id = InputInterface.inputInteger("Введите номер фильма: ");

        if (id > movies.size())
            id = InputInterface.inputInteger("Такого номера нет.");

        System.out.println("Искомый фильм: " + movies.get(id-1).toString());

        return id-1;
    }
}
