package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Hierarchy.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ArrayList<Movie> movies = new ArrayList<>(); // список фильмов

    @FXML
    private ListView<Movie> movieListView; // listView из fxml для отображения

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // добавляем
        try {
            // создаем поток для чтения из файла
            FileInputStream fis = new FileInputStream("settings.xml");
            // создаем xml декодер из файла
            XMLDecoder decoder = new XMLDecoder(fis);
            Settings settings = (Settings) decoder.readObject();

            // а теперь заполняем форму
            movies = settings.movies; // считываем тот список из settings
            movieListView.setItems( // заполняет listview
                    FXCollections.observableArrayList(settings.movies));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStageClose() {
        // создали экземпляр класса
        Settings settings = new Settings();

        settings.movies = movies;

        // добавляем
        try {
            // создаем поток для записи в файл experiment.xml
            FileOutputStream fos = new FileOutputStream("settings.xml");
            // создали энкодер, которые будет писать в поток
            XMLEncoder encoder = new XMLEncoder(fos);

            // записали настройки
            encoder.writeObject(settings);

            // закрыли энкодер и поток для записи
            // если не закрыть, то файл будет пустой
            encoder.close();
            fos.close();
        } catch (Exception e) {
            // на случай ошибки
            e.printStackTrace();
        }
    }

    public void onClickAdd(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addView.fxml"));
        // а затем уже непосредственно вызываем загрузку дерева разметки из файла
        Parent root = loader.load();

        AddController addController = loader.getController(); // считываем контроллер для addView.fxml

        stage.setScene(new Scene(root)); // ставим эту сцену
        stage.setTitle("Добавить");
        stage.initModality(Modality.WINDOW_MODAL); // делаем модальным
        stage.initOwner( // определяем владельца (главную форму)
                ((Node)event.getSource()).getScene().getWindow() );

        stage.showAndWait(); // показываем

        if (addController.isClickedOk()) { // если нажали добавить
            Movie workerForAdding = addController.getMovie(); // считываем фильм
            movies.add(workerForAdding); // добавляем в список

            movieListView.getItems().add(workerForAdding); // отображаем в listView
        }
    }

    public void onClickRemove(){
        Movie selectedMovie = movieListView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            final int selectedIdx = movieListView.getSelectionModel().getSelectedIndex();
            System.out.println(selectedIdx);
            movieListView.getItems().remove(selectedIdx);
            movies.remove(selectedIdx);
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ничего не выбрано");
            alert.setContentText("Выберите фильм из списка.");

            alert.showAndWait();
        }
    }

    public void onClickChange(ActionEvent event) throws IOException{
        Movie selectedMovie = movieListView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            final int selectedIdx = movieListView.getSelectionModel().getSelectedIndex();
            movieListView.getItems().remove(selectedIdx);
            movies.remove(selectedIdx);

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addView.fxml"));
            // а затем уже непосредственно вызываем загрузку дерева разметки из файла
            Parent root = loader.load();

            AddController addController = loader.getController();
            addController.setMovie(selectedMovie);

            stage.setScene(new Scene(root));
            stage.setTitle("Изменить");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
            stage.showAndWait();

            Movie changedMovie = addController.getMovie(); // считываем фильм с формы добавления

            movieListView.getItems().add(selectedIdx, changedMovie);
            movies.add(selectedIdx, changedMovie);

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ничего не выбрано");
            alert.setContentText("Выберите фильм из списка.");

            alert.showAndWait();
        }
    }

    public void onClickRequest(ActionEvent actionEvent) {
        Movie selectedMovie = movieListView.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            final int selectedIdx = movieListView.getSelectionModel().getSelectedIndex();

            Request requestInterface = movies.get(selectedIdx);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запрос");
            alert.setHeaderText("Оплата съёмочной группе:");
            alert.setContentText("" + requestInterface.calcPaymentToTheFilmCrew());

            alert.showAndWait();

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ничего не выбрано");
            alert.setContentText("Выберите фильм из списка.");

            alert.showAndWait();
        }
    }

}