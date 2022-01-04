package Application;
import javafx.application.Application;
import javafx.stage.Stage;

public class app extends Application{
	@Override
	public void start(Stage primaryStage) {
		try {
			SceneHandler.getInstance().init(primaryStage);
		} catch(Exception e) {			
		}
	}
	public static void main(String args[]) {
		launch(args);
	}
}
