package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Label lblBlack;
    @FXML
    public Label lblWhite;

    @FXML
    public ListView<String> lstBlack;
    @FXML
    public ListView<String> lstWhite;

    ObservableList<String> lstBlackItems = FXCollections.observableArrayList();
    ObservableList<String> lstWhiteItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lstBlack.setItems(lstBlackItems);
        lstWhite.setItems(lstWhiteItems);

        lblBlack.setOnDragDetected(this::onDragDetected);
        lblWhite.setOnDragDetected(this::onDragDetected);

        lstBlack.setOnDragDetected(this::onDragDetected);
        lstWhite.setOnDragDetected(this::onDragDetected);

        lstBlack.setOnDragOver(this::onListViewDragOver);
        lstWhite.setOnDragOver(this::onListViewDragOver);

        lstBlack.setOnDragDropped(this::onListViewDragDropped);
        lstWhite.setOnDragDropped(this::onListViewDragDropped);
    }

    public void onDragDetected(MouseEvent mouseEvent) {
        Node sourceNode = (Node) mouseEvent.getSource();
        Dragboard db = sourceNode.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString("");
        db.setContent(content);

        mouseEvent.consume();
    }

    public void onListViewDragOver(DragEvent dragEvent) {
        if (dragEvent.getGestureSource() == lblBlack && dragEvent.getSource() == lstBlack
                || dragEvent.getGestureSource() == lblWhite && dragEvent.getSource() == lstWhite) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }

        if (dragEvent.getGestureSource() instanceof ListView) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void onListViewDragDropped(DragEvent dragEvent) {
        ListView<String> targetListView = (ListView) dragEvent.getGestureTarget();

        // старый обработчик, когда тянут с лейбл, его просто под if спрятали
        if (dragEvent.getGestureSource() instanceof Label) {
            Label sourceLabel = (Label) dragEvent.getGestureSource();
            targetListView.getItems().add(sourceLabel.getText());
        } else if (dragEvent.getGestureSource() instanceof ListView) {
            // новый обработчик, когда тянут с ListView
            // берем источник
            ListView sourceListView = (ListView) dragEvent.getGestureSource();

            // берем последний элемент в списке
            String lastItem = (String)sourceListView.getItems().get(sourceListView.getItems().size() - 1);

            // удаляем последний элемент из списка источника
            sourceListView.getItems().remove(sourceListView.getItems().size() - 1);

            // добавляем в список, над котором отпустили мышку
            targetListView.getItems().add(lastItem);
        }
    }
}
