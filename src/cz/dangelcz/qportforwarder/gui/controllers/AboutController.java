package cz.dangelcz.qportforwarder.gui.controllers;

import cz.dangelcz.qportforwarder.config.AppConfig;
import cz.dangelcz.qportforwarder.gui.ApplicationResources;
import cz.dangelcz.qportforwarder.gui.WindowApplication;
import cz.dangelcz.qportforwarder.libs.GeneralHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable
{
	private static Logger logger = LogManager.getLogger(AboutController.class);

	@FXML
	private Label versionLabel;

	@FXML
	private ImageView appLogo;

	@FXML
	private Hyperlink githubClick;

	@FXML
	private AnchorPane root;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		versionLabel.setText("Version: " + GeneralHelper.getPomXmlVersion());
		appLogo.setImage(ApplicationResources.ICON_64);
	}

	@FXML
	public void linkClicked(ActionEvent event)
	{
		String url = AppConfig.REPOSITORY_URL;
		try
		{
			java.awt.Desktop.getDesktop().browse(new URI(url));
		}
		catch (Exception e)
		{
			logger.error(e);
		}
	}

	public void onCloseClick(ActionEvent event)
	{
		Node source = (Node) event.getSource();
		Stage parentStage = (Stage) source.getScene().getWindow();
		parentStage.close();
	}
}
