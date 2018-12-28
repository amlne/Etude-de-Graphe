import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public abstract class Graphe implements GrapheInterface {

    public HashMap<Integer, SommetInterface> listeSommets;
    private int nbAretes;
    private int nbSommet;
    int degreMax;

    public Graphe() {
        listeSommets = new HashMap<>();
        nbAretes = 0;
        nbSommet = 0;
        degreMax = 0;
    }

    @Override
    public void chargement(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splittedLine = line.split("\\s|\\t");
                if (splittedLine[0].equals("#")) {      // Ignore les commentaires
                    continue;
                }
                int n1 = Integer.parseInt(splittedLine[0]);
                int n2 = Integer.parseInt(splittedLine[1]);
                SommetInterface sommet1, sommet2;
                if (n1 != n2) {
                    SommetInterface s1, s2;
                    s1 = (this instanceof GrapheO) ? new SommetO(n1) : new SommetNO(n1);
                    s2 = (this instanceof GrapheO) ? new SommetO(n2) : new SommetNO(n2);

                    sommet1 = ((sommet1 = listeSommets.putIfAbsent(n1, s1)) == null)
                            ? listeSommets.get(n1) : sommet1;

                    sommet2 = ((sommet2 = listeSommets.putIfAbsent(n2, s2)) == null)
                            ? listeSommets.get(n2) : sommet2;

                    sommet1.ajoutSommet(sommet2);
                    this.nbAretes++;
                    degreMax = (sommet1.getListe().size() > degreMax) ? sommet1.getListe().size() : degreMax;
                    degreMax = (sommet2.getListe().size() > degreMax) ? sommet2.getListe().size() : degreMax;
                }
            }
            nbSommet = listeSommets.size();
            this.nbAretes /= 2;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    @Override
    public SommetInterface[] triSommetC() {      // retourne le tableau de sommets trié croissant
        SommetInterface[] sommets = new SommetInterface[nbSommet];
        this.listeSommets.values().toArray(sommets);
        Arrays.sort(sommets);
        return sommets;
    }

    @Override
    public SommetInterface[] triSommetD() {      // retourne le tableau de sommets trié décroissant
        SommetInterface[] sommets = new SommetInterface[nbSommet];
        this.listeSommets.values().toArray(sommets);
        Arrays.sort(sommets, Collections.reverseOrder());
        return sommets;
    }

    @Override
    public void bascule() {
        SommetInterface[] sommetsTries = this.triSommetD();

        int sommePartiel = 0;
        for (int b = 0; b < sommetsTries.length; b++) {
            sommePartiel += sommetsTries[b].getListe().size();
            double m2 = 2 * nbAretes;
            if ((double) b / nbSommet < (1 - (sommePartiel / m2))) {
                if (((double) (b + 1) / nbSommet) > (1 - ((sommePartiel + sommetsTries[b + 1].getListe().size()) / m2))) {
                    System.out.println("valeur d’équirépartition : " + ((double) b / nbSommet * 100) + "%");
                    System.out.println("bascule : " + b + " sur " + nbSommet);
                    System.out.println("degré de bascule : " + sommetsTries[b].getListe().size());
                    break;
                }
            }
        }
    }

    @Override
    public void coeur() {
        int k = 0;
        int nbSommets = listeSommets.size();
        boolean stop = false;

        while (!stop) {
            boolean checked = true;
            for (SommetInterface s : listeSommets.values()) {
                if (!s.getSupp() && s.getDegre() < k) {
                    s.supprSommet();
                    nbSommets--;
                    checked = false;
                }
            }
            if (!checked) {
                continue;
            }
            System.out.println(k + "-coeur restent: " + nbSommets + " sommets.");
            if (nbSommets == 0) {
                stop = true;
            }
            k++;
        }

    }

    @Override
    public void cluG() { //a améliorer

        int nbTriangle = 0; //ok
        double nv = 0; //ok

        for (SommetInterface s : listeSommets.values()) {
            if (s.getDegre() < 2) {
                continue;
            }
            nv += ((s.getListe().size()) * (s.getListe().size() - 1)) / 2;

            for (SommetInterface voisin : s.getListe()) {
                for (SommetInterface voisinBis : voisin.getListe()) {//voisinBis voisin des voisins de s
                    if (voisinBis.getListe().contains(s)) {
                        nbTriangle++;
                    }
                }
            }
        }
        System.out.println("nb de triangle:" + nbTriangle / 6 + " nombre de V = " + nv);
        System.out.println("clustering global : " + nbTriangle / 2 / nv);
    }

    @Override
    public void cluL() {
        int nbTriangleL;
        double cluLSommePartiel = 0;
        for (SommetInterface s : listeSommets.values()) {
            nbTriangleL = 0;
            if (s.getListe().size() <= 1) {
                continue;
            }
            for (SommetInterface voisin : s.getListe()) {
                for (SommetInterface voisinBis : voisin.getListe()) {//voisinBis voisin des voisins de s
                    if (voisinBis.getListe().contains(s)) {
                        nbTriangleL++;

                    }
                }
            }
            cluLSommePartiel += (double) (2 * nbTriangleL) / ((s.getListe().size()) * (s.getListe().size() - 1));


        }
        System.out.println("clustering local moyen " + cluLSommePartiel / (listeSommets.size() * 2));
    }

    @Override
    public void diametre(int i) {
        //en route pour trouver a1 depuis i!!!!
        int distancea1b1;
        int distancea2b2;
        SommetInterface a1;
        a1 = parcoursBFF(i, true);


        //2eme BFF de a1 pour trouver b1 // tout les sommets sont a true
        SommetInterface b1 = null;
        LinkedList<SommetInterface> listAvisite = new LinkedList<SommetInterface>();
        int nbSommetParc = 0;
        SommetInterface s = listeSommets.get(a1.getId());
        listAvisite.add(s);
        s.setSupp(false);
        s.setDistance(0);
        HashMap<SommetInterface, SommetInterface> ascendance = new HashMap<>();
        ascendance.put(s, null);


        while (!listAvisite.isEmpty()) {
            SommetInterface sTmp = listAvisite.get(0);
            listAvisite.remove(sTmp);
            nbSommetParc++;
            for (SommetInterface voisin : sTmp.getListe()) {
                if (voisin.getSupp()) {
                    listAvisite.add(voisin);
                    voisin.setSupp(false);
                    voisin.setDistance(sTmp.getDistance() + 1);
                    b1 = voisin;
                    ascendance.put(voisin, sTmp);
                }
            }
        }
        if (nbSommetParc < listeSommets.size()) {
            System.out.println("Warning : non connexe, on a parcouru seulement " + nbSommetParc + " sommets sur " + listeSommets.size());
        }
        distancea1b1 = b1.getDistance();

        //calcul de m2, le milieu de a1 et b1
        SommetInterface tmpM2 = b1;
        for (int j = 0; j < b1.getDistance() / 2; j++) {
            tmpM2 = ascendance.get(tmpM2);
        }

        //3eme BFF pour le calcul de a2 //tout les sommets a false
        SommetInterface a2;
        a2 = parcoursBFF(tmpM2.getId(), true);

        //4eme BFF pour calculer b2  //tout les sommets sont a true
        SommetInterface b2;
        b2 = parcoursBFF(a2.getId(), false);
        distancea2b2 = b2.getDistance();
        //affichage:
        System.out.println("Heuristique de Habib : r = " + i + " ,a1 = " + a1.getId() + " ,b1 = " + b1.getId()
                + " ,a2 = " + a2.getId() + ", b2 = " + b2.getId());
        System.out.println("dist(" + a1.getId() + " ," + b1.getId() + ") = " + distancea1b1 +
                "  ,dist(" + a2.getId() + " ," + b2.getId() + ") = " + distancea2b2);
    }


    public SommetInterface parcoursBFF(int sommet, boolean b) {
        SommetInterface resulat = null;
        SommetInterface s = listeSommets.get(sommet);
        s.setDistance(0);
        LinkedList<SommetInterface> listAvisite = new LinkedList<SommetInterface>();
        listAvisite.add(s);
        int nbSommetParc = 0;
        s.setSupp(b);
        while (!listAvisite.isEmpty()) {
            SommetInterface sTmp = listAvisite.get(0);
            listAvisite.remove(sTmp);
            nbSommetParc++;
            for (SommetInterface voisin : sTmp.getListe()) {
                if (voisin.getSupp() != b) {
                    listAvisite.add(voisin);
                    voisin.setSupp(b);
                    voisin.setDistance(sTmp.getDistance() + 1);
                    resulat = voisin;
                }
            }
        }
        if (nbSommetParc < listeSommets.size()) {
            System.out.println("Warning : non connexe, on a parcouru seulement " + nbSommetParc + " sommets sur " + listeSommets.size());
        }
        return resulat;
    }
}
