import java.util.*;

public class Game {

    private static ArrayList<String> suits = new ArrayList<>(Arrays.asList("S", "D", "C", "H"));
    private static ArrayList<String> numbers = new ArrayList<>(Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"));
    private static Map<String, Integer> numCardsForStage = Map.of(
        "holes", 2,
        "flop", 3,
        "turn", 1,
        "river", 1
    );
    private static Map<String, String> faceToNum = Map.of(
        "1", "14",
        "A", "14",
        "J", "11",
        "Q", "12",
        "K", "13"
    );

    private static ArrayList<String> cardTuplesToString(ArrayList<ArrayList<String>> cardTuples) {
        // Converts a list of cards represented by a tuple of number and suit back into a list of cards each represented by a unified string
        ArrayList<String> cardStrings = new ArrayList<String>();
        for (ArrayList<String> tup : cardTuples) {
            cardStrings.add(tup.get(0) + tup.get(1));
        }
        return cardStrings;
    }

    private static ArrayList<ArrayList<String>> getValidCards(Scanner sc, String stage,  ArrayList<ArrayList<String>> cardsOut) {
        ArrayList<String> cardsOutStrings = cardTuplesToString(cardsOut); // Used later to ensure that no duplicate cards are given even in between stages
        ArrayList<ArrayList<String>> splitCards = new ArrayList<>();
        boolean invalid = true;

        while (invalid) {
            System.out.println("Enter " + stage + ":");
            String cardsString = sc.nextLine().toUpperCase().strip();
            if (cardsString.equals("RESET")) return null;

            String[] cardsArray = cardsString.split(" ");
            ArrayList<String> cardsList = new ArrayList<>(Arrays.asList(cardsArray));
            int numCards = cardsList.size();
            splitCards.clear();

            // Checking for invalid duplicates and invalid number of cards shown for the given stage
            Set<String> cardsSet = new HashSet<String>(cardsList);
            if (cardsSet.size() < numCards || numCards != numCardsForStage.get(stage)) {
                System.out.println("Invalid Format\n");
                continue;
            }

            for (String card : cardsList) {
                String num = card.substring(0, card.length() - 1);
                String suit = card.substring(card.length() - 1);

                if (faceToNum.containsKey(num)) {
                    num = faceToNum.get(num);
                }

                if (!numbers.contains(num) || !suits.contains(suit) || cardsOutStrings.contains(card)) {
                    System.out.println("Invalid Format\n");
                    invalid = true;
                    break;
                }
                ArrayList<String> splitCard = new ArrayList<>(Arrays.asList(num, suit));
                splitCards.add(splitCard);
                invalid = false;
            }
        }

        return splitCards;
    }

    public static void main(String[] args) {
        boolean play = true;
        Scanner sc = new Scanner(System.in);

        while (play) {
            ArrayList<ArrayList<String>> cardsOut = new ArrayList<ArrayList<String>>();
            ArrayList<ArrayList<String>> holes = Game.getValidCards(sc, "holes", cardsOut);
            if (holes == null) {continue;}
            cardsOut.addAll(holes);
            System.out.println(cardsOut);

            ArrayList<ArrayList<String>> flop = Game.getValidCards(sc, "flop", cardsOut);
            if (flop == null) {continue;}
            cardsOut.addAll(flop);
            System.out.println(cardsOut);

            // while (true) {
            //     System.out.println("Another Round? (Y/N)");
            //     String cont = sc.nextLine().toLowerCase().strip();
            //     if (cont.equals("y")) {break;}
            //     else if (cont.equals("n")) {
            //         play = false;
            //         break;
            //     }
            // }
            play = false;
        }

        sc.close();
    }
}