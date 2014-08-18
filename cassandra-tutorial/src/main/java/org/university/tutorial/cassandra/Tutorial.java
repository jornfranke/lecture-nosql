/**
 * Simple Tutorial for Accessing Apache Cassandra using Java
 */
package org.university.tutorial.cassandra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * @author Jörn Franke <jornfranke@gmail.com>
 *
 */
public class Tutorial {
	
	public Cluster cluster;
	public Session session;
	


	/*
	 * Connect to a Cassandra cluster
	 * @param node any node in the cluster
	 */
	
	   public void connect(String node) {
	      cluster = Cluster.builder()
	            .addContactPoint(node).build();
	      Metadata metadata = cluster.getMetadata();
	      System.out.printf("Connected to cluster: %s\n", 
	            metadata.getClusterName());
	      for ( Host host : metadata.getAllHosts() ) {
	         System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
	               host.getDatacenter(), host.getAddress(), host.getRack());
	      }
	      session=cluster.connect();
	   }
	   
	   /*
	    * Simple Query with Cassandra
	    * performas a query and prints the column "destination" from the result set
	    */
	  public void query1() {
		  String query = "SELECT * FROM logtest.logdata";
		  ResultSet queryresult=session.execute(query);
		  Iterator queryresultIterator = queryresult.iterator();
		  while (queryresultIterator.hasNext())  {
			  Row currentRow = (Row) queryresultIterator.next();
			  System.out.println(currentRow.getString("destination"));
		  }
	  }
	   
	   /*
	    * Closes connection to Cassandra cluster
	    */

	   public void close() {
		   
	      cluster.close();
	   }

	/**
	 * @param args none
	 */
	public static void main(String[] args) {
		System.out.println("Cassandra Tutorial");
		 Tutorial client = new Tutorial();
		
		 client.connect("localhost");
		/*** import some log file, used http://ita.ee.lbl.gov/html/contrib/NASA-HTTP.html
			ImportHelper myImportHelper = new ImportHelper();
		 	myImportHelper.importLog("access_log_Jul95",client.session,"logtest","logfull");
		**/
		 client.query1();
		 client.close();
	}

}
