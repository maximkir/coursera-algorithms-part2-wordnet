import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Assert;
import org.junit.Test;

public class SAPTest {


    @Test
    public void ancestorTest(){
        SAP sap = new SAP(new Digraph(new In("test/resources/wordnet/digraph1.txt")));

        Assert.assertTrue("", sap.ancestor(7,4) == 1);

        Assert.assertTrue("", sap.ancestor(11,12) == 10);

        Assert.assertTrue("", sap.ancestor(7,12) == 1);

        Assert.assertTrue("", sap.ancestor(1,6) == -1);

        Assert.assertEquals(10, sap.ancestor(12,10));
    }


    @Test
    public void lengthTest(){
        SAP sap = new SAP(new Digraph(new In("test/resources/wordnet/digraph1.txt")));
        Assert.assertTrue("", sap.length(3,11) == 4);

        Assert.assertTrue("", sap.length(9,12) == 3);

        Assert.assertTrue("", sap.length(7,2) == 4);

        Assert.assertTrue("", sap.length(1,6) == -1);

        Assert.assertEquals(1, sap.length(12,10));

    }
}
