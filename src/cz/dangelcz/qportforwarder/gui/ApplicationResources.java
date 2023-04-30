package cz.dangelcz.qportforwarder.gui;

import javafx.scene.image.Image;

import java.net.URL;

public class ApplicationResources
{
	public static final Image ICON_64 = new Image(ApplicationResources.class.getResourceAsStream("/app_icon_64.png"));
	public static final Image ICON_32 = new Image(ApplicationResources.class.getResourceAsStream("/app_icon_32.png"));
	public static final Image ICON_16 = new Image(ApplicationResources.class.getResourceAsStream("/app_icon_16.png"));
	public static final URL ABOUT_FXML = ApplicationResources.class.getResource("/about.fxml");
	public static final URL FORWARDING_LINE_FXML = ApplicationResources.class.getResource("/forwarding_line_grid.fxml");
	public static final URL FORWARDING_PANE_FXML = ApplicationResources.class.getResource("/forwarding_pane.fxml");

	public static final String DARK_MODE_CSS_FILE = "/dark_theme.css";


}
