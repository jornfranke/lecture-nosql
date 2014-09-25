/**
 * Importing the log files from http://ita.ee.lbl.gov/html/contrib/NASA-HTTP.html
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

import com.datastax.driver.core.Session;

/**
 * @author Jörn Franke <jornfranke@gmail.com>
 *
 */
public class ImportHelper {
	private String convertDateTime(String sourceStr) {
		 SimpleDateFormat sourceFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
		 SimpleDateFormat destinationFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
	
		 try {
			Date realDate = sourceFormat.parse(sourceStr);
			
			return destinationFormat.format(realDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "";
	}
	
	 /*
	    * Imports a log file into cassandra
	    * @param file file to import
	    */
	   
	   public void importLog(String filename, Session session, String keyspace, String table) {
		   File importfile = new File(filename);
		   BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader (importfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  String line;
		  try {
			while ((line= br.readLine()) !=null) {
				// Example for a line we need to parse
				//  String line ="199.72.81.55 - - [01/Jul/1995:00:00:01 -0400] \"GET /history/apollo/ HTTP/1.0\" 200 6245";
				   int destinationipStart=0;
					  int destinationipEnd =line.indexOf(" ");
					  String destinationip=line.substring(destinationipStart,destinationipEnd);
					  //System.out.println(destinationip);
					  int sourceDateStart = line.indexOf("[",destinationipEnd)+1;
					  int sourceDateEnd = line.indexOf("]",sourceDateStart);
					  String sourceDate=line.substring(sourceDateStart,sourceDateEnd);
					  //System.out.println(sourceDate);
					  int quoteStart = line.indexOf("\"")+1;
					  int quoteEnd = line.indexOf("\"",quoteStart);
					  String request=line.substring(quoteStart,quoteEnd);
					  //System.out.println(request);
					  int httpCodeStart=quoteEnd+2;
					  int httpCodeEnd=line.indexOf(" ",httpCodeStart);
					  String httpCode=line.substring(httpCodeStart,httpCodeEnd);
					  try {
						  Integer.parseInt(httpCode);
					  } catch  (NumberFormatException e) {
						  httpCode="0";
					  }
					  //System.out.println(httpCode);
					  int sizeStart=httpCodeEnd+1;
					  String size=line.substring(sizeStart);
					  try {
						  Integer.parseInt(size);
					  } catch  (NumberFormatException e) {
						  size="0";
					  }
					  //System.out.println(size);
					  // create cassandra insert request
					  String execInsert = "insert into "+keyspace+"."+table+"(ident,datetime,destination,httpcode,size,url) VALUES (now(),'"+convertDateTime(sourceDate)+"','"+destinationip+"',"+httpCode+","+size+","+"'"+request+"');";
					 // System.out.println(execInsert);
					  session.execute(execInsert);
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
}
