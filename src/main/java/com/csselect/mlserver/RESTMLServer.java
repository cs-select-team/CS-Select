package com.csselect.mlserver;

import com.csselect.configuration.Configuration;
import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.csselect.utils.ImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;
import org.apache.commons.io.FileUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Rest Implementation of the {@link MLServer} Interface
 */
public class RESTMLServer implements MLServer {

    private final Client client;
    private final String mlserverUrl;
    private final String homeDir;

    /**
     * Constructor to instantiate a {@link RESTMLServer}
     * @param configuration configuration to be used by the server. Injected by guice
     */
    @Inject
    RESTMLServer(Configuration configuration) {
        this.client = ClientBuilder.newClient();
        this.mlserverUrl = "http://" + configuration.getMLServerURL();
        this.homeDir = configuration.getHomeDirectory();
    }

    @Override
    public String getVersion() {
        String response = get("/version").get(String.class);
        Gson gson = new Gson();
        Properties data = gson.fromJson(response, Properties.class);
        return data.getProperty("APIVersion");
    }

    @Override
    public FeatureSet getFeatures(String dataset) throws IOException {
        Gson gson = new Gson();
        String datasetDir = homeDir + File.separator + dataset + File.separator;
        writeDataset(dataset);
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
            String stats = "Min. " + e.get("Min.") + ", 1st Qu. " + e.get("1st Qu.") + ", Median " + e.get("Median")
                    + ", Mean " + e.get("Mean") + ", 3rd Qu. " + e.get("3rd Qu.") + ", Max. " + e.get("Max.");
            feature.setEnglishDescription((String) e.get("description_en" + "\\n Values: " + values + "\\n" + stats));
            feature.setGermanDescription((String) e.get("description_de" + "\\n Werte : " + values + "\\n" + stats));
            feature.setTotalGraph(ImageUtils.readImage(new File(datasetDir + e.get("name") + ".png")));
            feature.setClassGraph(ImageUtils.readImage(new File(datasetDir + e.get("name") + "_class.png")));
            featureSet.addFeature(feature);
        }
        reader.close();
        return featureSet;
    }

    @Override
    public double getScore(String dataset, Collection<Feature> selectedFeatures) {
        StringBuilder request = new StringBuilder("/score?dataset=" + dataset + "&features=");
        selectedFeatures.forEach(f -> request.append(f.getID()).append(","));
        request.deleteCharAt(request.length() - 1);
        String response = get(request.toString()).get(String.class);
        return Double.parseDouble(response);
    }

    private Invocation.Builder get(String request) {
        WebTarget target = client.target(mlserverUrl + request);
        return target.request();
    }

    /**
     * Writes the dataset to the user directory
     * @param datasetIdentifier id of the dataset to write out
     * @throws IOException Thrown when an error occurs while communicating with the ML-Server
     */
    private void writeDataset(String datasetIdentifier) throws IOException {
        File tmpZipFile = new File(homeDir + File.separator + datasetIdentifier + File.separator + "dataset.zip");
        FileUtils.copyURLToFile(
                new URL(mlserverUrl + "/features?dataset=" + datasetIdentifier), tmpZipFile, 1000, 1000);
        ZipFile zipFile = new ZipFile(tmpZipFile);
        String datasetPathname = homeDir + File.separator + datasetIdentifier;
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            InputStream is = zipFile.getInputStream(entry);
            FileUtils.copyInputStreamToFile(is, new File(datasetPathname + File.separator + entry.getName()));
        }
        zipFile.close();
        tmpZipFile.delete();
    }
}
