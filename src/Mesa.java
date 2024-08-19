public class Mesa {
    enum Estado {
        PENSANDO, COMENDO, FOME,
    }

    final static int NR_FILOSOFOS = 5;
    final static int PRIMEIRO_FILOSOFO = 0;
    final static int ULTIMO_FILOSOFO = NR_FILOSOFOS - 1;

    boolean[] hashis = new boolean[NR_FILOSOFOS]; //false -> ocupado; true -> desocupado
    Estado[] filosofos = new Estado[NR_FILOSOFOS];
    int[] tentativas = new int[NR_FILOSOFOS];

    public Mesa()
    {
        for (int i = 0; i < 5; i++)
        {
            hashis[i] = true;
            filosofos[i] = Estado.PENSANDO;
            tentativas[i] = 0;
        }
    }

    public synchronized void pegarHashis (int idFilosofo) {
        filosofos[idFilosofo] = Estado.FOME;
        while (filosofos[aEsquerda(idFilosofo)] == Estado.COMENDO ||
                filosofos[aDireita(idFilosofo)] == Estado.COMENDO) {
            try {
                tentativas[idFilosofo]++;
                if (tentativas[idFilosofo] > 10) {
                    System.out.println("O Filósofo " + idFilosofo + " morreu devido a starvation");
                    return;
                }
                wait();
            }
            catch (InterruptedException e) {
                System.err.printf("Erro ao esperar para o filósofo %d: %s%n", idFilosofo, e.getMessage());
            }
        }

        tentativas[idFilosofo] = 0;
        hashis[garfoEsquerdo(idFilosofo)] = false;
        hashis[garfoDireito(idFilosofo)] = false;
        filosofos[idFilosofo] = Estado.COMENDO;

        imprimeEstadosFilosofos();
        imprimeHashis();
        imprimeTentativas();
    }

    public synchronized void retornarHashis (int idFilosofo) {
        hashis[garfoEsquerdo(idFilosofo)] = true;
        hashis[garfoDireito(idFilosofo)] = true;

        if (filosofos[aEsquerda(idFilosofo)] == Estado.FOME ||
                filosofos[aDireita(idFilosofo)] == Estado.FOME) {
            notifyAll();
        }

        filosofos[idFilosofo] = Estado.PENSANDO;

        imprimeEstadosFilosofos();
        imprimeHashis();
        imprimeTentativas();
    }

    public int aDireita (int filosofo) {
        return (filosofo == ULTIMO_FILOSOFO) ? PRIMEIRO_FILOSOFO : filosofo + 1;
    }

    public int aEsquerda (int filosofo) {
        return (filosofo == PRIMEIRO_FILOSOFO) ? ULTIMO_FILOSOFO : filosofo - 1;
    }

    public int garfoEsquerdo (int filosofo) {
        return filosofo;
    }

    public int garfoDireito (int idFilosofo) {
        return (idFilosofo == ULTIMO_FILOSOFO) ? 0 : idFilosofo + 1;
    }

    public void imprimeEstadosFilosofos() {
        System.out.println("\nEstado dos Filósofos:");
        for (int i = 0; i < NR_FILOSOFOS; i++) {
            String estado = switch (filosofos[i]) {
                case Estado.PENSANDO -> "Pensando";
                case Estado.FOME -> "Com Fome";
                case Estado.COMENDO -> "Comendo";
                default -> "Desconhecido";
            };
            System.out.printf("Filósofo %d: %s%n", i, estado);
        }
        System.out.println();
    }

    public void imprimeHashis() {
        System.out.println("Estado dos Hashis:");
        for (int i = 0; i < NR_FILOSOFOS; i++) {
            String status = hashis[i] ? "Livre" : "Ocupado";
            System.out.printf("Hashi %d: %s%n", i, status);
        }
        System.out.println();
    }

    public void imprimeTentativas() {
        System.out.println("Tentativas de Comer:");
        for (int i = 0; i < NR_FILOSOFOS; i++) {
            System.out.printf("Filósofo %d tentou %d vezes%n", i, tentativas[i]);
        }
        System.out.println();
    }
}
