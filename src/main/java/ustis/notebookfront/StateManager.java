package ustis.notebookfront;

import javafx.stage.Stage;

public class StateManager {

    private Stage mainStage;

    private StateManager() {
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private static class StateManagerHolder {
        public static final StateManager INSTANCE = new StateManager();
    }

    public static StateManager getInstance() {
        return StateManagerHolder.INSTANCE;
    }
}
