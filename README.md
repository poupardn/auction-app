# auction-app
Requirements:
- JDK 8+
- Node/NPM
- Redis-Server
# Running the app
I chose to use Redis with the Append-Only file option on (syncs to disc every sec) for database/persistence. To the run the app, first a redis-server must be started. There is a provided redis.conf in the conf folder.
Then the Spring Boot app can be started. Front-end is started with: cd /frontend && npm start.
