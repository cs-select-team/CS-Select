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
