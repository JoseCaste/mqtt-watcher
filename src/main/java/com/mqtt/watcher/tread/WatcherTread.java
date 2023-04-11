package com.mqtt.watcher.tread;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.mqtt.watcher.singleton.MqttClientB;

public class WatcherTread implements Runnable{

	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep((long) 1000.0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				MqttClientB.getMqttConection().getMessage();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
	}

}
