package ustis.notebookfront;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import ustis.notebookfront.api.PageApi;
import ustis.notebookfront.api.SimpleNoteApi;
import ustis.notebookfront.api.TodoNodeApi;
import ustis.notebookfront.controls.SimplePopupDialog;
import ustis.notebookfront.dao.Page;
import ustis.notebookfront.dao.SimpleNote;
import ustis.notebookfront.dao.TodoNode;
import ustis.notebookfront.service.TodoTreeService;

import java.io.IOException;
import java.util.List;

public class NoteBookController {
    private PageApi pageApi = new PageApi();
    private SimpleNoteApi simpleNoteApi = new SimpleNoteApi();
    private TodoNodeApi todoNodeApi = new TodoNodeApi();

    List<Page> pages;

    Page selectedPage = null;
    SimpleNote selectedSimpleNote = null;

    @FXML
    private ListView<Page> notes;

    @FXML
    private TextField selectedNoteTF;

    @FXML
    private TextArea simpleNoteTA;

    @FXML
    private TreeView<TodoNode> todoListTV;

    @FXML
    private SplitPane todoTreeBlock;

    @FXML
    private TextField selectedTodoNodeTF;

    @FXML
    private ChoiceBox<String> pageTypeCB;

    public void initialize() {
        todoTreeBlock.setVisible(false);
        simpleNoteTA.setVisible(false);
        todoListTV.setVisible(false);

        todoListTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        notes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        notes.setEditable(true);

        pageTypeCB.setItems(FXCollections.observableArrayList("Записка", "Список дел"));
        pageTypeCB.getSelectionModel().select(0);

        fillPages();
    }

    private void fillPages() {
        notes.setItems(FXCollections.observableArrayList());
        try {
            pages = pageApi.getAll();
        } catch (IOException exception) {
            new SimplePopupDialog().show(StateManager.getInstance().getMainStage(), "Ошибка, возможно нет доступа к серверу");
        }
        try {
            notes.getItems().addAll(pages);
        } catch (NullPointerException e) {
            new SimplePopupDialog().show(StateManager.getInstance().getMainStage(), "Ошибка, возможно нет доступа к серверу");
        }
    }

    @FXML
    private void openNote() throws IOException {
        selectedTodoNodeTF.setText("");
        MultipleSelectionModel<Page> selected = notes.getSelectionModel();
        this.selectedPage = selected.getSelectedItem();
        if (this.selectedPage == null)
            return;
        selectedNoteTF.setText(this.selectedPage.getName());
        switch (selectedPage.getNoteType()) {
            case "Simple note" -> showSimpleNote();
            case "Todo note" -> showTodoTree(selectedPage);
            default ->
                    throw new RuntimeException("Не существует такого типа заметки. Возможно вам необходимо обновить приложение");
        }
    }

    private void showSimpleNote() throws IOException {
        todoListTV.setVisible(false);
        todoTreeBlock.setVisible(false);

        this.selectedSimpleNote = simpleNoteApi.getById(selectedPage.getId());
        simpleNoteTA.setEditable(true);
        simpleNoteTA.setVisible(true);
        simpleNoteTA.setText(selectedSimpleNote.getText());
    }

    private void showTodoTree(Page page) throws IOException {
        simpleNoteTA.setVisible(false);

        todoListTV.setVisible(true);
        todoTreeBlock.setVisible(true);

        TreeView<TodoNode> tree = new TodoTreeService().buildTree(todoNodeApi.getById(page.getId()));

        todoListTV.setCellFactory(CheckBoxTreeCell.forTreeView());

        todoListTV.setRoot(tree.getRoot());
    }

    @FXML
    private void createNote() throws IOException {
        if (pageTypeCB.getSelectionModel().getSelectedItem().equals("Записка"))
            pageApi.post(new Page(selectedNoteTF.getText(), "Simple note"));
        if (pageTypeCB.getSelectionModel().getSelectedItem().equals("Список дел"))
            pageApi.post(new Page(selectedNoteTF.getText(), "Todo note"));
        fillPages();
    }

    @FXML
    private void updateNote() throws IOException {
        selectedPage.setName(selectedNoteTF.getText());
        pageApi.put(selectedPage);
        fillPages();
    }

    @FXML
    private void deleteNote() throws IOException {
        pageApi.delete(selectedPage.getId());
        fillPages();
    }

    @FXML
    private void saveNote() throws IOException {
        if (simpleNoteTA.isVisible()) {
            selectedSimpleNote.setText(simpleNoteTA.getText());
            simpleNoteApi.put(selectedSimpleNote);
        }
    }


    @FXML
    private void addTodoNode() throws IOException {
        if (todoListTV.getSelectionModel().isEmpty()) {
            new SimplePopupDialog().show(StateManager.getInstance().getMainStage(),
                    "Не выбрана строка для добавления");
            return;
        }

        TreeItem<TodoNode> selected = todoListTV.getSelectionModel().getSelectedItem();

        TodoNode childNode = new TodoNode(selectedTodoNodeTF.getText(), false, selected.getValue().getId());
        childNode = todoNodeApi.post(childNode);

        CheckBoxTreeItem<TodoNode> newChild = new CheckBoxTreeItem<>(childNode);

        selected.getChildren().add(newChild);
    }

    @FXML
    private void changeTodoNode() throws IOException {
        if (todoListTV.getSelectionModel().isEmpty()) {
            new SimplePopupDialog().show(StateManager.getInstance().getMainStage(),
                    "Не выбрана строка для изменения");
            return;
        }

        CheckBoxTreeItem<TodoNode> selected = (CheckBoxTreeItem<TodoNode>) todoListTV.getSelectionModel().getSelectedItem();

        TodoNode childNode = selected.getValue();
        childNode.setText(selectedTodoNodeTF.getText());
        todoNodeApi.put(childNode);

        selected.getValue().setText(selectedTodoNodeTF.getText());
        todoListTV.getSelectionModel().clearSelection();
        selectedTodoNodeTF.setText("");
    }

    @FXML
    private void deleteTodoNode() throws IOException {
        if (todoListTV.getSelectionModel().isEmpty()) {
            new SimplePopupDialog().show(StateManager.getInstance().getMainStage(),
                    "Не выбрана строка для удаления");
            return;
        }

        TreeItem<TodoNode> forDelete = todoListTV.getSelectionModel().getSelectedItem();
        TreeItem<TodoNode> parent = forDelete.getParent();

        todoNodeApi.delete(forDelete.getValue().getId());

        parent.getChildren().addAll(forDelete.getChildren());
        forDelete.getParent().getChildren().remove(forDelete);
    }

    @FXML
    private void updateSelectedTodoNodeTF() {
        if (todoListTV.getSelectionModel().isEmpty())
            return;
        selectedTodoNodeTF.setText(todoListTV.getSelectionModel().getSelectedItem().getValue().getText());
    }
}