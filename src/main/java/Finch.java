public class Finch {

    // Constant for the bot's name
    private static final String NAME = "Finch";

    // Prints a horizontal line
    private static void line() {
        System.out.println("____________________________________________________________");
    }

    public static void main(String[] args) {
        line();
        System.out.println("Hello! I'm " + NAME);
        System.out.println("What can I do for you?");
        line();
        System.out.println("Bye. Hope to see you again soon!");
        line();
    }
}
