package cz.dangelcz.qportforwarder.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AboutDialog extends Stage
{

	private static Logger logger = LogManager.getLogger(AboutDialog.class);

	public AboutDialog()
	{
		super();

		setTitle("About");
		getIcons().add(WindowApplicationResources.ICON_64);
		setResizable(false);
		initStyle(StageStyle.UTILITY);

		initScene();
	}

	private void initScene()
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(WindowApplicationResources.ABOUT_FXML);
			Scene scene = new Scene(fxmlLoader.load());
			setScene(scene);
		}
		catch (Exception e)
		{
			// log error
			logger.error(e);
		}
	}


}
