public enum State {
    COMPUTER('X'), USER('O'), EMPTY('-');

    private char symbol;
    private State(char symbol){
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "" + symbol;
    }
}
