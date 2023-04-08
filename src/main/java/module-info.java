module ustis.notebookfront {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires okhttp3;
    requires annotations;
    requires com.google.gson;

    opens ustis.notebookfront to javafx.fxml;
    exports ustis.notebookfront;
    exports ustis.notebookfront.api;
    opens ustis.notebookfront.api to javafx.fxml;
    exports ustis.notebookfront.dao;
    opens ustis.notebookfront.dao to com.google.gson;
}