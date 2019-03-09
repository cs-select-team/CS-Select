package com.csselect.API.httpAPI;

import com.csselect.API.APIFacadePlayer;
import com.csselect.game.Feature;
import com.csselect.game.Game;
import com.csselect.game.Gamemode;
import com.csselect.utils.Localisation;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.pmw.tinylog.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * only request from players.
 * handles '/games/*'
 */
public class Games extends Servlet {

    private static final String BASE_64_PNG_PREFIX = "data:image/png;base64,";
    private static final String IMAGE_TYPE = "PNG";


    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        /*
         this method only parses the request string and redirects the call
         */
        String requestString = req.getPathInfo();
        if (requestString == null) {
            getGames(req, resp);
        } else if (requestString.matches("/[0-9]+")) {
            getGame(req, resp);
        }


    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {

        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        String requestString = req.getPathInfo();
        if (requestString.matches("/[0-9]+/accept")) {
            acceptInvite(req, resp);
        } else if (requestString.matches("/[0-9]+/decline")) {
            declineInvite(req, resp);
        } else if (requestString.matches("/[0-9]+/play")) {
            playRound(req, resp);
        } else if (requestString.matches("/[0-9]+/skip")) {
            skipRound(req, resp);
        } else if (requestString.matches("/[0-9]+/start")) {
            startRound(req, resp);
        }
    }


    private void skipRound(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String body = getBody(req);
        JsonObject json = new Gson().fromJson(body, JsonObject.class);
        int[] useless = convertJsonPrimitiveToIntArray(json.getAsJsonPrimitive("useless"));
        getPlayerFacade().skipRound(useless);
    }

    private void playRound(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String body = getBody(req);
        JsonObject json = new Gson().fromJson(body, JsonObject.class);
        int[] selected = convertJsonPrimitiveToIntArray(json.getAsJsonPrimitive("selected"));
        int[] useless = convertJsonPrimitiveToIntArray(json.getAsJsonPrimitive("useless"));
        returnAsJson(resp, getPlayerFacade().selectFeatures(selected, useless));
    }

    private int[] convertJsonPrimitiveToIntArray(JsonPrimitive primitive) {
        String p = primitive.getAsString();
        p = p.replace("[", "");
        p = p.replace("]", "");
        // if no integer was in the array to begin with
        if ("".equals(p)) {
            return new int[0];
        }
        String[] arrayOfStrings = p.split(",");
        int[] returnval = new int[arrayOfStrings.length];
        for (int i = 0; i < arrayOfStrings.length; i++) {

            returnval[i] = Integer.parseInt(arrayOfStrings[i]);
        }
        return returnval;
    }
    private void declineInvite(HttpServletRequest req, HttpServletResponse resp) {

        getPlayerFacade().declineInvite(getId(req.getPathInfo()));
    }

    private void acceptInvite(HttpServletRequest req, HttpServletResponse resp) {

        getPlayerFacade().acceptInvite(getId(req.getPathInfo()));
    }

    /* should return a json object of the form
        { listOfFeatures : [{id: , desc:, name:, graph1:(base64img), graph2: (base64img)}],
            options: {} // gamemode specific


        }
     */
    private void startRound(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int gameId = getId(req.getPathInfo());
        JsonObject jsonObject = new JsonObject();
        JsonArray featureList = new JsonArray();
        Collection<Feature> featureCollection;
        try {
            featureCollection = getPlayerFacade().startRound(gameId);
            if (featureCollection == null) {
                resp.sendError(HttpServletResponse.SC_NO_CONTENT);
                return;
            }
            for (Feature feature: featureCollection) {
                String desc = feature.getDescription(lang) + "<br>"
                        + Localisation.get(lang, "statvalues") + feature.getValue("values") + "<br>";
                if (feature.hasValue(Feature.STAT_MIN_KEY)) {
                    desc = desc + Localisation.get(lang, "statmin") + feature.getValue(Feature.STAT_MIN_KEY) + " "
                                + Localisation.get(lang, "stat1q") + feature.getValue(Feature.STAT_1QU_KEY) + " "
                                + Localisation.get(lang, "statmedian") + feature.getValue(Feature.STAT_MEDIAN_KEY) + " "
                                + Localisation.get(lang, "statmean") + feature.getValue(Feature.STAT_MEAN_KEY) + " "
                                + Localisation.get(lang, "stat3q") + feature.getValue(Feature.STAT_3QU_KEY) + " "
                                + Localisation.get(lang, "statmax") + feature.getValue(Feature.STAT_MAX_KEY);
                }
                JsonObject jsonFeature = new JsonObject();
                jsonFeature.addProperty("id", feature.getID());
                jsonFeature.addProperty("desc", desc);
                jsonFeature.addProperty("name", feature.getName(lang));
                jsonFeature.addProperty("graph1", encodeToString(feature.getClassGraph(), IMAGE_TYPE));
                jsonFeature.addProperty("graph2", encodeToString(feature.getTotalGraph(), IMAGE_TYPE));
                featureList.add(jsonFeature);
            }
            jsonObject.add("listOfFeatures", featureList);
            Gamemode gm = getPlayerFacade().getGame(gameId).getGamemode();
            JsonObject options = new Gson().fromJson(new Gson().toJson(gm), JsonObject.class);
            jsonObject.add("options", options);
            jsonObject.addProperty("gameType", gm.getName());
            returnJson(resp, jsonObject);
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_NO_CONTENT);
        }
    }




    private void getGames(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Collection<Game> games = getPlayerFacade().getGames();
        JsonArray json = new JsonArray();
        for (Game game:games) {


            json.add(gameToJson(game));
        }
        returnJson(resp, json);
    }



    private void getGame(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        APIFacadePlayer facade = getPlayerFacade();
        // getting the game id
        int gameId = Integer.parseInt(req.getPathInfo().substring(1));
        returnAsJson(resp, facade.getGame(gameId));
    }

    private int getId(String urlPath) {
        Pattern p = Pattern.compile("/([0-9]+)*");
        Matcher m = p.matcher(urlPath);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    private static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            imageString = Base64.getEncoder().encodeToString(imageBytes);

            bos.close();
        } catch (IOException e) {
            Logger.error(e);
        }
        return BASE_64_PNG_PREFIX + imageString;
    }

}
