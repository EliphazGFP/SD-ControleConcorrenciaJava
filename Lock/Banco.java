package Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {
    private double saldo;
    private Lock lock = new ReentrantLock();

    public Banco(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void depositar(double valor) {
        lock.lock();
        try {
            saldo += valor;
            System.out.println(Thread.currentThread().getName() + " depositou " + valor + ". Saldo: " + saldo);
        } finally {
            lock.unlock();
        }
    }

    public void sacar(double valor) {
        lock.lock();
        try {
            if (saldo >= valor) {
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                saldo -= valor;
                System.out.println(Thread.currentThread().getName() + " sacou " + valor + ". Saldo: " + saldo);
            }
        } finally {
            lock.unlock();
        }
    }

    public double getSaldo() {
        lock.lock();
        try {
            return saldo;
        } finally {
            lock.unlock();
        }
    }
}
