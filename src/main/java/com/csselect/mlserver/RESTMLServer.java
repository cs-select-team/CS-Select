package com.csselect.mlserver;

import com.csselect.configuration.Configuration;
import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.google.gson.Gson;
import com.google.inject.Inject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.util.Collection;
import java.util.Properties;

/**
 * Rest Implementation of the {@link MLServer} Interface
 */
public class RESTMLServer implements MLServer {

    private final Client client;
    private final String mlserverUrl;

    /**
     * Constructor to instantiate a {@link RESTMLServer}
     * @param configuration configuration to be used by the server. Injected by guice
     */
    @Inject
    RESTMLServer(Configuration configuration) {
        this.client = ClientBuilder.newClient();
        this.mlserverUrl = "http://" + configuration.getMLServerURL();
    }

    @Override
    public String getVersion() {
        String response = get("/version", String.class);
        Gson gson = new Gson();
        Properties data = gson.fromJson(response, Properties.class);
        return data.getProperty("APIVersion");
    }

    @Override
    public FeatureSet getFeatures(String dataset) {
        return null;
    }

    @Override
    public double getScore(String dataset, Collection<Feature> selectedFeatures) {
        return 0;
    }

    private <T> T get(String request, Class<T> type) {
        WebTarget target = client.target(mlserverUrl + request);
        Invocation.Builder builder = target.request();
        return builder.get(type);
    }
}
