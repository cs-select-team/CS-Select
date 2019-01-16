package com.csselect.game;

import java.awt.image.BufferedImage;

/**
 * The Feature class represents a feature belonging to a feature set {@link FeatureSet} and stores the information
 * of the feature
 */

public class Feature {

    private final int id;
    private final String internalName;

    /**
     * Constructor for a feature object
     * @param id the unique numerical identifier of a feature
     * @param internalName the internal name of the feature
     */
    public Feature(int id, String internalName) {
        this.id = id;
        this.internalName = internalName;
    }
    /**
     * Getter for the ID of the feature
     * @return id
     */
    public int getID() {
        return id;
    }

    /**
     * Getter for the internal name of the feature
     * @return the internal name
     */
    public String getInternalName() {
        return "";
    }

    /**
     * Getter for the german name of the feature
     * @return the german name
     */
    public String getGermanName() {
        return "";
    }

    /**
     * Getter for the english name of the feature
     * @return the english name
     */
    public String getEnglishName() {
        return "";
    }

    /**
     * Getter for the german description of the feature
     * @return the german description
     */
    public String getGermanDescription() {
        return "";
    }

    /**
     * Getter for the english description of the feature
     * @return the english description
     */
    public String getEnglishDescription() {
        return "";
    }

    /**
     * Getter for the graph with the total data of the feature
     * @return the graph with the total data
     */
    public BufferedImage getTotalGraph() {
        return null;
    }

    /**
     * Getter for the graph with the data split into classes of the feature
     * @return the graph with the data split into classes
     */
    public BufferedImage getClassGraph() {
        return null;
    }

    /**
     * Setter for the german name of the feature
     * @param germanName the german name
     */
    public void setGermanName(String germanName) {

    }

    /**
     * Setter for the english name of the feature
     * @param englishName the english name
     */
    public void setEnglishName(String englishName) {

    }

    /**
     * Setter for the german description of the feature
     * @param germanDescription the german description
     */
    public void setGermanDescription(String germanDescription) {

    }

    /**
     * Setter for the english description of the feature
     * @param englishDescription the english description
     */
    public void setEnglishDescription(String englishDescription) {

    }

    /**
     * Setter for the graph with the total data of the feature
     * @param totalGraph the graph with the total data
     */
    public void setTotalGraph(BufferedImage totalGraph) {

    }

    /**
     * Setter for the graph with the data split into classes of the feature
     * @param classGraph the graph with the data split into classes
     */
    public void setClassGraph(BufferedImage classGraph) {

    }
}
