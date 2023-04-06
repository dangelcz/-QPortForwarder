package cz.dangelcz.qportforwarder.gui.controllers;

import cz.dangelcz.qportforwarder.data.ForwardingParameters;
import cz.dangelcz.qportforwarder.libs.GeneralHelper;
import cz.dangelcz.qportforwarder.logic.TcpForwarder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;


public class ForwardingLineController implements Initializable
{
	@FXML
	private TextField commentTextField;

	@FXML
	private TextField localIpTextField;

	@FXML
	private Spinner<Integer> localPortSpinner;

	@FXML
	private ToggleButton startForwardingButton;

	@FXML
	private Label statusLabel;

	@FXML
	private TextField targetIpTextField;

	@FXML
	private Spinner<Integer> targetPortSpinner;

	private TcpForwarder forwarder;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		forwarder = new TcpForwarder();

		localPortSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 65535, 0));
		targetPortSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 65535, 0));
	}

	@FXML
	protected void onStartButtonClick()
	{
		if (startForwardingButton.isSelected())
		{
			if (!validate())
			{
				startForwardingButton.setSelected(false);
				return;
			}

			start();
			startForwardingButton.setText("Stop");
		}
		else
		{
			stop();
			startForwardingButton.setText("Start");
		}
	}

	private void stop()
	{
		forwarder.stopForwarding();
		setInfoStatus("Stopped");
	}


	private void start()
	{
		ForwardingParameters parameters = getParameters();
		resetForwarder(parameters);

		setSuccessStatus("Running");
	}

	private boolean validate()
	{
		//force commit changes between spinner view and model - it is not auto :-(
		localPortSpinner.increment(0);
		targetPortSpinner.increment(0);

		if (!GeneralHelper.isIp(localIpTextField.getText()))
		{
			setErrorStatus("Not valid local ip");
			return false;
		}

		if (localPortSpinner.getValue() == null)
		{
			setErrorStatus("Not valid local port");
			return false;
		}

		if (!GeneralHelper.isIp(targetIpTextField.getText()))
		{
			setErrorStatus("Not valid target ip");
			return false;
		}

		if (targetPortSpinner.getValue() == null)
		{
			setErrorStatus("Not valid target port");
			return false;
		}

		return true;
	}

	private void resetForwarder(ForwardingParameters parameters)
	{
		forwarder.resetParameters(parameters);
		forwarder.startForwarding();
	}

	private ForwardingParameters getParameters()
	{
		ForwardingParameters parameters = new ForwardingParameters();
		parameters.setLocalIp(localIpTextField.getText());
		parameters.setLocalPort(localPortSpinner.getValue());
		parameters.setTargetIp(targetIpTextField.getText());
		parameters.setTargetPort(targetPortSpinner.getValue());

		return parameters;
	}


	private void setInfoStatus(String message)
	{
		setStatus(message, Color.BLACK);
	}

	private void setSuccessStatus(String message)
	{
		setStatus(message, Color.GREEN);
	}

	private void setErrorStatus(String message)
	{
		setStatus(message, Color.RED);
	}

	public void setStatus(String message, Color color)
	{
		statusLabel.setText("Status: " + message);
		statusLabel.setTextFill(color);
	}

}
