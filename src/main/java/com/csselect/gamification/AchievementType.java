package com.csselect.gamification;

/**
 * An enumeration for all the existing achievements. Every instance has to implement the method
 * checkProgress.
 */
public enum AchievementType {

    PLAY_ROUND_ONE {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    PLAY_ROUND_FIVE {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    PLAY_ROUND_TEN  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    PLAY_ROUND_FOURTYTWO  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    PLAY_ROUND_HUNDRED  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    STREAK_TWO  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    STREAK_FIVE  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    STREAK_TEN  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    DAILY_ONE  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    DAILY_THREE  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    DAILY_SEVEN  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_HUNDRED  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_TWOHUNDREDFIFTY  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_FIVEHUNDRED  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_THOUSAND  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_TWOTHOUSAND  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    TOTAL_SCORE_FIVETHOUSAND  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    ROUND_SCORE_SIXTY  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    ROUND_SCORE_SEVENTY  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    ROUND_SCORE_EIGHTY  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    },

    ROUND_SCPRE_NINETY  {
        @Override
        public Achievement checkProgress(PlayerStats stats) {
            return null;
        }
    };

    public abstract Achievement checkProgress(PlayerStats stats);

}
