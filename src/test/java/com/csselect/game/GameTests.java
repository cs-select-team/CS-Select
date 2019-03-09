package com.csselect.game;

import com.csselect.inject.Injector;
import com.csselect.inject.TestClass;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class GameTests extends TestClass {
    
    private static final String EMAIL_1 = "test@test.de";
    private static final String EMAIL_2 = "secondtest@test.de";
    private static final String USERNAME_1 = "user1";
    private static final String USERNAME_2 = "user2";
    private static final String HASH = "hash";
    private static final String SALT = "salt";

    private Game game;

    @Override @Before
    public void setUp() {
        this.game = new Game(1);
        this.game.setGamemode(new BinarySelect());
        FeatureSet features = new FeatureSet("abc");
        for (int i = 0; i < 10; i++) {
            features.addFeature(new Feature(i, "a"));
        }
        this.game.setFeatureSet(features);
        Termination term = new NumberOfRoundsTermination(5);
        term.setGame(game);
        this.game.setTermination(term);
    }

    @Override @After
    public void reset() {
        this.game = null;
    }

    @Test
    public void terminateGame() {
        Assert.assertFalse(game.isTerminated());
        game.terminateGame();
        Assert.assertTrue(game.isTerminated());
        game.terminateGame();
        Assert.assertTrue(game.isTerminated());

    }

    @Test
    public void termination() {
        Assert.assertFalse(game.isTerminated());
        Player player = Injector.getInstance().getDatabaseAdapter().createPlayer(EMAIL_1, HASH, SALT, USERNAME_1);
        Round round = new StandardRound(player, 1, 2, 3, 4);
        game.addFinishedRound(round);
        Round round2 = new StandardRound(player, 1, 2, 3, 4);
        game.addFinishedRound(round2);
        Round round3 = new StandardRound(player, 1, 2, 3, 4);
        game.addFinishedRound(round3);
        Round round4 = new StandardRound(player, 1, 2, 3, 4);
        game.addFinishedRound(round4);
        Assert.assertFalse(game.isTerminated());
        Round round5 = new StandardRound(player, 1, 2, 3, 4);
        game.addFinishedRound(round5);
        Assert.assertTrue(game.isTerminated());
        Round round6 = new StandardRound(player, 1, 2, 3, 4);
        game.addFinishedRound(round6);
        Assert.assertEquals(6, game.getNumberOfRounds());
    }


    @Test
    public void inviteTwoPlayers() {
        invitePlayer1();
        invitePlayer2();
        Assert.assertNotNull(game.getInvitedPlayers());
        Assert.assertEquals(2, game.getInvitedPlayers().size());
    }

    @Test
    public void inviteSamePlayer() {
        invitePlayer1();
        Collection<String> emails = new ArrayList<>();
        emails.add(EMAIL_1);
        game.invitePlayers(emails);
        Assert.assertNotNull(game.getInvitedPlayers());
        Assert.assertEquals(1, game.getInvitedPlayers().size());
    }

    @Test
    public void declineInvite() {
        invitePlayer1();
        invitePlayer2();
        game.declineInvite(EMAIL_1);
        Assert.assertEquals(1, game.getInvitedPlayers().size());
        Assert.assertTrue(game.getInvitedPlayers().contains(EMAIL_2));
    }

    @Test
    public void declineWrongEmail() {
        game.declineInvite("xd");
        invitePlayer1();
        game.declineInvite(EMAIL_2);
        Assert.assertEquals(1, game.getInvitedPlayers().size());
    }

    @Test
    public void addOnePlayer() {
        this.addPlayer1();
        Assert.assertNotNull(game.getPlayingPlayers());
        Assert.assertEquals(1, game.getPlayingPlayers().size());
    }

    @Test
    public void addTwoPlayers() {
        this.addPlayer1();
        this.addPlayer2();
        Assert.assertNotNull(game.getPlayingPlayers());
        Assert.assertEquals(2, game.getPlayingPlayers().size());
    }

    @Test
    public void addPlayerSameID() {
        this.addPlayer1();
        game.acceptInvite(0, EMAIL_1);
        Assert.assertNotNull(game.getPlayingPlayers());
        Assert.assertEquals(1, game.getPlayingPlayers().size());
    }

    @Test
    public void addPlayerWrongEmail() {
        game.acceptInvite(3, "a");
        Assert.assertEquals(0, game.getPlayingPlayers().size());
    }


    @Test
    public void createRound() {
        Player player = this.addPlayer1();
        Collection<Feature> features = game.startRound(player);
        Assert.assertNotNull(features);
        Assert.assertEquals(10, features.size());
    }

    @Test
    public void createMatrixRound() {
        Player player = this.addPlayer1();
        game.setGamemode(new MatrixSelect(8, 3, 5));
        Collection<Feature> features = game.startRound(player);
        Assert.assertNotNull(features);
        Assert.assertEquals(8, features.size());
    }

    @Test
    public void createRoundWrongPlayer() {
        DatabaseAdapter database = Injector.getInstance().getDatabaseAdapter();
        Player player = database.createPlayer(EMAIL_2, HASH, SALT, USERNAME_2);
        Collection<Feature> features = game.startRound(player);
        Assert.assertNull(features);
    }

    @Test
    public void addFinishedRound() {
        Assert.assertEquals(0, game.getNumberOfRounds());
        Assert.assertEquals(0, game.getRounds().size());
        Round round = new StandardRound(Injector.getInstance().getDatabaseAdapter().createPlayer(EMAIL_1, HASH, SALT, USERNAME_1), 1, 2, 3, 4);
        game.addFinishedRound(round);
        Assert.assertEquals(1, game.getNumberOfRounds());
        Assert.assertEquals(1, game.getRounds().size());
    }

    @Test
    public void numberOfRounds() {
        Assert.assertEquals(0, game.getNumberOfRounds());
        Player player = addPlayer1();
        game.startRound(player);
        game.startRound(player);
        Assert.assertEquals(0, game.getNumberOfRounds());
        Round round = new StandardRound(player, 1, 2, 3, 4);
        game.addFinishedRound(round);
        Assert.assertEquals(1, game.getNumberOfRounds());
    }

    private Player addPlayer1() {
        Player player = invitePlayer1();
        game.acceptInvite(0, EMAIL_1);
        return player;
    }

    private Player addPlayer2() {
        Player player = invitePlayer2();
        game.acceptInvite(1, EMAIL_2);
        return player;
    }

    private Player invitePlayer1() {
        DatabaseAdapter database = Injector.getInstance().getDatabaseAdapter();
        Player player = database.createPlayer(EMAIL_1, HASH, SALT, USERNAME_1);
        Collection<String> emails = new ArrayList<>();
        emails.add(EMAIL_1);
        game.invitePlayers(emails);
        return player;
    }

    private Player invitePlayer2() {
        DatabaseAdapter database = Injector.getInstance().getDatabaseAdapter();
        Player player = database.createPlayer(EMAIL_2, HASH, SALT, USERNAME_2);
        Collection<String> emails = new ArrayList<>();
        emails.add(EMAIL_2);
        game.invitePlayers(emails);
        return player;
    }
}
