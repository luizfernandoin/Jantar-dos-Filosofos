import java.util.concurrent.ThreadLocalRandom;

public class Filosofo extends Thread {
    final static int QUANTUM_MAX = 100;
    private final Mesa mesa;
    private final int idFilosofo;

    public Filosofo(String nome, Mesa mesa, int idFilosofo) {
        super(nome);
        this.mesa = mesa;
        this.idFilosofo = idFilosofo;
    }

    @Override
    public void run() {
        while (true) {
            int quantum = ThreadLocalRandom.current().nextInt(QUANTUM_MAX);
            pensar(quantum);

            mesa.pegarHashis(idFilosofo);
            quantum = ThreadLocalRandom.current().nextInt(QUANTUM_MAX);

            comer(quantum);
            mesa.retornarHashis(idFilosofo);
        }
    }

    private void pensar(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            System.out.println(getName() + " pensou em demasia");
            Thread.currentThread().interrupt(); // Preserva o estado de interrupção da thread
        }
    }

    private void comer(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            System.out.println(getName() + " comeu em demasia");
            Thread.currentThread().interrupt(); // Preserva o estado de interrupção da thread
        }
    }
}
