package DiceGame;


public class main {
    //Program argument
    //5 2 3
    //N K M

    public static void main(String[] args) {

        try {
            Game game = new Game(
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    Integer.parseInt(args[2])

            );
            game.startGame();
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Cant parse input");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();

        }


    }
}
