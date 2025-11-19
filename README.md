# Controle de Concorrencia
Devido a inconsistências que podem ocorrer com código paralelo, é preciso fazer o controle de concorrência entre processos e threads

## Mecanismos de Controle de Concorrência 
* Limitam o acesso concorrente a dados e recursos compartilhados
* Garantem o isolamento entre processos e threads concorrentes
* Buscam evitar inconsistências nos dados causadas pelo acesso concorrente

## Mecanismos de Controle de Concorrência disponíveis em Java:
* **Monitor** : protege trechos de código/métodos que manipulam dados/recursos compartilhados, impedindo o acesso concorrente;
* **Lock (ou Mutex)**: cria uma fila de acesso a um dado/recurso compartilhado, impedindo o acesso concorrente;
* **Semáforo**: limita o número de usuários que acessam simultaneamente um recurso, criando filas de acesso se este número for excedido.

### Monitores
Sincronização de Método:
* O objeto usado na invocação é bloqueado para uso da thread que invocou o método*
* Se o método for static, a classe é bloqueada
* Métodos não sincronizados e atributos ainda podem ser acessados
  
```java
public synchronized void metodo (int param) {
// código protegido
}
```

### Lock
Interface Lock
* Mecanismo de exclusão mútua 
* Permite somente um acesso por vez
* Caso o dado/recurso esteja em uso, a thread que tentar bloqueá-lo entra numa fila

#### Principais Métodos:
* lock(): primitiva de bloqueio; deve ser chamada antes do acesso ao dado/recurso
* unlock() : primitiva de desbloqueio; usada para liberar o acesso o dado/recurso

#### Classe ReentrantLock
* Implementa mecanismo de bloqueio exclusivo
* Por default a retirada de threads da fila não é ordenada, ou seja, não há garantias de quem irá adquirir o lock quando este for liberado
* O construtor ReentrantLock(true) cria um lock com ordenação FIFO da fila, o que torna o acesso significativamente mais lento

```java
import java.util.concurrent.lock.*;
public class Conta {
  private double saldo = 0;
  private Lock lock = new ReentrantLock();
  public double getSaldo() {
    lock.lock();
    try {
      return saldo;
    } finally {
      lock.unlock();
    }
  }
  // ... idem para os demais métodos
}
```

#### Interface ReadWriteLock
Possui dois Locks:
* readLock(): para acesso compartilhado de threads com direito de leitura (acesso)
* writeLock(): para acesso exclusivo de uma thread com direito de escrita (modificação)
* Implementada por ReentrantReadWriteLock 
* Por default não garante a ordem de liberação nem preferência entre leitores e escritores
* Ordenação FIFO é garantida passando o valor ‘true’ para o construtor da classe

```java
import java.util.concurrent.locks.*;
public class Conta {
  private double saldo = 0;
  private ReadWriteLock lock = new ReentrantReadWriteLock();
  
  public double getSaldo() {
    lock.readLock().lock();
    try { 
    return this.saldo; 
    }finally { 
    lock.readLock.unlock(); 
    }
  }
  
  public double setSaldo(double saldo){
      lock.writeLock().lock();
      try { 
        this.saldo = saldo; 
      }finally { 
        lock.writeLock.unlock(); 
      }
  }
  // ... idem para debitarValor()
}
```

### Semáforos
Permite controlar o número de acessos simultâneos a um dado ou recurso

#### Métodos da classe Semaphore
* Semaphore(int acessos [, boolean ordem]): construtor; parâmetros definem o número de acessos simultâneos possíveis e se a ordem de
liberação de threads em espera será FIFO
* acquire(): solicita acesso a um dado ou recurso, entrando em espera se todos os direitos de acesso estiverem sendo usados
* release(): libera um direito de acesso

### Deadlock
![image](https://github.com/user-attachments/assets/cffa1e98-ae6e-419e-b5e4-fcf51e80f07d)

* Caso os cinco filósofos peguem o garfo da esquerda, nenhum deles conseguirá comer
* Esta situação é chamada de deadlock 
* Deadlock ocorre quando, em um grupo de processos/threads em espera, uma aguarda o término da outra para que possa prosseguir
* Em Java, as threads ficarão em espera indefinidamente
* Algumas linguagens/sistemas detectam o deadlock e reportam exceções

## Atividade Prática
1. Criar um respositório com o nome: **SD-ControleConcorrenciaJava**;
2. Crie um codespace, acesse as instruções de como criar um codespace [aqui](https://docs.github.com/pt/codespaces/developing-in-a-codespace/creating-a-codespace-for-a-repository);
3. Abra um terminal do codespace criado,  clone o repositório github, acesse as instruçõesde como clonar o repostório [aqui](https://docs.github.com/pt/repositories/creating-and-managing-repositories/cloning-a-repository);
4. Mover os arquivos que foram baixados dentro da pasta SD-SincronizacaoThreadsJava para a pasta raiz do novo repositório;
5. Compilar os programas das quatro pastas: Concorrente, Lock, RwLock e Synk;
6. Executar 3 vezes cada programa Familia.java de nas pastas, de modo que ele gere uma log da execução;
7. Realize o Commit de todos os arquivos para o repostório, juntamente com os prints de todas as execuções;
8. Realizar as analises das execuções e incluir o texto no README.md.

OBS: Os programas apresentam erros de desenvolvimento, que geraram erro de compilação. Leia as mensagem e corrigas os problemas. Realize o commit do programa corrigido em seu repositório.
 
