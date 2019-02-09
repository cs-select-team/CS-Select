package com.csselect.utils;

import com.csselect.inject.Injector;
import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Class providing utility methods for interacting with {@link FeatureSet}s
 */
public final class FeatureSetUtils {

    private FeatureSetUtils() {
        //Utility classes shouldn't be instantiated
    }

    private static final String HOMEDIR = Injector.getInstance().getConfiguration().getHomeDirectory();

    /**
     * Loads a {@link FeatureSet} from the disk
     * @param dataset dataset to load
     * @return FeatureSet
     * @throws IOException Thrown if an error reading the dataset occurs
     */
    public static FeatureSet loadFeatureSet(String dataset) throws IOException {
        Gson gson = new Gson();
        String datasetDir = HOMEDIR + File.separator + dataset + File.separator;
        File summaryFile = new File(datasetDir + "summary.json");
        JsonReader reader = new JsonReader(new FileReader(summaryFile));
        Type type = new TypeToken<List<Map<String, Object>>>() { } .getType();
        List<Map<String, Object>> summary = gson.fromJson(reader, type);
        FeatureSet featureSet = new FeatureSet(dataset);
        for (Map<String, Object> e : summary) {
            Feature feature = new Feature((int) (double) e.get("id"), (String) e.get("name"));
            feature.setEnglishName((String) e.get("name_en"));
            feature.setGermanName((String) e.get("name_de"));
            String values = e.get("values").toString();
            values = values.substring(1, values.length() - 1);
            String stats;
            if (e.get("Min.") != null) {
                stats = "Min. " + e.get("Min.") + ", 1st Qu. " + e.get("1st Qu.") + ", Median " + e.get("Median")
                        + ", Mean " + e.get("Mean") + ", 3rd Qu. " + e.get("3rd Qu.") + ", Max. " + e.get("Max.");
            } else {
                stats = null;
            }
            feature.setEnglishDescription(e.get("description_en") + "\\n Values: " + values
                    + (stats != null ? "\\n" + stats : ""));
            feature.setGermanDescription(e.get("description_de") + "\\n Werte : " + values
                    + (stats != null ? "\\n" + stats : ""));
            feature.setTotalGraph(ImageUtils.readImage(new File(datasetDir + e.get("name") + ".png")));
            feature.setClassGraph(ImageUtils.readImage(new File(datasetDir + e.get("name") + "_class.png")));
            featureSet.addFeature(feature);
        }
        reader.close();
        return featureSet;
    }
}
