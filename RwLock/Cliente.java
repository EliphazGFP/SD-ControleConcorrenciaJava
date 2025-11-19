package RwLock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cliente implements Runnable {
    private Banco banco;

    public Cliente(Banco banco) {
        this.banco = banco;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            banco.sacar(200.0);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Banco {
    private double saldo;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public Banco(double saldoInicial) {
        this.saldo = saldoInicial;
        System.out.println("Conta criada. Saldo inicial: R$ " + saldo);
    }

    public void depositar(double valor) {
        lock.writeLock().lock();
        try {
            saldo += valor;
            System.out.println(Thread.currentThread().getName() + " depositou " + valor + ". Saldo: " + saldo);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void sacar(double valor) {
        lock.writeLock().lock();
        try {
            if (saldo >= valor) {
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                saldo -= valor;
                System.out.println(Thread.currentThread().getName() + " sacou " + valor + ". Saldo: " + saldo);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public double getSaldo() {
        lock.readLock().lock();
        try {
            return saldo;
        } finally {
            lock.readLock().unlock();
        }
    }
}
