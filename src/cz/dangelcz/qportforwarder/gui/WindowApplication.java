package cz.dangelcz.qportforwarder.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowApplication extends Application
{
	@Override
	public void start(Stage stage) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(WindowApplication.class.getResource("/forwarding_pane.fxml"));

		Scene scene = new Scene(fxmlLoader.load(), 800,480);

		stage.setTitle("QPortForwarder v1.0.0");
		stage.setScene(scene);
		stage.show();
	}
}
