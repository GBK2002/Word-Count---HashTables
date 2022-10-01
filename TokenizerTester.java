import org.junit.Test;

import static org.junit.Assert.*;

public class TokenizerTester { 
  
  @Test
  public void wordListTest() {
    Tokenizer t = new Tokenizer(new String[]{"Hello", "Hi!!!!!","#R#R#R","Ok!E#$(#2"});
    assertEquals("Testing wordList","[hello, hi, rrr, oke]", t.wordList().toString());
  }
  
  @Test
  public void wordPairListTest() {
    Tokenizer t = new Tokenizer(new String[]{"Hello", "Hi!!!!!","#R#R#R","Ok!E#$(#2"});
    assertEquals("Testing wordList","[hello hi, hi rrr, rrr oke]", t.wordPairList().toString());
  }
}