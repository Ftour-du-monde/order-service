package fr.univamu.iut.orderservice.domain;

/**
 * Domain entity (Value Object) representing a snapshot of a subscriber.
 *
 * Role: Represents a subscriber fetched from the external system to validate orders.
 * Architecture: Domain layer. It is a pure Java object without dependencies on frameworks.
 */
public class Subscriber {
    private final int id;
    private final String lastName;
    private final String firstName;

    public Subscriber(int id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public int getId() { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
}
