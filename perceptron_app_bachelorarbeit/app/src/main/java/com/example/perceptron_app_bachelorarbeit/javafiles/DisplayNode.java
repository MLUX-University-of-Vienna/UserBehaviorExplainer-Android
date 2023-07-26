package com.example.perceptron_app_bachelorarbeit.javafiles;

import java.util.HashMap;

/**
 * Display Node is a basic Node with all events and Values which occured
 */

public class DisplayNode {
    String nodeName;
    HashMap<Integer, Node> nodesComplete = new HashMap<>();
    HashMap<Integer, Node> bestNodes = new HashMap<>();

    public DisplayNode(String nodeName, HashMap<Integer, Node> nodesComplete, HashMap<Integer, Node> bestNodes) {
        this.nodeName = nodeName;
        this.nodesComplete = nodesComplete;
        this.bestNodes = bestNodes;
    }

    public DisplayNode(String nodeName) {
        this.nodeName = nodeName;
    }

    public DisplayNode() {

    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public HashMap<Integer, Node> getNodesComplete() {
        return nodesComplete;
    }

    public void setNodesComplete(HashMap<Integer, Node> nodesComplete) {
        this.nodesComplete = nodesComplete;
    }

    public HashMap<Integer, Node> getBestNodes() {
        return bestNodes;
    }

    public void setBestNodes(HashMap<Integer, Node> bestNodes) {
        this.bestNodes = bestNodes;
    }
}
