package cz.dangelcz.qportforwarder.launch.modules;

import cz.dangelcz.qportforwarder.gui.WindowApplication;
import cz.dangelcz.qportforwarder.launch.ARunModule;
import cz.dangelcz.qportforwarder.launch.ArgumentReader;
import cz.dangelcz.qportforwarder.launch.annotation.RunModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

@RunModule(value = "start-gui", isDefault = true)
public class StartGui extends ARunModule
{
	@Override
	public void run(ArgumentReader arguments)
	{
		WindowApplication.launch(WindowApplication.class);
		//try
		//{
		//	FXMLLoader fxmlLoader = new FXMLLoader(WindowApplication.class.getResource("/forwarding_line.fxml"));
//
		//	Scene scene = new Scene(fxmlLoader.load(), 640,480);
//
		//	Stage stage = new Stage();
		//	stage.setTitle("QPortForwarder v1.0.0");
		//	stage.setScene(scene);
		//	stage.show();
		//}
		//catch (IOException e)
		//{
		//	throw new RuntimeException(e);
		//}
	}

	@Override
	public String getRequiredParameters()
	{
		return "";
	}

	@Override
	public String getOptionalParameters()
	{
		return "";
	}

	@Override
	public String getHelpDescription()
	{
		return "Starts application with graphic user interface. This is default command.";
	}
}
