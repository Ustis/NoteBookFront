package ustis.notebookfront;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ustis.notebookfront.controls.SimplePopupDialog;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class NoteBookApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Thread.setDefaultUncaughtExceptionHandler(NoteBookApplication::showError);

        FXMLLoader fxmlLoader = new FXMLLoader(NoteBookApplication.class.getResource("notebook-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setMaximized(true);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        StateManager.getInstance().setMainStage(stage);
    }

    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        System.err.println("An unexpected error occurred in " + e.toString());
        showErrorDialog(e);
    }

    private static void showErrorDialog(Throwable e) {
        StringWriter errorMsg = new StringWriter();
        e.printStackTrace(new PrintWriter(errorMsg));

        new SimplePopupDialog().show(StateManager.getInstance().getMainStage(),
                new Exception(e.getCause().getCause()).getMessage());
    }

    public static void main(String[] args) {
        launch();
    }
}