package com.mqtt.watcher.singleton;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClientB {

	private static MqttClientB mqttClient;
	private static String topic = "dev/test";
	private static int qos = 2;
	private static String broker = "tcp://localhost:1883";
	private static String clientId = "JavaSample";
	private static MemoryPersistence persistence = new MemoryPersistence();
	private static MqttClient sampleClient;
	private static MqttConnectOptions connOpts = new MqttConnectOptions();
	
	private static Properties properties;
	static {
		properties = new Properties();
		try {
			properties.load(MqttClientB.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			sampleClient = new MqttClient(broker, clientId, persistence);
			connOpts.setUserName(properties.getProperty("mqtt.user"));
			connOpts.setPassword(properties.getProperty("mqtt.password").toCharArray());
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public MqttClientB() {

	}

	public static MqttClientB getMqttConection() {
		if(Objects.isNull(mqttClient)) {
			return new MqttClientB();
		}
		
		return mqttClient;
		
		
	}
	
	public void getMessage() throws MqttException {
		
		sampleClient.subscribeWithResponse(topic,qos, (topic, message) ->{
			JdbcConnection.getJdbcConection().saveValueOntoDatabase(topic, message.toString());
		});
	}
}
