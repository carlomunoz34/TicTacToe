import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    private List<Cell> emptyCells;
    private Scanner scanner;
    private State[][] board;
    private List<Cell> rootValues;
    public static final byte BOARD_SIZE = 3;

    public Board() {
        initializeBoard();
    }

    private void initializeBoard() {
        rootValues = new ArrayList<>();
        scanner = new Scanner(System.in);
        board = new State[BOARD_SIZE][BOARD_SIZE];
    }

    public boolean isRunning(){
        if(isWinning(State.COMPUTER) || isWinning(State.USER) || getEmptyCells().isEmpty()) return false;
        return true;
    }

    private List<Cell> getEmptyCells() {
        emptyCells = new ArrayList<>();
        for(int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                if(board[i][j] == State.EMPTY)
                    emptyCells.add(new Cell(i, j));

        return emptyCells;
    }

    public int returnMinimum(List<Integer> list){
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < list.size(); i++)
            if(min > list.get(i))
                min = list.get(i);

        return min;
    }

    public int returnMaximum(List<Integer> list){
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < list.size(); i++)
            if(max < list.get(i))
                max = list.get(i);

        return max;
    }

    public boolean isWinning(State player) {
        //Horizontal
        boolean won = true;
        for(int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j< BOARD_SIZE; j++)
                if(board[i][j] != player)
                    won = false;

            if(won == true) return  true;
            won = true;
        }

        //Vertical
        won = true;
        for(int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j< BOARD_SIZE; j++)
                if(board[j][i] != player)
                    won = false;

            if(won == true) return  true;
            won = true;
        }

        //Diagonal
        if(BOARD_SIZE % 2 == 1){
            won = true;
            for(int i = 0; i < BOARD_SIZE; i++)
                if(board[i][i] != player)
                    won = false;

            if(won == true) return  true;
            won = true;

            for(int i = 0; i < BOARD_SIZE; i++)
                if(board[i][BOARD_SIZE - 1 - i] != player)
                    won = false;

            if(won == true) return  true;
        }

        return false;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public List<Cell> getRootValues() {
        return rootValues;
    }

    public void move(Cell cell, State player) throws IllegalMoveException{
        if(board[cell.getX()][cell.getY()] != State.EMPTY) throw new IllegalMoveException();
        board[cell.getX()][cell.getY()] = player;
    }

    public void display() {
        System.out.println();

        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    public Cell getBestMove() {
        int max, best;
        max = best = Integer.MIN_VALUE;
        for(int i = 0; i < rootValues.size(); i++)
            if(max < rootValues.get(i).getMinimax()){
                max = rootValues.get(i).getMinimax();
                best = i;
            }

        return rootValues.get(best);
    }

    public void setup() {
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++)
                board[i][j] = State.EMPTY;
    }

    public void callMinimax() {
        rootValues.clear();
        minimax(0, State.COMPUTER);
    }

    private int minimax(int depth, State player){
        if(isWinning(State.COMPUTER)) return 1;
        if(isWinning(State.USER)) return -1;

        List<Cell> availableCells = getEmptyCells();
        if(availableCells.isEmpty()) return 0;

        List<Integer> scores = new ArrayList<>();

        for(Cell point: availableCells){
            try{
                move(point, player);
            } catch(Exception e) {
                e.printStackTrace();
            }

            if(player == State.COMPUTER){
                int currentScore = minimax(depth + 1, State.USER);
                scores.add(currentScore);

                if(depth == 0){
                    point.setMinimax(currentScore);
                    rootValues.add(point);
                }
            }

            else if(player == State.USER){
                scores.add(minimax(depth + 1, State.COMPUTER));
            }
            board[point.getX()][point.getY()] = State.EMPTY;
        }
        if(player == State.COMPUTER) return returnMaximum(scores);
        return returnMinimum(scores);
    }
}
