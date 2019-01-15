package com.csselect.game;

import java.awt.image.BufferedImage;

public class Feature {

    private final int id;
    private final String internalName;

    public Feature(int id, String internalName) {
        this.id = id;
        this.internalName = internalName;
    }
    /**
     * Gets the {@link Feature}s ID
     * @return id
     */
    public int getID() {
        return id;
    }

    public String getInternalName() {
        return "";
    }

    public String getGermanName() {
        return "";
    }

    public String getEnglishName() {
        return "";
    }

    public String getGermanDescription() {
        return "";
    }

    public String getEnglishDescription() {
        return "";
    }

    public BufferedImage getTotalGraph() {
        return null;
    }

    public BufferedImage getClassGraph() {
        return null;
    }

    public void setGermanName(String germanName) {

    }

    public void setEnglishName(String englishName) {

    }
    public void setGermanDescription(String germanDescription) {

    }

    public void setEnglishDescription(String englishDescription) {

    }

    public void setTotalGraph(BufferedImage totalGraph) {

    }

    public void setClassGraph(BufferedImage classGraph) {

    }
}
