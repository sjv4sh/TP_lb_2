package sample.gui;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.net.URL;
import java.util.ResourceBundle;

public class BookFormController implements Initializable{

    public BookModel bookModel;

    public ChoiceBox cmbBookType;
    public TextField txtTitle;
    public TextField txtAuthor;
    public TextField txtRday;
    public TextField txtDpopular;
    public TextField txtTypeofissue;

    public VBox vboxSatire;
    public TextField txtCountOfHeroes;
    public CheckBox chkOkCeleb;

    public VBox vboxDetective;
    public TextField txtNumberOfMurdCrimes;
    public ChoiceBox cmbNarTimeType;

    public VBox vboxNovel;
    public TextField txtAgeMain;
    public TextField txtAgeSecond;
    public TextField txtCountTransform;

    final String BOOK_SATIRE = "Сатира";
    final String BOOK_DETECTIVE = "Детектив";
    final String BOOK_NOVEL = "Новелла";

    private Integer id = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cmbBookType.setItems(FXCollections.observableArrayList(
                BOOK_SATIRE,
                BOOK_DETECTIVE,
                BOOK_NOVEL
        ));

        cmbBookType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.vboxSatire.setVisible(newValue.equals(BOOK_SATIRE));
            this.vboxSatire.setManaged(newValue.equals(BOOK_SATIRE));
            this.vboxDetective.setVisible(newValue.equals(BOOK_DETECTIVE));
            this.vboxDetective.setManaged(newValue.equals(BOOK_DETECTIVE));
            this.vboxNovel.setVisible(newValue.equals(BOOK_NOVEL));
            this.vboxNovel.setManaged(newValue.equals(BOOK_NOVEL));
            updatePanes((String) newValue);
        });

        cmbNarTimeType.getItems().setAll(
                Detective.NarTime.middle,
                Detective.NarTime.new1,
                Detective.NarTime.modern,
                Detective.NarTime.modernity
        );

        cmbNarTimeType.setConverter(new StringConverter<Detective.NarTime>() {
            @Override
            public String toString(Detective.NarTime object) {
                switch (object){
                    case middle:
                        return "Средние века";
                    case new1:
                        return "Новое время";
                    case modern:
                        return "Новейшее время";
                    case modernity:
                        return "Современность";
                }
                return null;
            }

            @Override
            public Detective.NarTime fromString(String s) {
                return null;
            }
        });
        cmbBookType.setValue(BOOK_SATIRE);
        cmbNarTimeType.setValue(Detective.NarTime.middle);
        updatePanes(BOOK_SATIRE);
    }

    public void updatePanes(String value) {
        this.vboxSatire.setVisible(value.equals(BOOK_SATIRE));
        this.vboxSatire.setManaged(value.equals(BOOK_SATIRE));
        this.vboxDetective.setVisible(value.equals(BOOK_DETECTIVE));
        this.vboxDetective.setManaged(value.equals(BOOK_DETECTIVE));
        this.vboxNovel.setVisible(value.equals(BOOK_NOVEL));
        this.vboxNovel.setManaged(value.equals(BOOK_NOVEL));
    }

    public void onCancelClick(javafx.event.ActionEvent actionEvent) {
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void onSaveClick(javafx.event.ActionEvent actionEvent) {
        if (!checkCorrectInput().equals("")){
            // показываем окно с  ошибкой
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Некорректный ввод");
            alert.setContentText(checkCorrectInput());

            alert.showAndWait();
            return;
        } else {
            if (this.id != null) {
                Book book = getBook();
                book.id = this.id;
                this.bookModel.edit(book);
            } else {
                this.bookModel.add(getBook());
            }
        }
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void setBook(Book book) {
        this.cmbBookType.setDisable(book != null);
        this.id = book != null ? book.id : null;
        if (book != null) {
            this.txtTitle.setText(String.valueOf(book.getTitle()));
            this.txtAuthor.setText(book.getAuthor());
            this.txtRday.setText(book.getRday());
            this.txtDpopular.setText(book.getDpopular());
            this.txtTypeofissue.setText(book.getTypeofissue());

            if (book instanceof Satire) { // если сатира
                this.cmbBookType.setValue(BOOK_SATIRE);
                this.txtCountOfHeroes.setText(Integer.toString(((Satire)book).countOfHeroes)) ;
                this.chkOkCeleb.setSelected(((Satire) book).okCeleb);
            } else if (book instanceof Detective) { // если детектив
                this.cmbBookType.setValue(BOOK_DETECTIVE);
                this.txtNumberOfMurdCrimes.setText(Integer.toString(((Detective)book).numberOfMurdCrimes));
                this.cmbNarTimeType.setValue(((Detective) book).narTime);
            } else if (book instanceof Novel) { // если новелла
                this.cmbBookType.setValue(BOOK_NOVEL);
                this.txtAgeMain.setText(Integer.toString((int) ((Novel)book).ageMain));
                this.txtAgeSecond.setText(Integer.toString((int) ((Novel)book).ageSecond));
                this.txtCountTransform.setText(Integer.toString((int) ((Novel)book).countTransform));
            }
        }
    }

    public Book getBook() {
        Book result = null;
        String title = this.txtTitle.getText();
        String author = this.txtAuthor.getText();
        String rday = this.txtRday.getText();
        String dpopular = this.txtDpopular.getText();
        String typeofissue = this.txtTypeofissue.getText();

        switch ((String)this.cmbBookType.getValue()) {
            case BOOK_SATIRE:
                Integer countOfHeroes = Integer.valueOf(this.txtCountOfHeroes.getText()) ;
                result = new Satire(title, author, rday, dpopular, typeofissue, countOfHeroes, this.chkOkCeleb.isSelected());
                break;
            case BOOK_DETECTIVE:
                Integer numberOfMurdCrimes = Integer.valueOf(this.txtNumberOfMurdCrimes.getText());
                result = new Detective(title, author, rday, dpopular, typeofissue, numberOfMurdCrimes, (Detective.NarTime)this.cmbNarTimeType.getValue());
                break;
            case BOOK_NOVEL:
                Integer ageMain =  Integer.valueOf(this.txtAgeMain.getText());
                Integer ageSecond =  Integer.valueOf(this.txtAgeSecond.getText());
                Integer countTransform =  Integer.valueOf(this.txtCountTransform.getText());
                result = new Novel(title, author, rday, dpopular, typeofissue, ageMain, ageSecond, countTransform);
                break;
        }
        return result;
    }

    private String checkCorrectInput(){
        String res ="";

        if (isEmpty(txtTitle))
            res = "Введите название";
        if (isEmpty(txtAuthor))
            res = "Введите автора";
        if (isEmpty(txtRday))
            res = "Введите дату выхода";
        if (isEmpty(txtDpopular))
            res = "Введите степень популяризации";
        if (isEmpty(txtTypeofissue))
            res = "Введите вид выпуска";

        // если выбрана сатира
        if (this.cmbBookType.getValue() == BOOK_SATIRE){
            if (isEmpty(txtCountOfHeroes))
                res = "Введите кол-во героев в рассказе";
            else if (!isInteger(txtCountOfHeroes.getText()))
                res = "Некорректный ввод кол-ва героев в рассказе";
        }
        // если выбран детектив
        else if (this.cmbBookType.getValue() == BOOK_DETECTIVE) {
            if (isEmpty(txtNumberOfMurdCrimes))
                res = "Введите число убийств/преступлений";
            else if (!isInteger(txtNumberOfMurdCrimes.getText()))
                res = "Некорректный ввод числа убийств/преступлений";
        }
        // если выбрана новелла
        else if (this.cmbBookType.getValue() == BOOK_NOVEL){
            if (isEmpty(txtAgeMain))
                res = "Введите возраст главного героя";
            else if (!isInteger(txtAgeMain.getText()))
                res = "Некорректный ввод возраста главного героя";
            if (isEmpty(txtAgeSecond))
                res = "Введите возраст пассии главного героя";
            else if (!isInteger(txtAgeSecond.getText()))
                res = "Некорректный ввод возраста пассии главного героя";
            if (isEmpty(txtCountTransform))
                res = "Введите кол-во трансформаций главного героя";
            else if (!isInteger(txtCountTransform.getText()))
                res = "Некорректный ввод кол-ва трансформаций главного героя";
        }
        return res;
    }

    private boolean isEmpty(TextField txt){
        if (txt.getText().equals(""))
            return true;
        return false;
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

}