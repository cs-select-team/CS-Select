package com.csselect.API;

import com.csselect.game.Feature;
import com.csselect.game.Game;
import com.csselect.gamification.Achievement;
import com.csselect.gamification.DailyChallenge;
import com.csselect.gamification.DailyGetStreakThree;
import com.csselect.gamification.Streak;
import com.csselect.user.Player;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class APIFacadePlayer extends APIFacadeUser {
    private Player player;
    public void register(String[] args) {

    }
    public Player getPlayer() {
        return player;
    }
     public Game getGame() {
        return new Game();
     }

    /**
     *
     * @return All games which this Player participates in
     */
     public List<Game> getGames() {
        return new LinkedList<>();
     }

     public void acceptInvite(int gameId) {

     }

     public void declineInvite(int gameId) {

     }

     public void startRound(int gameId) {

     }

     public void selectFeatures(Collection<Feature> selected, Collection<Feature> useless) {

     }


     public void skipRound(Collection<Feature> features) { // TODO besserer Parametername

     }

     public List<Achievement> getAchievments() {
         return new LinkedList<>();
     }

     public List<Player> getLeaderboard() {
         return new LinkedList<>();
     }

     public DailyChallenge getDaily() {
         return new DailyGetStreakThree() {
             @Override
             public int hashCode() {
                 return super.hashCode();
             }
         };
     }

     public Streak getStreak() {
         return new Streak();
     }

     public int getScore() {
         return 0;
     }

     public String getNotifications() {
         return "";
     }
}
