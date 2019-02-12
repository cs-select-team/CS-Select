package com.csselect.mlserver;

import com.csselect.configuration.Configuration;
import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.csselect.inject.Injector;
import com.csselect.utils.FeatureSetUtils;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.pmw.tinylog.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Rest Implementation of the {@link MLServer} Interface
 */
public class RESTMLServer implements MLServer {

    private static final int TIMEOUT = 1000;
    private static final String GET_VERSION = "/version";
    private final Client client;
    private final String mlserverUrl;
    private final String homeDir;
    private final Map<String, FeatureSet> featureSetMap;

    /**
     * Constructor to instantiate a {@link RESTMLServer} Only to be used by the {@link Injector}
     * @param configuration configuration to be used by the server. Injected by our {@link Injector}
     */
    public RESTMLServer(Configuration configuration) {
        this.client = ClientBuilder.newClient();
        this.mlserverUrl = "http://" + configuration.getMLServerURL();
        this.homeDir = configuration.getHomeDirectory();
        this.featureSetMap = new HashMap<>();
    }

    @Override
    public String getVersion() {
        String response = get(GET_VERSION).get(String.class);
        Gson gson = new Gson();
        Properties data = gson.fromJson(response, Properties.class);
        return data.getProperty("APIVersion");
    }

    @Override
    public FeatureSet getFeatures(String dataset) throws IOException {
        if (featureSetMap.containsKey(dataset)) {
            return featureSetMap.get(dataset);
        } else if (!datasetExists(dataset)) {
            writeDataset(dataset);
        }
        FeatureSet set = FeatureSetUtils.loadFeatureSet(dataset);
        featureSetMap.put(dataset, set);
        return set;
    }

    @Override
    public double getScore(String dataset, Collection<Feature> selectedFeatures) {
        StringBuilder request = new StringBuilder("/score?dataset=" + dataset + "&features=");
        selectedFeatures.forEach(f -> request.append(f.getID()).append(","));
        request.deleteCharAt(request.length() - 1);
        String response = get(request.toString()).get(String.class);
        return Double.parseDouble(response);
    }

    @Override
    public boolean isValidDataset(String dataset) {
        if (datasetExists(dataset)) {
            return true;
        } else {
            try {
                writeDataset(dataset);
                return true;
            } catch (IOException e) {
                Logger.error(e);
                return false;
            }
        }
    }

    @Override
    public boolean isOnline() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(mlserverUrl + GET_VERSION).openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException e) {
            return false;
        }
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
                new URL(mlserverUrl + "/features?dataset=" + datasetIdentifier), tmpZipFile, TIMEOUT, TIMEOUT);
        ZipFile zipFile = new ZipFile(tmpZipFile);
        String datasetPathname = homeDir + File.separator + datasetIdentifier;
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            InputStream is = zipFile.getInputStream(entry);
            FileUtils.copyInputStreamToFile(is, new File(datasetPathname + File.separator + entry.getName()));
        }
        zipFile.close();
        //noinspection ResultOfMethodCallIgnored
        tmpZipFile.delete();
    }

    private boolean datasetExists(String dataset) {
        File dataSetFolder = new File(homeDir + File.separator + dataset);
        return dataSetFolder.exists();
    }
}
