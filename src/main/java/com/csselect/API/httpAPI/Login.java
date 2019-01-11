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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("playerFacade", new APIFacadePlayer());
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {

    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws IOException, HttpError{
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String organiserSwitch = req.getParameter("organiser");

        if(organiserSwitch.equals("on")) {
            if (getOrganiserFacade().login(email, password)) resp.sendError(HttpServletResponse.SC_ACCEPTED);
            else resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else {
            if (getPlayerFacade().login(email, password)) resp.sendError(HttpServletResponse.SC_ACCEPTED);
            else resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
