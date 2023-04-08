package ustis.notebookfront.dao;

public class Page {

    private Integer id;

    private String name;

    private String noteType;

    public Page() {
    }

    public Page(String name) {
        this.name = name;
    }

    public Page(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Page(String name, String noteType) {
        this.name = name;
        this.noteType = noteType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    @Override
    public String toString() {
        return name;
    }
}
