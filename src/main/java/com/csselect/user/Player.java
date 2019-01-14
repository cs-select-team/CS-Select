package com.csselect.user;

import com.csselect.database.PlayerAdapter;
import com.csselect.game.Feature;
import com.csselect.game.Game;
import com.csselect.game.Round;
import com.csselect.gamification.Gamification;
import com.csselect.gamification.Leaderboard;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * The player is a user with specified possibilities of interaction with our system. This class represents a player in
 * our system. Players are important for crowd sourcing, the main component of our solution.
 * They are allowed to play games ({@link Game} organisers created and invited them to and see information about
 * their statistics, more precisely their score over time, their achievements and daily challenges.
 */
public class Player extends User {
    private PlayerAdapter databaseAdapter;
    private Collection<Game> invitedGames;
    private Collection<Game> games;
    private Gamification gamification;
    private Round activeRound;

    /**
     * Constructor for an Player object. Database adapter is set to allow communication with our database
     * (object of {@link PlayerAdapter}. The constructor will be called as soon as a player registers or logs in.
     * Which value the unique ID will have (registration) is determined by the
     * {@link com.csselect.database.DatabaseAdapter}
     * @param databaseAdapter Interface for database communication with player tables
     */
    public Player(PlayerAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
        this.games = new HashSet<>();
        this.activeRound = null;
    }

    /**
     * To register a player in our database, we need 3 Strings representing email, password and username.
     * @param args String array of arguments for registration (has to have length of 3 cells)
     * @return If the player was successfully registered
     **/
    public boolean register(String[] args) {
        assert args.length == 3;
        return false;
    }

    /**
     * If a player is invited to a game ({@link Game}), he has to accept said invitation before he is allowed to play it.
     * Game objects representing games the player is invited to are stored as an attribute (array) in the Player object.
     * @param gameId Unique ID of the game the player is invited to and shall be played by him/her
     */
    public void acceptInvite(int gameId) {
        invitedGames.forEach((Game invited) -> {
            if (invited.getId() == gameId) {
                //TODO: Notify game
                invitedGames.remove(invited);
                games.add(invited);
            }
        });
    }


    /**
     * Analog to accepting an invitation to a game, a player is able to decline the invite to a game he is invited to.
     * As for that it is not enough to delete the representing {@link Game} object but also communicate with it that the
     * player won't take part of the game.
     * @param gameId Unique ID of the game the player is invited to but shall not be able to be played by him/her
     */
    public void declineInvite(int gameId) {
        invitedGames.forEach((Game invited) -> {
            if (invited.getId() == gameId) {
                //TODO: Notify game
                invitedGames.remove(invited);
            }
        });
    }

    /**
     * To start a round ({@link Round}) of a game ({@link Game}) the API needs a collection of features
     * ({@link Feature}) to render.
     * If the player decides to start a game, this method will be called. Only games the player accepted invitations to
     * are playable.
     * @param gameId Unique ID of the game the player wants to start a round of
     * @return Features to show in this round of the game
     */
    public Collection<Feature> startRound(int gameId) { return null; }

    /**
     * If the player starts a round ({@link Round}), we want to remember which round the player is playing.
     * Therefore a setter for according attribute.
     * @param round ({@link Round} object the player is currently playing
     */
    public void setActiveRound(Round round) {
        this.activeRound = round;
    }

    /**
     * Getter for the {@link Round} the player is playing
     * @return {@link Round} object
     */
    public Round getActiveRound() {
        return this.activeRound;
    }

    /**
     * In a round ({@link Round}), the player is able to select features ({@link Feature} he thinks that are important.
     * Additionally, he is able (but not forced) to mark features which are not relevant at all.
     * Calling this method means to actually play the active round and retrieving a score.
     * @param selectedFeatures Features the player has selected for this round
     * @param uselessFeatures Features the player marked as unimportant
     */
    public void selectFeatures(Collection<Feature> selectedFeatures, Collection<Feature> uselessFeatures) {

    }

    /**
     * It could be that in some rounds ({@link Round}) the features ({@link Feature}) are not suitable to make a good
     * decision. We provide a player the possibility to skip over a round.
     * @param features Features the player selected in this round to be skipped
     */
    public void skipRound(Collection<Feature> features) {

    }

    /**
     * To retrieve the player's stats, it is necessary to get access to the according {@link Gamification} interface.
     * @return Gamification interface of the player
     */
    public Gamification getStats() {
        return this.gamification;
    }

    /**
     * To take a look at the leaderboard ({@link com.csselect.gamification.Leaderboard}) it will be calculated a
     * sorted list of Player objects which shall be rendered by the front-end of the system.
     * @return List of Players in leaderboard's order
     */
    public List<Player> getLeaderboard() {
        return Leaderboard.getInstance().getPlayers();
    }
}
