package com.csselect.gamification;

/**
 * An enumeration for all the existing achievements. Every instance
 * has to implement the method getState.
 */
public enum AchievementType {

    /**
     * This type of achievement is completed after having played one round.
     */
    PLAY_ROUND_ONE("Die allererste Runde!",
            "Spiele eine Runde.",
            "The very first round!",
            "Play one round.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 1) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    /**
     * This type of achievement is completed after having played five rounds.
     */
    PLAY_ROUND_FIVE("Fünf!",
            "Spiele fünf Runden.",
            "Five!",
            "Play five rounds.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 5) {
                return AchievementState.FINISHED;
            }

            if (stats.getRoundsPlayed() >= 1) {
                return AchievementState.SHOWN;
            }

            return AchievementState.CONCEALED;
        }
    },

    /**
     * This type of achievement is completed after having played ten rounds.
     */
    PLAY_ROUND_TEN("Treuer Mitspieler!",
            "Spiele zehn Runden.",
            "Loyal player!",
            "Play ten rounds.")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 10) {
                return AchievementState.FINISHED;
            }

            if (stats.getRoundsPlayed() >= 5) {
                return AchievementState.SHOWN;
            }

            if (stats.getRoundsPlayed() >= 1) {
                return AchievementState.CONCEALED;
            }

            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having played 42 rounds.
     */
    PLAY_ROUND_FORTYTWO("Die Antwort auf alles!",
            "Spiele 42 Runden.",
            "The answer to everything!",
            "Play 42 rounds.")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 42) {
                return AchievementState.FINISHED;
            }

            if (stats.getRoundsPlayed() >= 10) {
                return AchievementState.SHOWN;
            }

            if (stats.getRoundsPlayed() >= 5) {
                return AchievementState.CONCEALED;
            }

            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having played 100 rounds.
     */
    PLAY_ROUND_HUNDRED("Es hört nie auf!",
            "Spiele 100 Runden.",
            "Never ending!",
            "Play 100 rounds.")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 100) {
                return AchievementState.FINISHED;
            }

            if (stats.getRoundsPlayed() >= 42) {
                return AchievementState.SHOWN;
            }

            if (stats.getRoundsPlayed() >= 10) {
                return AchievementState.CONCEALED;
            }

            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a streak of two.
     */
    STREAK_TWO("Zwei am Stück!",
            "Spiele zwei Runden in Folge.",
            "Two in a row!",
            "Play two rounds in a row.")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getHighestStreak() >= 2) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    /**
     * This type of achievement is completed after having reached a streak of five.
     */
    STREAK_FIVE ("Alle guten Dinge sind fünf!",
            "Alle guten Dinge sind fünf!",
            "All good things come in fives!",
            "Play five rounds in a row.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getHighestStreak() >= 5) {
                return AchievementState.FINISHED;
            }

            if (stats.getHighestStreak() >= 2) {
                return AchievementState.SHOWN;
            }
            return AchievementState.CONCEALED;
        }
    },

    /**
     * This type of achievement is completed after having reached a streak of ten.
     */
    STREAK_TEN("Niemals aufhören!",
            "Spiele zehn Runden in Folge.",
            "Never stop!",
            "Play ten rounds in a row.")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getHighestStreak() >= 10) {
                return AchievementState.FINISHED;
            }

            if (stats.getHighestStreak() >= 5) {
                return AchievementState.SHOWN;
            }

            if (stats.getHighestStreak() >= 2) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having completed one daily challenge.
     */
    DAILY_ONE("Tägliche Aufgabe!",
            "Schließe eine Daily-Challenge ab.",
            "Daily task!",
            "Complete one daily challenge.")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getDailiesCompleted() >= 1) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    /**
     * This type of achievement is completed after having completed three daily challenges.
     */
    DAILY_THREE("Daily-Challenge Veteran!",
            "Schließe drei Daily-Challenges ab.",
            "Daily challenge veteran!",
            "Complete three daily challenges.")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getDailiesCompleted() >= 3) {
                return AchievementState.FINISHED;
            }

            if (stats.getDailiesCompleted() >= 1) {
                return AchievementState.SHOWN;
            }
            return AchievementState.CONCEALED;
        }
    },

    /**
     * This type of achievement is completed after having completed seven daily challenges.
     */
    DAILY_SEVEN("Daily-Challenge Meister!",
            "Schließe sieben Daily-Challenges ab.",
            "Daily challenge master!",
            "Complete seven daily challenges.")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getDailiesCompleted() >= 7) {
                return AchievementState.FINISHED;
            }

            if (stats.getDailiesCompleted() >= 3) {
                return AchievementState.SHOWN;
            }

            if (stats.getDailiesCompleted() >= 1) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 100.
     */
    TOTAL_SCORE_HUNDRED("100 Punkte!",
            "Erreiche einen Punktestand von 100 Punkten.",
            "100 points!",
            "Reach a total of 100 points.")  {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 100) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 250.
     */
    TOTAL_SCORE_TWOHUNDREDFIFTY("Mehr Punkte!",
            "Erreiche einen Punktestand von 250 Punkten.",
            "More points!",
            "Reach a total of 250 points.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 250) {
                return AchievementState.FINISHED;
            }

            if (stats.getScore() >= 100) {
                return AchievementState.SHOWN;
            }
            return AchievementState.CONCEALED;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 500.
     */
    TOTAL_SCORE_FIVEHUNDRED("Punktesammler!",
            "Erreiche einen Punktestand von 500 Punkten.",
            "Points collector!",
            "Reach a total of 500 points.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 500) {
                return AchievementState.FINISHED;
            }

            if (stats.getScore() >= 250) {
                return AchievementState.SHOWN;
            }

            if (stats.getScore() >= 100) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 1000.
     */
    TOTAL_SCORE_THOUSAND("Ich mag dieses Spiel!",
            "Erreiche einen Punktestand von 1000 Punkten.",
            "I like this game!",
            "Reach a total of 1000 points.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 1000) {
                return AchievementState.FINISHED;
            }

            if (stats.getScore() >= 500) {
                return AchievementState.SHOWN;
            }

            if (stats.getScore() >= 250) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 2000.
     */
    TOTAL_SCORE_TWOTHOUSAND("Ich höre nie auf!",
            "Erreiche einen Punktestand von 2000 Punkten.",
            "I will not stop!",
            "Reach a total of 2000 points.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 2000) {
                return AchievementState.FINISHED;
            }

            if (stats.getScore() >= 1000) {
                return AchievementState.SHOWN;
            }

            if (stats.getScore() >= 500) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a total score of 5000.
     */
    TOTAL_SCORE_FIVETHOUSAND("CS:Select Koryphäe!",
            "Erreiche einen Punktestand von 5000 Punkten.",
            "CS:Select luminary!",
            "Reach a total of 5000 points.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 5000) {
                return AchievementState.FINISHED;
            }

            if (stats.getScore() >= 2000) {
                return AchievementState.SHOWN;
            }

            if (stats.getScore() >= 1000) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a round score of 60.
     */
    ROUND_SCORE_SIXTY("Nicht schlecht!",
            "Erreiche 60 Punkte nach einer einzelnen Runde.",
            "Not bad!",
            "Reach a score of 60 after a single round.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getMaxRoundScore() >= 60) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    /**
     * This type of achievement is completed after having reached a round score of 70.
     */
    ROUND_SCORE_SEVENTY("Gute Auswahl!",
            "Erreiche 70 Punkte nach einer einzelnen Runde.",
            "Good choice!",
            "Reach a score of 70 after a single round.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getMaxRoundScore() >= 70) {
                return AchievementState.FINISHED;
            }

            if (stats.getMaxRoundScore() >= 60) {
                return AchievementState.SHOWN;
            }
            return AchievementState.CONCEALED;
        }
    },

    /**
     * This type of achievement is completed after having reached a round score of 80.
     */
    ROUND_SCORE_EIGHTY("Profi!",
            "Erreiche 80 Punkte nach einer einzelnen Runde.",
            "Pro!",
            "Reach a score of 80 after a single round.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getMaxRoundScore() >= 80) {
                return AchievementState.FINISHED;
            }

            if (stats.getMaxRoundScore() >= 70) {
                return AchievementState.SHOWN;
            }

            if (stats.getMaxRoundScore() >= 60) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    },

    /**
     * This type of achievement is completed after having reached a round score of 90.
     */
    ROUND_SCORE_NINETY("Fachexperte!",
            "Erreiche 90 Punkte nach einer einzelnen Runde.",
            "Domain expert!",
            "Reach a score of 90 after a single round.") {

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getMaxRoundScore() >= 90) {
                return AchievementState.FINISHED;
            }

            if (stats.getMaxRoundScore() >= 80) {
                return AchievementState.SHOWN;
            }

            if (stats.getMaxRoundScore() >= 70) {
                return AchievementState.CONCEALED;
            }
            return AchievementState.INVISIBLE;
        }
    };

    private final String germanName;
    private final String germanDesc;
    private final String englishName;
    private final String englishDesc;

    /**
     * Sets the names and the descriptions of the achievement types in the given languages.
     * @param germanName The German name.
     * @param germanDesc The German description.
     * @param englishName The English name.
     * @param englishDesc The English description.
     */
    AchievementType(String germanName, String germanDesc, String englishName, String englishDesc) {
        this.germanName = germanName;
        this.germanDesc = germanDesc;
        this.englishName = englishName;
        this.englishDesc = englishDesc;
    }

    /**
     * Checks the progress of an achievement and then return a new achievement that
     * represents the progress of that achievement.
     * @param stats The player's stats.
     * @return A new Achievement with the corresponding state and type.
     */
    public final Achievement checkProgress(PlayerStats stats) {
        AchievementState state = getState(stats);
        return new Achievement(state, this);
    }

    /**
     * Gets the current state of the achievement type determined by the players'
     * progress.
     * @param stats The player's stats.
     * @return The current state of the achievement.
     */
    protected abstract AchievementState getState(PlayerStats stats);

    /**
     * Gets the German name of the achievement type.
     * @return The German name.
     */
    public final String getGermanName() {
        return germanName;
    }

    /**
     * Gets the German description for the achievement type.
     * @return The German description.
     */
    public final String getGermanDescription() {
        return germanDesc;
    }

    /**
     * Gets the English name of the achievement type.
     * @return The English name.
     */
    public final String getEnglishName() {
        return englishName;
    }

    /**
     * Gets the English description of the achievement type.
     * @return The English description.
     */
    public final String getEnglishDescription() {
        return englishDesc;
    }

}
