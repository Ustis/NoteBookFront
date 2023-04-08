package ustis.notebookfront.api;

public class SimpleResponse {
    private String bodyJson;

    private String message = "";
    private Integer code;

    public SimpleResponse() {
    }

    public SimpleResponse(String bodyJson) {
        this.bodyJson = bodyJson;
    }

    public SimpleResponse(String bodyJson, Integer code) {
        this.bodyJson = bodyJson;
        this.code = code;
    }

    public SimpleResponse(String bodyJson, String message, Integer code) {
        this.bodyJson = bodyJson;
        this.message = message;
        this.code = code;
    }

    public String getBodyJson() {
        return bodyJson;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return "SimpleResponse{" +
                "bodyJson='" + bodyJson + '\'' +
                ", code=" + code +
                '}';
    }
}
