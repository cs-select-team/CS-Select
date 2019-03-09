package com.csselect.API.httpAPI;

import com.csselect.API.APIFacadeOrganiser;
import com.csselect.API.APIFacadePlayer;
import com.csselect.API.APIFacadeUser;
import com.csselect.game.Game;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/** this class handles requests from a url and provides helpful methods
 *
 */
public abstract  class Servlet extends HttpServlet {
    private static final String PLAYER_FACADE_ATTR_NAME = "playerFacade";
    private static final String ORGANISER_FACADE_ATTR_NAME = "organiserFacade";
    private static final String IS_PLAYER = "player";
    HttpSession session;
    protected String lang;



    private boolean isPlayer;
    private APIFacadePlayer facadePlayer;
    private APIFacadeOrganiser facadeOrganiser;

    /**
     *
     * @return true if the current session belongs to a player, false if organiser or not determined yet
     */
    public boolean isPlayer() {
        return isPlayer;
    }

    /** sets the value of the player variable
     *
     * @param player true if it is player, false otherwise
     */
    public void setPlayer(boolean player) {
        isPlayer = player;
        session.setAttribute(IS_PLAYER, player);
    }

    /**
     *
     *
     * @return the current playerFacade
     */
    protected APIFacadePlayer getPlayerFacade() {
        return facadePlayer;

    }


    /**
     * @return current organiserFacade
     */
    protected APIFacadeOrganiser getOrganiserFacade() {
        return facadeOrganiser;
    }


    private void setup(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        session = req.getSession();
        req.setCharacterEncoding("UTF-8");
        facadeOrganiser = (APIFacadeOrganiser) session.getAttribute(ORGANISER_FACADE_ATTR_NAME);
        facadePlayer = (APIFacadePlayer) session.getAttribute(PLAYER_FACADE_ATTR_NAME);
        lang = (String) session.getAttribute("lang");
        if (session.getAttribute(IS_PLAYER) == null) {
            isPlayer = false;
        } else {
            isPlayer = (boolean) session.getAttribute(IS_PLAYER);
        }
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setup(req, resp);


        try {
            get(req, resp);
        } catch (HttpError e) {
            resp.sendError(e.getErrorCode());
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setup(req, resp);

        try {
            post(req, resp);
        } catch (HttpError e) {
            resp.sendError(e.getErrorCode());
        }
    }

    /** returns the userFacade of this object
     *
     * @return either the PlayerFacade or the organiserFacade, depending on the value of isPlayer
     */
    protected APIFacadeUser getUserFacade() {
        if (isPlayer()) {
            return getPlayerFacade();
        } else {
            return getOrganiserFacade();
        }
    }

    /** checks if a boolean value in the request is set( name=true has to be in the request)
     *
     * @param name key value of the parameter
     * @param req request in which to look for the parameter
     * @return true if the value was set, false otherwise
     * @throws HttpError if the request does not contain the parameter
     */
    protected boolean isSet(String name, HttpServletRequest req) throws HttpError {
        if (req.getParameterMap().containsKey(name)) {
            return getParameter(name, req).equals("true");
        }
        return false;
    }

    /** get parameter securely from request
     *
     * @param name name of the parameter
     * @param req request in which to search the parameter
     * @return the value of the parameter as a string
     *          request: www.example.com/index.jsp?name=value
     *                                                  ^^^^^
     *                                                  this
     * @throws HttpError if name is not present as parameter in the request
     */
    String getParameter(String name, HttpServletRequest req) throws HttpError {
        if (req.getParameterMap().containsKey(name)) {
            return req.getParameter(name);
        }
        throw new HttpError(HttpServletResponse.SC_BAD_REQUEST);

    }

    /** returns a json object to a HttpServletResponse, i.e. to the browser
     *
     * @param resp response to which to send the json object
     * @param o object which to send as json
     * @throws IOException if there was an ioException
     */
    void returnAsJson(HttpServletResponse resp, Object o) throws IOException {
        String json = new Gson().toJson(o);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
        resp.getWriter().close();
    }

    /** returns a json Element over http to the request
     *
     * @param resp response object to  which to return the json Element
     * @param json Element to return
     * @throws IOException if the response failed
     */
    void returnJson(HttpServletResponse resp, JsonElement json) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json.toString());
        resp.getWriter().close();
    }

    /**
     * creates a new OrganiserFacade for this object
     * {@link Servlet#getOrganiserFacade()}
     */
    void createOrganiser() {
        facadeOrganiser = new APIFacadeOrganiser();
        session.setAttribute(ORGANISER_FACADE_ATTR_NAME, facadeOrganiser);
    }
    /**
     * creates a new PlayerFacade for this object
     * {@link Servlet#getPlayerFacade()}
     */
    void createPlayer() {
        facadePlayer = new APIFacadePlayer();
        session.setAttribute(PLAYER_FACADE_ATTR_NAME, facadePlayer);
    }

    /** gets the body of an httpRequest
     *
     * @param request the request from which to get the body
     * @return The Body string of the request
     * @throws IOException if the request is malformed
     */
    static String getBody(HttpServletRequest request) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return stringBuilder.toString();
    }

    /** method to overwrite to handle a GET request that this object receives
     * session and isPlayer and the facades will be set, this means you will most likely not need
     * to access the session
     *
     * @param req the request
     * @param resp the response to which to response
     * @throws HttpError will return the value of HttpError.errorCode as http code to the client
     * @throws IOException if there are errors in IO
     */
    public abstract void get(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException;
    /** method to overwrite to handle a POST request that this object receives
     * session and isPlayer and the facades will be set, this means you will most likely not need
     * to access the session
     *
     * @param req the request
     * @param resp the response to which to response
     * @throws HttpError will return the value of HttpError.errorCode as http code to the client
     * @throws IOException if there are errors in IO
     */
    public abstract void post(HttpServletRequest req, HttpServletResponse resp) throws HttpError, IOException;

    /** updates the lang attribute and the session attribute with the users
     *  language from the database
     *
     * @throws NullPointerException if no user is logged in
     */
    void updateLanguage() throws NullPointerException {
        lang = getUserFacade().getLanguage();
        session.setAttribute("lang", lang);
    }

    /**
     * converts a game to a json object
     * @param game game to convert
     * @return json object with id, title, roundsPlayer, type, desc, termination
     */
    protected JsonObject gameToJson(Game game) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", game.getId());
        jsonObject.addProperty("title", game.getTitle());
        jsonObject.addProperty("roundsPlayed", game.getNumberOfRounds());
        jsonObject.addProperty("type", game.getGamemode().getName());
        jsonObject.addProperty("desc", game.getDescription());
        jsonObject.addProperty("termination", game.getTermination().toString());
        return jsonObject;
    }
}
