/**
 * Simple NoSQL tutorial for connecting to MongoDB
 */
package org.university.tutorial.mongodb;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * @author Jörn Franke <jornfranke@gmail.com>
 *
 */
public class Tutorial {
	MongoClient mongoClient;
	DB mongoDB;
	DBCollection mongoCollection;
	
	/*
	 * Connect to a mongodb server
	 */
	public void connect(String host) {
		
			 mongoClient = new MongoClient(host);
	}
	
	/* select database */
	public void setDB(String db) {
		mongoDB = mongoClient.getDB(db);
	}
	
	/* select collection */
	public void setCollection(String collection) {
		mongoCollection = mongoDB.getCollection(collection);
	}
	
	/* insert a document into selected collection */
	public void insertDoc(BasicDBObject doc) {
		mongoCollection.insert(doc);
	}
	
	/*
	 * Close connection
	 */
	public void close() {
		mongoClient.close();
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("MongoDB Tutorial");
		Tutorial client = new Tutorial();
		// connect to localhost
		client.connect("localhost");
		// choose a database
		client.setDB("test");
		// choose a collection
		client.setCollection("test");
		// create a sample document with two fields
		BasicDBObject doc = new BasicDBObject("name", "mustermann").append("firstname", "max");
		client.insertDoc(doc);
		// never forget to close the connection
		client.close();

	}

}
