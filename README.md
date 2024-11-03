# GBTec Email Test
This is a project that shows some ideas of how can we implement an email management api where a client can create, update, send and find emails.

## Prerequisites
- Maven 3.9.9 and OpenJDK 23 (it may be compatible with other versions but we set those versions on the maven wrapper and the pom configuration).
- Docker.

## Setup
### Project
In order to run the project, we need to instantiate four servers:
- Start the api with the profile `local`.
- Start the business part with the profiles `local1`, `local2` and `local3`.

There are some run configurations included in the project (`.idea/runConfigurations`) compatible with IntelliJ IDEA so you can start all the servers in one go.

There is a postman configuration collection with some API call examples in `docs/Emails.postman_collection.json`.

### RabbitMq Server
We need to run a rabbitmq server. With docker installed on our machine, we can execute the following command:

```docker run -d --hostname localhost --name rabbitmq -p 5672:5672 -p 5673:5673 -p 15672:15672 rabbitmq:3-management```

It will start a docker container. When we try to run the server again, docker will show a warning that a container with the same info already exists. We can start it with the command:

```docker run containerId```

Where containerId is the hash that was shown in the warning message.

## Known issues
- There is a few limitations with the load balancer because it doesn't try to repeat the call if some server is down and it is not possible to "assign" a server to a client.
- All the business instances must be connected to the same database because if they don't share the info, one client in different calls can find different info as expected.
- There are some validations that we should manage instead of using a not managed hibernate exception and returning them "dirty" in an api call.

## Future steps
- Add some missing tests.
- Use custom exceptions everywhere and use another one for "unexpected" problems on the API side so the clients only need to support the responses we define.
- The update date of the emails may not work as expected, because if the intended value is to know the last time an API user made some change, it won't work. At the moment, we update the date with every entity update (changing the state when the message is sent for example).
- Change the API load balancer so a client uses the same business server for a given time.
- Add a mechanism to keep the information updated between business instances if needed (it can be database replication or we can use something like rabbitmq with a lower priority queue). If the requirements are deploying one business api per user id range (a-d, e-h...) we should change how we send the emails so the message is only sent and persisted where it needs.
- It would be great to use something like Testcontainer to create a instance of the rabbitmq server and use it on the integration tests. This would allow us to test even better how all the microservices work together creating multiple instances of every server.
- A deployment script is needed. We could use something like docker compose for a simple solution. The intellij run configuration is an ugly workaround for this problem.
- Add spring security so a client needs to be authenticated to insert and update their mails and it cannot change mails of other users.
