package com.csselect.API;

import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.csselect.user.Organiser;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/** Facade for all calls that are specific to organisers
 *
 */
public class APIFacadeOrganiser extends APIFacadeUser {
    private Organiser organiser;

    /** registers a new organiser. This will create a new Organiser in the database
     *
     * @param args String array of arguments for registration
     *             0: email
     *             1: password
     *             2: masterpassword
     */
    public void register(String[] args) {

    }

    /** returns the logged in organiser that is assoziated with this object
     *
     * @return the organiser
     */
    public Organiser getOrganiser() {
        return organiser;
    }

    /** gets a list of all active games that the logged in organiser has
     *
     * @return list of all active games that the logged in organiser has
     */
    public List<Game> getActiveGames() {
        return new LinkedList<>();
    }

    /** gets a list of all terminated games that the logged in organiser has
     *
     * @return list of all terminated games that the logged in organiser has
     *
     */
    public List<Game> getTerminatedGames() {
        return new LinkedList<>();
    }

    /** sets options for the game that the organiser is currently creating
     * if there is no game beeing created right now, this will start the process
     *
     * @param option name of the option
     * @param data value
     */
    public void setGameOption(String option, String data) {

    }

    /** gets all patterns, that this organiser has ever saved
     *
     * @return All patterns, that were ever saved with {@link APIFacadeOrganiser#savePattern(String)}
     */
    public Collection<Pattern> getPatterns() {
        return new LinkedList<>();
    }

    /** saves the options that were saved for the current gamecreation as a new pattern
     *
     * @param title title under which to save this pattern. Has to be unique
     */
    public void savePattern(String title) {

    }

    /** loads the pattern parameter as setting for the current game
     *
     * @param pattern To be loaded
     */
    public void loadPattern(Pattern pattern) {

    }

    /** ends gamecreation and uses the current settings to create a game
     *
     * @return true if the creation was successful, otherwise false. Probably because not all necessary  options
     *          have been set
     */
    public boolean createGame() {
        return false;
    }

    /** invites a player to a game. gameId has to belong to a game that this organiser owns
     *
     * @param playerEmail email adress of the player that is to be invited
     * @param gameId id of the game to which to invite the player
     */
    public void invitePlayer(String playerEmail, int gameId) {

    }

    /** ends a game that this organiser owns
     *
     * @param gameId id of the game that is to be terminated
     */
    public void terminateGame(int gameId) {

    }

    /** deletes a game that this organiser owns. the game has to be terminated before, otherwise
     * this method will have no effect
     *
     * @param gameId id of the game that is to be deleted
     */
    public void deleteGame(int gameId) {

    }

}
