# SD-ControleConcorrenciaJava  
**Disciplina:** Sistemas Distribuídos / Programação Concorrente  
**Atividade:** Controle de Concorrência em Java  
**Aluno:** EliphazGFP  
**Data de entrega:** 19/11/2025  

---

## Objetivo da Atividade
Demonstrar na prática os problemas de **race condition** em acesso concorrente a recursos compartilhados e como os principais mecanismos de controle de concorrência do Java resolvem esses problemas.

Cenário utilizado:  
Uma família com **1 pai** e **4 filhos** acessando simultaneamente a mesma conta bancária:
- Pai deposita R$ 200, 5 vezes → +R$ 1.000  
- Cada filho saca R$ 200, 5 vezes → –R$ 1.000 no total  
**Saldo inicial:** R$ 1.000 → **Saldo esperado ao final:** R$ 1.000

Foram implementadas e comparadas 4 versões:

| Pasta         | Mecanismo usado          | Comportamento esperado                     |
|---------------|---------------------------|--------------------------------------------|
| `Concorrente` | Nenhum (sem sincronização)| Race condition → saldo imprevisível        |
| `Synk`        | `synchronized`            | Sempre correto (saldo = 1000)              |
| `Lock`        | `ReentrantLock`           | Sempre correto (saldo = 1000)              |
| `RwLock`      | `ReadWriteLock`           | Sempre correto (saldo = 1000)              |

---

## Resultados das Execuções (3 rodadas de cada versão)

| Estratégia               | Execução 1 | Execução 2 | Execução 3 | Consistente? | Observação                              |
|--------------------------|------------|------------|------------|--------------|-----------------------------------------|
| Concorrente (sem sync)   | 800        | 900        | 700        | Não          | Race condition clara                    |
| Synk (synchronized)      | 1000       | 1000       | 1000       | Sim          | Totalmente consistente                  |
| Lock (ReentrantLock)     | 1000       | 1000       | 1000       | Sim          | Correto (unlock sempre no finally)      |
| RwLock (ReadWriteLock)   | 1000       | 1000       | 1000       | Sim          | Correto e mais eficiente em leituras    |

*Log completo com todas as saídas está no arquivo `log-execucoes.txt`*

---

## Principais Correções Realizadas
Durante a atividade foram encontrados e corrigidos diversos erros clássicos:
- Falta de tratamento de `InterruptedException`
- Imports ausentes (`java.util.concurrent.locks.*`)
- `unlock()` fora do bloco `finally` (risco de deadlock)
- Uso incorreto de `readLock()` / `writeLock()`
- Classes duplicadas em arquivos diferentes
- Métodos soltos fora de classes
- Chaves `{ }` faltando ou sobrando
- Construtores com nome errado
- Remoção de arquivos duplicados (`Conta.java`, `Banco.java`, `Cliente.java`)

**Resultado final:** todos os códigos compilam com `javac */*.java` sem nenhum erro ou warning.

---

## Conclusões
1. **Sem sincronização** → ocorre **race condition** → o saldo final é imprevisível e quase sempre incorreto.
2. O uso de **`synchronized`** é a solução mais simples e suficiente para este caso.
3. **`ReentrantLock`** oferece maior flexibilidade (fairness, tryLock, etc.), mas exige disciplina rigorosa com `try/finally`.
4. **`ReadWriteLock`** é ideal quando há muitas leituras e poucas escritas (permite múltiplas threads lendo simultaneamente). Neste exemplo específico o ganho é pequeno, mas o mecanismo funcionou perfeitamente.

**Nenhum deadlock ocorreu** porque o recurso crítico é sempre adquirido na mesma ordem e por tempo curto.

---

## Estrutura Final do Repositório
