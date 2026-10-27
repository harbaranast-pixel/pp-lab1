package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void constructor_WithBlankName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Client("", "12374"));

        assertEquals("Client's name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructor_WithBlankPhone_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Client("Alex", ""));

        assertEquals("Client's phone cannot be blank.", exception.getMessage());
    }

    @Test
    void gettersAndSetters_ShouldExposeAndUpdateClientDetails() {
        Client client = new Client("Alex", "12345");

        assertEquals("Alex", client.getName());
        assertEquals("12345", client.getPhone());

        client.setName("Jordan");
        client.setPhone("67890");

        assertEquals("Jordan", client.getName());
        assertEquals("67890", client.getPhone());
    }
}
