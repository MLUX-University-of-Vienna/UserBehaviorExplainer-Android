package com.example.perceptron_app_bachelorarbeit.javafiles;

/**
 * Simple Node with value and event which accured
 */

public class Node {
    String value;
    String event;

    public Node(String event, String value) {
        this.value = value;
        this.event = event;
    }

    public Node() {

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
