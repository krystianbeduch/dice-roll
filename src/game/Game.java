package game;

import game.printer.GamePrinter;
import players.Player;
import players.PlayerHuman;
import settings.DefaultSettings;
import settings.Settings;
import settings.UserSettings;
import stats.NullStatistics;
import stats.Statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public final Settings settings;
    public final Statistics stats;
    private final Random dice = new Random();
    private final List<Player> players = new ArrayList<>();
    private int cnt = -1;
    private int cntBot = -1;

    public Game() {
        this(null, null);
    }

    public Game(Settings settings, Statistics stats) {
        if (settings != null) {
            this.settings = settings;
        }
        else {
            this.settings = new DefaultSettings();
        }

        if (stats != null) {
            this.stats = stats;
        }
        else {
            this.stats = new NullStatistics();
        }
    }

    public void config(Game game, GamePrinter printer, GameScanner scanner) {
        settings.setGameMode(printer, scanner);
        if (settings instanceof DefaultSettings) {
            addPlayer(new PlayerHuman());
            printer.printDefaultSettings();
        }
        else if (settings instanceof UserSettings) {
            if (!settings.isMultiPlayer()) {
                printer.printEnterNameSingle();
                addPlayer(new PlayerHuman(scanner.enterName()));
            }
            else {
                boolean startGame = true;
                do {
                    printer.printMenuMulti(game);
                    startGame = scanner.menuMulti(game, printer);
                }
                while (startGame || howManyPlayers(printer) < 2);
            }
            printer.printHowManyRounds();
            settings.setRounds(scanner.enterNumber());
        }
    }

    public void addPlayer(Player player) {
        players.forEach(p -> {
            if (player.getName().equals(p.getName())) {
                player.setName(player.getName() + (player.getIsComputerPlayer() ? cntBot : cnt));
                if (player.getIsComputerPlayer()) {
                    cntBot -= 1;
                }
                else {
                    cnt -= 1;
                }
            }
        });
        players.add(player);
        stats.addPlayer(player);
    }

    public void printPlayers() {
        players.forEach((player) -> {
            System.out.println(player.getName());
        });
    }

    public void removePlayer(String name) {
        players.removeIf(player -> player.getName().equals(name));
        if (name.startsWith("Bot-")) {
            cntBot++;
        }
        stats.removePlayer(name);
    }

    public int howManyPlayers(GamePrinter printer) {
        int cntPlayer = 0;
        for (Player player : players) {
            cntPlayer++;
        }
        if (cntPlayer < 2) {
            printer.printNotEnoughPlayers();
        }
        return cntPlayer;
    }

    public int getCntBot() {
        return cntBot;
    }

    public void play(GamePrinter printer, GameScanner gameScanner) {
        int guess = 0;
        int number = dice.nextInt(6) + 1;

        // Comment out the line bellow to avoid seeing the corect answer
        printer.printDice(number);
        ///////////////////////////////////////////

        for (Player player : players) {
            if (player instanceof PlayerHuman) {
                printer.printPlayerTurn(player);
            }
            guess = player.guess(gameScanner);
            printer.printPlayerGuess(player, guess);
            if (guess == number) {
                printer.printCorrectAnswer();
                stats.updatePlayer(player);
            }
            else {
                printer.printWrongAnswer();
            }
        } // for
    } // play()
}