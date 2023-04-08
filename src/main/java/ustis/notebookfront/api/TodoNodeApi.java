package ustis.notebookfront.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ustis.notebookfront.dao.TodoNode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TodoNodeApi {
    private NotebookApi notebookApi = NotebookApi.getInstance();

    public List<TodoNode> getById(Integer id) throws IOException {
        SimpleResponse response = notebookApi.get("todo-node/", id.toString());

        if(response.getCode() == 404)
            throw new IOException("Не найдено");

        Type todoNodeType = new TypeToken<ArrayList<TodoNode>>() {
        }.getType();
        return new Gson().fromJson(response.getBodyJson(), todoNodeType);
    }

    public TodoNode post(TodoNode todoNode) throws IOException {
        String json = new Gson().toJson(todoNode);
        SimpleResponse response = notebookApi.post("todo-node/", json);

        if(response.getCode() == 500)
            throw new IOException("Не найдено " + response.getMessage());

        todoNode.setId(Integer.valueOf(response.getBodyJson()));
        return todoNode;
    }

    public void put(TodoNode todoNode) throws IOException {
        String json = new Gson().toJson(todoNode);
        SimpleResponse response = notebookApi.put("todo-node/", json);

        if (response.getCode() == 500)
            throw new IOException("Не найдено " + response.getBodyJson());
    }

    public void delete(Integer id) throws IOException {
        SimpleResponse response = notebookApi.delete("todo-node/", id.toString());

        if (response.getCode() == 500)
            throw new IOException("Не найдено " + response.getMessage());
    }
}
