package com.csselect.game;

import com.csselect.utils.Languages;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * The Feature class represents a feature belonging to a feature set {@link FeatureSet} and stores the information
 * of the feature
 */

public class Feature implements Comparable<Feature> {

    private final int id;
    private final String internalName;
    private Map<String, String> nameMap;
    private Map<String, String> descriptionMap;
    private BufferedImage totalGraph;
    private BufferedImage classGraph;


    private static final String DEFAULT_LANGUAGE_CODE = Languages.ENGLISH;


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
     * @param langCode langCode of the language to set the title for
     * @param germanName the german name
     */
    public void setName(String langCode, String germanName) {
        this.nameMap.put(langCode, germanName);
    }


    /**
     * Setter for the german description of the feature
     * @param langCode langCode of the language to set the description for
     * @param germanDescription the german description
     */
    public void setDescription(String langCode, String germanDescription) {
        this.descriptionMap.put(langCode, germanDescription);
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

    @Override
    public int compareTo(@NotNull Feature feature) {
        return  this.id - feature.id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else {
            Feature feature = (Feature) o;
            return this.id == feature.id;
        }
    }
}
