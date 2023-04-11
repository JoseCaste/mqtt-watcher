package com.mqtt.watcher.singleton;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class JdbcConnection {

	private static Connection connection;
	private static Properties properties;
	private static JdbcConnection jdbcConnection;
	
	static {
		properties = new Properties();
		try {
			properties.load(JdbcConnection.class.getClassLoader().getResourceAsStream("application.properties"));
			connection = DriverManager.getConnection(properties.getProperty("jdbc.postgresql"));
			System.out.println("database connnected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JdbcConnection getJdbcConection() {
		if(Objects.isNull(jdbcConnection)) {
			return new JdbcConnection();
		}
		
		return jdbcConnection;
	}
	
	public void saveValueOntoDatabase(final String topic, final String value) throws SQLException {
		final String query = "INSERT INTO public.topics(topic, value) values (?,?)";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, topic);
		ps.setString(2, value);
		ps.executeUpdate();
	}
}
