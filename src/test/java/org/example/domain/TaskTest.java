package org.example.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void setTime_WithNegativeTime_ShouldThrowException() {
        Task task = new Task("Inspection", new BigDecimal("50"), new BigDecimal("50"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> task.setTime(-5));

        assertEquals("Time cannot be negative.", exception.getMessage());
    }

    @Test
    void constructor_WithNegativeDetailPrice_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Task("Inspection", new BigDecimal("-50.00"), new BigDecimal("10.00")));

        assertEquals("Detail price cannot be null or negative.", exception.getMessage());
    }

    @Test
    void constructor_WithNegativeWorkPrice_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Task("Inspection", new BigDecimal("50.00"), new BigDecimal("-10.00")));

        assertEquals("Work price cannot be null or negative.", exception.getMessage());
    }

    @Test
    void constructor_WithBlankDescription_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Task("", new BigDecimal("50.00"), new BigDecimal("10.00")));

        assertEquals("Description cannot be blank.", exception.getMessage());
    }

    @Test
    void gettersAndSetters_ShouldExposeAndUpdateTaskDetails() {
        Task task = new Task("Inspection", new BigDecimal("50.00"), new BigDecimal("10.00"));

        assertEquals(new BigDecimal("50.00"), task.getDetailPrice());
        assertEquals(new BigDecimal("10.00"), task.getWorkPrice());
        assertEquals(0, task.getTime());

        task.setDescription("Full inspection");
        task.setTime(30);

        assertEquals(30, task.getTime());
        assertDoesNotThrow(task::completeTask);
        assertTrue(task.isCompleted());
    }
}
