package com.csselect.gamification;

/**
 * An enumeration for all the existing achievements. Every instance has to implement the method
 * getState.
 */
public enum AchievementType {

    PLAY_ROUND_ONE {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getRoundsPlayed() >= 1) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    PLAY_ROUND_FIVE {
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

    PLAY_ROUND_TEN  {
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

    PLAY_ROUND_FOURTYTWO  {
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

    PLAY_ROUND_HUNDRED  {
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

    STREAK_TWO  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getHighestStreak() >= 2) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    STREAK_FIVE  {
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

    STREAK_TEN  {
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

    DAILY_ONE  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getDailiesCompleted() >= 1) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    DAILY_THREE  {
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

    DAILY_SEVEN  {
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

    TOTAL_SCORE_HUNDRED  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getScore() >= 100) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    TOTAL_SCORE_TWOHUNDREDFIFTY  {
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

    TOTAL_SCORE_FIVEHUNDRED  {
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

    TOTAL_SCORE_THOUSAND  {
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

    TOTAL_SCORE_TWOTHOUSAND  {
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

    TOTAL_SCORE_FIVETHOUSAND  {
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

    ROUND_SCORE_SIXTY  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            if (stats.getMaxRoundScore() >= 60) {
                return AchievementState.FINISHED;
            }
            return AchievementState.SHOWN;
        }
    },

    ROUND_SCORE_SEVENTY  {
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

    ROUND_SCORE_EIGHTY  {
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

    ROUND_SCORE_NINETY  {
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

    public final Achievement checkProgress(PlayerStats stats) {
        AchievementState state = getState(stats);
        return new Achievement(state, this);
    }

    protected abstract AchievementState getState(PlayerStats stats);

}
