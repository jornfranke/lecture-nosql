/**
 * Simple Neo4J Tutorial
 */
package org.university.tutorial.neo4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.rest.graphdb.RestAPI;
import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.entity.RestNode;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

/**
 * @author Jörn Franke <jornfranke@gmail.com>
 *
 */
public class Tutorial {

	private RestAPI graphdb;
	private RestCypherQueryEngine engine;
	
	public void connect(String host) {
		graphdb=new RestAPIFacade(host);
		engine = new RestCypherQueryEngine(graphdb);
		
	}
	
	/** Simple query **/
	/** get the name of the actors playing in a movie starting with "The", such as "The Matrix **/
	public void query1() {
		System.out.println("Executing Query 1");
		QueryResult<Map<String,Object>> qresult=engine.query("start movie=node:Movie(title='The Matrix') match movie<- [:ACTS_IN] - actor\n return actor", Collections.EMPTY_MAP);
		Iterator<Map<String,Object>> queryResultIterator = qresult.iterator();
		while (queryResultIterator.hasNext()) {
			Map<String,Object> row = queryResultIterator.next();
			RestNode currentActor = (RestNode)row.get("actor");
			System.out.println(currentActor.getProperty("name"));
		}
 	}
	
	public void close() {
		graphdb.close();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Neo4j tutorial");
		Tutorial client = new Tutorial();
		// Connect to Neo4J server
		client.connect("http://localhost:7474/db/data");
		// perform a query based on the following dataset: http://example-data.neo4j.org/files/cineasts_12k_movies_50k_actors.zip?_ga=1.54322859.288818896.1408394321
		client.query1();
		// never forget to close connection
		client.close();
		
	}

}
