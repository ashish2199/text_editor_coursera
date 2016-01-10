package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		word=word.toLowerCase();
		TrieNode curr = root;
		int i=0;
		char c=word.charAt(i);
		boolean inserted = false;
		while(curr!=null){
			if(curr.getText().equals(word)&&!curr.endsWord()){
				inserted=true;
			}
			if(i>=word.length()){
				break;
			}
			c = word.charAt(i);
			if(curr.getChild(c)==null){
				inserted=true;
				curr.insert(c);
			}
			curr=curr.getChild(c);
			++i;
		}
		
		curr.setEndsWord(true);
		String inserted_text=curr.getText();
		//printTree();
		//System.out.println("inserted the word : "+inserted_text+"  "+inserted);
		if(inserted)size++;
		
		return inserted;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
		
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		//printTree();
		s=s.toLowerCase();
		TrieNode curr = root;
		int i=0;
		char c;
		while(curr!=null){
			if(curr.endsWord()){
				if(curr.getText().equals(s)){
					return true;
				}
			}
			if(i>=s.length()){break;}
			c = s.charAt(i);
			curr=curr.getChild(c);
			++i;
		}
		return false;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
    	 
    	 
    	 
    	String s=prefix.toLowerCase();
 		TrieNode curr = root;
 		int i=0;
 		char c;
 		while(curr!=null){
 			if(curr.endsWord()){
 				if(curr.getText().equals(s)){
 					break;
 				}
 			}
 			if(i>=s.length()){break;}
 			c = s.charAt(i);
 			//if(curr.getChild(c)!=null){
 				curr=curr.getChild(c);
 			/*
 			}else{
 				break;
 			}
 			*/
 			++i;
 		}
 		//System.out.println("asked for '"+prefix+"' size:"+numCompletions);
 		//System.out.println("reached '"+curr.getText()+"'");
 		LinkedList<String> completionList = new LinkedList<String>();
		LinkedList<TrieNode> q = new LinkedList<TrieNode>();
		q.addLast(curr);
		i=0;
		while(!q.isEmpty()){
		 	TrieNode current = q.remove();
		 	if(current==null){break;}
		 	//System.out.println("exploring: "+current.getText());
		 	if(current.endsWord()){
		 		if(i<numCompletions){
		 			//System.out.println("adding to list: "+current.getText());
		 			completionList.add(current.getText());
		 			i++;
		 		}
		 		else{
		 			//System.out.println("sending size: "+completionList.size());
		 			return completionList;
		 		}
		 	}
		 	Set <Character> st = current.getValidNextCharacters();
		 	Iterator<Character> it = st.iterator();
		 	while(it.hasNext()){
		 		q.add(current.getChild(it.next()));
		 	}
		}	
		
    	//System.out.println("sending size: "+completionList.size());
        return completionList;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}