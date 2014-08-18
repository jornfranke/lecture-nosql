/**
 * Simple Tutorial for key/value store redis
 */
package org.university.tutorial.redis;

import redis.clients.jedis.Jedis;

/**
 * @author Jörn Franke <jornfranke@gmail.com>
 *
 */
public class Tutorial {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Connect to REDIS
		Jedis redisServer = new Jedis("localhost");
		// Set key "foo" to value "bar"
		redisServer.set("foo","bar");
		// print value of key "foo"
		System.out.println(redisServer.get("foo"));
		// never forget to close the connection
		redisServer.close();

	}

}
