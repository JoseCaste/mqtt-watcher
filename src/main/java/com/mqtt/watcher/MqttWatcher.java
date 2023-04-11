package com.mqtt.watcher;

import com.mqtt.watcher.tread.WatcherTread;

public class MqttWatcher {

	public static void main(String[] args) {
		Thread thread = new Thread(new WatcherTread());
		thread.start();
	}

}
