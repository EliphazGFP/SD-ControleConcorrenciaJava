package Synk;

public class Familia {
    public static void main(String[] args) throws InterruptedException {
        Conta conta = new Conta(1000.0);

        Thread pai = new Thread(new Pai(conta), "Pai");
        Thread f1 = new Thread(new Filho(conta), "Filho-1");
        Thread f2 = new Thread(new Filho(conta), "Filho-2");
        Thread f3 = new Thread(new Filho(conta), "Filho-3");
        Thread f4 = new Thread(new Filho(conta), "Filho-4");

        pai.start(); f1.start(); f2.start(); f3.start(); f4.start();
        pai.join(); f1.join(); f2.join(); f3.join(); f4.join();

        System.out.println("Saldo final (synchronized): " + conta.getSaldo());
    }
}

class Conta {
    private double saldo;

    public Conta(double saldoInicial) {
        this.saldo = saldoInicial;
        System.out.println("Conta criada. Saldo inicial: R$ " + saldo);
    }

    public synchronized void depositar(double valor) {
        saldo += valor;
        System.out.println(Thread.currentThread().getName() + " depositou " + valor + " → saldo: " + saldo);
    }

    public synchronized void sacar(double valor) {
        if (saldo >= valor) {
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            saldo -= valor;
            System.out.println(Thread.currentThread().getName() + " sacou " + valor + " → saldo: " + saldo);
        }
    }

    public synchronized double getSaldo() {
        return saldo;
    }
}

class Pai implements Runnable {
    private Conta conta;
    public Pai(Conta c) { this.conta = c; }
    public void run() {
        for (int i = 0; i < 5; i++) {
            conta.depositar(200);
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}

class Filho implements Runnable {
    private Conta conta;
    public Filho(Conta c) { this.conta = c; }
    public void run() {
        for (int i = 0; i < 5; i++) {
            conta.sacar(200);
            try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}