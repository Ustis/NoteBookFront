package ustis.notebookfront.dao;

public class TodoNode {
    private Integer id;

    private String text;

    private Boolean status;
    private Integer pageId;
    private Integer parentId;

    final String type = "Todo note";

    public TodoNode() {
    }

    public TodoNode(String text, Boolean status) {
        this.text = text;
        this.status = status;
    }

    public TodoNode(String text, Boolean status, Integer parentId) {
        this.text = text;
        this.status = status;
        this.parentId = parentId;
    }

    public TodoNode(Integer id, String text, Boolean status, Integer parentId) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.parentId = parentId;
    }

    public TodoNode(Integer id, String text, Boolean status, Integer pageId, Integer parentId) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.pageId = pageId;
        this.parentId = parentId;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getType() { return type; }

    @Override
    public String toString() {
        return text;
    }
}
