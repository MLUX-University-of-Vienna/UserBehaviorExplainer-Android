package com.example.perceptron_app_bachelorarbeit.storagefiles;

/**
 * Event is used to make a Event with an identifier and a description which was given by the Professor
 */

public class Event {
    String identifier;
    String description;

    /**
     * Constructur of Class
     *
     * @param identifier  represents the event that occured with the right identifier
     * @param description represents the representation of it like the arrows for display
     */
    public Event(String identifier, String description) {
        this.identifier = identifier;
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDescription() {
        return description;
    }
}
