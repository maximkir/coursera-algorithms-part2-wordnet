import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WordNet {

    private static final boolean DEBUG = false;

    private final Digraph wordNetDigraph;
    private final Map<String, List<Integer>> synonymsToIds = new HashMap<>();
    private final Map<Integer, String> idsToSynonyms = new HashMap<>();
    private final SAP sap;


    public WordNet(String synsets, String hypernyms){
        if (synsets == null || hypernyms == null){
            throw new IllegalArgumentException();
        }

        In in = new In(synsets);
        String[] lines =  in.readAllLines();
        wordNetDigraph = new Digraph(lines.length);
        String[] tuple;
        for (String line : lines){
            tuple = line.split(",");
            if (DEBUG){
                System.out.println("synset_id: " + tuple[0] + " ,synset: " + tuple[1] + " ,gloss: " + tuple[2]);
            }
            Integer synsetId = Integer.parseInt(tuple[0]);
            for (String synonym : tuple[1].split(" ")){
                synonymsToIds
                        .computeIfAbsent(synonym, k -> new LinkedList<>())
                        .add(synsetId);
            }
            idsToSynonyms.put(synsetId, tuple[1]);
        }
        in.close();
        in = new In(hypernyms);
        while (in.hasNextLine()){
            String line = in.readLine();
            tuple = line.split(",");
            for (int i=1; i < tuple.length; i++){
                if (DEBUG){
                    System.out.println("Adding edge: " + tuple[0] + " --> " + tuple[i]);
                }
                wordNetDigraph.addEdge(Integer.parseInt(tuple[0]), Integer.parseInt(tuple[i]));
            }
        }
        in.close();
        List<Integer> roots = findRoots(wordNetDigraph);
        if (roots.size() != 1 || hasCycle(wordNetDigraph)){
            throw new IllegalArgumentException("Not a DAG!");
        }
        sap = new SAP(wordNetDigraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return synonymsToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return synonymsToIds.containsKey(word);
    }

    /**
     * A distance between nounA and nounB,
     * distance(A, B) = distance is the minimum length of any ancestral path between any synset v of A and any synset w of B.
     *
     * Runtime: O(V + E)
     *
     * @param nounA
     * @param nounB
     * @return
     */
    public int distance(String nounA, String nounB){
        if (!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        return sap.length(synonymsToIds.get(nounA), synonymsToIds.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if (!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        int ancestor = sap.ancestor(synonymsToIds.get(nounA), synonymsToIds.get(nounB));
        return idsToSynonyms.get(ancestor);
    }


    private List<Integer> findRoots(Digraph g){
        List<Integer> roots = new LinkedList<>();
        for (int v = 0; v < g.V(); v++){
            if (!g.adj(v).iterator().hasNext()){
                roots.add(v);
            }
        }
        return roots;
    }

    /**
     * Checks whether given <code>Digraph</code> contains a cycle.
     *
     * This done using strong components algorithm.
     *
     * @param g
     * @return
     */
    private boolean hasCycle(Digraph g){
        DirectedCycle directedCycle = new DirectedCycle(g);
        return directedCycle.hasCycle();
    }

}
