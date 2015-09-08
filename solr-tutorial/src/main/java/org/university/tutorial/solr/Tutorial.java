/**
 * Simple Solr Tutorial
 */
package org.university.tutorial.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 * @author Jörn Franke <jornfranke@gmail.com>
 *
 */
public class Tutorial {
	private HttpSolrServer server;
	
	/** Connect to server
	* @param host servername
	*
	*/
	public void connect(String host) {
		server = new HttpSolrServer(host);
	}
	
	/** add an example document **/
	public void addSampleDocument () {
		System.out.println("Adding sample document");
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("product_type","test");
		doc.addField("unique_id","book1");
		doc.addField("title","test");
		try {
			server.add(doc);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** perform a query for the aforementioned example document and print the result **/
	public void query1()  {
		System.out.println("Executing query 1");
		SolrQuery query = new SolrQuery();
		query.setQuery("title:test");
		
		query.setFields("id","title");
	
		QueryResponse response=null;
		try {
			 response = server.query(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SolrDocumentList results = response.getResults();
		System.out.println(results.size()+" results");
		for (int i=0; i<results.size();i++) {
			System.out.println(results.get(i));
		}
	}
	
	
	public void close() {
		server.shutdown();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Solr Tutorial");
		Tutorial client = new Tutorial();
		// connect to solr core "corereview"
		client.connect("http://localhost:8080/solr/corereview");
		// add an example document
		client.addSampleDocument();
		// perform query
		client.query1();
		// never forget to close connection
		client.close();
	}

}
