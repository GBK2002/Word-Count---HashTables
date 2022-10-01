import java.io.*;
import java.util.*;
public class WordStat {
  //Stores the tokenizer that processes the data for this word stat instance
  private Tokenizer tokenizer;
  
  //stores the priority queue that stores all the words
  private PriorityQueue<String> wordQueue;
  
  //stores the priority queue that stores all the wordpairs
  private PriorityQueue<String> wordPairQueue;
    
  //stores the array list containing most common words
  private ArrayList<String> mostCommonWords;
  
  //stores the array list containing most common word pairs
  private ArrayList<String> mostCommonWordPairs;
  
  //stores the hash table that stores each word's count
  private HashTable wordTable;
  
  //stores the hash table that stores each word pair's count
  private HashTable wordPairTable;
  
  public WordStat(String[] wordList) {
    try{
      tokenizer = new Tokenizer(wordList);
      
      mostCommonWords = tokenizer.wordList();
      
      mostCommonWordPairs = tokenizer.wordPairList();
    }
    catch(Exception e) {
      throw e;
    }
    
    //Hash table created with load factor less than 2
    wordTable = new HashTable(2 * mostCommonWords.size());
    
    //adds all words to hash table, and increases count if it already exists
    for (String i : mostCommonWords) {
      if(wordTable.get(i) == -1) {
        wordTable.put(i, 1);
      }
      else {
        wordTable.update(i, wordTable.get(i) + 1);

      }
    }  
    
    
    wordPairTable = new HashTable(2 * mostCommonWordPairs.size());
    
    //adds all word pairs to hash table, and increases count if it already exists
    for (String i : mostCommonWordPairs) {
      if(wordPairTable.get(i) == -1) {
        wordPairTable.put(i, 1);
      }
      else {
        wordPairTable.update(i, wordPairTable.get(i) + 1);

      }
    }  
    
    //removing all duplicates
    mostCommonWords = removeDuplicates(mostCommonWords);
    mostCommonWordPairs = removeDuplicates(mostCommonWordPairs);
    
    //creating and adding all words to priority queue based on their table values
    wordQueue = new PriorityQueue<String>((a, b) -> wordTable.get(b) - wordTable.get(a));
    for(String i: mostCommonWords) {
      wordQueue.add(i);
    }
    
    //creating and adding all word pairs to priority queue based on their table values
    wordPairQueue = new PriorityQueue<String>((a, b) -> wordPairTable.get(b) - wordPairTable.get(a));
    for(String i: mostCommonWordPairs) {
      wordPairQueue.add(i);
    }
  }
  
  
  //same functionality but with file name
  public WordStat(String fileName) throws IOException{
    try{
      tokenizer = new Tokenizer(fileName);
      
      mostCommonWords = tokenizer.wordList();
      
      mostCommonWordPairs = tokenizer.wordPairList();
    }
    catch(IOException e) {
      throw e;
    }
    wordTable = new HashTable(2 * mostCommonWords.size());
    for (String i : mostCommonWords) {
      if(wordTable.get(i) == -1) {
        wordTable.put(i, 1);
      }
      else {
        wordTable.update(i, wordTable.get(i) + 1);

      }
    }  
    
    wordPairTable = new HashTable(2 * mostCommonWordPairs.size());
    for (String i : mostCommonWordPairs) {
      if(wordPairTable.get(i) == -1) {
        wordPairTable.put(i, 1);
      }
      else {
        wordPairTable.update(i, wordPairTable.get(i) + 1);

      }
    }  
    
    mostCommonWords = removeDuplicates(mostCommonWords);
    
    mostCommonWordPairs = removeDuplicates(mostCommonWordPairs);
    
    
    wordQueue = new PriorityQueue<String>((a, b) -> wordTable.get(b) - wordTable.get(a));
    for(String i: mostCommonWords) {
      wordQueue.add(i);
    }
    
    wordPairQueue = new PriorityQueue<String>((a, b) -> wordPairTable.get(b) - wordPairTable.get(a));
    for(String i: mostCommonWordPairs) {
      wordPairQueue.add(i);
    }
  
      
  }
  
  //returns hash table value of a word/key
  public int wordCount(String word) {
    return wordTable.get(word.toLowerCase());
  }
  
  //returns hash table value of a wordpair/key
  public int wordPairCount(String w1, String w2) {
    return wordPairTable.get(w1.toLowerCase() + " " + w2.toLowerCase());
  }
  
  //returns index of a word in mostCommonWords
  public int wordRank(String word) {
    word = word.toLowerCase();
    int rank = 1;
    for (String i: wordQueue) {
      if (i.equals(word)) {
        return rank;
      }
      rank++;
    }
    return 0;
  }
  
  //returns index of a wordpair in mostCommonWordPairs
  public int wordPairRank(String w1, String w2) {
    String s = w1.toLowerCase() + " " + w2.toLowerCase();
    int rank = 1;
    for (String i: wordPairQueue) {
      if (i.equals(s)) {
        return rank;
      }
      rank++;
    }
    return 0;
  }
  
  //returns the k most common words by adding the first k elements in mostCommonWords
  public String [] mostCommonWords(int k) {
    String[] s = new String[k];
    int count = 0;
    for(String i: wordQueue) {
      if (count < k){
        s[count++] = i;
      }
    }
    return s;
  }
  
  //returns the k least common words by adding the last k elements in leastCommonWords
  public String[] leastCommonWords(int k) {
    ArrayList<String> temp = new ArrayList<String>();
    for (String i: wordQueue) {
      temp.add(i);
    }
    Collections.reverse(temp);
    String[] s = new String[k];
    for(int i = 0; i < k; i++) {
      s[i] = temp.get(i);
    }
    return s;
  }
  
  //returns the k least common word pairs by adding the first k elements in mostCommonWordPairs
  public String[] mostCommonWordPairs(int k) {
    String[] s = new String[k];
    int count = 0;
    for(String i: wordPairQueue) {
      if (count < k) {
        s[count++] = i;
      }
    }
    return s;
  }
  
  //returns the k most common collocations
  public String[] mostCommonCollocs(int k, String baseWord, int i) {
    ArrayList<String> words = tokenizer.wordList();
    ArrayList<String> temp = new ArrayList<String>();
    
    //checking if i is -1 or 1 and the base word exists
    if((i == 1 || i == -1) && words.contains(baseWord.toLowerCase())) {
      int index = 0;
      //finding the position at which the base word exists
      for (int j = 0; j < words.size();j++) {
        if (words.get(j).equals(baseWord)){
          break;
        }
        index++;
      }
      
      //adding all words before a base word
      if (i == - 1) {
        for (int j = 0; j < index; j++) {
          temp.add(words.get(j));
        }
      }
      //adding all words after a base word
      else {
        for (int j = index + 1; j < words.size();j++) {
          temp.add(words.get(j));
        }
      }
      PriorityQueue<String> collocQueue = new PriorityQueue<String>((a, b) -> wordTable.get(b) - wordTable.get(a));
      int count = 0;
      //removing all duplicates
      temp = removeDuplicates(temp);
      //choosing only first k words in the queue
      for(String j: temp) {
        collocQueue.add(j);
        if(count == k - 1) {
          break;
        }
        count++;
      }
      return collocQueue.toArray(new String[collocQueue.size()]);
    }
    else
      return wordQueue.toArray(new String[wordQueue.size()]);
  }
  
  //removing duplicates method
  public ArrayList<String> removeDuplicates(ArrayList<String> list) {
    ArrayList<String> newList = new ArrayList<String>();
    for (String element : list) {
      if (!newList.contains(element)) {
        newList.add(element);
      }
    }
    return newList;
  }
  
  //uses mostCommonCollocs and improves upon the functionality
  public String[] mostCommonCollocs(int k, String baseWord, int i, String[] exclusions) {
    ArrayList<String> words = tokenizer.wordList();
    ArrayList<String> temp = new ArrayList<String>();
    if((i == 1 || i == -1) && words.contains(baseWord.toLowerCase())) {
      int index = 0;
      for (int j = 0; j < words.size();j++) {
        if (words.get(j).equals(baseWord)){
          break;
        }
        index++;
      }
      if (i == - 1) {
        for (int j = 0; j < index; j++) {
          temp.add(words.get(j));
        }
      }
      else {
        for (int j = index + 1; j < words.size();j++) {
          temp.add(words.get(j));
        }
      }
      
      //Removing all words in exclusions
      temp.removeAll(Arrays.asList(exclusions));
      PriorityQueue<String> collocQueue = new PriorityQueue<String>((a, b) -> wordTable.get(b) - wordTable.get(a));
      int count = 0;
      temp = removeDuplicates(temp);
      for(String j: temp) {
        collocQueue.add(j);
        if(count == k - 1) {
          break;
        }
        count++;
      }
      return collocQueue.toArray(new String[collocQueue.size()]);
    }
    else
      return wordQueue.toArray(new String[wordQueue.size()]);
  }
    
  //generates string of all common words after a startword
  public String generateWordString(int k, String startWord) {
    String[] wordArray = mostCommonCollocs(k, startWord, 1);
    StringBuilder string = new StringBuilder(startWord);
    for (String i : wordArray) {
      string.append(" ");
      string.append(i);
      
    }
    return string.toString();
  }
  
  
  public static void main(String[] args) throws IOException {
    WordStat w1 = new WordStat("Project Gutenburg.txt");
    System.out.println("Project Gutenburg :");
    
    System.out.print("WordCount of the: ");
    System.out.println(w1.wordCount("the"));
    
    System.out.print("WordRank of the: ");
    System.out.println(w1.wordRank("the"));
    
    System.out.print("WordPairCount of is the: ");
    System.out.println(w1.wordPairCount("is", "the"));
    
    System.out.print("10 mostCommonWords: ");
    System.out.println(w1.mostCommonWords(10));
    
    System.out.print("10 leastCommonWords: ");
    System.out.println(w1.leastCommonWords(10));
    
    System.out.print("10 mostCommonWordPairs: ");
    System.out.println(w1.mostCommonWordPairs(10));
    
    System.out.print("10 mostCommonWords before veterinary");
    System.out.println(w1.mostCommonCollocs(10, "veterinary", -1));
                       
  }
    
    
    
  
  
  

          
          
  
  
  
  
          
      
    
    
}