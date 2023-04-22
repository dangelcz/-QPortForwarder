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
		setInfoStatus("Idle");
	}


	private void start()
	{
		ForwardingParameters parameters = getParameters();
		resetForwarder(parameters);

		setSuccessStatus("Running");
	}

	private boolean validate()
	{
		refreshSpinnerValues();

		if (GeneralHelper.INL(localIpTextField.getText()))
		{
			setErrorStatus("Empty local ip");
			return false;
		}

		if (localPortSpinner.getValue() == null || localPortSpinner.getValue() == 0)
		{
			setErrorStatus("Not valid local port");
			return false;
		}

		if (GeneralHelper.INL(targetIpTextField.getText()))
		{
			setErrorStatus("Empty target ip");
			return false;
		}

		if (targetPortSpinner.getValue() == null || targetPortSpinner.getValue() == 0)
		{
			setErrorStatus("Not valid target port");
			return false;
		}

		return true;
	}

	public void refreshSpinnerValues()
	{
		//force commit changes between spinner view and model - it is not auto :-(
		localPortSpinner.increment(0);
		targetPortSpinner.increment(0);
	}

	private void resetForwarder(ForwardingParameters parameters)
	{
		forwarder.resetParameters(parameters);
		forwarder.startForwarding();
	}

	public ForwardingParameters getParameters()
	{
		refreshSpinnerValues();

		ForwardingParameters parameters = new ForwardingParameters();
		parameters.setLocalIp(localIpTextField.getText());
		parameters.setLocalPort(localPortSpinner.getValue());
		parameters.setTargetIp(targetIpTextField.getText());
		parameters.setTargetPort(targetPortSpinner.getValue());
		parameters.setComment(commentTextField.getText());

		return parameters;
	}

	public void setParameters(ForwardingParameters parameters)
	{
		if (parameters == null)
		{
			return;
		}

		localIpTextField.setText(parameters.getLocalIp());
		localPortSpinner.getValueFactory().setValue(parameters.getLocalPort());
		targetIpTextField.setText(parameters.getTargetIp());
		targetPortSpinner.getValueFactory().setValue(parameters.getTargetPort());
		commentTextField.setText(parameters.getComment());

		refreshSpinnerValues();
	}

	private void setInfoStatus(String message)
	{
		setStatus(message, Color.GRAY);
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
		statusLabel.setText(message);
		statusLabel.setTextFill(color);
	}
}
