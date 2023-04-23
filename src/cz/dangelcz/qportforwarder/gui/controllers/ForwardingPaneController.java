package cz.dangelcz.qportforwarder.gui.controllers;

import com.fasterxml.jackson.jr.ob.JSON;
import cz.dangelcz.qportforwarder.config.AppConfig;
import cz.dangelcz.qportforwarder.data.ApplicationConfigData;
import cz.dangelcz.qportforwarder.data.ForwardingParameters;
import cz.dangelcz.qportforwarder.libs.IoHelper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ForwardingPaneController implements Initializable
{
	private static Logger logger = LogManager.getLogger(ForwardingPaneController.class);

	private static final String SESSION_FILE_NAME = "session.json";

	@FXML
	private VBox lineContainer;

	@FXML
	private BorderPane rootPane;

	@FXML
	private CheckMenuItem darkModeMenu;

	private ObservableList<ForwardingLineController> rows;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		updateDarkMode();

		rows = FXCollections.observableArrayList();

		if (IoHelper.fileExists(SESSION_FILE_NAME))
		{
			loadSession();
		}
		else
		{
			newSession();
		}
	}

	private void newSession()
	{
		logger.info("Creating new session");

		deleteRows();

		for (int i = 0; i < AppConfig.DEFAULT_LINE_COUNT; i++)
		{
			addLinePanel();
		}
	}

	private void addLinePanel()
	{
		addLinePanel(null);
	}

	private void addLinePanel(ForwardingParameters parameters)
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(ForwardingLineController.class.getResource("/forwarding_line_grid.fxml"));
			Node component = fxmlLoader.load();
			ForwardingLineController controller = fxmlLoader.getController();
			controller.setParameters(parameters);

			rows.add(controller);

			lineContainer.getChildren().add(component);
		}
		catch (Exception e)
		{
			logger.error(e);
		}
	}

	public void onAddButtonClick()
	{
		addLinePanel();
	}

	private void updateDarkMode()
	{
		String cssFile = "/dark_theme.css";

		ObservableList<String> stylesheets = rootPane.getStylesheets();

		if (darkModeMenu.isSelected() && !stylesheets.contains(cssFile))
		{
			stylesheets.add(cssFile);
			return;
		}

		if (!darkModeMenu.isSelected() && stylesheets.contains(cssFile))
		{
			stylesheets.remove(cssFile);
		}
	}

	public void onDarkModeClick(ActionEvent actionEvent)
	{
		updateDarkMode();
	}


	public void onLoadClick(ActionEvent actionEvent)
	{
		loadSession();
	}

	private void loadSession()
	{
		deleteRows();

		String name = SESSION_FILE_NAME;
		String data = IoHelper.loadTextFile(name);
		try
		{
			ApplicationConfigData sessionData = JSON.std.beanFrom(ApplicationConfigData.class, data);
			List<ForwardingParameters> parameters = sessionData.getParameters();
			parameters.forEach(p -> addLinePanel(p));
			darkModeMenu.setSelected(sessionData.isDarkMode());
			updateDarkMode();
		}
		catch (IOException e)
		{
			logger.error("Error while loading session", e);
			newSession();
		}
	}

	private void deleteRows()
	{
		rows.clear();
		ObservableList<Node> children = lineContainer.getChildren();
		children.remove(1, children.size());
	}

	public void onSaveClick(ActionEvent actionEvent)
	{
		saveSession();
	}

	//TODO change to getManager().saveSession()
	public void saveSession()
	{
		List<ForwardingParameters> parameters = new ArrayList<>();
		rows.forEach(r -> parameters.add(r.getParameters()));

		ApplicationConfigData sessionData = new ApplicationConfigData();
		sessionData.setParameters(parameters);
		sessionData.setDarkMode(darkModeMenu.isSelected());

		try
		{
			String name = SESSION_FILE_NAME;
			String data = JSON.std.asString(sessionData);
			IoHelper.saveTextFile(name, data, true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void onSaveAsClick(ActionEvent actionEvent)
	{
		//TODO
	}

	public void onCloseClick(ActionEvent actionEvent)
	{
		closeApplication();
	}

	public void closeApplication()
	{
		saveSession();
		Platform.exit();
		System.exit(0);
	}

	public void onAboutClick(ActionEvent actionEvent)
	{
		// TODO
	}

	public void onClearClick(ActionEvent actionEvent)
	{
		newSession();
	}
}
