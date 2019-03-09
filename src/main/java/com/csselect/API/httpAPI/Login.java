package com.csselect.API.httpAPI;

import com.csselect.configuration.ConfigurationException;
import com.csselect.database.DatabaseException;
import com.csselect.user.management.OrganiserManagement;
import com.csselect.user.management.PlayerManagement;
import org.pmw.tinylog.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * handles requests to "/login"
 */
public class Login extends Servlet {


    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getPathInfo().equals("/logout")) {
            logout(resp);
        }
    }

    private void logout(HttpServletResponse resp) throws IOException {
        if (isPlayer()) {
            getPlayerFacade().logout();
            Logger.debug("player logged out");
        } else {
            getOrganiserFacade().logout();
            Logger.debug("organiser logged out");
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
            } else if (req.getPathInfo().equals("/reset")) {
                reset(req, resp);
            }
        } catch (ConfigurationException e) {
            resp.sendError(550); // tell the frontend that the config file is missing
        } catch (DatabaseException e) {
            resp.sendError(552); // tell frontend the database is not online
        }
    }

    private void reset(HttpServletRequest req, HttpServletResponse resp) throws HttpError {
        String email = getParameter("email", req);
        if (isSet("organiser", req)) {
            new OrganiserManagement().resetPassword(email);
        } else {
            new PlayerManagement().resetPassword(email);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        String email = getParameter("email", req);
        String second = getParameter("secondParam", req);
        boolean success = false;
        try {
            if (isSet("organiser", req)) {
                createOrganiser();
                success = getOrganiserFacade().register(email, second);
            } else {
                createPlayer();
                success = getPlayerFacade().register(email, second);
            }
        } catch (IllegalArgumentException e) {
            switch (e.getMessage()) {
                case OrganiserManagement.EMAIL_IN_USE:
                    resp.sendError(409, e.getMessage());
                    return;
                case OrganiserManagement.MASTER_PASSWORD_INCORRECT:
                    resp.sendError(401, e.getMessage());
                    return;
                case PlayerManagement.USERNAME_IN_USE:
                    resp.sendError(450, e.getMessage());
                    return;
                default:
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
            }
        }
        if (success) {
            resp.sendError(HttpServletResponse.SC_OK);
        }
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
