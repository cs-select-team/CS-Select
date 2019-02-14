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

    /**
     * Key identifying the Minimum
     */
    public static final String STAT_MIN_KEY = "Min.";

    /**
     * Key identifying the 1st Quartile
     */
    public static final String STAT_1QU_KEY = "1st Qu.";

    /**
     * Key identifying the Median
     */
    public static final String STAT_MEDIAN_KEY = " Median";

    /**
     * Key identifying the Mean
     */
    public static final String STAT_MEAN_KEY = "Mean";

    /**
     * Key identifying the 3rd Quartile
     */
    public static final String STAT_3QU_KEY = "3rd Qu.";

    /**
     * Key identifying the Maximum
     */
    public static final String STAT_MAX_KEY = "Max.";

    private final int id;
    private final String internalName;
    private final Map<String, String> nameMap;
    private final Map<String, String> descriptionMap;
    private BufferedImage totalGraph;
    private BufferedImage classGraph;
    private final Map<String, String> valueMap;


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
        this.valueMap = new HashMap<>();
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
     * Setter for the name of the feature with the given langCode
     * @param langCode langCode of the language to set the title for
     * @param name the name
     */
    public void setName(String langCode, String name) {
        this.nameMap.put(langCode, name);
    }


    /**
     * Setter for the description of the feature with the given langCode
     * @param langCode langCode of the language to set the description for
     * @param description the description
     */
    public void setDescription(String langCode, String description) {
        this.descriptionMap.put(langCode, description);
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

    /**
     * Stores a value for extra information about the {@link Feature} under the given key
     * @param key key to store the value under
     * @param value value to store
     */
    public void addValue(String key, String value) {
        valueMap.put(key, value);
    }

    /**
     * Gets the value stored under the given key
     * @param key key to get value for
     * @return stored value
     */
    public String getValue(String key) {
        return valueMap.get(key);
    }

    /**
     * Checks whether the given key exists in the {@link Feature}s values
     * @param key key to check
     * @return true if key exists, false otherwise
     */
    public boolean hasValue(String key) {
        return valueMap.containsKey(key);
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
