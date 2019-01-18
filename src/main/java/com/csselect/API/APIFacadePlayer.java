package com.csselect.API;

import com.csselect.game.Feature;
import com.csselect.game.Game;
import com.csselect.gamification.Achievement;
import com.csselect.gamification.DailyChallenge;
import com.csselect.gamification.Streak;
import com.csselect.user.Player;

import java.util.Collection;
import java.util.List;

/**
 * API facade for player specific calls
 * this class is the entrypoint for the system. everything that you want to do with cs-select should be done through
 * the API
 * @author bendix
 */
public class APIFacadePlayer extends APIFacadeUser {


    /** registers a new player
     *
     * @param args array with arguments for this method
     *              0: email
     *              1: password
     *              2: username
     */

    private Player player;

    public void register(String[] args) {

    }

    @Override
    public boolean login(String email, String password) {
        return true;
    }

    /** getter for the player associated with this facade
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /** gets the game with the given gameid. The currently logged in player has to be a participant in the game
     *
     * @param gameId id of the game which to get
     * @return game with id gameId or null if the player is not participant in the game
     */
     public Game getGame(int gameId) {
        return player.getGame(gameId);
     }

    /** gets a list of all games in which the logged in player participates
     *
     * @return All games which this Player participates in
     */
     public Collection<Game> getGames() {
        return player.getGames();
     }

    /** acceptInvite for a game to which the player has been invited
     *
     * @param gameId id of the game of which to accept the invite
     */
     public void acceptInvite(int gameId) {
        player.acceptInvite(gameId);
     }

    /** declines the invite for a game
     *
     * @param gameId game of which to decline the invite
     */
     public void declineInvite(int gameId) {
        player.declineInvite(gameId);
     }

    /** starts a round in the game with gameId
     *
     * @param gameId game of which to start the round
     *
     * @return All the features that are to be displayed this round
     */
     public Collection<Feature> startRound(int gameId) {
         return player.startRound(gameId);
     }

    /** plays the current round
     *
     * @param selected ids of the selected Features
     * @param useless ids of the features that are marked useless
     *
     * @return the score that the selection of the player has achieved
     */
     public void selectFeatures(int[] selected, int[] useless) {
        player.selectFeatures(selected, useless);
     }


    /** skips the current round
     *
     * @param features ids of the features that are to be marked useless
     */
     public void skipRound(int[] features) { // TODO besserer Parametername
        player.skipRound(features);
     }

    /** gets a list of all achievments and their state for this player
     *
     * @return list of all achievments and their state for this player
     */
     public List<Achievement> getAchievments() {
         return player.getStats().getAchievements();
     }

    /** gets a leaderboard (so an ordered list of players)
     *
     * @return leaderboard, first entrance has the most points
     */
     public List<Player> getLeaderboard() {
         return player.getLeaderboard();
     }

    /** gets the current daily challenge
     *
     * @return current daily challange
     */
     public DailyChallenge getDaily() {
        return player.getStats().getDaily();
     }

    /** returns the current streak of this player
     *
     * @return streak of this player
     */
     public Streak getStreak() {
        return player.getStats().getStreak();
     }

    /** returns the absolute score of this player. This means the sum of all points he has ever received
     *
     * @return score of this player
     */
     public int getScore() {
         return player.getStats().getScore();
     }

    /** gets all notifications that have to be shown to the player
     *
     * @return string of all notifications
     */
     public String getNotifications() {
         return "";
     }
}
