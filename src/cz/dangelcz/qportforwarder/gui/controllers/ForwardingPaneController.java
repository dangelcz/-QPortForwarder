package cz.dangelcz.qportforwarder.gui.controllers;

import cz.dangelcz.qportforwarder.config.AppConfig;
import cz.dangelcz.qportforwarder.gui.WindowApplication;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForwardingPaneController implements Initializable
{
	@FXML
	private VBox lineContainer;

	@FXML
	private BorderPane rootPane;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		toggleDarkMode();

		for (int i = 0; i < AppConfig.DEFAULT_LINE_COUNT; i++)
		{
			addLinePanel();
		}
	}

	private void addLinePanel()
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(ForwardingLineController.class.getResource("/forwarding_line.fxml"));
			Node component = fxmlLoader.load();
			AnchorPane anchor = new AnchorPane(component);
			AnchorPane.setLeftAnchor(anchor, 0d);
			AnchorPane.setRightAnchor(anchor, 0d);
			lineContainer.getChildren().add(anchor);
		}
		catch (Exception e)
		{
			//TODO logger
			e.printStackTrace();
		}
	}

	public void onAddButtonClick()
	{
		addLinePanel();
	}

	private void toggleDarkMode()
	{
		String cssFile = "/dark_theme.css";

		ObservableList<String> stylesheets = rootPane.getStylesheets();

		if (stylesheets.contains(cssFile))
		{
			stylesheets.remove(cssFile);
		}
		else
		{
			stylesheets.add(cssFile);
		}

	}

	public void onDarkModeClick(ActionEvent actionEvent)
	{
		toggleDarkMode();
	}
}
