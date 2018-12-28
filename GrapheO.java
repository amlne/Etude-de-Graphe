public class GrapheO extends Graphe implements GrapheInterface {

    int degreMaxSortant = 0;

    public GrapheO() {
        super();
    }

    @Override
    public void distri() {
        int[] distribSortant = new int[degreMax + 1];
        int[] distribEntrant = new int[degreMax + 1];
        for (SommetInterface sommet : this.listeSommets.values()) {
            SommetO sommetO = (SommetO) sommet;
            distribSortant[sommetO.listSucc.size()]++;
            distribEntrant[sommetO.listPred.size()]++;
        }
        for (int i = 0; i < Math.max(distribEntrant.length, distribSortant.length); i++) {
            System.out.println(i + " " + distribSortant[i] + " " + distribEntrant[i]);
        }
    }
}

