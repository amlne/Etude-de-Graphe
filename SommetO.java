import java.util.ArrayList;
import java.util.List;

public class SommetO extends Sommet implements SommetInterface {

    public List<SommetInterface> listSucc;
    public List<SommetInterface> listPred;

    public SommetO(int x) {
        super(x);
        listSucc = new ArrayList<>();
        listPred = new ArrayList<>();
    }

    @Override
    public void ajoutSommet(SommetInterface s) {
        this.listSucc.add(s);
        this.degre++;
        ((SommetO) s).listPred.add(this);
    }

    @Override
    public void supprSommet() {
        supp = true;
        for (SommetInterface s: listPred) {
            if (!s.getSupp()){
                s.decDegre();
            }
        }
    }

    @Override
    public List<SommetInterface> getListe() {
        return listSucc;
    }
}
