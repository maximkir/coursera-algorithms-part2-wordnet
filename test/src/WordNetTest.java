import org.junit.Assert;
import org.junit.Test;

public class WordNetTest {



    @Test(expected = IllegalArgumentException.class)
    public void cycleDetectionTest(){
        WordNet wn = new WordNet("test/resources/wordnet/synsets.txt", "test/wordnet/hypernyms6InvalidCycle.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void multipleRootsDetectionTest(){
        WordNet wn = new WordNet("test/resources/wordnet/synsets.txt", "test/wordnet/hypernyms6InvalidTwoRoots.txt");
    }


    @Test
    public void distanceTest(){
        WordNet wn = new WordNet("test/resources/wordnet/synsets.txt", "test/resources/wordnet/hypernyms.txt");

        Assert.assertEquals(16, wn.distance("hibernation", "biomedicine"));
    }


}
