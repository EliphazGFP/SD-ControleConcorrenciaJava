package Synk;

public class Familia {
    public static void main(String[] args) {
        Conta conta = new Conta(1000.0);

        Thread pai = new Thread(new Pai(conta), "Pai");
        Thread filho1 = new Thread(new Filho(conta), "Filho1");
        Thread filho2 = new Thread(new Filho(conta), "Filho2");
        Thread filho3 = new Thread(new Filho(conta), "Filho3");
        Thread filho4 = new Thread(new Filho(conta), "Filho4");

        pai.start();
        filho1.start();
        filho2.start();
        filho3.start();
        filho4.start();

        try {
            pai.join();
            filho1.join();
            filho2.join();
            filho3.join();
            filho4.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Saldo final: " + conta.getSaldo());
    }
}

class Conta {
    private double saldo;

    public Conta(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public synchronized void depositar(double valor) {
        saldo += valor;
        System.out.println(Thread.currentThread().getName() + " depositou " + valor + ". Saldo: " + saldo);
    }

    public synchronized void sacar(double valor) {
        if (saldo >= valor) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            saldo -= valor;
            System.out.println(Thread.currentThread().getName() + " sacou " + valor + ". Saldo: " + saldo);
        } else {
            System.out.println(Thread.currentThread().getName() + " não pôde sacar " + valor + ". Saldo insuficiente: " + saldo);
        }
    }

    public synchronized double getSaldo() {
        return saldo;
    }
}

class Pai implements Runnable {
    private Conta conta;

    public Pai(Conta conta) {
        this.conta = conta;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            conta.depositar(200.0);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Filho implements Runnable {
    private Conta conta;

    public Filho(Conta conta) {
        this.conta = conta;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            conta.sacar(200.0);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
