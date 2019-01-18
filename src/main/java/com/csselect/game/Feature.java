package com.csselect.game;

import java.awt.image.BufferedImage;

/**
 * The Feature class represents a feature belonging to a feature set {@link FeatureSet} and stores the information
 * of the feature
 */

public class Feature {

    private final int id;
    private final String internalName;
    private String germanName;
    private String englishName;
    private String germanDescription;
    private String englishDescription;
    private BufferedImage totalGraph;
    private BufferedImage classGraph;


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
        return this.internalName;
    }

    /**
     * Getter for the german name of the feature
     * @return the german name
     */
    public String getGermanName() {
        return this.germanName;
    }

    /**
     * Getter for the english name of the feature
     * @return the english name
     */
    public String getEnglishName() {
        return this.englishName;
    }

    /**
     * Getter for the german description of the feature
     * @return the german description
     */
    public String getGermanDescription() {
        return this.germanDescription;
    }

    /**
     * Getter for the english description of the feature
     * @return the english description
     */
    public String getEnglishDescription() {
        return this.englishDescription;
    }

    /**
     * Getter for the graph with the total data of the feature
     * @return the graph with the total data
     */
    public BufferedImage getTotalGraph() {
        return this.totalGraph;
    }

    /**
     * Getter for the graph with the data split into classes of the feature
     * @return the graph with the data split into classes
     */
    public BufferedImage getClassGraph() {
        return this.classGraph;
    }

    /**
     * Setter for the german name of the feature
     * @param germanName the german name
     */
    public void setGermanName(String germanName) {
        this.germanName = germanName;
    }

    /**
     * Setter for the english name of the feature
     * @param englishName the english name
     */
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    /**
     * Setter for the german description of the feature
     * @param germanDescription the german description
     */
    public void setGermanDescription(String germanDescription) {
        this.germanDescription = germanDescription;
    }

    /**
     * Setter for the english description of the feature
     * @param englishDescription the english description
     */
    public void setEnglishDescription(String englishDescription) {
        this.englishDescription = englishDescription;
    }

    /**
     * Setter for the graph with the total data of the feature
     * @param totalGraph the graph with the total data
     */
    public void setTotalGraph(BufferedImage totalGraph) {
        this.totalGraph = totalGraph;
    }

    /**
     * Setter for the graph with the data split into classes of the feature
     * @param classGraph the graph with the data split into classes
     */
    public void setClassGraph(BufferedImage classGraph) {
        this.classGraph = classGraph;
    }
}
