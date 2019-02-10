package ru.sergey_gusarov.hw25;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("all")
@Ignore
public class MessagesTest {

    @Test
    public void testCreateSimpleGenericMessage() {
        // TODO: create message with the "Hello" payload
        Message<String> message = new GenericMessage<String>("Hello");

        assertNotNull(message);
        assertNotNull(message.getPayload());
        assertEquals("Hello", message.getPayload());
    }

    @Test
    public void testCreateGenericMessage() {
        // TODO: create message with the User payload
        Message<User> message = new GenericMessage<User>(new User("John", 23));

        assertNotNull(message);
        assertNotNull(message.getPayload());
        assertEquals(new User("John", 23), message.getPayload());
    }

    @Test
    public void testGenericMessageWithHeaders() {
        // TODO: create message with the "Hello" payload, and "to" header
        Map<String, Object> headers = Collections.singletonMap("to", "World");
        Message<String> message = new GenericMessage<>("Hello", headers);

        assertNotNull(message);
        assertEquals("Hello", message.getPayload());
        assertEquals("World", message.getHeaders().get("to", String.class));
    }

    @Test
    public void testGenericMessageWithMessageHeaders() {
        MessageHeaders headers = new MessageHeaders(
                Collections.singletonMap("to", "World")
        );
        Message<String> message = new GenericMessage<>("Hello", headers);

        assertNotNull(message);
        assertEquals("Hello", message.getPayload());
        assertEquals("World", message.getHeaders().get("to", String.class));
    }

    @Test
    public void testErrorMessage() {
        Message errorMessage = new ErrorMessage(new NullPointerException());

        assertNotNull(errorMessage);
        assertEquals(ErrorMessage.class, errorMessage.getClass());
        assertEquals(NullPointerException.class, errorMessage.getPayload().getClass());
    }

    @Test
    public void testMessageBuilder() {
        Message message = MessageBuilder.withPayload("Hello")
                .setHeader("to", "World")
                .build();

        assertNotNull(message);
        assertEquals("Hello", message.getPayload());
        assertEquals("World", message.getHeaders().get("to", String.class));
    }

    @Test
    public void testBuildFromMessage() {
        Message<User> original = MessageBuilder
            .withPayload(new User("Kate", 30))
            .setHeader("processor", "userService")
            .build();

        // TODO: create message with the same headers and payload using MessageBuilder
        Message<User> newMessage = MessageBuilder.fromMessage(original).build();

        assertNotNull(newMessage);
        assertEquals(original.getPayload(), newMessage.getPayload());
        assertEquals(original.getHeaders().get("processor"), newMessage.getHeaders().get("processor"));
    }
}
