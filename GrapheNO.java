public class GrapheNO extends Graphe implements GrapheInterface {

    public GrapheNO() {
        super();
    }


    @Override
    public void distri() {
        int[] distribution = new int[degreMax + 1];
        for (SommetInterface s : listeSommets.values()) {
            distribution[s.getListe().size()]++;
        }
        for (int i = 0; i < distribution.length; i++) {
            System.out.println(i + " " + distribution[i]);
        }
    }

    /*public double cluG(){
        //fonct todo int nb triangle = 
        int nbTriangle=0;
        double nv =0;
        double res=0;
        for (SommetInterface s : listeSommets.values()) {
            nv+= ((s.getListe().size())*(s.getListe().size()-1))/2;
            for(SommetInterface voisin : s.getListe()){
                for(SommetInterface voisinBis: s.getListe()){
                    if(voisinBis == s){
                        nbTriangle++;
                    }
                }
            }
        }
        res = (3*nbTriangle)/nv;
        return res;
    }*/
}
