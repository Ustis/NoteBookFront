package ustis.notebookfront.dao;

public class SimpleNote {
    private Integer id;

    private String text;

    private Integer pageId;

    final String type = "Simple note";

    public SimpleNote(Integer id, String text, Integer pageId) {
        this.id = id;
        this.text = text;
        this.pageId = pageId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getType() { return type; }

    @Override
    public String toString() {
        return "SimpleNote{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", page_id=" + pageId +
                '}';
    }
}
