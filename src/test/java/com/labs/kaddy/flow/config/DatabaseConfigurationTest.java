/**
 * 
 */
package com.labs.kaddy.flow.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.labs.kaddy.flow.system.config.DatabaseConfiguration;
import com.labs.kaddy.flow.system.config.DatabaseProperties;
/**
 * @author Surjyakanta
 *
 */
public class DatabaseConfigurationTest {

	@Mock
	private DatabaseProperties props = mock(DatabaseProperties.class);
	
	@InjectMocks
	private DatabaseConfiguration config = new DatabaseConfiguration(props);
	
	@Before
	public void setup() {
		//MockitoAnnotations.initMocks(this);
		//config = new DatabaseConfiguration(props);
	}
	
	@Ignore
	@Test
	public void testDataSource() {
		when(props.getDbFilePath()).thenReturn("run/secrets/kaddyflow-database.properties-demo");
		when(props.getDriverClassName()).thenReturn("com.mysql.cj.jdbc.Drive");
		DataSource ds = config.dataSource();
		Assertions.assertNotEquals("HikariDataSource", ds.toString());
	}
	
	@Test
	public void testDataSource_Exception() {
		when(props.getDbFilePath()).thenReturn("run/secrets/kaddyflow-database.-demo");
		Assertions.assertThrows(RuntimeException.class, () -> {
			DataSource ds = config.dataSource();
		});
	}
}
