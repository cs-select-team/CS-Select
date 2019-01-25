package com.csselect.gamification;

/**
 * An enumeration for all the existing achievements. Every instance
 * has to implement the method getState as well as the names and description
 * getters for the languages.
 */
public enum AchievementType {

    PLAY_ROUND_ONE {
        private final static String GERMAN_NAME = "Die allererste Runde!";
        private final static String GERMAN_DESCRIPTION = "Spiele eine Runde.";
        private final static String ENGLISH_NAME = "The very first round!";
        private final static String ENGLISH_DESCRIPTION = "Play one round.";

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 1) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    PLAY_ROUND_FIVE {
        private final static String GERMAN_NAME = "Fünf!";
        private final static String GERMAN_DESCRIPTION = "Spiele fünf Runden.";
        private final static String ENGLISH_NAME = "Five!";
        private final static String ENGLISH_DESCRIPTION = "Play five rounds.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    PLAY_ROUND_TEN  {
        private final static String GERMAN_NAME = "Treuer Mitspieler!";
        private final static String GERMAN_DESCRIPTION = "Spiele zehn Runden.";
        private final static String ENGLISH_NAME = "Loyal player!";
        private final static String ENGLISH_DESCRIPTION = "Play ten rounds.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    PLAY_ROUND_FOURTYTWO  {
        private final static String GERMAN_NAME = "Die Antwort auf alles!";
        private final static String GERMAN_DESCRIPTION = "Spiele 42 Runden.";
        private final static String ENGLISH_NAME = "The answer to everything!";
        private final static String ENGLISH_DESCRIPTION = "Play 42 rounds.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    PLAY_ROUND_HUNDRED  {
        private final static String GERMAN_NAME = "Es hört nie auf!";
        private final static String GERMAN_DESCRIPTION = "Spiele 100 Runden.";
        private final static String ENGLISH_NAME = "Never ending!";
        private final static String ENGLISH_DESCRIPTION = "Play 100 rounds.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    STREAK_TWO  {
        private final static String GERMAN_NAME = "Zwei am Stück!";
        private final static String GERMAN_DESCRIPTION = "Spiele zwei Runden in Folge.";
        private final static String ENGLISH_NAME = "Two in a row!";
        private final static String ENGLISH_DESCRIPTION = "Play two rounds in a row.";

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getHighestStreak() >= 2) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    STREAK_FIVE  {
        private final static String GERMAN_NAME = "Alle guten Dinge sind fünf!";
        private final static String GERMAN_DESCRIPTION = "Spiele fünf Runden in Folge.";
        private final static String ENGLISH_NAME = "All good things come in fives!";
        private final static String ENGLISH_DESCRIPTION = "Play five rounds in a row.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    STREAK_TEN  {
        private final static String GERMAN_NAME = "Niemals aufhören!";
        private final static String GERMAN_DESCRIPTION = "Spiele zehn Runden in Folge.";
        private final static String ENGLISH_NAME = "Never stop!";
        private final static String ENGLISH_DESCRIPTION = "Play ten rounds in a row.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    DAILY_ONE  {
        private final static String GERMAN_NAME = "Tägliche Aufgabe!";
        private final static String GERMAN_DESCRIPTION = "Schließe eine Daily-Challenge ab.";
        private final static String ENGLISH_NAME = "Daily task!";
        private final static String ENGLISH_DESCRIPTION = "Complete one daily challenge.";

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getDailiesCompleted() >= 1) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    DAILY_THREE  {
        private final static String GERMAN_NAME = "Daily-Challenge Veteran!";
        private final static String GERMAN_DESCRIPTION = "Schließe drei Daily-Challenges ab.";
        private final static String ENGLISH_NAME = "Daily challenge veteran!";
        private final static String ENGLISH_DESCRIPTION = "Complete three daily challenges.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    DAILY_SEVEN  {
        private final static String GERMAN_NAME = "Daily-Challenge Meister!";
        private final static String GERMAN_DESCRIPTION = "Schließe sieben Daily-Challenges ab.";
        private final static String ENGLISH_NAME = "Daily challenge master!";
        private final static String ENGLISH_DESCRIPTION = "Complete seven daily challenges.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    TOTAL_SCORE_HUNDRED  {
        private final static String GERMAN_NAME = "100 Punkte!";
        private final static String GERMAN_DESCRIPTION = "Erreiche einen Punktestand von 100 Punkten.";
        private final static String ENGLISH_NAME = "100 points!";
        private final static String ENGLISH_DESCRIPTION = "Reach a total of 100 points.";

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 100) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    TOTAL_SCORE_TWOHUNDREDFIFTY  {
        private final static String GERMAN_NAME = "Mehr Punkte!";
        private final static String GERMAN_DESCRIPTION = "Erreiche einen Punktestand von 250 Punkten.";
        private final static String ENGLISH_NAME = "More points!";
        private final static String ENGLISH_DESCRIPTION = "Reach a total of 250 points.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    TOTAL_SCORE_FIVEHUNDRED  {
        private final static String GERMAN_NAME = "Punktesammler!";
        private final static String GERMAN_DESCRIPTION = "Erreiche einen Punktestand von 500 Punkten.";
        private final static String ENGLISH_NAME = "Points collector!";
        private final static String ENGLISH_DESCRIPTION = "Reach a total of 500 points.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    TOTAL_SCORE_THOUSAND  {
        private final static String GERMAN_NAME = "Ich mag dieses Spiel!";
        private final static String GERMAN_DESCRIPTION = "Erreiche einen Punktestand von 1000 Punkten.";
        private final static String ENGLISH_NAME = "I like this game!";
        private final static String ENGLISH_DESCRIPTION = "Reach a total of 1000 points.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    TOTAL_SCORE_TWOTHOUSAND  {
        private final static String GERMAN_NAME = "Ich höre nie auf!";
        private final static String GERMAN_DESCRIPTION = "Erreiche einen Punktestand von 2000 Punkten.";
        private final static String ENGLISH_NAME = "I will not stop!";
        private final static String ENGLISH_DESCRIPTION = "Reach a total of 2000 points.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    TOTAL_SCORE_FIVETHOUSAND  {
        private final static String GERMAN_NAME = "CS:Select Koryphäe!";
        private final static String GERMAN_DESCRIPTION = "Erreiche einen Punktestand von 5000 Punkten.";
        private final static String ENGLISH_NAME = "CS:Select luminary!";
        private final static String ENGLISH_DESCRIPTION = "Reach a total of 5000 points.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },


    ROUND_SCORE_SIXTY  {
        private final static String GERMAN_NAME = "Nicht schlecht!";
        private final static String GERMAN_DESCRIPTION = "Erreiche 60 Punkte nach einer einzelnen Runde.";
        private final static String ENGLISH_NAME = "Not bad!";
        private final static String ENGLISH_DESCRIPTION = "Reach a score of 60 after a single round.";

        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getMaxRoundScore() >= 60) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    ROUND_SCORE_SEVENTY  {
        private final static String GERMAN_NAME = "Gute Auswahl!";
        private final static String GERMAN_DESCRIPTION = "Erreiche 70 Punkte nach einer einzelnen Runde.";
        private final static String ENGLISH_NAME = "Good choice!";
        private final static String ENGLISH_DESCRIPTION = "Reach a score of 70 after a single round.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    },

    ROUND_SCORE_EIGHTY  {
        private final static String GERMAN_NAME = "Profi!";
        private final static String GERMAN_DESCRIPTION = "Erreiche 80 Punkte nach einer einzelnen Runde.";
        private final static String ENGLISH_NAME = "Pro!";
        private final static String ENGLISH_DESCRIPTION = "Reach a score of 80 after a single round.";

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

        @Override
        public String getGermanName() {
            return null;
        }

        @Override
        public String getGermanDescription() {
            return null;
        }

        @Override
        public String getEnglishName() {
            return null;
        }

        @Override
        public String getEnglishDescription() {
            return null;
        }
    },

    ROUND_SCORE_NINETY  {
        private final static String GERMAN_NAME = "Fachexperte!";
        private final static String GERMAN_DESCRIPTION = "Erreiche 90 Punkte nach einer einzelnen Runde.";
        private final static String ENGLISH_NAME = "Domain expert!";
        private final static String ENGLISH_DESCRIPTION = "Reach a score of 90 after a single round.";

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

        @Override
        public String getGermanName() {
            return GERMAN_NAME;
        }

        @Override
        public String getGermanDescription() {
            return GERMAN_DESCRIPTION;
        }

        @Override
        public String getEnglishName() {
            return ENGLISH_NAME;
        }

        @Override
        public String getEnglishDescription() {
            return ENGLISH_DESCRIPTION;
        }
    };

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
    public abstract String getGermanName();

    /**
     * Gets the German description for the achievement type.
     * @return The German description.
     */
    public abstract String getGermanDescription();

    /**
     * Gets the English name of the achievement type.
     * @return The English name.
     */
    public abstract String getEnglishName();

    /**
     * Gets the English description of the achievement type.
     * @return The English description.
     */
    public abstract String getEnglishDescription();

}
