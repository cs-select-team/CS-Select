package com.csselect.API.httpAPI;
import com.csselect.game.Game;
import com.csselect.gamification.Achievement;
import com.csselect.gamification.AchievementState;
import com.csselect.gamification.DailyChallenge;
import com.csselect.user.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
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
        } else if (requestString.matches("/achievements")) {
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
        }
    }

    private void setEmail(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        try {
            getUserFacade().changeEmail(getParameter("email", req));
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT);
        }
    }

    private void setPassword(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        getUserFacade().changePassword(getParameter("password", req));
    }

    private void setLanguage(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        getUserFacade().setLanguage(getParameter("lang", req));
        updateLanguage();
    }

    private void getNotifications(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {
        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        Collection<Game> notifications = getPlayerFacade().getNotifications();
        JsonArray array = new JsonArray();
        for (Game invite: notifications) {
            array.add(gameToJson(invite));
        }
        returnAsJson(resp, array);
    }


    private void getStreak(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {
        if (!isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        returnAsJson(resp, getPlayerFacade().getStreak().getCounter());
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
            int points = ((int) pair.getValue());
            Player p = ((Player) pair.getKey());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", p.getUsername());
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

        JsonArray array = new JsonArray();
        Collection<Achievement> achievementCollection = getPlayerFacade().getAchievments();
        for (Achievement ach: achievementCollection) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("state", convertStateToInt(ach.getState()));
            jsonObject.addProperty("desc", ach.getType().getDescription(lang));
            jsonObject.addProperty("name", ach.getType().getName(lang));
            array.add(jsonObject);
        }
        returnJson(resp, array);
    }

    private int convertStateToInt(AchievementState state) {
        switch (state) {
            case INVISIBLE: return 0;
            case CONCEALED: return 1;
            case SHOWN: return 2;
            case FINISHED: return 3;
            default: return -1;
        }
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
        DailyChallenge d = getPlayerFacade().getDaily();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("points", d.getReward());
        jsonObject.addProperty("title", d.getDescription(lang));
        jsonObject.addProperty("finished", d.isCompleted());
        returnJson(resp, jsonObject);
    }


    private void getUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (isPlayer()) {
            JsonObject json = new JsonObject();
            json.addProperty("username", getPlayerFacade().getPlayer().getUsername());
            json.addProperty("points", getPlayerFacade().getScore());
            returnJson(resp, json);
        } else {
            returnAsJson(resp, getOrganiserFacade().getOrganiser());
        }
    }


}
