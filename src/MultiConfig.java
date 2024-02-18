public class MultiConfig {
    public void addPlayerHuman(Game game, GamePrinter printer, GameScanner gameScanner) {
        printer.printEnterNameMulti();
        game.addPlayer(new PlayerHuman(gameScanner.enterName()));
    }
    public void addPlayerComp(Game game) {
        game.addPlayer(new PlayerComp("Bot"));
    }
    public void removePlayerHuman(Game game, GamePrinter printer, GameScanner gameScanner) {
        printer.printRemovePlayerHuman();
        game.removePlayer(gameScanner.enterName());
    }
    public void removePlayerComp(Game game) {
        StringBuilder name = new StringBuilder();
        name.append("Bot").append(game.getCntBot());
        game.removePlayer(name.toString());
    }
}
