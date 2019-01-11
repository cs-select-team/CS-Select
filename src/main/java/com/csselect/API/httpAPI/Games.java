package com.csselect.API.httpAPI;
import com.csselect.API.APIFacadePlayer;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Games extends Servlet {


    public void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException {
        /*
         this method only parses the request string and redirects the call
         */
        String requestString = req.getPathInfo();
        if (requestString.matches("/[0-9]+")) getGame(req, resp);

    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) {

    }

    public void getGame(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException{
        APIFacadePlayer facade = getPlayerFacade();
        // getting the game id
        int gameId = Integer.parseInt(req.getPathInfo().substring(1));
        returnAsJson(resp, facade.getGame(gameId));
    }

}
