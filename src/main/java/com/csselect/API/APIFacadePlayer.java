package com.csselect.API;

import com.csselect.game.Feature;
import com.csselect.game.Game;
import com.csselect.gamification.Achievement;
import com.csselect.gamification.DailyChallenge;
import com.csselect.gamification.Streak;
import com.csselect.user.Player;
import com.csselect.user.management.PlayerManagement;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * API facade for player specific calls
 * this class is the entrypoint for the system. everything that you want to do with cs-select should be done through
 * the API
 *
 * @author bendix
 */
public class APIFacadePlayer extends APIFacadeUser {


    private Player player;

    /**
     * registers a new player
     *
     * @param email players email address
     * @param username players username
     * @return true if registration successful, false otherwise
     */
    public boolean register(String email, String username) {
        PlayerManagement pm = new PlayerManagement();
        player = pm.register(email, username);
        return player != null;
    }

    @Override
    public boolean login(String email, String password) {
        PlayerManagement pm = new PlayerManagement();
        player = pm.login(email, password);
        return player != null;
    }

    @Override
    public void logout() {
        player.logout();
        player = null;
    }

    @Override
    public void changeEmail(String email) throws IllegalArgumentException {
        player.changeEmail(email);

    }

    @Override
    public void changePassword(String password) {
        player.changePassword(password);
    }

    @Override
    public void setLanguage(String languageCode) {
        player.setLanguage(languageCode);
    }


    @Override
    public String getLanguage() {
        return player.getLanguage();
    }

    /**
     * getter for the player associated with this facade
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * gets the game with the given gameid. The currently logged in player has to be a participant in the game
     *
     * @param gameId id of the game which to get
     * @return game with id gameId or null if the player is not participant in the game
     */
    public Game getGame(int gameId) {
        return player.getGame(gameId);
    }

    /**
     * gets a list of all games in which the logged in player participates
     *
     * @return All games which this Player participates in
     */
    public Collection<Game> getGames() {
        return player.getGames();
    }

    /**
     * acceptInvite for a game to which the player has been invited
     *
     * @param gameId id of the game of which to accept the invite
     */
    public void acceptInvite(int gameId) {
        player.acceptInvite(gameId);
    }

    /**
     * declines the invite for a game
     *
     * @param gameId game of which to decline the invite
     */
    public void declineInvite(int gameId) {
        player.declineInvite(gameId);
    }

    /**
     * starts a round in the game with gameId
     *
     * @param gameId game of which to start the round
     * @return All the features that are to be displayed this round
     */
    public Collection<Feature> startRound(int gameId) {
        return player.startRound(gameId);
    }

    /**
     * plays the current round
     *
     * @param selected ids of the selected Features
     * @param useless  ids of the features that are marked useless
     * @return the points that the user achieved with their selection
     */
    public int selectFeatures(int[] selected, int[] useless) {
        return player.selectFeatures(selected, useless);
    }


    /**
     * skips the current round
     *
     * @param features ids of the features that are to be marked useless
     */
    public void skipRound(int[] features) { // TODO besserer Parametername
        player.skipRound(features);
    }

    /**
     * gets a list of all achievements and their state for this player
     *
     * @return list of all achievements and their state for this player
     */
    public List<Achievement> getAchievments() {
        return player.getStats().getAchievements();
    }

    /**
     * gets a leaderboard (so an ordered list of players)
     *
     * @return leaderboard, first entrance has the most points
     */
    public Map<Player, Integer> getLeaderboard() {
        return player.getLeaderboard();
    }

    /**
     * gets the current daily challenge
     *
     * @return current daily challange
     */
    public DailyChallenge getDaily() {
        return player.getStats().getDaily();
    }

    /**
     * returns the current streak of this player
     *
     * @return streak of this player
     */
    public Streak getStreak() {
        return player.getStats().getStreak();
    }

    /**
     * returns the absolute score of this player. This means the sum of all points he has ever received
     *
     * @return score of this player
     */
    public int getScore() {
        return player.getStats().getScore();
    }

    /**
     * gets all notifications that have to be shown to the player
     *
     * @return all games that this player has been invited to
     */
    public Collection<Game> getNotifications() {
        return player.getInvitedGames();
    }
}
