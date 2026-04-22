package kdg.view.aboutscherm;

import javafx.stage.Stage;

/**
 * @author Farok
 * @version 1.0 20/04/2026
 */
public class AboutschermPresenter {

    private final AboutschermView view;

    public AboutschermPresenter(AboutschermView view) {
        this.view = view;
        this.addEventHandlers();
    }

    private void addEventHandlers() {
        view.getSluitKnop().setOnAction(e -> {
            Stage stage = (Stage) view.getScene().getWindow();
            stage.close();
        });
    }
}
