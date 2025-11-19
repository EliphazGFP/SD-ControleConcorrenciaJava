# SD-ControleConcorrenciaJava

Atividade prática – Controle de Concorrência em Java  
**Aluno:** EliphazGFP  
**Data:** 19/11/2025

## Objetivo
Demonstrar o comportamento de acesso concorrente a uma conta bancária com e sem mecanismos de sincronização.

## Resultados das 3 execuções de cada versão

| Estratégia               | Execução 1 | Execução 2 | Execução 3 | Saldo final esperado | Resultado observado        |
|--------------------------|------------|------------|------------|----------------------|----------------------------|
| Concorrente (sem sync)   | variou     | variou     | variou     | 1000                 | **Race condition** (700–900) |
| Synk (synchronized)      | 1000       | 1000       | 1000       | 1000                 | Sempre correto             |
| Lock (ReentrantLock)     | 1000       | 1000       | 1000       | 1000                 | Sempre correto             |
| RwLock (ReadWriteLock)   | 1000       | 1000       | 1000       | 1000                 | Sempre correto             |

**Saldo inicial:** R$ 1000  
**Pai:** deposita 200 × 5 = +1000  
**4 filhos:** sacam 200 × 5 = –1000 no total  
**Saldo correto:** sempre R$ 1000 nas versões sincronizadas

## Conclusões
- Sem sincronização → race condition → resultado imprevisível  
- `synchronized`, `ReentrantLock` e `ReadWriteLock` → 100% consistentes  
- Todos os códigos foram corrigidos e compilam sem erros  
- Log completo das 3 execuções de cada versão está no arquivo `log-execucoes.txt`

**Atividade concluída com sucesso!**
