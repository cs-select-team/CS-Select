package com.csselect.API.httpAPI;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GameCreation extends Servlet {


    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (isPlayer()) throw new HttpError(HttpServletResponse.SC_FORBIDDEN);  // players are never allowed to create games
        switch (req.getPathInfo()) {
            case "/patterns":
                getPatterns(req, resp);
                break;

        }
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (isPlayer()) throw new HttpError(HttpServletResponse.SC_FORBIDDEN);   // players are never allowed to create games
        switch (req.getPathInfo()) {
            case "/setParam":
                setParam(req, resp);
                break;
            case "/savePattern":
                savePattern(req, resp);
                break;
            case "/loadPattern":
                loadPattern(req, resp);
                break;
                default:
                    createGame(req, resp);
        }
    }

    private void loadPattern(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException{
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

    private void setParam(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException{
        String option = getParameter("option", req);
        String data = getParameter("data", req);

        getOrganiserFacade().setGameOption(option, data);
        resp.sendError(HttpServletResponse.SC_ACCEPTED);
    }

    private void createGame(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException{

        if (getOrganiserFacade().createGame()) resp.sendError(HttpServletResponse.SC_ACCEPTED);
        else resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
