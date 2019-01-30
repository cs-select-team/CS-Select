package com.csselect.game;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * The Feature class represents a feature belonging to a feature set {@link FeatureSet} and stores the information
 * of the feature
 */

public class Feature {

    private final int id;
    private final String internalName;
    private Map<String, String> nameMap;
    private Map<String, String> descriptionMap;
    private BufferedImage totalGraph;
    private BufferedImage classGraph;

    private static final String DEFAULT_LANGUAGE_CODE = "en";


    /**
     * Constructor for a feature object
     * @param id the unique numerical identifier of a feature
     * @param internalName the internal name of the feature
     */
    public Feature(int id, String internalName) {
        this.id = id;
        this.internalName = internalName;

        this.nameMap = new HashMap<>();
        this.descriptionMap = new HashMap<>();
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
     * Getter for the name of the feature in a specific language, default is english
     * @param langCode the language code for the language of the name of the feature
     * @return the name in the specific language if it exists, else in english
     */
    public String getName(String langCode) {
        if (this.nameMap.containsKey(langCode)) {
            return this.nameMap.get(langCode);
        }
        if (this.nameMap.containsKey(DEFAULT_LANGUAGE_CODE)) {
            return this.nameMap.get(DEFAULT_LANGUAGE_CODE);
        }
        return "";
    }


    /**
     * Getter for the description of the feature in a specific language, default is english
     * @param langCode the language code for the language of the description of the feature
     * @return the description in the specific language if it exists, else in english
     */
    public String getDescription(String langCode) {
        if (this.descriptionMap.containsKey(langCode)) {
            return this.descriptionMap.get(langCode);
        }
        if (this.descriptionMap.containsKey(DEFAULT_LANGUAGE_CODE)) {
            return this.descriptionMap.get(DEFAULT_LANGUAGE_CODE);
        }
        return "";
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
        this.nameMap.put("de", germanName);
    }

    /**
     * Setter for the english name of the feature
     * @param englishName the english name
     */
    public void setEnglishName(String englishName) {
        this.nameMap.put("en", englishName);
    }

    /**
     * Setter for the german description of the feature
     * @param germanDescription the german description
     */
    public void setGermanDescription(String germanDescription) {
        this.descriptionMap.put("de", germanDescription);
    }

    /**
     * Setter for the english description of the feature
     * @param englishDescription the english description
     */
    public void setEnglishDescription(String englishDescription) {
        this.descriptionMap.put("en", englishDescription);
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
