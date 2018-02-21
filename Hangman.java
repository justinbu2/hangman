import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Hangman game...with a twist ;)
 * 
 * TODO: Output guessed letters somewhere
 * 
 * @author Justin Bu
 */

public class Hangman {

    // Word bank for normal Hangman
    private static final String[] WORD_BANK = new String[] {
        "TELEVISION", "LIBRARY", "FINISH", "PAYMENT", "REVIEW",
        "DOWNHILL", "PERFECT", "MISTAKE", "LETTER", "CORRECT"
    };

    // Word bank for modified Hangman
    private static final String[] ASK_WORD_BANK = new String[] {
        "BEAUTIFUL", "CUTE", "GORGEOUS", "STUNNING", "CAPTIVATING" 
    };

    // Modes that trigger modified Hangman behavior
    private static final HashSet<String> ASK_MODES = new HashSet<>(
        Arrays.asList("FILE", "MESSAGE")
    );

    private static final int MAX_MISTAKES = 6;
    private static final Random random = new Random();

    private String mode;

    public Hangman(String mode) {
        this.mode = mode;
    }

    /**
     * Main loop for Hangman game
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        boolean play = true;

        String[] wordBank;
        if (ASK_MODES.contains(mode.toUpperCase())) {
            wordBank = ASK_WORD_BANK;
        } else {
            wordBank = WORD_BANK;
        }

        System.out.println("Welcome to Hangman!");
        while (play) {
            int dictIndex = random.nextInt(wordBank.length);
            String word = wordBank[dictIndex];
            System.out.printf("This word contains %d letters:\n", word.length());

            // Set up data structure for storing word
            Map<Character, List<Integer>> wordMap = new HashMap<>();
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (!wordMap.containsKey(c)) {
                    wordMap.put(c, new ArrayList<>());
                }
                wordMap.get(c).add(i);
            }
            Set<Character> guesses = new HashSet<>();
            int incorrectAttempts = 0;
            boolean success = false;
            boolean[] corrects = new boolean[word.length()];
            while (!success && incorrectAttempts < MAX_MISTAKES) {                
                boolean correctGuess = makeGuess(sc, word, wordMap, guesses, corrects);
                incorrectAttempts = correctGuess ? incorrectAttempts : incorrectAttempts + 1;

                boolean check = true;
                for (boolean b : corrects) {
                    check &= b;
                }
                success = check;
                if (check || MAX_MISTAKES - incorrectAttempts == 0) {
                    break;
                }
                if (correctGuess) {
                    continue;
                } else if (MAX_MISTAKES - incorrectAttempts > 0) {
                    System.out.printf("Wrong letter! You have %d attempts left.\n", MAX_MISTAKES - incorrectAttempts);
                }
            }

            showProgress(word, corrects);
            play = queryPlayAgain(sc, success);
        }
    }

    private void showProgress(String word, boolean[] corrects) {
        for (int j = 0; j < word.length(); j++) {
            if (corrects[j]) {
                System.out.print(word.charAt(j) + " ");
            } else {
                System.out.print("_ ");
            }
        }
        System.out.println();
    }

    private boolean makeGuess(Scanner sc, String word,
                              Map<Character, List<Integer>> wordMap,
                              Set<Character> guesses,
                              boolean[] corrects) {
        showProgress(word, corrects);

        char guess = getGuess(sc, guesses);
        guesses.add(guess);
        if (word.indexOf(guess) != -1) {
            List<Integer> letterIndices = wordMap.get(guess);
            for (int i = 0; i < letterIndices.size(); i++) {
                corrects[wordMap.get(guess).get(i)] = true;
            }
            return true;
        } else {
            return false;
        }
    }

    private char getGuess(Scanner sc, Set<Character> guesses) {
        String input;
        do {
            input = sc.nextLine().toUpperCase();
        } while (!isValidGuess(input, guesses));
        return input.charAt(0);
    }

    private boolean isValidGuess(String input, Set<Character> guesses) {
        if (input.length() == 0) {
            return false;
        } else if (input.length() != 1
                   || (input.length() == 1 && !Character.isLetter(input.charAt(0)))) {
            System.out.println("Please enter a valid letter.");
            return false;
        } else if (input.length() == 1 && guesses.contains(input.charAt(0))) {
            System.out.printf("You already chose %s. Please try again.\n", input);
            return false;
        }
        return true;
    }

    private boolean queryPlayAgain(Scanner sc, boolean success) {
        if (ASK_MODES.contains(mode.toUpperCase())) {
            if (success) {
                return false;
            } else {
                System.out.println("Oops, you were close. Try again, yeah?");
                return true;
            }
        }

        // Normal mode
        if (success) {
            System.out.print("Congratulations! :)");
        } else {
            System.out.println("Oops! You lost :( ");
        }
        System.out.println(" Would you like to play again? (yes/no)");
        String response = sc.nextLine().toUpperCase();
        while (!(response.startsWith("Y") || response.startsWith("N"))) {
            System.out.println("Please type 'yes' or 'no.'");
            response = sc.nextLine().toUpperCase();
        }
        if (response.equals("YES")) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String mode = "normal";
        if (args.length > 0) {
            mode = args[0];
        }
        Hangman game = new Hangman(mode);
        game.run();
    }

}
