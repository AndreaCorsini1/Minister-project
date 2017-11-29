package applicazione;

import java.sql.Connection;
import java.sql.DriverManager;

//Questa classe crea la connessione al database
public class SQLliteConnection{
    
	private Connection connection;
    
	public SQLliteConnection(String database){
    	try {
    		Class.forName("org.sqlite.JDBC");
    		connection = DriverManager.getConnection("jdbc:sqlite:" + database);
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    		System.exit(0);
    	}
    	System.out.println("Opened database "+ database + " successfully");
  }
    public Connection getConnection() {
		return connection;
	}
}
