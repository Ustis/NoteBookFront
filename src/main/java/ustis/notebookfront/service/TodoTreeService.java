package ustis.notebookfront.service;

import javafx.event.EventHandler;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import ustis.notebookfront.api.TodoNodeApi;
import ustis.notebookfront.dao.TodoNode;

import java.io.IOException;
import java.util.List;

public class TodoTreeService {

    private TodoNodeApi todoNodeApi = new TodoNodeApi();

    public TreeView<TodoNode> buildTree(List<TodoNode> todoNodes) {
        TodoNode root = todoNodes.stream().filter(node -> node.getPageId() != null).findFirst().get();
        CheckBoxTreeItem<TodoNode> treeRoot = new CheckBoxTreeItem<>(root);
        treeRoot.setSelected(root.getStatus());
        treeRoot.addEventHandler(CheckBoxTreeItem.checkBoxSelectionChangedEvent(), new EventHandler<CheckBoxTreeItem.TreeModificationEvent<TodoNode>>() {
            @Override
            public void handle(CheckBoxTreeItem.TreeModificationEvent<TodoNode> event) {
                CheckBoxTreeItem<TodoNode> treeNode = event.getTreeItem();
                TodoNode node = treeNode.getValue();
                node.setStatus(treeNode.isSelected());
                try {
                    todoNodeApi.put(node);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Checked");
            }
        });
        searchForChildTodos(todoNodes, treeRoot);
        return new TreeView<>(treeRoot);
    }

    private void searchForChildTodos(List<TodoNode> todoNodes, CheckBoxTreeItem<TodoNode> parent) {
        List<TodoNode> childs = todoNodes.stream()
                .filter(node -> node.getParentId().equals(parent.getValue().getId())).toList();
        if (childs.isEmpty())
            return;

        for (var child : childs) {
            CheckBoxTreeItem<TodoNode> childTree = new CheckBoxTreeItem<>(child);
            childTree.setSelected(child.getStatus());
            parent.getChildren().add(childTree);
            searchForChildTodos(todoNodes, childTree);
        }
    }
}
