package cribhand;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import jcrib.Score;
import jcrib.Scoring;
import jcrib.cards.Card;
import jcrib.cards.Deck;
import jcrib.cards.Hand;

public class CribHand {

    public static void main(String[] cards) {

        Deck deck = new Deck();
        Hand hand = new Hand();
        for (String card : cards) {
            Card c = new Card(card);
            deck.removeCard(c);
            hand.addCard(c);
        }
        List<Hand> handCombinations = buildHandCombinations(hand);
        for (Hand h : handCombinations) {
            System.out.println(h);
            int total = 0;
            for (Score s : Scoring.scoreHand(h, new Card("JS"))) {
                total += s.getPoints();
            }
            System.out.println(total + " points");
        }
        System.out.println(handCombinations.size());
    }

    private static List<Hand> buildHandCombinations(Hand seedHand) {
        List<Hand> hands = new ArrayList<>();
        scanHands(new Hand(), seedHand, hands);
        return hands;
    }

    private static void scanHands(Hand currentHand, Hand baseHand,
            List<Hand> hands) {
        if (currentHand.size() == 4) {
            hands.add(currentHand);
            return;
        }

        Iterator<Card> it = baseHand.iterator();
        while (it.hasNext()) {
            Card c = it.next();
            it.remove();

            baseHand.removeCard(c);
            Hand newCurrent = new Hand(currentHand);
            newCurrent.addCard(c);

            Hand newBase = new Hand(baseHand);
            newBase.removeCard(c);

            scanHands(newCurrent, newBase, hands);
        }
    }
}
