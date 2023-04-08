package ustis.notebookfront.api;

import okhttp3.*;

import java.io.IOException;

public class NotebookApi {
    private final OkHttpClient client = new OkHttpClient.Builder()
            .build();

    private final String baseUrl = "http://localhost:8080/";
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private NotebookApi() {
    }

    private static class NotebookApiHolder {
        public static final NotebookApi INSTANCE = new NotebookApi();
    }

    public static NotebookApi getInstance() {
        return NotebookApiHolder.INSTANCE;
    }

    public SimpleResponse execute(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Запрос к серверу не был успешен: " +
                        response.code() + " " + response.message());
            }
            return new SimpleResponse(response.body().string(), response.message(), response.code());
        } catch (IOException e) {
            throw e;
        }
    }

    public SimpleResponse get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .build();

        return execute(request);
    }

    public SimpleResponse get(String url, String pathVariable) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + url + pathVariable)
                .build();

        return execute(request);
    }

    public SimpleResponse post(String url, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(baseUrl + url)
                .post(body)
                .build();

        return execute(request);
    }

    public SimpleResponse put(String url, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(baseUrl + url)
                .put(body)
                .build();

        return execute(request);
    }

    public SimpleResponse delete(String url, String pathVariable) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + url + pathVariable)
                .delete()
                .build();

        return execute(request);
    }
}
