package com.csselect.utils;

import com.csselect.inject.Injector;
import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Class providing utility methods for interacting with {@link FeatureSet}s
 */
public final class FeatureSetUtils {

    private static final String SUMMARY_FILE_NAME = "summary.json";
    private static final String FEATURE_ID = "id";
    private static final String FEATURE_NAME = "name";
    private static final String FEATURE_NAME_EN = "name_en";
    private static final String FEATURE_NAME_DE = "name_de";
    private static final String FEATURE_DESC_EN = "description_en";
    private static final String FEATURE_DESC_DE = "description_de";
    private static final String FEATURE_VALUES = "values";

    private static final String IMG_TYPE = ".png";
    private static final String IMG_CLASS_TYPE = "_class" + IMG_TYPE;

    private static final String STAT_MIN = "Min.";
    private static final String STAT_1QU = "1st Qu.";
    private static final String STAT_MEDIAN = " Median";
    private static final String STAT_MEAN = "Mean";
    private static final String STAT_3QU = "3rd Qu.";
    private static final String STAT_MAX = "Max.";

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
        File summaryFile = new File(datasetDir + SUMMARY_FILE_NAME);
        JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(summaryFile),
                StandardCharsets.UTF_8));
        Type type = new TypeToken<List<Map<String, Object>>>() { } .getType();
        List<Map<String, Object>> summary = gson.fromJson(reader, type);
        FeatureSet featureSet = new FeatureSet(dataset);
        for (Map<String, Object> e : summary) {
            Feature feature = new Feature((int) (double) e.get(FEATURE_ID), (String) e.get(FEATURE_NAME));
            feature.setName(Languages.ENGLISH, (String) e.get(FEATURE_NAME_EN));
            feature.setName(Languages.GERMAN, (String) e.get(FEATURE_NAME_DE));
            String values = e.get(FEATURE_VALUES).toString();
            values = values.substring(1, values.length() - 1);
            if (e.get(STAT_MIN) != null) {
                feature.addValue(STAT_MIN, (String) e.get(STAT_MIN));
                feature.addValue(STAT_1QU, (String) e.get(STAT_1QU));
                feature.addValue(STAT_MEDIAN, (String) e.get(STAT_MEDIAN));
                feature.addValue(STAT_MEAN, (String) e.get(STAT_MEAN));
                feature.addValue(STAT_3QU, (String) e.get(STAT_3QU));
                feature.addValue(STAT_MAX, (String) e.get(STAT_MAX));
            }
            feature.setDescription(Languages.ENGLISH, (String) e.get(FEATURE_DESC_EN));
            feature.setDescription(Languages.GERMAN, (String) e.get(FEATURE_DESC_DE));
            feature.setTotalGraph(ImageUtils.readImage(new File(datasetDir + e.get(FEATURE_NAME) + IMG_TYPE)));
            feature.setClassGraph(ImageUtils.readImage(new File(datasetDir + e.get(FEATURE_NAME) + IMG_CLASS_TYPE)));
            featureSet.addFeature(feature);
        }
        reader.close();
        return featureSet;
    }
}
