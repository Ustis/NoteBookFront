package ustis.notebookfront.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ustis.notebookfront.dao.Page;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PageApi {
    private NotebookApi notebookApi = NotebookApi.getInstance();

    public List<Page> getAll() throws IOException {
        SimpleResponse response = notebookApi.get("page/");

        if(response.getCode() == 500)
            throw new IOException("Не найдено");

        Type pageType = new TypeToken<ArrayList<Page>>() {
        }.getType();

        return new Gson().fromJson(response.getBodyJson(), pageType);
    }

    public void post(Page page) throws IOException {
        String json = new Gson().toJson(page);
        SimpleResponse response = notebookApi.post("page/", json);

        if(response.getCode() == 500)
            throw new IOException("Не найдено " + response.getMessage());
    }

    public void put(Page page) throws IOException {
        String json = new Gson().toJson(page);
        SimpleResponse response = notebookApi.put("page/", json);

        if (response.getCode() == 500)
            throw new IOException("Не найдено " + response.getCode());
    }

    public void delete(Integer id) throws IOException {
        SimpleResponse response = notebookApi.delete("page/", id.toString());

        if (response.getCode() == 500)
            throw new IOException("Не найдено " + response.getMessage());
    }
}
