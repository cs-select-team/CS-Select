package com.csselect.gamification;

/**
 * An enumeration for all the existing achievements. Every instance has to implement the method
 * checkProgress.
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
            return null;
        }
    },

    DAILY_THREE  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    DAILY_SEVEN  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_HUNDRED  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_TWOHUNDREDFIFTY  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_FIVEHUNDRED  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_THOUSAND  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_TWOTHOUSAND  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_FIVETHOUSAND  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    ROUND_SCORE_SIXTY  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    ROUND_SCORE_SEVENTY  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    ROUND_SCORE_EIGHTY  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    ROUND_SCORE_NINETY  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    };

    public final Achievement checkProgress(PlayerStats stats) {
        AchievementState state = getState(stats);
        return new Achievement(state, this);
    }

    protected abstract AchievementState getState(PlayerStats stats);

}
