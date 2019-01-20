package com.csselect.database.mysql;

import com.csselect.database.OrganiserAdapter;
import com.csselect.game.gamecreation.patterns.Pattern;

import java.util.Collection;

/**
 * Mysql-Implementation of the {@link OrganiserAdapter} Interface
 */
public class MysqlOrganiserAdapter extends MysqlUserAdapter implements OrganiserAdapter {

    @Override
    public Collection<Pattern> getPatterns() {
        return null;
    }

    @Override
    public void addPattern(Pattern pattern) {

    }
}
