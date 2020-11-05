package com.docentes.jaxws.repository;

import java.sql.Connection;
import java.sql.SQLException;


import com.zaxxer.hikari.*;

public class DataSource {
	//private static String configFile = "../src/main/resources/db.properties";
//    private static HikariConfig config = new HikariConfig(configFile);
	private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
 
    static {
    	//sm9j2j5q6c8bpgyq.cbetxkdyhwsb.us-east-1.rds.amazonaws.com
        /* EN SERVER
    	config.setJdbcUrl( "jdbc:mysql://sm9j2j5q6c8bpgyq.cbetxkdyhwsb.us-east-1.rds.amazonaws.com/pod3i2eblsvv0rz1?reconnect=true" );
        config.setUsername( "lqo0ahe9urejc5cw" );
        config.setPassword( "yznskdvuv9lj45on" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
        */
    	//LOCAL
    	String jdbcConnectionString = System.getenv("JDBC_DATABASE_URL");
    	String jdbcPassword = System.getenv("JDBC_PASSWORD");
    	String jdbcUser = System.getenv("JDBC_USER");    	
    	
    	config.setMaximumPoolSize(5);
    	config.setJdbcUrl( jdbcConnectionString );
        config.setUsername( jdbcUser );
        config.setPassword( jdbcPassword );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        
        
   
        ds = new HikariDataSource( config );

    }
 
    private DataSource() {}
 
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

	public static void closeConnection() {
		ds.close();
		
	}
	
	public static HikariDataSource getDataSource() {
		return ds;
		
	}
	
}