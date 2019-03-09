package com.csselect.database.mysql;

import com.csselect.game.BinarySelect;
import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.csselect.game.Gamemode;
import com.csselect.game.Round;
import com.csselect.inject.Injector;
import com.csselect.inject.MysqlTestClass;
import com.csselect.database.GameAdapter;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashSet;

public class MysqlGameAdapterTest extends MysqlTestClass {

    private static final String TEST_TITLE = "PSE - Projekt Studenten exmatrikulieren";
    private static final String TEST_DESCRIPTION = "This games aim is to find out which of students traits correlate with failure in the PSE";
    private static final String TEST_DATABASE_NAME = "PSE";
    private static final String TEST_EMAIL = "test@test.de";
    private static final String TEST_EMAIL2 = "test2@test.de";
    private static final Gamemode GAMEMODE = new BinarySelect();

    private GameAdapter adapter;
    private MysqlDatabaseAdapter mysqlDatabaseAdapter;

    @Override
    public void setUp() {
        mysqlDatabaseAdapter = (MysqlDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();
        adapter = new MysqlGameAdapter();
        adapter.setDatabase("PSE");
    }

    @Override
    public void reset() {
        mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE CS_SELECT;");
        mysqlDatabaseAdapter.executeMysqlUpdate("DROP DATABASE PSE;", "PSE");
    }

    @Test
    public void testGetID() {
        Assert.assertEquals(1, adapter.getID());
    }

    @Test
    public void testTitle() {
        adapter.setTitle(TEST_TITLE);
        Assert.assertEquals(TEST_TITLE, adapter.getTitle());
    }

    @Test
    public void testDescription() {
        adapter.setDescription(TEST_DESCRIPTION);
        Assert.assertEquals(TEST_DESCRIPTION, adapter.getDescription());
    }

    @Test
    public void testDatabaseName() {
        Assert.assertEquals(TEST_DATABASE_NAME, adapter.getDatabaseName());
    }

    @Test
    public void testFinished() {
        Assert.assertFalse(adapter.isFinished());
        adapter.setFinished();
        Assert.assertTrue(adapter.isFinished());
    }

    @Test
    public void testPlayerInvitation() {
        Collection<String> emails = new HashSet<>();
        emails.add(TEST_EMAIL);
        emails.add(TEST_EMAIL2);
        adapter.addInvitedPlayers(emails);
        Assert.assertEquals(emails, adapter.getInvitedPlayers());
    }

    @Test
    public void testPlayingPlayersAdding() {
        Collection<String> emails = new HashSet<>();
        Player player1 = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_EMAIL, TEST_EMAIL, TEST_EMAIL);
        Player player2 = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL2, TEST_EMAIL, TEST_EMAIL, TEST_EMAIL2);
        emails.add(TEST_EMAIL);
        emails.add(TEST_EMAIL2);
        adapter.addPlayingPlayers(emails);
        Collection<Player> players = adapter.getPlayingPlayers();
        Assert.assertTrue(players.contains(player1));
        Assert.assertTrue(players.contains(player2));
    }

    @Test
    public void testPlayingPlayerAdding(){
        Player player = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_EMAIL, TEST_EMAIL, TEST_EMAIL);
        Assert.assertTrue(adapter.getInvitedPlayers().isEmpty());
        Assert.assertTrue(adapter.getPlayingPlayers().isEmpty());
        adapter.addPlayingPlayer(player.getId());
        Assert.assertTrue(adapter.getInvitedPlayers().isEmpty());
        Assert.assertEquals(1, adapter.getPlayingPlayers().size());
    }

    @Test
    public void testInvitedPlayerRemoval() {
        Collection<String> emails = new HashSet<>();
        Collection<String> toRemove = new HashSet<>();
        emails.add(TEST_EMAIL);
        emails.add(TEST_EMAIL2);
        toRemove.add(TEST_EMAIL2);
        adapter.addInvitedPlayers(emails);
        adapter.removeInvitedPlayers(toRemove);
        Assert.assertTrue(adapter.getInvitedPlayers().contains(TEST_EMAIL));
        Assert.assertFalse(adapter.getInvitedPlayers().contains(TEST_EMAIL2));
    }

    @Test
    public void testRounds() {
        Player player = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_EMAIL, TEST_EMAIL, TEST_EMAIL);
        adapter.setGamemode(GAMEMODE);
        Round round = createMockRound(player);
        Assert.assertTrue(adapter.getRounds().isEmpty());
        adapter.addRound(round);
        Assert.assertEquals(1, adapter.getRounds().size());
        adapter.addRound(round);
        Assert.assertEquals(2, adapter.getRounds().size());
    }

    @Test
    public void testDuplicateFeatureProvision() {
        Player player = mysqlDatabaseAdapter.createPlayer(TEST_EMAIL, TEST_EMAIL, TEST_EMAIL, TEST_EMAIL);
        Collection<Feature> features = createMockFeatures();
        Assert.assertFalse(adapter.checkDuplicateFeatureProvision(features));
        adapter.addRound(createMockRound(player));
        Assert.assertTrue(adapter.checkDuplicateFeatureProvision(features));
    }

    @Test
    public void organiserTest() {
        Organiser organiser = mysqlDatabaseAdapter.createOrganiser(TEST_EMAIL, TEST_EMAIL, TEST_EMAIL);
        adapter.setOrganiser(organiser);
        Assert.assertEquals(organiser, adapter.getOrganiser());
    }

    @Test
    public void featuresTest() {
        FeatureSet featureSet = new FeatureSet(TEST_DATABASE_NAME);
        adapter.setFeatures(featureSet);
        Assert.assertNotNull(adapter.getFeatures());
        Assert.assertEquals(featureSet, adapter.getFeatures());
    }

    private Collection<Feature> createMockFeatures() {
        Collection<Feature> features = new HashSet<>();
        features.add(new Feature(1, ""));
        features.add(new Feature(2, ""));
        features.add(new Feature(3, ""));
        return features;
    }

    private Round createMockRound(Player player){
        Collection<Feature> features = createMockFeatures();
        Round round = Mockito.mock(Round.class);
        Mockito.when(round.getPlayer()).thenReturn(player);
        Mockito.when(round.isSkipped()).thenReturn(false);
        Mockito.when(round.getQuality()).thenReturn(0.5);
        Mockito.when(round.getPoints()).thenReturn(10);
        Mockito.when(round.getUselessFeatures()).thenReturn(features);
        Mockito.when(round.getChosenFeatures()).thenReturn(features);
        Mockito.when(round.getShownFeatures()).thenReturn(features);
        return round;
    }
}