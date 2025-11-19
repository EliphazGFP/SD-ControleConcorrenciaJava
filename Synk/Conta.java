package Synk;

public class Conta {
    private double saldo;

    public Conta(double saldoInicial) {
        this.saldo = saldoInicial;
        System.out.println("Conta criada. Saldo inicial: R$ " + saldo);
    }

    public synchronized void depositar(double valor) {
        saldo += valor;
        System.out.println(Thread.currentThread().getName() + " depositou " + valor + ". Saldo: " + saldo);
    }

    public synchronized void sacar(double valor) {
        if (saldo >= valor) {
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            saldo -= valor;
            System.out.println(Thread.currentThread().getName() + " sacou " + valor + ". Saldo: " + saldo);
        }
    }

    public synchronized double getSaldo() {
        return saldo;
    }
}
