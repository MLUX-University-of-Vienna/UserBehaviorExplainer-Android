package com.example.perceptron_app_bachelorarbeit.javafiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class Storage is used for the whole storing and processing of the CSV Data
 */

public class Storage {
    /**
     * csvData is the raw stored Data from the select and import
     */
    private HashMap<Integer, HashMap<Integer,String>> csvData = new HashMap<>();
    /**
     * firstRow is the first column with the event Names - needed for Recycle View
     */
    private HashMap<Integer, String> firstRow = new HashMap<>();
    /**
     * displayNodeMap is a HashMap with all nodes and the corresponding events + values
     */
    private HashMap<Integer, DisplayNode> displayNodeMap = new HashMap<>();
    /**
     * events saves all possbile events and descriptions
     */
    private HashMap<Integer, Event> events = new HashMap<>();

    private DisplayNode runningNode;

    /**
     * Constructor
     */
    public Storage() {

    }

    /**
     * setNames gets the Values of the Events and Nodes and saves them in firstRow
     */
    public void setNames() {
        HashMap<Integer, String> valuesOfFirstRow = csvData.get(0);
        int counterAdding = 0;
        for(int counterNames = 1; counterNames < valuesOfFirstRow.size(); counterNames++){
            firstRow.put(counterAdding, valuesOfFirstRow.get(counterNames));
            counterAdding++;
        }
    }

    /**
     * Inserts all Values and events to a given Node and saves it.
     * Also starts the best and Worst function to get the Data
     */
    public void setEventsAndValues() {
        boolean first = true;

        HashMap<Integer, DisplayNode> insertMap = new HashMap<>();
        for(int counterRows = 0; counterRows < firstRow.size(); counterRows++){
            DisplayNode insert = new DisplayNode(firstRow.get(counterRows));
            insertMap.put(counterRows,insert);
        }

        int index = 0;

        for(int counterCSV = 1; counterCSV < csvData.size(); counterCSV++){
            HashMap<Integer, String> element = csvData.get(counterCSV);
            String event = element.get(0);
            int counterForMap = 0;

            for(int counterElement = 1; counterElement < element.size(); counterElement++){
                Node insert = new Node(event,element.get(counterElement));
                HashMap<Integer, Node> nodeInsert = insertMap.get(counterForMap).getNodesComplete();
                nodeInsert.put(index,insert);
                insertMap.get(counterForMap).setNodesComplete(nodeInsert);
                counterForMap++;
            }
            index++;
        }

        displayNodeMap = insertMap;
        setBestAndWorst();
    }

    /**
     * Searches for the three best cases and worst cases for a node and sets them in the storage
     */
    public void setBestAndWorst() {
        int index = 0;
        HashMap<Integer, DisplayNode> displayNodeMapInsert = new HashMap<>();

        for (DisplayNode nodeElement : displayNodeMap.values()) {
            List<Node> allNodesFromPoint = new ArrayList<>();
            allNodesFromPoint.addAll(nodeElement.nodesComplete.values());

            Collections.sort(allNodesFromPoint, new Comparator<Node>() {
                @Override
                public int compare(Node firstNode, Node secondNode) {
                    double compareFirst = Double.parseDouble(firstNode.getValue());
                    double compareSecond = Double.parseDouble(secondNode.getValue());
                    return Double.compare(compareFirst, compareSecond);
                }
            });

            List<Node> lowest = allNodesFromPoint.subList(0, Math.min(5, allNodesFromPoint.size()));
            List<Node> highest = allNodesFromPoint.subList(Math.max(allNodesFromPoint.size() - 5, 0), allNodesFromPoint.size());

            HashMap<Integer, Node> bestNodes = new HashMap<>();

            int counter = 0;
            for (Node elementHighest : highest) {
                bestNodes.put(counter, elementHighest);
                counter++;
            }

            for (Node elementLowest : lowest) {
                bestNodes.put(counter, elementLowest);
                counter++;
            }

            DisplayNode insert = new DisplayNode(nodeElement.getNodeName(), nodeElement.getNodesComplete(), bestNodes);
            displayNodeMapInsert.put(index, insert);
            index++;
        }

        displayNodeMap = displayNodeMapInsert;
    }

    /**
     * Here the Event gets converted to a readable String which can be displayed
     * @param convert is the Node which should be converted
     * @return gives back a readable String for the Activity to present
     */

    public String convertEventPrefixForNode(String convert) {
        System.out.println(convert);
        String convertedReturn = "";
        HashMap<Integer, String> values = new HashMap<>();
        String running = "";
        int counterForValues = 0;
        String valueBefore = "";

        for(int elementInString = 0; elementInString < convert.length(); elementInString++){
            running = running + convert.charAt(elementInString);

            if(running.contains("$")){
                values.put(counterForValues,translate(running));
                valueBefore = running;
                running = "";
                counterForValues++;
            } else if(running.contains("-+-")){
                String valueToCheck = running.substring(0, running.length()-3);

                if(valueBefore.contains("t_n")){
                    valueToCheck = changeForTemperature(valueToCheck);
                } else if (valueBefore.contains("p_n")){
                    valueToCheck = changeForPrecip(valueToCheck);
                } else if (valueBefore.contains("tq_n")){
                    valueToCheck = changeValueForQuarter(valueToCheck);
                }

                values.put(counterForValues, valueToCheck);
                valueBefore = "";
                running = "";
                counterForValues++;
            }
        }

        String valueToCheck = running;

        if(valueBefore.contains("t_n")){
            valueToCheck = changeForTemperature(valueToCheck);
        } else if (valueBefore.contains("p_n")){
            valueToCheck = changeForPrecip(valueToCheck);
        } else if (valueBefore.contains("tq_n")){
            valueToCheck = changeValueForQuarter(valueToCheck);
        }

        values.put(counterForValues, changeValueForQuarter(valueToCheck));

        int counterForBreakline = 0;

        for(String elementInValues : values.values()){
            if(counterForBreakline%2 != 0 && counterForBreakline != 0 && counterForBreakline != values.size()-1){
                convertedReturn += elementInValues + "\n";

            } else {
                convertedReturn += " " + elementInValues + " ";
            }
            counterForBreakline++;
        }

        return convertedReturn;
    }

    /**
     * Function translate gets an identifier and translates it to the real Name from the CSV given
     * @param identifier is the Value which should be given from the System to translate
     * @return the String with the real name of the element
     */
    private String translate(String identifier){
        String translated = "";

        for(Event elementInEvents : events.values()){
            if(identifier.equals("t_n$")) { //First Element needs to be set - Not able to change to standard format
                return elementInEvents.description;
            }

            if(identifier.equals(elementInEvents.getIdentifier())){
                translated = elementInEvents.getDescription();
                break;
            }
        }

        return translated;
    }

    private String changeValueForQuarter(String value){
        switch(value) {
            case "q1":
                return "00:00-06:00";
            case "q2":
                return "06:00-12:00";
            case "q3":
                return "12:00-18:00";
            case "q4":
                return  "18:00-24:00";
            default:
                return "?";
        }
    }

    private String changeForTemperature(String value) {
        return value.length() <= 6 ?
                "?"
                :
                value;
    }

    private String changeForPrecip(String value){
        switch(value){
            case "0.0":
                return "Sunny";
            case "1.0":
                return "Rainy";
            default:
                return "?";
        }
    }

    /**
     * getNode returns the Node which got selected and gives back the Information
     * @param nodeName nodeName is the Node selected from the RecycleView to be displayed
     * @return gives back the Node according to the name
     */

    public DisplayNode getNode(String nodeName) {
        DisplayNode node = new DisplayNode();
        for (DisplayNode nodeToDisplay : displayNodeMap.values()) {
            if (nodeToDisplay.getNodeName().equals(nodeName)) {
                node = nodeToDisplay;
            } else {
                continue;
            }
        }
        return node;
    }

    /**
     * getNextNode gives back the Node for the next page and the Information to it
     * @param currentNode is the value where the node is currently at
     * @return a DisplayNode which is selected - If the List is at the End the first element should be returned else the next element
     */

    public DisplayNode getNextNode(String currentNode) {
        int index = 0;
        for (int elementIndex = 0; elementIndex < firstRow.size(); elementIndex++) {
            String first = firstRow.get(elementIndex);
            if (currentNode.equals(firstRow.get(elementIndex))) {
                index = elementIndex;
                break;
            }
        }
        if (index == firstRow.size()-1) {
            index = 0;
        } else {
            index++;
        }
        return displayNodeMap.get(index);
    }

    /**
     * getNextNode gives back the Node for the previous page and the Information to it
     * @param currentNode is the value where the node is currently at
     * @return a DisplayNode which is selected - If the List is at the Start the last element should be returned else the previous element
     */

    public DisplayNode getPreviousNode(String currentNode) {
        int index = 0;
        for (int elementIndex = 0; elementIndex < firstRow.size(); elementIndex++) {
            if (currentNode.equals(firstRow.get(elementIndex))) {
                index = elementIndex;
                break;
            }
        }
        if (index == 0) {
            index = displayNodeMap.size()-1;
        } else {
            index--;
        }
        return displayNodeMap.get(index);
    }

    /**
     * Getters and Setters of the Class
     */
    public void setCsvData(HashMap<Integer, HashMap<Integer, String>> csvData) {
        this.csvData = csvData;
    }

    public HashMap<Integer, String> getNames() {
        return firstRow;
    }

    public DisplayNode getRunningNode() {
        return runningNode;
    }

    public void setRunningNode(String nodeName) {
        this.runningNode = getNode(nodeName);
    }

    public void setEvents(HashMap<Integer, Event> events) {
        this.events = events;
    }
}
