package com.csselect.API.httpAPI;

import com.csselect.API.APIFacadeOrganiser;
import com.csselect.API.APIFacadePlayer;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract  class Servlet extends HttpServlet {
    public static final String PLAYERFACADE_ATTR_NAME = "playerFacade";
    public static final String ORGANISERFACADE_ATTR_NAME = "organiserFacade";

    protected APIFacadePlayer facadePlayer;
    protected APIFacadeOrganiser facadeOrganiser;

    protected  APIFacadePlayer getPlayerFacade() {
        return facadePlayer;
    }
    protected APIFacadePlayer getPlayerFacade(HttpServletRequest req) {
        if (facadePlayer == null)
        facadePlayer = (APIFacadePlayer) req.getSession().getAttribute(PLAYERFACADE_ATTR_NAME);

        if (facadePlayer == null) createPlayer(req);
        return getPlayerFacade();

    }

    private void createOrganiser(HttpServletRequest req) {
        facadeOrganiser = new APIFacadeOrganiser();
        req.getSession().setAttribute(ORGANISERFACADE_ATTR_NAME, facadePlayer);
    }

    private void createPlayer(HttpServletRequest req) {
        facadePlayer = new APIFacadePlayer();
        req.getSession().setAttribute(PLAYERFACADE_ATTR_NAME, facadePlayer);
    }
    protected APIFacadeOrganiser getOrganiserFacade() {
        return facadeOrganiser;
    }
    protected APIFacadeOrganiser getOrganiserFacade(HttpServletRequest req) {
        if (facadeOrganiser == null)
            facadeOrganiser = (APIFacadeOrganiser) req.getSession().getAttribute(ORGANISERFACADE_ATTR_NAME);
        if (facadeOrganiser == null) createOrganiser(req);
        return getOrganiserFacade();
    }
    protected void returnAsJson(HttpServletResponse resp, Object o) throws IOException{
        String json = new Gson().toJson(o);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
        resp.getWriter().close();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        getOrganiserFacade(req);
        getPlayerFacade(req);

        try {
            get(req,resp);
        }
        catch (HttpError e) {
            resp.sendError(e.getErrorCode());
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        getOrganiserFacade(req);
        getPlayerFacade(req);

        try {
            post(req,resp);
        }
        catch (HttpError e) {
            resp.sendError(e.getErrorCode());
        }
    }

    public abstract void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException;
    public abstract void post(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException;
}
