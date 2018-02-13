# TornadoFX Hibernate Example

A simple example of using Hibernate with TornadoFX. The application is a small
Vendor database where you can perform CRUD operations, including inline editing.

It's using an in memory H2 database which will be populated with sample data on start.

I did not use `beforeShutdown {  }` to register the `sessionFactory.close()` callback
because Hibernate was preventing the shutdown hook from running, so I overrode `App.stop()` 
instead, because that is called earlier in the shutdown cycle.