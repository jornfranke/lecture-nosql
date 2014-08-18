lecture-nosql
=============

Example projects for using various NoSQL technologies

Building the applications
==============
You need to clone the whole repository by using the following command:

git clone https://github.com/jornfranke/lecture-nosql.git

You will need to install Gradle (http://www.gradle.org)

You can change to the folder lecture-nosql

Afterwards you can change into any of the subfolders to build the projects you are interested in
* cassandra-tutorial : Apache Cassandra tutorial (Column-oriented database)
* mongodb-tutorial : Mongo DB tutorial (Document database)
* neo4j-tutorial : Neo4J (Graph Database)
* redis-tutorial : Redis (Key/Value Store)
* solr-tutorial : Apache SolrCloud (Search technology)

Once you changed into the corresponding subfolder you can execute the following command to build the application:

gradle clean build

Running an application
================
You can use the following command to run an application:

gradle run

However, keep in mind that you need to have the corresponding server installed and you may need to adapt the server address in the source code.
