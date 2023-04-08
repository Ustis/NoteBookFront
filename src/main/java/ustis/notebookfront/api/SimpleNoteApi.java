package ustis.notebookfront.api;

import com.google.gson.Gson;
import ustis.notebookfront.dao.SimpleNote;

import java.io.IOException;

public class SimpleNoteApi {
    private NotebookApi notebookApi = NotebookApi.getInstance();

    public SimpleNote getById(Integer id) throws IOException {
        SimpleResponse response = notebookApi.get("simple-note/", id.toString());

        if(response.getCode() == 404)
            throw new IOException("Не найдено");

        return new Gson().fromJson(response.getBodyJson(), SimpleNote.class);
    }

    public void post(SimpleNote simpleNote) throws IOException {
        String json = new Gson().toJson(simpleNote);
        SimpleResponse response = notebookApi.post("simple-note/", json);

        if(response.getCode() == 500)
            throw new IOException("Не найдено " + response.getBodyJson());
    }

    public void put(SimpleNote simpleNote) throws IOException {
        String json = new Gson().toJson(simpleNote);
        SimpleResponse response = notebookApi.put("simple-note/", json);

        if (response.getCode() == 500)
            throw new IOException("Не найдено " + response.getMessage());
    }

    public void delete(Integer id) throws IOException {
        SimpleResponse response = notebookApi.delete("simple-note/", id.toString());

        if (response.getCode() == 500)
            throw new IOException("Не найдено " + response.getMessage());
    }
}
