package setup;

import java.util.Random;

/** RandomHello selects and prints a random greeting. */
public class RandomHello {

    /**
     * Prints a random greeting to the console.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }

    /** @return a greeting, randomly chosen from five possibilities */
    public String getGreeting() {
        // YOUR CODE GOES HERE
        Random randomGenerator = new Random();
        String[] greetings = new String[3];
        greetings[0] = "Ni Hao";
        greetings[1] = "Hello World";
        greetings[2] = "Bonjour Monde";
        return greetings[randomGenerator.nextInt(3)];
    }
}