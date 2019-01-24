package com.csselect.gamification;

/**
 * An enumeration for all the existing achievements. Every instance has to implement the method
 * checkProgress.
 */
public enum AchievementType {

    PLAY_ROUND_ONE {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    PLAY_ROUND_FIVE {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    PLAY_ROUND_TEN  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    PLAY_ROUND_FOURTYTWO  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    PLAY_ROUND_HUNDRED  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    STREAK_TWO  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    STREAK_FIVE  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    },

    STREAK_TEN  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
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

    ROUND_SCPRE_NINETY  {
        @Override
        protected AchievementState getState(PlayerStats stats) {
            return null;
        }
    };

    public final Achievement checkProgress(PlayerStats stats) {
        AchievementState s = getState(stats);
        return new Achievement(s, this);
    }

    protected abstract AchievementState getState(PlayerStats stats);

}
