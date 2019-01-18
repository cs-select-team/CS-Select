package com.csselect.API.httpAPI;
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
        String third = getParameter("thirdParam", req);
        boolean success = false;
        if (isSet("organiser", req)) {
            success = getOrganiserFacade().register(new String[]{email, password, third});
        } else {
            success = getPlayerFacade().register(new String[]{email, password, third});
        }
        if (success) {
            resp.sendError(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
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
