import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

  /*
   * this is the tester class for WordStat
   */
public class WordStatTester {
  
  String[] arr = new String[] {"hello","my","name","is","gautam", "gautam", "is", "is", "name","oh", "name", "is"};
  WordStat wordstat = new WordStat(arr);
  
  
  /*
   * this is the method to test wordCount()
   */
  @Test
  public void wordCountTest() {
    assertEquals(1, wordstat.wordCount("hello"));
    assertEquals(2, wordstat.wordCount("gautam"));
    assertEquals(-1, wordstat.wordCount("whattup"));
  }
  
  
  /*
   * this is the method to test wordPairCount()
   */
  @Test
  public void wordPairCountTest() {
    assertEquals(1, wordstat.wordPairCount("hello", "my"));
    assertEquals(1, wordstat.wordPairCount("gautam", "is"));
    
  }
  
  @Test
  public void wordRankTest() {
    assertEquals(1, wordstat.wordCount("hello"));
    assertEquals(-1, wordstat.wordCount("bro"));
    
  }
  
  /*
   * this is the tes
   */
  @Test 
  public void mostCommonWordsTest() {
    assertEquals("[is]", Arrays.toString(wordstat.mostCommonWords(1)));
    assertEquals("[is, name]", Arrays.toString(wordstat.mostCommonWords(2)));
  
  }
  
  @Test
  public void leastCommonWordsTest() {
    String[] arr1 = new String[]{"hello", "this", "is", "is", "is", "is", "testing", "testing", "testing", "testing"};
    WordStat wordstat1 = new WordStat(arr1);
    assertEquals("[this]", Arrays.toString(wordstat1.leastCommonWords(1)));
  }
 @Test
 public void wordPairRankTest() {
   assertEquals(1, wordstat.wordPairRank("name","is"));
 }
  
    
  @Test
  public void collocsTest() {
    String[] arr1 = new String[]{"hello", "this", "this", "is", "is", "is", "my", "testing", "testing", "testing", "testing"};
    WordStat wordstat1 = new WordStat(arr1);
    assertEquals("[this, hello]", Arrays.toString(wordstat1.mostCommonCollocs(2,"my",-1)));
  }
  
  @Test
  public void collocsExclusionTest() {
    String[] arr1 = new String[]{"hello", "this", "this", "is", "is", "is", "my", "testing", "testing", "testing", "testing"};
    WordStat wordstat1 = new WordStat(arr1);
    assertEquals("[hello]", Arrays.toString(wordstat1.mostCommonCollocs(1, "my",-1, new String[]{"is"})));
  }
  
  @Test
  public void generateWordStringTest() {
    String[] arr1 = new String[]{"hello", "this", "this", "is", "is", "is", "my", "testing", "testing", "testing", "testing"};
    WordStat wordstat1 = new WordStat(arr1);
    assertEquals("my testing", wordstat1.generateWordString(1, "my"));
  }
}