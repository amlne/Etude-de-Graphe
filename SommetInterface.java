import java.util.List;

public interface SommetInterface{
    void ajoutSommet(SommetInterface s);
    void supprSommet();
    List<SommetInterface> getListe();
    int getDegre();
    void decDegre();
    boolean getSupp();
    void setSupp(boolean b);
    void setDistance(int x);
    int getDistance();
    int getId();
    void setId(int x);

}
