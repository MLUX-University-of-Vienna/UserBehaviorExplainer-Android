package com.example.perceptron_app_bachelorarbeit.javafiles;

/**
 * Event is used to make a Event with an identifier and a description which was given by the Professor
 */

public class Event {
    String identifier;
    String description;

    public Event(String identifier, String description) {
        this.identifier = identifier;
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
