package com.csselect.API.httpAPI;
import com.csselect.configuration.ConfigurationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * handles requests to "/login"
 */
public class Login extends Servlet {


    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (req.getPathInfo().equals("/logout")) {
            logout(resp);
        }
    }

    private void logout(HttpServletResponse resp) throws IOException {
        if (isPlayer()) {
            getPlayerFacade().logout();
            System.out.println("player logged out");
        } else {
            getOrganiserFacade().logout();
            System.out.println("player logged out");
        }

        resp.sendError(HttpServletResponse.SC_ACCEPTED);
    }
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {
        try {
            if (req.getPathInfo() == null) {
                login(req, resp);
            } else if (req.getPathInfo().equals("/register")) {
                register(req, resp);
            }
        } catch (ExceptionInInitializerError e) {
            resp.sendError(550); // tell the frontend that the config file is missing
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        String email = getParameter("email", req);
        String password = getParameter("password", req);
        String third = getParameter("thirdParam", req);
        boolean success = false;
        if (isSet("organiser", req)) {
            createOrganiser();
            success = getOrganiserFacade().register(new String[]{email, password, third});
            setPlayer(false);
        } else {
            createPlayer();
            success = getPlayerFacade().register(new String[]{email, password, third});
            setPlayer(true);
        }
        if (success) {
            resp.sendError(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        updateLanguage();

    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {

        String email = getParameter("email", req);
        String password = getParameter("password", req);

        if (isSet("organiser", req)) {
            createOrganiser();
            if (getOrganiserFacade().login(email, password)) {
                setPlayer(false);
                resp.sendError(HttpServletResponse.SC_ACCEPTED);
                updateLanguage();
            } else {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            createPlayer();
            if (getPlayerFacade().login(email, password)) {
                setPlayer(true);
                resp.sendError(HttpServletResponse.SC_ACCEPTED);
                updateLanguage();
            } else {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

        }


    }
}
