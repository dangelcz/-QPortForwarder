package cz.dangelcz.qportforwarder.gui;

import cz.dangelcz.qportforwarder.gui.controllers.ForwardingPaneController;
import cz.dangelcz.qportforwarder.libs.GeneralHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class WindowApplication extends Application
{
	private static Logger logger = LogManager.getLogger(WindowApplication.class);

	private ForwardingPaneController mainController;

	@Override
	public void start(Stage stage) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(ApplicationResources.FORWARDING_PANE_FXML);

		try
		{
			Scene scene = new Scene(fxmlLoader.load(), 800, 480);
			mainController = fxmlLoader.getController();

			String version = GeneralHelper.getPomXmlVersion();
			stage.setTitle("QPortForwarder " + version);

			stage.getIcons().addAll(ApplicationResources.ICON_64, ApplicationResources.ICON_32, ApplicationResources.ICON_16);

			stage.setOnCloseRequest(t -> {
				mainController.closeApplication();
			});

			stage.setScene(scene);
			stage.show();
		}
		catch (Exception e)
		{
			// log error
			logger.error(e);
			// rethrow error into framework
			throw e;
		}
	}
}
