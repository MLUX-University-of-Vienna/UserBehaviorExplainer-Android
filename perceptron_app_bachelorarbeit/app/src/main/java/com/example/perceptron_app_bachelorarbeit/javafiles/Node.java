package com.example.perceptron_app_bachelorarbeit.javafiles;

/**
 * Simple Node with value and event which accured
 */

public class Node {
    String value;
    String event;

    /**
     * Constructur of Class
     *
     * @param event represents the event that occured
     * @param value represents the perceptron value
     */
    public Node(String event, String value) {
        this.value = value;
        this.event = event;
    }

    public String getValue() {
        return value;
    }

    public String getEvent() {
        return event;
    }


}
