import java.util.ArrayList;
import java.util.List;

public class SommetNO extends Sommet implements SommetInterface {

    public List<SommetInterface> listeAdj;

    public SommetNO(int x) {
        super(x);
        listeAdj = new ArrayList<>();
    }

    @Override
    public void ajoutSommet(SommetInterface s) {
        if (!this.listeAdj.contains(s)) {
            this.listeAdj.add(s);
            this.degre++;
            s.ajoutSommet(this);
        }
    }

    @Override
    public void supprSommet() {
        supp = true;
        for (SommetInterface s: listeAdj) {
            if (!s.getSupp()) {
                s.decDegre();
            }
        }
    }

    @Override
    public List<SommetInterface> getListe() {
        return listeAdj;
    }
}
