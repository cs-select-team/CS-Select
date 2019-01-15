package com.csselect.API.httpAPI;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * only request from organisers in this class.
 */
public class GameManagement extends Servlet {


    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (isPlayer()) {
            throw new HttpError(HttpServletResponse.SC_FORBIDDEN);
        }
        if (req.getPathInfo().equals("/patterns")) {
            getPatterns(req, resp);
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
        } else {
            createGame(req, resp);
        }
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

    private void getPatterns(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        returnAsJson(resp, getOrganiserFacade().getPatterns());
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

    private void createGame(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {

        if (getOrganiserFacade().createGame()) {
            resp.sendError(HttpServletResponse.SC_ACCEPTED);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
