package sample.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public TableView mainTable;
    public ChoiceBox cmbBookType;
    ObservableList<Class<? extends Book>> bookTypes = FXCollections.observableArrayList(
            Book.class,
            Satire.class,
            Detective.class,
            Novel.class
    );
    BookModel bookModel = new BookModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bookModel.addDataChangedListener((books, dpopular) -> {
            mainTable.setItems(FXCollections.observableArrayList(books));
            ObservableList<String> newList = FXCollections.observableArrayList(dpopular);
        });

        TableColumn<Book, String> titleColumn = new TableColumn<>("Название");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Автор");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> rdayColumn = new TableColumn<>("Дата выхода");
        rdayColumn.setCellValueFactory(new PropertyValueFactory<>("rday"));

        TableColumn<Book, String> dpopularColumn = new TableColumn<>("Степень популяризации");
        dpopularColumn.setCellValueFactory(new PropertyValueFactory<>("dpopular"));

        TableColumn<Book, String> typeofissueColumn = new TableColumn<>("Вид выпуска");
        typeofissueColumn.setCellValueFactory(new PropertyValueFactory<>("typeofissue"));

        TableColumn<Book, String> descriptionColumn = new TableColumn<>("Описание");
        descriptionColumn.setPrefWidth(600);
        descriptionColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDescription());
        });

        cmbBookType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.bookModel.setBookFilter((Class<? extends Book>) newValue);
        });

        mainTable.getColumns().addAll(titleColumn, authorColumn, rdayColumn, dpopularColumn,typeofissueColumn, descriptionColumn);

        cmbBookType.setItems(bookTypes);
        cmbBookType.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class object) {
                if (Book.class.equals(object)) {
                    return "Все";
                } else if (Satire.class.equals(object)) {
                    return "Сатира";
                } else if (Detective.class.equals(object)) {
                    return "Детектив";
                } else if (Novel.class.equals(object)) {
                    return "Новелла";
                }
                return null;
            }

            @Override
            public Class fromString(String string) {
                return null;
            }
        });
    }

    public void onAddClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BookForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.mainTable.getScene().getWindow());
        BookFormController controller = loader.getController();
        controller.bookModel = bookModel;
        stage.showAndWait();
    }

    public void onEditClick(ActionEvent actionEvent) throws IOException {
        Book book = (Book) this.mainTable.getSelectionModel().getSelectedItem();
        if (book != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("BookForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.mainTable.getScene().getWindow());

            BookFormController controller = loader.getController();
            controller.setBook((Book) this.mainTable.getSelectionModel().getSelectedItem());
            controller.bookModel = bookModel;

            stage.showAndWait();
        } else {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Ошибка");
            alert1.setHeaderText("Ничего не выбрано");
            alert1.setContentText("Выберите книгу из списка.");

            alert1.showAndWait();
        }
    }

    public void onDeleteClick(ActionEvent actionEvent) {
        Book book = (Book) this.mainTable.getSelectionModel().getSelectedItem();
        if (book != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(String.format("Точно удалить %s?", book.getTitle()));
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                bookModel.delete(book.id);
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Ошибка");
            alert1.setHeaderText("Ничего не выбрано");
            alert1.setContentText("Выберите книгу из списка.");

            alert1.showAndWait();
        }
    }

    public void onSaveToFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные");
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showSaveDialog(mainTable.getScene().getWindow());

        if (file != null) {
            bookModel.saveToFile(file.getPath());
        }
    }

    public void onLoadFromFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить данные");
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog(mainTable.getScene().getWindow());

        if (file != null) {
            bookModel.loadToFile(file.getPath());
        }
    }

}
