import java.util.*;

/**
 * A poker hand is a list of cards, which can be of some "kind" (pair, straight, etc.)
 * 
 */
public class Hand implements Comparable<Hand> {

    public enum Kind {HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, 
        FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH};

    private final List<Card> cards;

    /**
     * Create a hand from a string containing all cards (e,g, "5C TD AH QS 2D")
     */
    public Hand(String c) {
    	cards = new ArrayList<Card>();
    	String delims = "[ ]+";
    	String[] cardstr = c.split(delims);
    	for(int i=0; i<cardstr.length; ++i){
    		cards.add(new Card(cardstr[i]));
    	}
    }
    
    /**
     * @returns true if the hand has n cards of the same rank
	 * e.g., "TD TC TH 7C 7D" returns True for n=2 and n=3, and False for n=1 and n=4
     */
    protected boolean hasNKind(int n) {
    	HashMap<Card.Rank,Integer> occurance = new HashMap<Card.Rank,Integer>();
    	for(int i=0; i<5; ++i){
    		if(occurance.containsKey(cards.get(i).getRank())){
    			occurance.put(cards.get(i).getRank(),1);
    		}
    		else{
    			int tempcount = occurance.get(cards.get(i).getRank());
    			occurance.remove(cards.get(i).getRank());
    			occurance.put(cards.get(i).getRank(),++tempcount);
    		}
    	}
    	
    	return(occurance.containsValue(n));
    	
    }
    
    /**
	 * Optional: you may skip this one. If so, just make it return False
     * @returns true if the hand has two pairs
     */
    public boolean isTwoPair() {
    	HashMap<Card.Rank,Integer> occurance = new HashMap<Card.Rank,Integer>();
    	for(int i=0; i<5; ++i){
    		if(occurance.containsKey(cards.get(i).getRank())){
    			occurance.put(cards.get(i).getRank(),1);
    		}
    		else{
    			int tempcount = occurance.get(cards.get(i).getRank());
    			occurance.remove(cards.get(i).getRank());
    			occurance.put(cards.get(i).getRank(),++tempcount);
    		}
    	}
    	
    	for(int i=0; i<occurance.size(); ++i){
    		if(occurance.get(i)!=2) occurance.remove(i);
    	}
    	return(occurance.size()==2);
    }   
    
    /**
     * @returns true if the hand is a straight 
     */
    public boolean isStraight() {
    	Card.Rank temp = cards.get(0).getRank();
    	int currOrdinal = temp.ordinal();
    	for(int i=1; i<5; ++i){
    		temp=cards.get(0).getRank();
    		if(temp.ordinal()!=currOrdinal+1){
    			return(false);
    		}
    		else{
    			currOrdinal=temp.ordinal();
    		}
    	}
    	return(true);
    }
    
    /**
     * @returns true if the hand is a flush
     */
    public boolean isFlush() {
        Card.Suit temp = cards.get(0).getSuit();
        for(int i=1; i<5; ++i){
        	if(cards.get(i).getSuit()!=temp)return(false);
        }
        return(true);
    	
    }
    
    public int compareTo(Hand h) {
        return(this.kind().ordinal() - h.kind().ordinal());
    }
    
    /**
	 * This method is already implemented and could be useful! 
     * @returns the "kind" of the hand: flush, full house, etc.
     */
    public Kind kind() {
        if (isStraight() && isFlush()) return Kind.STRAIGHT_FLUSH;
        else if (hasNKind(4)) return Kind.FOUR_OF_A_KIND; 
        else if (hasNKind(3) && hasNKind(2)) return Kind.FULL_HOUSE;
        else if (isFlush()) return Kind.FLUSH;
        else if (isStraight()) return Kind.STRAIGHT;
        else if (hasNKind(3)) return Kind.THREE_OF_A_KIND;
        else if (isTwoPair()) return Kind.TWO_PAIR;
        else if (hasNKind(2)) return Kind.PAIR; 
        else return Kind.HIGH_CARD;
    }

}


