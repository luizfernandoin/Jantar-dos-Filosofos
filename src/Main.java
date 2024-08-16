//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Mesa mesa = new Mesa ();
        for (int filosofo = 0; filosofo < 5; filosofo++)
        {
            new Filosofo("Filosofo_" + filosofo, mesa, filosofo).start();
        }
    }
}