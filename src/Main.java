
public class Main {
    public static void main(String[] args) {
        Mesa mesa = new Mesa ();
        for (int pidFilosofo = 0; pidFilosofo < 5; pidFilosofo++)
        {
            new Filosofo("Filosofo_" + pidFilosofo, mesa, pidFilosofo).start();
        }
    }
}