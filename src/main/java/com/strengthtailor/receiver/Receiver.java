package com.strengthtailor.receiver;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import static graphql.Scalars.GraphQLInt;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.Scalars.GraphQLString;

@Component
public class Receiver implements MessageListener {

    private final RabbitTemplate rabbitTemplate;
    private final ConfigurableApplicationContext context;

    public Receiver(RabbitTemplate rabbitTemplate, ConfigurableApplicationContext context) {
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;
    }

    @Override
    public void onMessage(Message message) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            RequestMessage requestMessage = objectMapper.readValue(message.getBody(), RequestMessage.class);

            System.out.println("Received type: '" + requestMessage.getType() + "' <" + requestMessage.getRequest() + ">");

            System.out.println("Sending message...");

            final String replyToQueue = message.getMessageProperties().getReplyTo();

            GraphQLObjectType queryType = newObject()
                    .name("helloWorldQuery")
                    .field(newFieldDefinition()
                            .type(GraphQLString)
                            .name("hello")
                            .staticValue("world"))
                    .field(newFieldDefinition()
                            .type(GraphQLInt)
                            .name("age")
                            .staticValue(55))
                    .build();

            GraphQLSchema schema = GraphQLSchema.newSchema()
                    .query(queryType)
                    .build();
            Map<String, Object> result = (Map<String, Object>) new GraphQL(schema).execute(requestMessage.getRequest()).getData();

            ResponseMessage responseMessage = new ResponseMessage(requestMessage.getType(), result);

            String resultString = null;
            try {
                resultString = objectMapper.writeValueAsString(responseMessage);

                System.out.println("Result = " + resultString);

                rabbitTemplate.convertAndSend(replyToQueue, resultString);

            } catch (JsonProcessingException e) {
                resultString = "{ error: '" + e.toString() + "' }";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
