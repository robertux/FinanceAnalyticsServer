package org.robertux.financeAnalytics.FinanceAnalyticsServer.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataSourceConfigurator {
	public static final String DRIVER_PROP = "jdbc.connection.driver";
	public static final String URL_PROP = "jdbc.connection.url";
	public static final String USER_PROP = "jdbc.connection.user";
	public static final String PASSWORD_PROP = "jdbc.connection.password";
	public static final String CONF_FILE_PROP = "jdbc.file.path";
	private String driver;
	private String url;
	private String user;
	private String password;
	private Logger logger = LogManager.getLogger(this.getClass());
	
	public DataSourceConfigurator(String driver, String url, String user, String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	public DataSourceConfigurator(String propsFile) {
		Properties props = this.loadPropsFile(propsFile);
		if (props != null) {
			this.driver = props.getProperty(DRIVER_PROP);
			this.url = props.getProperty(URL_PROP);
			this.user = props.getProperty(USER_PROP);
			this.password = props.getProperty(PASSWORD_PROP);
		} else {
			this.logger.error("Archivo de propiedades inválido");
		}
	}
	
	public DataSourceConfigurator() {
		this(System.getProperty(CONF_FILE_PROP));
		
		if (System.getProperty(CONF_FILE_PROP) == null) {
			this.logger.error("No se ha definido la propiedad {}", CONF_FILE_PROP);
		}
	}
	
	protected Properties loadPropsFile(String propsFile) {
		if (propsFile == null) {
			this.logger.error("Ruta del archivo es nula");
			return null;
		}
		
		File f = new File(propsFile);
		if (!f.exists() || !f.isFile() || !f.canRead()) {
			this.logger.error("Archivo {} no existe o no puede ser leido", propsFile);
			return null;
		}
		
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(f));
		} catch (IOException e) {
			this.logger.error("Archivo {} no pudo ser leido como Properties", propsFile);
			return null;
		}
		
		if (!props.containsKey(DRIVER_PROP) || !props.containsKey(URL_PROP) || !props.containsKey(USER_PROP) || !props.containsKey(PASSWORD_PROP)) {
			this.logger.error("Archivo {} no contiene una o varias de las propiedades de conexión", propsFile);
			return null;
		}
		return props;
	}
	
	public Connection getConnection() {
		try {
		Class.forName(this.driver);
			return DriverManager.getConnection(this.url, this.user, this.password);
		} catch (ClassNotFoundException | SQLException ex) {
			logger.error("Error conectando a la base de datos con la URL {} y el usuario {}", this.url, this.user);
			return null;
		}
	}

}
