package com.example.perceptron_app_bachelorarbeit.javafiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Class Storage is used for the whole storing and processing of the CSV Data
 */

public class Storage {
    /**
     * csvData is the raw stored Data from the select and import
     */
    private HashMap<Integer, HashMap<Integer, String>> csvData = new HashMap<>();
    /**
     * firstRow is the first column with the event Names - needed for Recycle View
     */
    private HashMap<Integer, String> firstRow = new HashMap<>();
    /**
     * displayNodeMap is a HashMap with all nodes and the corresponding events + values
     */
    private HashMap<Integer, DisplayNode> displayNodeMap = new HashMap<>();
    /**
     * events saves all possible events and descriptions
     */
    private HashMap<Integer, Event> events = new HashMap<>();
    /**
     * running Node Represents the Node which is currently displayed
     */
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
        for (int counterNames = 1; counterNames < valuesOfFirstRow.size(); counterNames++) {
            firstRow.put(counterAdding, valuesOfFirstRow.get(counterNames));
            counterAdding++;
        }
    }

    /**
     * Inserts all Values and events to a given Node and saves it.
     * Also starts the best and Worst function to get the Data
     */
    public void setEventsAndValues() {

        HashMap<Integer, DisplayNode> insertMap = new HashMap<>();
        for (int counterRows = 0; counterRows < firstRow.size(); counterRows++) {
            System.out.println(firstRow.get(counterRows));
            DisplayNode insert = new DisplayNode(firstRow.get(counterRows));
            insertMap.put(counterRows, insert);
        }

        int index = 0;

        for (int counterCSV = 1; counterCSV < csvData.size(); counterCSV++) {
            HashMap<Integer, String> element = csvData.get(counterCSV);
            String event = element.get(0);
            int counterForMap = 0;

            for (int counterElement = 1; counterElement < element.size(); counterElement++) {
                String value = element.get(counterElement);
                String substring = value.substring(0, Math.min(value.length(), 6));
                Node insert = new Node(event, substring);
                HashMap<Integer, Node> nodeInsert = insertMap.get(counterForMap).getNodesComplete();
                nodeInsert.put(index, insert);
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
     * Searches for the best and Worst nodes of the given style, Like Temperature, Time ...
     *
     * @param displayNode is given by the NodeActivity to check which are the highest and lowest values
     * @return the values selected as highest and lowest 5 per each
     */
    public DisplayNode getBestAndWorst(DisplayNode displayNode) {
        List<Node> allNodesFromPoint = new ArrayList<>();
        allNodesFromPoint.addAll(displayNode.bestNodes.values());

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

        return new DisplayNode(bestNodes);
    }

    /**
     * Here the Event gets converted to a readable String which can be displayed
     *
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

        for (int elementInString = 0; elementInString < convert.length(); elementInString++) {
            running = running + convert.charAt(elementInString);

            if (running.contains("$")) {
                values.put(counterForValues, translate(running));
                valueBefore = running;
                running = "";
                counterForValues++;
            } else if (running.contains("-+-")) {
                String valueToCheck = running.substring(0, running.length() - 3);

                if (valueBefore.contains("t_n")) {
                    valueToCheck = changeForTemperature(valueToCheck);
                } else if (valueBefore.contains("p_n")) {
                    valueToCheck = changeForPrecip(valueToCheck);
                } else if (valueBefore.contains("tq_n")) {
                    valueToCheck = changeValueForQuarter(valueToCheck);
                }

                values.put(counterForValues, valueToCheck);
                valueBefore = "";
                running = "";
                counterForValues++;
            }
        }

        String valueToCheck = running;

        if (valueBefore.contains("t_n")) {
            valueToCheck = changeForTemperature(valueToCheck);
        } else if (valueBefore.contains("p_n")) {
            valueToCheck = changeForPrecip(valueToCheck);
        } else if (valueBefore.contains("tq_n")) {
            valueToCheck = changeValueForQuarter(valueToCheck);
        }

        values.put(counterForValues, changeValueForQuarter(valueToCheck));

        int counterForBreakLine = 0;

        for (String elementInValues : values.values()) {
            if (counterForBreakLine % 2 != 0 && counterForBreakLine != 0 && counterForBreakLine != values.size() - 1) {
                convertedReturn += elementInValues + "\n";
            } else {
                convertedReturn += elementInValues;
            }
            counterForBreakLine++;
        }
        return convertedReturn;
    }

    /**
     * Function translate gets an identifier and translates it to the real Name from the CSV given
     *
     * @param identifier is the Value which should be given from the System to translate
     * @return the String with the real name of the element
     */
    private String translate(String identifier) {
        String translated = "";

        for (Event elementInEvents : events.values()) {
            if (identifier.equals("t_n$")) { //First Element needs to be set - Not able to change to standard format
                return elementInEvents.description;
            }

            if (identifier.equals(elementInEvents.getIdentifier())) {
                translated = elementInEvents.getDescription();
                break;
            }
        }

        return translated;
    }

    /**
     * Changes the value of a quarter of the day
     *
     * @param value is the quarter from the value
     * @return the changed value for the quarter
     */

    private String changeValueForQuarter(String value) {
        String first;
        String second;
        switch (value) {
            case "q1":
                first = new StringBuilder().appendCodePoint(0x1F55B).toString();
                second = new StringBuilder().appendCodePoint(0x1F555).toString();
                return first + " - " + second;
            case "q2":
                first = new StringBuilder().appendCodePoint(0x1F555).toString();
                second = new StringBuilder().appendCodePoint(0x1F55B).toString();
                return first + " - " + second;
            case "q3":
                first = new StringBuilder().appendCodePoint(0x1F55B).toString();
                second = new StringBuilder().appendCodePoint(0x1F555).toString();
                return first + " - " + second;
            case "q4":
                first = new StringBuilder().appendCodePoint(0x1F555).toString();
                second = new StringBuilder().appendCodePoint(0x1F55B).toString();
                return first + " - " + second;
            default:
                return "❔";
        }
    }

    /**
     * Changes temperature if its a false field like -91.0
     *
     * @param value value from the list which needs to be translated
     * @return the changed value if its faulty
     */
    private String changeForTemperature(String value) {
        return value.length() <= 6 ?
                "❔"
                :
                value + "° celcius";
    }

    /**
     * Changes the precipitation from 0.0 and 1.0 to sunny and rainy
     *
     * @param value is from the list which needs to be translated
     * @return gives back rainy or sunny or ? for undefined values
     */
    private String changeForPrecip(String value) {
        switch (value) {
            case "0.0":
                return "☀";
            case "1.0":
                return "☔";
            default:
                return "❔";
        }
    }

    /**
     * getNode returns the Node which got selected and gives back the Information
     *
     * @param nodeName nodeName is the Node selected from the RecycleView to be displayed
     * @return gives back the Node according to the name
     */
    public DisplayNode getNode(String nodeName) {
        DisplayNode node = new DisplayNode();
        for (DisplayNode nodeToDisplay : displayNodeMap.values()) {
            if (nodeToDisplay.getNodeName().equals(nodeName)) {
                node = nodeToDisplay;
            }
        }
        return node;
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
