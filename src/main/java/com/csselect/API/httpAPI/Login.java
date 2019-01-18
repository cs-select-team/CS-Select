package com.csselect.API.httpAPI;
import com.csselect.API.APIFacadePlayer;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.game.Game;
import com.csselect.user.Organiser;
import com.csselect.user.Player;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class Login extends Servlet {


    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        if (req.getPathInfo().equals("/logout")) logout(resp);
    }

    private void logout(HttpServletResponse resp) throws IOException {
        if (isPlayer()) {
            getPlayerFacade().logout();
        } else {
            getOrganiserFacade().logout();
        }

        resp.sendError(HttpServletResponse.SC_ACCEPTED);
    }
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {
        if (req.getPathInfo() == null) {
            login(req, resp);
        } else if (req.getPathInfo().equals("register")) {
            register(req, resp);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        String email = getParameter("email", req);
        String password = getParameter("password", req);
        if (isSet("organiser", req)) {
            //
        } else {
            //
        }
        resp.sendError(HttpServletResponse.SC_OK);
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError {

        String email = getParameter("email", req);
        String password = getParameter("password", req);

        if (isSet("organiser", req)) {
            if (getOrganiserFacade().login(email, password)) {
                resp.sendError(HttpServletResponse.SC_ACCEPTED);
                setPlayer(false);
            } else {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            if (getPlayerFacade().login(email, password)) {
                setPlayer(true);
                resp.sendError(HttpServletResponse.SC_ACCEPTED);
            } else {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

        }


    }
}
