package com.csselect.API.httpAPI;
import com.csselect.user.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * handles requests to /users
 */
public class Users extends Servlet {

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        String requestString = req.getPathInfo();
        if (requestString == null) {
            getUser(req, resp);
        } else if (requestString.matches("/daily")) {
            getDaily(req, resp);
        } else if (requestString.matches("/achievments")) {
            getAchievments(req, resp);
        } else if (requestString.matches("/leaderboard")) {
            getLeaderboard(req, resp);
        } else if (requestString.matches("/streak")) {
            getStreak(req, resp);
        } else if (requestString.matches("/score")) {
            getScore(req, resp);
        } else if (requestString.matches("/notifications")) {
            getNotifications(req, resp);
        }
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        String requestString = req.getPathInfo();
        if (requestString == null) {
            throw  new HttpError(HttpServletResponse.SC_BAD_REQUEST);
        } else if (requestString.matches("/setEmail")) {
            setEmail(req, resp);
        } else if (requestString.matches("/setPassword")) {
            setPassword(req, resp);
        } else if (requestString.matches("/setLanguage")) {
            setLanguage(req, resp);
        } else if (requestString.matches("/recoverPassword")) {
            recoverPassword(req, resp);
        } else if (requestString.matches("/validateEmail")) {
            validateEmail(req, resp);
        }
    }

    private void setEmail(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        getUserFacade().changeEmail(getParameter("email", req));
    }

    private void setPassword(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        getUserFacade().changePassword(getParameter("password", req));
    }

    private void setLanguage(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        getUserFacade().setLanguage(getParameter("lang", req));
    }

    private void recoverPassword(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        getUserFacade().recoverPassword(getParameter("email", req));
    }

    private void validateEmail(HttpServletRequest req, HttpServletResponse resp) {
        getUserFacade().validateEmail();
    }

    private void getNotifications(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {
        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        returnAsJson(resp, getPlayerFacade().getNotifications());
    }



    private void getStreak(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {
        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        returnAsJson(resp, getPlayerFacade().getStreak());
    }

    private void getLeaderboard(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {
        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        Map<Player, Integer> leaderboard = getPlayerFacade().getLeaderboard();
        JsonArray array = new JsonArray();
        Iterator it = leaderboard.entrySet().iterator();
        int place = 0;
        while (it.hasNext()) {
            place++;
            Map.Entry pair = (Map.Entry) it.next();
            Player p = ((Player) pair.getValue());
            int points = ((int) pair.getKey());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", "Bendix"); // TODO change if the player.getUsername method is available
            jsonObject.addProperty("points", points);
            jsonObject.addProperty("place", place);
            array.add(jsonObject);
            it.remove(); // avoids a ConcurrentModificationException
        }
        returnJson(resp, array);
    }

    private void getAchievments(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {
        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        returnAsJson(resp, getPlayerFacade().getAchievments());
    }

    private void getScore(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        returnAsJson(resp, getPlayerFacade().getScore());
    }

    private void getDaily(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        returnAsJson(resp, getPlayerFacade().getDaily());
    }


    private void getUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (isPlayer()) {
            JsonObject json = new JsonObject();
            json.addProperty("username", "Benidx"); // TODO add username
            json.addProperty("points", getPlayerFacade().getScore() );
            returnJson(resp, json);
        } else {
            returnAsJson(resp, getOrganiserFacade().getOrganiser());
        }
    }


}
