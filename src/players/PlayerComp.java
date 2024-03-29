package players;

import game.GameScanner;

import java.util.Random;

public class PlayerComp extends Player {
    private final Random brain = new Random();

    public PlayerComp() {
    }

    public PlayerComp(String name) {
        super(name); // wywolanie konstruktora klasy nadrzednej (tutaj wywolaj konstruktor players.Player(String name) )
        isComputerPlayer = true;
    }

    @Override
    public int guess(GameScanner gameScanner) {
        return brain.nextInt(6) + 1;
    }
}
