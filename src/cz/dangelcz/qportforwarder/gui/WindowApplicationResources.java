package cz.dangelcz.qportforwarder.gui;

import javafx.scene.image.Image;

import java.net.URL;

public class WindowApplicationResources
{
	public static final Image ICON_64 = new Image(WindowApplicationResources.class.getResourceAsStream("/img/app_icon_64.png"));
	public static final Image ICON_32 = new Image(WindowApplicationResources.class.getResourceAsStream("/img/app_icon_32.png"));
	public static final Image ICON_16 = new Image(WindowApplicationResources.class.getResourceAsStream("/img/app_icon_16.png"));
	public static final URL ABOUT_FXML = WindowApplicationResources.class.getResource("/fxml/about.fxml");
	public static final URL FORWARDING_LINE_FXML = WindowApplicationResources.class.getResource("/fxml/forwarding_line_grid.fxml");
	public static final URL FORWARDING_PANE_FXML = WindowApplicationResources.class.getResource("/fxml/forwarding_pane.fxml");

	public static final String DARK_MODE_CSS_FILE = "/dark_theme.css";


}
