import java.util.List;

public abstract class Sommet implements SommetInterface, Comparable<Sommet> {
    public int degre = 0;
    public boolean supp = false;
    public abstract List<SommetInterface> getListe();
    public int distance =0;
    public int id=0;

    public Sommet(int x){
        this.id=x;
    }

    @Override
    public int compareTo(Sommet o) {
        return Integer.compare(this.getListe().size(), o.getListe().size());
    }

    @Override
    public int getDegre() {
        return degre;
    }

    @Override
    public void decDegre() {
        this.degre--;
    }

    @Override
    public boolean getSupp() {
        return supp;
    }

    @Override
    public void setSupp(boolean b) {
        this.supp = b;
    }

    public void setDistance(int x){
        distance = x;
    }
    public int getDistance(){
        return distance;
    }
    public void setId(int x){
        this.id = x;
    }
    public int getId(){
        return  this.id;
    }
}
