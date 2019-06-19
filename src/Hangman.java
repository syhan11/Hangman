import java.util.*;

/*
 * This program is a word guessing game app in Java.
 * This game is a simpler version of Hangman.
 *
 * When you run the application, randomly choose a word from that list for the user to
 * guess. For each letter the user guesses wrong, let them know along with how many
 * wrong guesses they have left. The user is allowed up to 5 wrong guesses.
 * On the 6th wrong guess, they lose the game.
 * If the user guesses correctly, output the word with the letters they have guessed
 * correctly so far and blanks for letters they have not guessed yet.
 * The user can input either a letter or a word for their guess.
 * If the user guesses the word correctly, output "You've won!" and end the game.
 */
public class Hangman {

    /*
     * printAnswer method will
     * print out the content of a char array separated with a blank
     */
    public static void printAnswer(char[] varAnswerA) {
        for (char c : varAnswerA)
            System.out.print(c + " ");

        System.out.println("");
    }

    /*
     * checkAnswer method will
     * look for any occurrences of a character (varGuess) in a string (varSecret)
     * if found then it will save the character into the answer array (varAnswer)
     * in the right position
     */
    public static void checkAnswer (String varSecret, char varGuess, char[] varAnswer) {
        int i = 0;
        int tmp = varSecret.indexOf(varGuess, i);
        int sz = varSecret.length();

        while ((0 <= tmp) && (i < sz)) {    // look for multiple occurrences of given character
            varAnswer[tmp] = varGuess;
            i = tmp+1;
            tmp = varSecret.indexOf(varGuess, i);
        }
    }

    public static boolean doneGuess(char[] varAnswer){
        boolean found = true;

        for (char tmpChar : varAnswer) {
            if (tmpChar == '_') {
                found = false;
                break;
            }
        }

        return (found);

    }



    public static void main(String[] args){
        final int MAX_TRIES = 6;
        List<String> wordsList = Arrays.asList("tree", "rain", "bear", "encourage", "promise",
                                                "soup", "chess", "insurance", "pancakes", "stream");
        ArrayList<String> wordsArray = new ArrayList<>();       // convert list into array list

        wordsArray.addAll(wordsList);                           // initialize wordsArray


        Random randomG = new Random();
        int idx = randomG.nextInt(wordsArray.size()-1);   // random numb: [0- array size)

        String secretS = wordsArray.get(idx);                   // get a random string
        int szSecretS = secretS.length();
        int noTries = 0;

        System.out.println("Secret word is " + secretS);
        System.out.print("Welcome, let's play hangman!\n" + "" +
                           "Here is the word I am thinking of:");

        // initialize answer to '_'
        char[] answerA = new char[szSecretS];
        for (int i = 0; i < szSecretS; i++)
            answerA[i] = '_';

        printAnswer(answerA);

        int noTry = 0;
        Scanner keyboard = new Scanner(System.in);
        String guess;
        boolean found = false;
        char guessCh;

        while ( !found && (noTry < MAX_TRIES)) {
            System.out.println("\nEnter a letter or word guess: ");
            guess = keyboard.next();

            if (guess.length() == 1) {
                checkAnswer(secretS, guess.charAt(0), answerA);
            }
            else if (secretS.compareToIgnoreCase(guess) == 0) {
                found = true;
                break;
            }

            found = doneGuess(answerA);
            if (!found) {
                System.out.printf("You have guessed incorrectly %d/%d times.\n", noTry + 1, MAX_TRIES);
                noTry++;
            }

            System.out.print("Your guess so far: ");
            printAnswer(answerA);
        }

        if (found)
            System.out.print("\nYou've won! The word was ");
        else
            System.out.print("\nSorry, you have no more guesses left. The word was ");

        System.out.println(secretS + "\n\nThank you for playing!");

    }
}
