package zenrus.com.container.report;

import java.sql.Connection;
import java.sql.DriverManager;

public class Report {

	public static void main(String[] args) {
		try( Connection con =  DriverManager.getConnection("jdbc:hsqldb:file:db/brw", "SA", "") ){
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
