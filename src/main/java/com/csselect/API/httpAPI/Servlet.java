package com.csselect.API.httpAPI;

import com.csselect.API.APIFacadeOrganiser;
import com.csselect.API.APIFacadePlayer;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract  class Servlet extends HttpServlet {
    public static final String PLAYERFACADE_ATTR_NAME = "playerFacade";
    public static final String ORGANISERFACADE_ATTR_NAME = "organiserFacade";
    public static final String IS_PLAYER = "player";
    protected HttpSession session;

    /**
     *
     * @return true if the current session belongs to a player, false if organiser or not determined yet
     */
    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
        session.setAttribute(IS_PLAYER, isPlayer);
    }

    private boolean isPlayer;
    protected APIFacadePlayer facadePlayer;
    protected APIFacadeOrganiser facadeOrganiser;

    protected APIFacadePlayer getPlayerFacade() {
        if (facadePlayer == null)
        facadePlayer = (APIFacadePlayer) session.getAttribute(PLAYERFACADE_ATTR_NAME);
        if (facadePlayer == null) createPlayer();
        return facadePlayer;

    }

    private void createOrganiser() {
        facadeOrganiser = new APIFacadeOrganiser();
        session.setAttribute(ORGANISERFACADE_ATTR_NAME, facadePlayer);
    }

    private void createPlayer() {
        facadePlayer = new APIFacadePlayer();
        session.setAttribute(PLAYERFACADE_ATTR_NAME, facadePlayer);
    }

    protected APIFacadeOrganiser getOrganiserFacade() {
        if (facadeOrganiser == null)
            facadeOrganiser = (APIFacadeOrganiser) session.getAttribute(ORGANISERFACADE_ATTR_NAME);
        if (facadeOrganiser == null) createOrganiser();
        return facadeOrganiser;
    }


    private void setup (HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        getOrganiserFacade();
        getPlayerFacade();
        if (session.getAttribute(IS_PLAYER) == null) setPlayer(false);
        else isPlayer = (boolean) session.getAttribute(IS_PLAYER);
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setup(req, resp);


        try {
            get(req,resp);
        }
        catch (HttpError e) {
            resp.sendError(e.getErrorCode());
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setup(req, resp);

        try {
            post(req,resp);
        }
        catch (HttpError e) {
            resp.sendError(e.getErrorCode());
        }
    }

    /*
    method to get parameters securely from request
     */
    protected String getParameter(String name, HttpServletRequest req) throws HttpError{
        if (req.getParameterMap().containsKey(name)) {
            return req.getParameter(name);
        }
        throw new HttpError(HttpServletResponse.SC_BAD_REQUEST);

    }
    protected void returnAsJson(HttpServletResponse resp, Object o) throws IOException{
        String json = new Gson().toJson(o);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
        resp.getWriter().close();
    }
    public abstract void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException;
    public abstract void post(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException;
}
