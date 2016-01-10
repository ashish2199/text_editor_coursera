package textgen;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.util.ArrayList;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class MarkovTestGeneratorLoLTester {		
   Random rng = new Random(42);	
   String s1;		
   MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));		
   
   @Before	
	   public void setUp(){
		   s1 = "hi there hi Leo";	
	   }

   @Test 
	   public void testGetRandomNextWord() {
		   //check if randomly picking a word works on a node	
		   ListNode l1 = new ListNode("hi");
		   l1.addNextWord("there");		
		   l1.addNextWord("Leo");
		   assertThat(l1.getRandomNextWord(rng), anyOf(is("there"), is("Leo")));
		    
		    //are we getting all possible words?
		    ArrayList<String> list = new ArrayList<String>();
		    for (int i=0; i<= 10; i++) {
		        list.add(l1.getRandomNextWord(gen.rnGenerator));
			}		
		    assertThat(list, hasItems("there", "Leo"));				
		    //integration test with earlier methods
		    gen.train(s1);		
		    assertThat(gen.wordList.get(0).getRandomNextWord(gen.rnGenerator), anyOf(is("there"), is("Leo")));	
		}
}
