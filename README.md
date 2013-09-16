Java Template App for Heroku
============================

ImmenseBastion is a simple Java application intended to be used as a starting point for writing REST APIs and deploying on Heroku.

What you get
------------

* A Spring application context, see ``AppConfiguration.java``, that starts a Hibernate session preconfigured to connect to PostgreSQL.

* An embedded Jetty - there is no WAR, so Jetty is configured in Java, see ``App.java`` for how to add more servlets, or just add your resource endpoints in the ``rest`` package.

* Jersey running with a Spring context, serializing to JSON using Jackson.

How to use it
-------------

Add business functionality and domain objects, make sure that they are registered in Spring and Hibernate, and then add Jersey resources in the ``rest`` package. See ``AccountService.java`` for an example.

To run it locally on your machine, set the environment variable ``DATABASE_URL`` point to your local PostgreSQL installation, e.g.

	DATABASE_URL="postgres://user:password@localhost:5432/dbname

and then run

	java -cp target/classes:target/dependency/* com.github.steingrd.immensebastion.App

To change the port that Jetty listens on, set the ``PORT`` variable.

How to deploy it
----------------

Simply add your Heroku repository as a remote and deploy using git push. If you change the name or location of ``App.java``, remember to update ``Procfile``.