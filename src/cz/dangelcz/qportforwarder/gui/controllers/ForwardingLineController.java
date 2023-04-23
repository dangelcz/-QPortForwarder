package cz.dangelcz.qportforwarder.gui.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import cz.dangelcz.qportforwarder.data.ForwardingParameters;
import cz.dangelcz.qportforwarder.libs.GeneralHelper;
import cz.dangelcz.qportforwarder.libs.NetHelper;
import cz.dangelcz.qportforwarder.logic.TcpForwarder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ForwardingLineController implements Initializable
{
	@FXML
	private TextField commentTextField;

	@FXML
	private ComboBox<String> localIpCombo;

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

	private static List<String> localAddresses;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		forwarder = new TcpForwarder();

		localPortSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 65535, 0));
		targetPortSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 65535, 0));

		if (localAddresses == null)
		{
			localAddresses = NetHelper.getLocalAddresses();
			localAddresses.add(NetHelper.getLocalHostname());
			localAddresses.add("0.0.0.0");
		}

		localIpCombo.setItems(new ObservableListWrapper<>(localAddresses));
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
		enableLine(true);
		setInfoStatus("Idle");
	}

	private void start()
	{
		ForwardingParameters parameters = getParameters();
		resetForwarder(parameters);
		enableLine(false);
		setSuccessStatus("Running");
	}

	private void enableLine(boolean enabled)
	{
		localIpCombo.setDisable(!enabled);
		localPortSpinner.setDisable(!enabled);
		targetIpTextField.setDisable(!enabled);
		targetPortSpinner.setDisable(!enabled);
		commentTextField.setDisable(!enabled);
	}

	private boolean validate()
	{
		refreshSpinnerValues();

		if (GeneralHelper.INL(localIpCombo.getValue()))
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
		parameters.setLocalIp(localIpCombo.getValue());
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

		localIpCombo.setValue(parameters.getLocalIp());
		localPortSpinner.getValueFactory().setValue(parameters.getLocalPort());
		targetIpTextField.setText(parameters.getTargetIp());
		targetPortSpinner.getValueFactory().setValue(parameters.getTargetPort());
		commentTextField.setText(parameters.getComment());

		refreshSpinnerValues();

		copyFromToAddress();
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

	private void copyFromToAddress()
	{
		if (GeneralHelper.INL(targetIpTextField.getText()))
		{
			targetIpTextField.setText(localIpCombo.getValue());
		}
	}

	public void comboAction(ActionEvent actionEvent)
	{
		copyFromToAddress();
	}

}
