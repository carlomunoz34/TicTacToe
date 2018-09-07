import java.util.Random;

public class Game {

    private Board board;
    private Random random;

    public Game() {
        random = new Random();
        int choice = 0;

        showInstructions();
        do {
            initializeGame();
            board.display();
            makeFirstMove();
            playGame();
            checkStatus();

            System.out.println("Seguir jugando? 0.- No 1.- Si");
            choice = board.getScanner().nextInt();
        }while(choice == 1);


    }

    private void showInstructions() {
        System.out.println("Mira, esto está fácil. Solo aprieta el numero de la casilla que quieras jugar");
        System.out.println("7|8|9");
        System.out.println("-----");
        System.out.println("4|5|6");
        System.out.println("-----");
        System.out.println("1|2|3");

    }

    private void playGame() {
        while(board.isRunning()){
            //Players move
            System.out.println("Te toca");
            int aux = board.getScanner().nextInt();
            Cell userCell = new Cell((int)((9 - aux) / board.BOARD_SIZE),(aux - 1) % board.BOARD_SIZE);

            try {
                board.move(userCell, State.USER);
            }
            catch (Exception e){
                System.out.println("No se me quiera pasar de listo mijo");
                continue;
            }
            board.display();

            if(!board.isRunning())
                break;

            //Computers move
            board.callMinimax();

            for(Cell cell: board.getRootValues())
                System.out.println("Cell value: " + cell + " minimaxValue: " + cell.getMinimax());

            try {
                board.move(board.getBestMove(), State.COMPUTER);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            board.display();
        }
    }

    private void initializeGame() {
        board = new Board();
        random = new Random();
        board.setup();
    }

    private void checkStatus() {
        if(board.isWinning(State.COMPUTER))
            System.out.println("Gané");
        else if(board.isWinning(State.USER))
            System.out.print("Me ganó el prro");
        else
            System.out.println("Jaja casi te gano");
    }

    private void makeFirstMove() {
        System.out.println("Quien empieza? \n1.- Tu merengues\n2.- Yo mero");
        int choice = board.getScanner().nextInt();

        if(choice == 1){
            Cell cell = new Cell(random.nextInt(board.BOARD_SIZE), random.nextInt(board.BOARD_SIZE));
            try {
                board.move(cell, State.COMPUTER);
            }
            catch (Exception e){
            }
            board.display();
        }
        else if(choice != 2){
            System.out.println("No le quiera jugar al vrgas");
            makeFirstMove();
        }

    }
}
