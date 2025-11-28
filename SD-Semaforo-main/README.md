# SD-ControleConcorrenciaJava

Atividade prática: Controle de Concorrência com Semáforo vs Lock  
Disciplina: Sistemas Distribuídos - IFPR

## Objetivo
Comparar duas formas de controlar acesso concorrente a um recurso compartilhado (ponte estreita com limite de 3 carros):

- `Carro.java` → Usa `java.util.concurrent.Semaphore`
- `CarroLock.java` → Usa `ReentrantLock` + `Condition` (versão correta com limite)

## Análise e Comparação

| Critério                    | Semaphore                              | ReentrantLock + Condition                     |
|-----------------------------|----------------------------------------|------------------------------------------------|
| Facilidade de uso           | Muito simples (ideal para limite fixo) | Mais verboso, exige mais cuidado              |
| Limite de acesso            | Nativo (construtor define o limite)    | Precisa de contador manual + Condition        |
| Ordem de atendimento       | Pode ser FIFO (`new Semaphore(n, true)`) | Com `fair = true` também garante ordem       |
| Try-lock / timeout          | Tem `tryAcquire()`, `acquireUninterruptibly()` | Tem `tryLock()`, muito mais flexível         |
| Performance                 | Excelente                              | Um pouco mais lento (mais funcionalidades)   |
| Quando usar                 | Controle de pool (DB, threads, conexões) | Quando precisa de lógica complexa de espera   |

**Conclusão:**
- Para problemas simples como "máximo N threads acessando um recurso", **Semaphore é a escolha ideal**: código limpo, seguro e performático.
- Para cenários avançados (tempo limite, cancelamento, múltiplas condições), **Lock + Condition** é mais poderoso.

**Resultado observado na execução:**
- Ambos os programas garantem que nunca passem mais de 3 carros ao mesmo tempo.
- Com `fair = true`, a ordem de chegada é respeitada.
- O Semaphore produz código mais legível e menos propenso a erros.

## Como executar

```bash
javac Carro.java
javac CarroLock.java

# Executar com log (não sobrescreve)
java Carro > Carro_$(date +%Y%m%d_%H%M%S).log &
java CarroLock > CarroLock_$(date +%Y%m%d_%H%M%S).log &
