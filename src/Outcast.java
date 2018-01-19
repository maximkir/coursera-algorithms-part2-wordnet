public class Outcast {

    private final WordNet wordNet;

    public Outcast(WordNet wordnet){
        this.wordNet = wordnet;

    }
    public String outcast(String[] nouns){

        int maxDistance = 0;
        String noun = null;
        for (int a = 0; a < nouns.length; a++){
            String nounA = nouns[a];
            int distance = 0;
            for (int b = 0; b < nouns.length; b++){
                String nounB = nouns[b];
                if (!nounA.equals(nounB)){
                    distance +=  wordNet.distance(nounA, nounB);
                }
            }
            if (distance > maxDistance){
                maxDistance = distance;
                noun = nounA;
            }
        }
        return noun;
    }
}
