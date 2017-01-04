GraphQL Spring Server
======================

Simple 'hello world' server application that reads from a RabbitMQ queue and processes the request (ReactJS action) as a GraphQL query, returning the response on a response queue.

Running
-------
* Run the class: com.strengthtailor.StrengthTailorServerApplication (setting the classpath appropariately)

NB: Probably easiest at the moment to just run it through the IntelliJ IDE since it is not current packaged as a jar.


Dependencies
------------
Install and run the RabbitMQ docker image by running the docker script:
     
    #!/bin/bash -e
    
    NAME='rabbitmq'
    DATA_ROOT='/opt/docker-containers'
    RABBITMQ_DATA="${DATA_ROOT}/${NAME}"
    
    HOST_NAME=rabbitmq
    NETWORK_NAME=dev_nw
    MSG_PORT=5672
    ALT_MSG_PORT=5671
    CLUSTER_PORT_1=4369
    CLUSTER_PORT_2=25672
    
    mkdir -p "$RABBITMQ_DATA"
    
    docker stop "${NAME}" 2>/dev/null && sleep 1
    docker rm "${NAME}" 2>/dev/null && sleep 1
    docker run --detach=true --name "${NAME}" --hostname "${HOST_NAME}" \
    --volume "${RABBITMQ_DATA}:/var/lib/rabbitmq" \
    --network=${NETWORK_NAME} \
    -p $MSG_PORT:5672 \
    -p $ALT_MSG_PORT:5671 \
    -p $CLUSTER_PORT_1:4369 \
    -p $CLUSTER_PORT_2:25672 \
    rabbitmq:3.6.6
    
Install and run the PostgreSQL database by running:

    #!/bin/bash -e
    
    NAME='postgres'
    DATA_ROOT='/opt/docker-containers'
    POSTGRES_DATA="${DATA_ROOT}/${NAME}"
    
    HOST_NAME=postgres
    NETWORK_NAME=dev_nw
    DB_PORT=5432
    
    mkdir -p "$POSTGRES_DATA"
    
    docker stop "${NAME}" 2>/dev/null && sleep 1
    docker rm "${NAME}" 2>/dev/null && sleep 1
    docker run --detach=true --name "${NAME}" --hostname "${HOST_NAME}" \
    --volume "${POSTGRES_DATA}:/var/lib/postgresql/data" \
    --network=${NETWORK_NAME} \
    -p $DB_PORT:5432 \
    postgres:9.6.1
    

NB: The Postgres code can also be removed since it is not needed for the purpose of demonstrating or working with GraphQL.

See also: [GraphQL ReactJS Client](https://github.com/nickweedon/graphql-reactjs-client).

