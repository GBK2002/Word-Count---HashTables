import java.util.*;
import java.io.*;
public class Tokenizer {
  
  //stores filepath
  private String filePath;
  
  //stores the list with words
  protected ArrayList<String> wordList;
  
  //stores the list with word pairs
  protected ArrayList<String> wordPairList;
  
  
  public Tokenizer(String filePath) throws IOException {
    this.filePath = filePath;
    FileReader fr = new FileReader(filePath);
    BufferedReader br = new BufferedReader(fr);
    String i;
    ArrayList<String> wordList = new ArrayList<String>();
    
    //Adds all words in file to an array list
    while((i = br.readLine()) != null) {
      if (i!=null){
        wordList.addAll(Arrays.asList(i.toLowerCase().split(" ")));
      }
    }
    
    //removes punctuation for all words in the word list
    for (int j = 0; j < wordList.size(); j++) {
      wordList.set(j, removePunct(wordList.get(j)));
    }
    
    //removes all empty characters in the word list
    while(wordList.contains("")) {
      wordList.remove("");
    }
    this.wordList = wordList;
    wordPairList = new ArrayList<String>();
    
    //creates the word pair list
    for (int j = 0; j < wordList.size() - 1; j++) {
      wordPairList.add(new StringBuilder(wordList.get(j)).append(' '+ wordList.get(j+1)).toString());
    }
    
    this.wordPairList = wordPairList;
      
      
  }
  
  //Constructor that performs same function except with a string[]
  public Tokenizer(String[] list) {
    ArrayList<String> wordList = new ArrayList<String>();
    for (String i : list) {
      wordList.addAll(Arrays.asList(i.toLowerCase().split(" ")));
    }
    while(wordList.contains("")) {
      wordList.remove("");
    }
    
    for (int j = 0; j < wordList.size(); j++) {
      wordList.set(j, removePunct(wordList.get(j)));

    }
    
    this.wordList = wordList;
    wordPairList = new ArrayList<String>();
    for (int j = 0; j < wordList.size() - 1; j++) {
      wordPairList.add(new StringBuilder(wordList.get(j)).append(' '+ wordList.get(j+1)).toString());
    }
  }
  
  //Returns the word list
  public ArrayList<String> wordList() {
    return wordList;
  }
  
  //Returns the word pair
  public ArrayList<String> wordPairList() {
    return wordPairList;
  }
  
  //private helper method to remove punctuation
  private String removePunct(String s) {
    for (int i = 0; i < s.length(); i++) {
      if (!Character.isLetter(s.charAt(i))) {
        StringBuilder string = new StringBuilder(s);
        s = string.deleteCharAt(i).toString();
        i--;
      }
    }
    return s;
  }
  
  
}