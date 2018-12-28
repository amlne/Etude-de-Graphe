
public class TP2 {

    public static void main(String[] args) {
        String fileName = "";
        String commande = "";
        boolean oriente = false;
        int numSommet = 0;

        if (args.length >= 2) {
            fileName = args[0];
            commande = args[1];
        } else {
            System.out.println("Erreur Arguments");
            System.exit(-1);
        }
        if (args.length == 3) {
            oriente = args[2].equals("oriente");
            if (!oriente) {
                numSommet = Integer.parseInt(args[2]);
            }
        }
        GrapheInterface graphe;
        if (oriente) {
            graphe = new GrapheO();
        } else {
            graphe = new GrapheNO();
        }
        graphe.chargement(fileName);
        System.out.println("Mémoire allouée: " +
                (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                + " Octets");
        switch (commande) {
            case "distri":
                graphe.distri();
                break;
            case "bascule":
                graphe.bascule();
                break;
            case "coeur":
                graphe.coeur();
                break;
            case "cluG":
                graphe.cluG();
                break;
            case "cluL":
                graphe.cluL();
                break;
            case "diametre":
                graphe.diametre(numSommet);
                break;
            default:
                System.out.println("Erreur dans la commande!");
        }
        System.out.println("Mémoire allouée: " +
                (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                + " Octets");
    }
}
