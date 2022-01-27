import java.util.Scanner;

public class Game {
    public enum gameMode {
        CLASSIC,
        ENHANCED
    }

    private String[] choices = {"rock", "paper", "scissors"};
    private String[] choiceInitial = {"r", "p", "s"};
    private String[] outcomes = {"breaks", "wraps", "cuts"};
    private final int ROCK = 0;
    private final int PAPER = 1;
    private final int SCISSORS = 2;
    private gameMode currentMode;
    private Player player1;
    private Player player2;
    private int maxLoop;

    public Game(gameMode currentMode) {
        this.currentMode = currentMode;
    }

    public Game() {
        currentMode = gameMode.CLASSIC;
    }

    private String getCompChoice() {
        return choiceInitial[(int)(Math.random() * 3)];
    }

    private String getPlayerChoice() {
        String choice = "";
        do {
            System.out.println("Type the correct letter to choose from the following: ");
            for (int i = 0; i < choices.length; i++) {
                System.out.println(choices[i].substring(0, 1) + " : " + choices[i]);
            }
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();
        } while (!(choice.equals(choiceInitial[ROCK]) || choice.equals(choiceInitial[PAPER]) || choice.equals(choiceInitial[SCISSORS])));
        return choice;
    }

    private String getName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Let’s play rock paper scissors!");
        String name = "";
        while (name.length() == 0) {
            System.out.println("What is your name? ");
            name = scanner.nextLine();
            if (name.length() < 1) {
                System.out.println("Name must be at least 1 character long!");
            }
        }
        return name;
    }

    private String playAgain() {
        String validChoice = "yn";
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        do {
            System.out.println("Play again y or n");
            choice = scanner.nextLine();
        } while (!validChoice.contains(choice) || (choice.length() != 1));
        return choice;
    }

    private int getItemIndex(String itemChoice) {
        for (int i = 0; i < choiceInitial.length; i++) {
            if (itemChoice.equals(choiceInitial[i])) {
                return i;
            }
        }
        return -1;
    }

    private boolean getWinner(int p1Choice, int p2Choice) {
        switch (p1Choice){
            case ROCK:
                if (p2Choice == SCISSORS) {
                    return true;
                }
                break;
            case PAPER:
                if (p2Choice == ROCK) {
                    return true;
                }
                break;
            case SCISSORS:
                if (p2Choice == PAPER) {
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    private void checkWin(String playerChoice, String compChoice) {
        int playerIndex = getItemIndex(playerChoice);
        int compIndex = getItemIndex(compChoice);
        System.out.println(player1.getName() + " chose " + choices[playerIndex]
            + ", "  + player2.getName() + " chose " + choices[compIndex]);
        if (playerIndex == compIndex) {
            //It's a draw
            System.out.println("It's a draw");
        } else if (getWinner(playerIndex, compIndex)) {
            System.out.println(player1.getName() + " won " + choices[playerIndex]
                    + " " + outcomes[playerIndex] + " " + choices[compIndex]);
            player1.incScore();
        } else {
            System.out.println(player2.getName() + " won " + choices[compIndex]
                    + " " + outcomes[compIndex] + " " + choices[playerIndex]);
            player2.incScore();
        }
    }

    private boolean playTurn() {
        String p1Choice = "";
        if (currentMode == gameMode.CLASSIC) {
            p1Choice = getPlayerChoice();
        } else {
            p1Choice = getCompChoice();
        }

        checkWin(p1Choice, getCompChoice());

        System.out.println(player1.getName() + " score is " + player1.getScore()
                + ". " + player2.getName() + " score is " + player2.getScore());

        String playAgain = playAgain();
        return playAgain.equals("n");
    }

    public void playGame() {
        if (currentMode == gameMode.CLASSIC) {
            player1 = new Player(getName());
            player2 = new Player("Computer");
            System.out.println("Hello player " + player1.getName() + " and player " + player2.getName());
            boolean playing = true;
            while (playing) {
                playing = playTurn();
            }
        } else {
            player1 = new Player("Computer 1");
            player2 = new Player("Computer 2");
            int maxTurn = 5;
            for (int i = 0; i < maxTurn; i++) {
                checkWin(getCompChoice(), getCompChoice());
            }
            System.out.println("Turns " + maxTurn + ": "
                    + player1.getName() + " score is " + player1.getScore()
                    + ". "
                    + player2.getName() + " score is " + player2.getScore()
                    + ". Total draws "
                    + (maxTurn - Math.abs(player1.getScore() + player2.getScore())));
        }
    }
}
