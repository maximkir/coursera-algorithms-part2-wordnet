import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Collections;


public class SAP {

    private final Digraph g;

    public SAP(Digraph G){
        this.g = new Digraph(G);
    }

    /**
     * length of shortest ancestral path between v and w; -1 if no such path
     *
     *
     * @param v
     * @param w
     * @return
     */
    public int length(int v, int w){
        return length(Collections.singleton(v), Collections.singleton(w));
    }

    /**
     * A common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
     *
     * Perform BFS while both vertices in the start queue.
     *
     * First encountered visited vertex is the common ancestor
     *
     *
     * Assumes that the digraph is rooted DAG
     * @param v
     * @param w
     * @return
     */
    public int ancestor(int v, int w){
        if (!isValidVertex(v) || !isValidVertex(w)){
            throw new IllegalArgumentException("No valid vertex");
        }
        return ancestor(Collections.singleton(v), Collections.singleton(w));
    }

    /**
     * length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     *
     *
     * @param v
     * @param w
     * @return
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        int ancestor = ancestor(v, w);
        int shortestLengthPath = Integer.MAX_VALUE;
        if (ancestor != -1){
            BreadthFirstDirectedPaths bfsDirectedPathsV = new BreadthFirstDirectedPaths(g, v);
            BreadthFirstDirectedPaths bfsDirectedPathsW = new BreadthFirstDirectedPaths(g, w);
            return bfsDirectedPathsV.distTo(ancestor) + bfsDirectedPathsW.distTo(ancestor);
        }
        return shortestLengthPath == Integer.MAX_VALUE ? -1 : shortestLengthPath;
    }

    /**
     * a common ancestor that participates in shortest ancestral path; -1 if no such path
     *
     *
     * @param v
     * @param w
     * @return
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){

        int shortestLengthPath = Integer.MAX_VALUE;
        int ancestor = -1;
        BreadthFirstDirectedPaths bfsDirectedPathsV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsDirectedPathsW = new BreadthFirstDirectedPaths(g, w);
        for (int k = 0; k < g.V(); k++){
            if (bfsDirectedPathsV.hasPathTo(k) && bfsDirectedPathsW.hasPathTo(k)){
                if (bfsDirectedPathsV.distTo(k) + bfsDirectedPathsW.distTo(k) < shortestLengthPath){
                    shortestLengthPath = bfsDirectedPathsV.distTo(k) + bfsDirectedPathsW.distTo(k);
                    ancestor = k;
                }
            }
        }
        return ancestor;
    }

    private boolean isValidVertex(int v){
        return v > -1 && v < g.V();
    }

}
