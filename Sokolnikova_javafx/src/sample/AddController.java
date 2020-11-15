package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Hierarchy.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    private TextField txtName, txtCountInGroup, txtMethod, txtPayment, txtProducer, txtCountOfArtist;

    @FXML
    private RadioButton rbGame, rbCartoon;

    @FXML
    private Button btnOk;

    @FXML
    private Label lMethod, lCountInGroup, lProducer, lCountOfArtist;

    private boolean isClickedOk; // нажата ли кнопка ОК

    @Override
    public void initialize(URL location, ResourceBundle resources){
        isClickedOk = false; // ставим false, т.к. еще не нажата кнопка

        // Помещаем радиобаттоны в группу (чтоыб можно было кликнуть только один)
        ToggleGroup group = new ToggleGroup();

        rbGame.setToggleGroup(group);
        rbCartoon.setToggleGroup(group);

        lCountInGroup.setVisible(false);
        lMethod.setVisible(false);
        lProducer.setVisible(false);
        lCountOfArtist.setVisible(false);
        txtMethod.setVisible(false);
        txtCountInGroup.setVisible(false);
        txtProducer.setVisible(false);
        txtCountOfArtist.setVisible(false);
    }

    public void addMovie(){
        // проверяем на корректный ввод
        if (!checkCorrectInput().equals("")){
            // показываем окно с  ошибкой
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Некорректный ввод");
            alert.setContentText(checkCorrectInput());

            alert.showAndWait();
            return;
        }

        isClickedOk = true; // ставим true, т.к. нажали "Ок"
        // закрываем
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    private String checkCorrectInput(){
        String res = "";

        if (isEmpty(txtPayment))
            res = "Введите оплату одному участнику";
        else if (isEmpty(txtName))
            res = "Введите название фильма";

        // если выбран игровой фильм
        if (rbGame.isSelected()){
            if (isEmpty(txtProducer))
                res = "Введите продюсера";
            else if (isEmpty(txtCountInGroup))
                res = "Введите кол-во человек в группе";
            else if (!isInteger(txtCountInGroup.getText()))
                res = "Некорректный ввод кол-ва человек в группе";
        }
        // если выбран мультфильм
        else if (rbCartoon.isSelected()){
            if (isEmpty(txtMethod))
                res = "Введите способ создания";

            else if (isEmpty(txtCountOfArtist))
                res = "Введите число художников";
            else if (!isInteger(txtCountOfArtist.getText()))
                res = "Некорректный ввод числа художников";

        } else
            res = "Выберите вид зарплаты";

        return res;
    }

    private boolean isEmpty(TextField txt){
        if (txt.getText().equals(""))
            return true;
        return false;
    }

    public Movie getMovie(){
        String name = txtName.getText();
        double payment = Double.parseDouble(txtPayment.getText());

        if (rbGame.isSelected()){
            String producer = txtProducer.getText();
            int countInGroup = Integer.parseInt(txtCountInGroup.getText());
            return new GameMovie(name, payment, producer, countInGroup);
        } else {
            String method = txtMethod.getText();
            int countOfArtist = Integer.parseInt(txtCountOfArtist.getText());
            return new Cartoon(name, payment, method, countOfArtist);
        }
    }

    public void setMovie(Movie movie){
        txtName.setText(movie.getName());
        txtPayment.setText(movie.getPaymentToOneParticipantOfFilming() + "");

        if (movie instanceof GameMovie){ // если это объект класса GameMovie
            rbGame.setSelected(true);
            lProducer.setVisible(true);
            txtProducer.setVisible(true);
            lCountInGroup.setVisible(true);
            txtCountInGroup.setVisible(true);
            GameMovie gameMovie = (GameMovie) movie;
            txtProducer.setText(gameMovie.getProducer() + "");
            txtCountInGroup.setText(gameMovie.getNumberOfPeopleInTheSet() + "");
        } else { // если это объект класса Cartoon
            rbCartoon.setSelected(true);
            lMethod.setVisible(true);
            txtMethod.setVisible(true);
            lCountOfArtist.setVisible(true);
            txtCountOfArtist.setVisible(true);
            Cartoon cartoon = (Cartoon) movie;
            txtMethod.setText(cartoon.getCreationMethod() + "");
            txtCountOfArtist.setText(cartoon.getNumberOfArtists() + "");
        }
    }

    private static boolean isInteger(String str)
    {
        try
        {
            int d = Integer.parseInt(str); //преобразовываем
        }
        catch(NumberFormatException nfe) //если возникает исключение
        {
            return false;
        }
        return true;
    }

    public boolean isClickedOk() {
        return isClickedOk;
    }

    public void setClickedOk(boolean clickedOk) {
        isClickedOk = clickedOk;
    }

    public void onClickRb(ActionEvent actionEvent) {
        RadioButton clickedRb = (RadioButton) actionEvent.getSource();

        // в соответствии с нажатой кнопкой показываем/скрываем поля
        if (clickedRb.getId().equals("rbGame")){
            lProducer.setVisible(true);
            lCountInGroup.setVisible(true);
            lMethod.setVisible(false);
            lCountOfArtist.setVisible(false);
            txtProducer.setVisible(true);
            txtCountInGroup.setVisible(true);
            txtMethod.setVisible(false);
            txtCountOfArtist.setVisible(false);
        } else  if (clickedRb.getId().equals("rbCartoon")){
            lProducer.setVisible(false);
            lCountInGroup.setVisible(false);
            lMethod.setVisible(true);
            lCountOfArtist.setVisible(true);
            txtProducer.setVisible(false);
            txtCountInGroup.setVisible(false);
            txtMethod.setVisible(true);
            txtCountOfArtist.setVisible(true);
        }

    }
}
