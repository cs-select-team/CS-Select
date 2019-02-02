package com.csselect.API;


import com.csselect.Injector;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.csselect.user.Organiser;
import com.csselect.user.management.OrganiserManagement;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

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
     * @return true if registration successfull, false otherwise
     */
    public boolean register(String[] args) {
        OrganiserManagement om = new OrganiserManagement();
        organiser = om.register(args);
        return organiser != null;
    }

    @Override
    public boolean login(String email, String password) {
        OrganiserManagement om = new OrganiserManagement();
        organiser = om.login(email, password);
        return organiser != null;
    }

    @Override
    public void logout() {
        organiser = null;
    }

    @Override
    public void changeEmail(String email) {
        organiser.changeEmail(email);
    }

    @Override
    public void changePassword(String password) {
        organiser.changePassword(password);
    }

    @Override
    public void setLanguage(String languageCode) {
        organiser.setLanguage(languageCode);
    }

    @Override
    public void recoverPassword(String email) {
    }

    @Override
    public void validateEmail() {

    }

    @Override
    public String getLanguage() {
        return organiser.getLanguage();
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
    public Collection<Game> getActiveGames() {
        return organiser.getActiveGames();
    }

    /** gets a list of all terminated games that the logged in organiser has
     *
     * @return list of all terminated games that the logged in organiser has
     *
     */
    public Collection<Game> getTerminatedGames() {
        return organiser.getTerminatedGames();
    }

    /** sets options for the game that the organiser is currently creating
     * if there is no game beeing created right now, this will start the process
     *
     * @param option name of the option
     * @param data value
     */
    public void setGameOption(String option, String data) {
        organiser.setGameOption(option, data);
    }

    /** gets all patterns, that this organiser has ever saved
     *
     * @return All patterns, that were ever saved with {@link APIFacadeOrganiser#savePattern(String)}
     */
    public Collection<Pattern> getPatterns() {
        return organiser.getPatterns();
    }

    /** saves the options that were saved for the current gamecreation as a new pattern
     *
     * @param title title under which to save this pattern. Has to be unique
     */
    public void savePattern(String title) {
        organiser.savePattern(title);
    }

    /** loads the pattern parameter as setting for the current game
     *
     * @param pattern To be loaded
     */
    public void loadPattern(Pattern pattern) {
        organiser.loadPattern(pattern);
    }

    /** ends gamecreation and uses the current settings to create a game
     *
     * @return true if the creation was successful, otherwise false. Probably because not all necessary  options
     *          have been set
     */
    public boolean createGame() {
        organiser.createGame();
        return true;
    }

    /** invites a player to a game. gameId has to belong to a game that this organiser owns
     *
     * @param playerEmail email adress of the player that is to be invited
     * @param gameId id of the game to which to invite the player
     */
    public void invitePlayer(String playerEmail, int gameId) {
        Collection<String> playerEmails = new HashSet<String>();
        playerEmails.add(playerEmail);
        organiser.invitePlayers(playerEmails, gameId);
    }

    /** ends a game that this organiser owns
     *
     * @param gameId id of the game that is to be terminated
     */
    public void terminateGame(int gameId) {
        organiser.terminateGame(gameId);
    }

    /** deletes a game that this organiser owns. the game has to be terminated before, otherwise
     * this method will have no effect
     *
     * @param gameId id of the game that is to be deleted
     */
    public void deleteGame(int gameId) {
        organiser.deleteGame(gameId);
    }

    /** checks if a feature set with the given name exists
     *
     * @throws IOException if the backend failed the connection to the ml Server
     * @param name name of the feature set to look up
     * @return true if a feature set does exist , false otherwise
     */
    public boolean checkFeatureSet(String name) {
        return Injector.getInstance().getMLServer().isValidDataset(name);
    }
    /** checks if a batabse with the given name exists
     *
     * @throws IOException if the backend failed the connection to the database
     * @param name name of the database to look up
     * @return true if the database does exist , false otherwise
     */
    public boolean checkDatabase(String name) throws IOException {
        return Injector.getInstance().getDatabaseAdapter().checkDuplicateDatabase(name);
    }

}
