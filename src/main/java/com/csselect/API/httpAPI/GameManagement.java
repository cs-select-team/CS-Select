package com.csselect.API.httpAPI;
import com.csselect.game.Game;
import com.csselect.game.Termination;
import com.csselect.game.gamecreation.patterns.GameOptions;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * only request from organisers in this class.
 * handles requests to '/create'
 */
public class GameManagement extends Servlet {


    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        if (req.getPathInfo().equals("/patterns")) {
            getPatterns(req, resp);
        } else if (req.getPathInfo().equals("/active")) {
            getActiveGames(req, resp);
        } else if (req.getPathInfo().equals("/terminated")) {
            getTerminatedGames(req, resp);
        } else if (req.getPathInfo().matches("/exists.*")) {
            checkExist(req, resp);
        } else if (req.getPathInfo().matches("/dbexists.*")) {
            checkDatabase(req, resp);
        } else if (req.getPathInfo().matches("/titleexists.*")) {
            checkTitleExists(req, resp);
        }
    }

    private void checkTitleExists(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {
        String title = getParameter("name", req);
        boolean exists = getOrganiserFacade().gameTitleInUse(title);
        returnAsJson(resp, exists);
    }

    private void checkDatabase(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {
        String name = getParameter("name", req);
        returnAsJson(resp, getOrganiserFacade().checkDatabase(name));

    }

    private void checkExist(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        String name = getParameter("name", req);
        try {
            boolean exists = getOrganiserFacade().checkFeatureSet(name);
            returnAsJson(resp, exists);
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT);
        }


    }


    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        String s = req.getPathInfo();
        if ("/setParam".equals(s)) {
            setParam(req, resp);
        } else if ("/savePattern".equals(s)) {
            savePattern(req, resp);
        } else if ("/loadPattern".equals(s)) {
            loadPattern(req, resp);
        } else if ("/invite".equals(s)) {
            invitePlayer(req, resp);
        } else if ("/terminate".equals(s)) {
            terminate(req, resp);
        } else if ("/delete".equals(s)) {
            delete(req, resp);
        } else if ("/patternFromGame".equals(s)) {
            createPatternFromGame(req, resp);
        } else {
            createGame(req, resp);
        }
    }

    private void createPatternFromGame(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        int gameId = Integer.parseInt(getParameter("gameId", req));
        String title = getParameter("title", req);
        getOrganiserFacade().createPatternFromGame(gameId, title);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        int gameId = Integer.parseInt(getParameter("gameId", req));
        getOrganiserFacade().deleteGame(gameId);
    }

    private void terminate(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        int gameId = Integer.parseInt(getParameter("gameId", req));
        getOrganiserFacade().terminateGame(gameId);
    }

    private void invitePlayer(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        String email = getParameter("email", req);
        int gameId = Integer.parseInt(getParameter("gameId", req));
        getOrganiserFacade().invitePlayer(email, gameId);
    }

    private void loadPattern(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        Pattern p = new Gson().fromJson(getParameter("pattern", req), Pattern.class);
        getOrganiserFacade().loadPattern(p);

        resp.sendError(HttpServletResponse.SC_ACCEPTED);
    }

    /*
        sends an json object with the format:
        [{
            title: <patternTitle>,
            gameOptions: {
                title: <gameTitle>,
                desc: <gameDescription>,
                database: <database>,
                termination: {
                    type: <ClassName of termination>,
                    value: <string with the parameters of the termination>
                },
                invites: [<emails of users to invite>]
            }
        }]
     */
    private void getPatterns(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Collection<Pattern> patterns = getOrganiserFacade().getPatterns();
        JsonArray array = new JsonArray();
        for (Pattern p: patterns) {
            JsonObject object = new JsonObject();
            object.addProperty("title", p.getTitle());


            JsonObject gameOptionsJson = new JsonObject();
            GameOptions go = p.getGameOptions();
            gameOptionsJson.addProperty("featureset", go.getDataset());
            gameOptionsJson.addProperty("desc", go.getDescription());
            gameOptionsJson.addProperty("title", go.getTitle());

            gameOptionsJson.addProperty("database", go.getResultDatabaseName());

            gameOptionsJson.addProperty("gamemodeConf", go.getGamemode().toString());

            Termination termination = go.getTermination();
            gameOptionsJson.addProperty("termination", termination.toString());

            JsonArray invitations = new JsonArray();
            for (String email: go.getInvitedEmails()) {
                invitations.add(email);
            }

            gameOptionsJson.add("invites", invitations);
            object.add("gameOptions", gameOptionsJson);

            array.add(object);
        }
        returnJson(resp, array);
    }

    private void savePattern(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        getOrganiserFacade().savePattern(getParameter("title", req));
    }

    private void setParam(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        String option = getParameter("option", req);
        String data = getParameter("data", req);

        getOrganiserFacade().setGameOption(option, data);
        resp.sendError(HttpServletResponse.SC_ACCEPTED);
    }

    private void createGame(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (getOrganiserFacade().createGame()) {
            resp.sendError(HttpServletResponse.SC_ACCEPTED);
        } else {
            resp.sendError(551);
        }
    }

    private void getActiveGames(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Collection<Game> activeGames = getOrganiserFacade().getActiveGames();
        sendGameArray(resp, activeGames);
    }
    private void getTerminatedGames(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Collection<Game> terminatedGames = getOrganiserFacade().getTerminatedGames();
        sendGameArray(resp, terminatedGames);
    }

    private void sendGameArray(HttpServletResponse resp, Collection<Game> terminatedGames) throws IOException {
        JsonArray array = new JsonArray();
        for (Game game: terminatedGames) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("title", game.getTitle());
            jsonObject.addProperty("id", game.getId());
            jsonObject.addProperty("type", game.getGamemode().getName());
            jsonObject.addProperty("desc", game.getDescription());
            jsonObject.addProperty("roundsPlayed", game.getNumberOfRounds());
            array.add(jsonObject);
        }
        returnJson(resp, array);
    }
}
