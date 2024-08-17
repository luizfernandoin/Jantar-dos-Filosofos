/*Cada filosofo representa um thread em nosso sistema. No java, para criar uma
nova thread podemos criar uma subclasse da superclasse de mesmo nome.
* */

public class Filosofo extends Thread {
    final static int QUANTUM_MAX = 100;
    Mesa mesa;
    int pidFilosofo;

    public Filosofo(String nome, Mesa mesadejantar, int pidFilosofo) {
        super(nome);
        this.mesa = mesadejantar;
        this.pidFilosofo = pidFilosofo;
    }

    /*
    Quando a thread é executada esse método é invocado. Basicamente, ele contem as
    instruções que a thread deve executar.

    - Chamado pelo método start(), visto que a chamada run() pode causar problemas.
    */
    public void run() {
        int quantum = 0;
        while (true) {
            quantum = (int) (Math.random() * QUANTUM_MAX);
            pensar(quantum);

            mesa.pegarHashis(pidFilosofo);
            quantum = (int) (Math.random() * QUANTUM_MAX);

            comer(quantum);
            mesa.retornarHashis(pidFilosofo);
        }
    }

    public void pensar(int tempo) {
        try {
            sleep(tempo);
        } catch (InterruptedException e) {
            System.out.println("O Filófoso pensou em demasia");
        }
    }

    public void comer(int tempo) {
        try {
            sleep(tempo);
        } catch (InterruptedException e) {
            System.out.println("O Filósofo comeu em demasia");
        }
    }
}