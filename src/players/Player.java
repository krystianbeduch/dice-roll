package players;

import game.GameScanner;

abstract public class Player {
    protected boolean isComputerPlayer = false;
    private String name = "NoName";

    public Player() {
    }

    public Player(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // imie moze skladac sie z malych i duzych znakow, cyfr i znakow . \ - _ , min. 3 znaki
        if (name != null && name.matches("^[A-Za-z0-9.\\-_]{3,}$")) {
            this.name = name;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean getIsComputerPlayer() {
        return isComputerPlayer;
    }

    abstract public int guess(GameScanner gameScanner); // metoda abstrakcyjna do zaimplementowania w klasach dziedziczacych
}
