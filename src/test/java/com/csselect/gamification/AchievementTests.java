package com.csselect.gamification;

import com.csselect.database.mock.MockPlayerStatsAdapter;
import com.csselect.inject.TestClass;
import org.junit.Assert;
import org.junit.Test;

public class AchievementTests extends TestClass {

    private PlayerStats stats;

    @Override
    public void setUp() {
        stats = new PlayerStats(new MockPlayerStatsAdapter());
    }

    @Override
    public void reset() {

    }

    @Test
    public void loadingTest() {
        Assert.assertNotNull(AchievementType.values());
        Assert.assertEquals(21, AchievementType.values().length); // Currently 21 achievements.
    }

    /**
     * Testing the five achievements that are finished by playing rounds.
     */
    @Test
    public void testPlayRounds() {
        AchievementType playOne = AchievementType.PLAY_ROUND_ONE;
        AchievementType playFive = AchievementType.PLAY_ROUND_FIVE;
        AchievementType playTen = AchievementType.PLAY_ROUND_TEN;
        AchievementType playFortytwo = AchievementType.PLAY_ROUND_FORTYTWO;
        AchievementType playHundred = AchievementType.PLAY_ROUND_HUNDRED;

        Assert.assertEquals(playOne, playOne.checkProgress(stats).getType());
        Assert.assertEquals(playFive, playFive.checkProgress(stats).getType());
        Assert.assertEquals(playTen, playTen.checkProgress(stats).getType());
        Assert.assertEquals(playFortytwo, playFortytwo.checkProgress(stats).getType());
        Assert.assertEquals(playHundred, playHundred.checkProgress(stats).getType());

        Assert.assertEquals(AchievementState.SHOWN, playOne.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, playFive.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, playTen.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, playFortytwo.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, playHundred.checkProgress(stats).getState());

        // Simulate one round.
        stats.finishRound(0.4);

        Assert.assertEquals(AchievementState.FINISHED, playOne.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.SHOWN, playFive.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, playTen.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, playFortytwo.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, playHundred.checkProgress(stats).getState());

        // Simulate 40 rounds. Total: 41
        for (int i = 0; i < 40; i++) {
            stats.finishRound(0.3);
        }

        Assert.assertEquals(AchievementState.FINISHED, playOne.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, playFive.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, playTen.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.SHOWN, playFortytwo.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, playHundred.checkProgress(stats).getState());

        // Simulate one round. Total: 42
        stats.finishRound(0.1);

        Assert.assertEquals(AchievementState.FINISHED, playOne.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, playFive.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, playTen.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, playFortytwo.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.SHOWN, playHundred.checkProgress(stats).getState());
    }

    /**
     * Testing the three achievements that are finished by reaching streaks.
     */
    @Test
    public void testGetStreak() {
        AchievementType streakTwo = AchievementType.STREAK_TWO;
        AchievementType streakFive = AchievementType.STREAK_FIVE;
        AchievementType streakTen = AchievementType.STREAK_TEN;

        Assert.assertEquals(streakTwo, streakTwo.checkProgress(stats).getType());
        Assert.assertEquals(streakFive, streakFive.checkProgress(stats).getType());
        Assert.assertEquals(streakTen, streakTen.checkProgress(stats).getType());

        Assert.assertEquals(AchievementState.SHOWN, streakTwo.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, streakFive.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, streakTen.checkProgress(stats).getState());

        // Simulate two rounds in a row. Streak: 2
        stats.finishRound(0.4);
        stats.finishRound(0.66);

        Assert.assertEquals(AchievementState.FINISHED, streakTwo.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.SHOWN, streakFive.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, streakTen.checkProgress(stats).getState());

        // Simulate seven rounds in a row. Streak: 9
        for (int i = 0; i < 7; i++) {
            stats.finishRound(0.3);
        }

        Assert.assertEquals(AchievementState.FINISHED, streakTwo.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, streakFive.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.SHOWN, streakTen.checkProgress(stats).getState());

        // Simulate one round. Streak: 10
        stats.finishRound(0.1);

        Assert.assertEquals(AchievementState.FINISHED, streakTwo.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, streakFive.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, streakTen.checkProgress(stats).getState());
    }

    /**
     * Testing the five achievements that are finished by completing dailies.
     */
    @Test
    public void testCompleteDaily() {
        AchievementType dailyOne = AchievementType.DAILY_ONE;
        AchievementType dailyThree = AchievementType.DAILY_THREE;
        AchievementType dailySeven = AchievementType.DAILY_SEVEN;

        Assert.assertEquals(dailyOne, dailyOne.checkProgress(stats).getType());
        Assert.assertEquals(dailyThree, dailyThree.checkProgress(stats).getType());
        Assert.assertEquals(dailySeven, dailySeven.checkProgress(stats).getType());

        Assert.assertEquals(AchievementState.SHOWN, dailyOne.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, dailyThree.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, dailySeven.checkProgress(stats).getState());

        // Simulating three rounds in a row. Every daily is now completed.
        for (int i = 0; i < 3; i++) {
            stats.finishRound(0.99);
        }

        Assert.assertEquals(AchievementState.FINISHED, dailyOne.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.SHOWN, dailyThree.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, dailySeven.checkProgress(stats).getState());
    }

    /**
     * Testing the six achievements that are finished by reaching a certain total score.
     */
    @Test
    public void testTotalScore() {
        AchievementType scoreHundred = AchievementType.TOTAL_SCORE_HUNDRED;
        AchievementType scoreTwoHundredFifty = AchievementType.TOTAL_SCORE_TWOHUNDREDFIFTY;
        AchievementType scoreFiveHundred = AchievementType.TOTAL_SCORE_FIVEHUNDRED;
        AchievementType scoreThousand = AchievementType.TOTAL_SCORE_THOUSAND;
        AchievementType scoreTwoThousand = AchievementType.TOTAL_SCORE_TWOTHOUSAND;
        AchievementType scoreFiveThousand = AchievementType.TOTAL_SCORE_FIVETHOUSAND;

        Assert.assertEquals(scoreHundred, scoreHundred.checkProgress(stats).getType());
        Assert.assertEquals(scoreTwoHundredFifty, scoreTwoHundredFifty.checkProgress(stats).getType());
        Assert.assertEquals(scoreFiveHundred, scoreFiveHundred.checkProgress(stats).getType());
        Assert.assertEquals(scoreThousand, scoreThousand.checkProgress(stats).getType());
        Assert.assertEquals(scoreTwoThousand, scoreTwoThousand.checkProgress(stats).getType());
        Assert.assertEquals(scoreFiveThousand, scoreFiveThousand.checkProgress(stats).getType());

        Assert.assertEquals(AchievementState.SHOWN, scoreHundred.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, scoreTwoHundredFifty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, scoreFiveHundred.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, scoreThousand.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, scoreTwoThousand.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, scoreFiveThousand.checkProgress(stats).getState());

        // Reach score of at least 100.
        stats.finishRound(1);

        Assert.assertEquals(AchievementState.FINISHED, scoreHundred.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.SHOWN, scoreTwoHundredFifty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, scoreFiveHundred.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, scoreThousand.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, scoreTwoThousand.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, scoreFiveThousand.checkProgress(stats).getState());

        // Reach score that is higher than 5000.
        for (int i = 0; i < 30; i++) {
            stats.finishRound(1);
        }

        Assert.assertEquals(AchievementState.FINISHED, scoreHundred.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, scoreTwoHundredFifty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, scoreFiveHundred.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, scoreThousand.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, scoreTwoThousand.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, scoreFiveThousand.checkProgress(stats).getState());
    }

    /**
     * Testing the four achievements that are finished by reaching a certain round score.
     */
    @Test
    public void testRoundScore() {
        AchievementType roundScoreSixty = AchievementType.ROUND_SCORE_SIXTY;
        AchievementType roundScoreSeventy = AchievementType.ROUND_SCORE_SEVENTY;
        AchievementType roundScoreEighty = AchievementType.ROUND_SCORE_EIGHTY;
        AchievementType roundScoreNinety = AchievementType.ROUND_SCORE_NINETY;

        Assert.assertEquals(roundScoreSixty, roundScoreSixty.checkProgress(stats).getType());
        Assert.assertEquals(roundScoreSeventy, roundScoreSeventy.checkProgress(stats).getType());
        Assert.assertEquals(roundScoreEighty, roundScoreEighty.checkProgress(stats).getType());
        Assert.assertEquals(roundScoreNinety, roundScoreNinety.checkProgress(stats).getType());

        Assert.assertEquals(AchievementState.SHOWN, roundScoreSixty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, roundScoreSeventy.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, roundScoreEighty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, roundScoreNinety.checkProgress(stats).getState());

        // Round scores are too low. No achievements finished.
        stats.finishRound(0.4);
        stats.finishRound(0.57);

        Assert.assertEquals(AchievementState.SHOWN, roundScoreSixty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, roundScoreSeventy.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, roundScoreEighty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.INVISIBLE, roundScoreNinety.checkProgress(stats).getState());

        // Round score: 74
        stats.finishRound(0.74);

        Assert.assertEquals(AchievementState.FINISHED, roundScoreSixty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, roundScoreSeventy.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.SHOWN, roundScoreEighty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.CONCEALED, roundScoreNinety.checkProgress(stats).getState());

        // Round score: 90
        stats.finishRound(0.9);

        Assert.assertEquals(AchievementState.FINISHED, roundScoreSixty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, roundScoreSeventy.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, roundScoreEighty.checkProgress(stats).getState());
        Assert.assertEquals(AchievementState.FINISHED, roundScoreNinety.checkProgress(stats).getState());
    }
}
