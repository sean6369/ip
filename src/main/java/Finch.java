import java.util.Scanner;

public class Finch {

    // Constant for the bot's name
    private static final String NAME = "Finch";

    // Prints a horizontal line
    private static void line() {
        System.out.println("____________________________________________________________");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                ███████╗██╗███╗   ██╗ ██████╗██╗  ██╗
                ██╔════╝██║████╗  ██║██╔════╝██║  ██║
                █████╗  ██║██╔██╗ ██║██║     ███████║
                ██╔══╝  ██║██║╚██╗██║██║     ██╔══██║
                ██║     ██║██║ ╚████║╚██████╗██║  ██║
                ╚═╝     ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝  ╚═╝
                                                    \s""");
        line();
        System.out.println("Hello! I'm " + NAME);
        System.out.println("What can I do for you?");
        line();

        String userInput;
        while (true) {
            System.out.println();
            userInput = scanner.nextLine();
            line();
            if (userInput.equals("bye")) {
                break;
            }
            System.out.println(userInput);
            line();
        }

        System.out.println("Bye. Hope to see you again soon!");
        line();
        scanner.close();
    }
}