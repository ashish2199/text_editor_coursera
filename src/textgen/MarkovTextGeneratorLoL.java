package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	public List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	public Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		String words[]=sourceText.split("[ ]+");
		/*
		for(String w:words){
			System.out.println(w+"");
		}
		*/
		if(starter.equals("")){starter = words[0];}
		String prevWord = starter;
		boolean present=false;
		for(int i=1;i<words.length;i++){
				int j=0;
				for(j=0;j<wordList.size();j++){
					present = wordList.get(j).getWord().equals(prevWord);
					if(present){break;}
				}
				
				//System.out.println("prev = "+prevWord);
				if(present){
					wordList.get(j).addNextWord(words[i]);
					//System.out.println("present at index="+j+" so added"+words[i]);
				}
				else{
					//System.out.println("not present");
					ListNode newNode = new ListNode(prevWord);
					newNode.addNextWord(words[i]);
					wordList.add(newNode);
				}
			prevWord=words[i];
		}
		/*
		for(ListNode n :wordList){
			System.out.print(n.toString());
		}
		*/
		int last=-1;
		
		
		int j;
		present = false;
		prevWord = words[words.length-1];
		for(j=0;j<wordList.size();j++){
			present = wordList.get(j).getWord().equals(prevWord);
			if(present){break;}
		}
		if(present){
			last = j;
		}else{
			ListNode newNode = new ListNode(prevWord);
			wordList.add(newNode);
			last = wordList.size()-1;
		}
		//System.out.println(last+" is "+words[words.length-1]);
		wordList.get(last).addNextWord(words[0]);
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if(numWords<=0||wordList.size()==0){
			return "";
		}
		String curWord = starter;
		String output = "";
		output+=curWord;
		while(--numWords>0){
			int j;
			boolean present;
			for(j=0;j<wordList.size();j++){
				present = wordList.get(j).getWord().equals(curWord);
				if(present){
					String w=wordList.get(j).getRandomNextWord(rnGenerator);
					//System.out.println("found "+w);
					output+=" "+w;
					curWord=w;
					break;
				}
			}
			
		}
		return 	output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		wordList.clear();
		starter="";
		train(sourceText);
		// TODO: Implement this method.
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		
		//gen.train("Hi there. Up there is the sky.");
		//System.out.println(gen);
		
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		
		
		
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		
	    int r = generator.nextInt(nextWords.size());
		//System.out.println("list size="+nextWords.size()+" and r="+r);
	    return nextWords.get(r);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


