# Caju Authorization System

Este projeto é um autorizador de transações de cartão de crédito, desenvolvido em Java utilizando o Spring Boot. Ele processa payloads JSON de transações e aplica regras específicas para autorizar ou rejeitar cada transação.

## Estrutura do Projeto

```plaintext
caju-authorization-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── caju/
│   │   │           └── authorizer/
│   │   │               ├── AuthorizerApplication.java
│   │   │               ├── model/
│   │   │               │   ├── Account.java
│   │   │               │   ├── MerchantMapping.java
│   │   │               │   └── Transaction.java
│   │   │               ├── service/
│   │   │               │   └── TransactionService.java
│   │   │               ├── repository/
│   │   │               │   ├── AccountRepository.java
│   │   │               │   └── MerchantMappingRepository.java
│   │   │               ├── controller/
│   │   │               │   └── TransactionController.java
│   │   │               └── util/
│   │   │                   └── TransactionUtils.java
│   ├── resources/
│   │   ├── application.properties
│   │   └── data.sql
├── .gitignore
├── README.md
├── pom.xml
└── HELP.md

````
## Requisitos
- **Java**: Versão 11 ou superior
- **Maven**: Versão 3.6.3 ou superior
- **Banco de dados**: H2 (em memória) para desenvolvimento e testes

## Configuração e Execução

1. **Clonar o repositório**:

   ```bash
   git clone <https://github.com/ProgRS/caju-authorization-system>
   cd caju-authorization-system
   ```
2. **Construir o projeto**:   

   ```bash
    mvn clean install
   ````

2. **Executar a aplicação:**:

   ```bash
   mvn spring-boot:run
   ````
## API
## Endpoint para processar transações
- Método: POST 
- URL: /transactions
- Payload : 
```
{
  "accountId": "123",
  "totalAmount": 100.00,
  "mcc": "5411",
  "merchant": "PADARIA DO ZE SAO PAULO BR"
  }
````

**Respostas**
- **200 OK**
```
{ "code": "00" } // Transação aprovada
```

- **400 Bad Request**
```
{ "code": "51" } // Transação rejeitada por saldo insuficiente
```

- **500  Internal Server Error**
```
{ "code": "07" } // Problema que impede o processamento da transação

```

## Banco de Dados

- **H2 Console**
  - URL: http://localhost:8080/h2-console
  - Usuário: sa
  - Senha: (em branco)
## Observações
 
- **Dados iniciais**:   Os dados do banco de dados são carregados do arquivo
    ```bash   
  src/main/resources/data.sql
  ````
-  **Códigos de resposta:**   Os códigos de resposta da API seguem o padrão HTTP e indicam o status da requisição.

## Discussão sobre L4: Transações Simultâneas

Para lidar com transações simultâneas e garantir que apenas uma transação por conta seja processada de cada vez, podemos considerar as seguintes abordagens:

1. Locks no Banco de Dados:
 - Utilize locks pessimistas (Pessimistic Locking) para garantir exclusividade durante a transação.
 - Exemplo em JPA:
  ```
   @Lock(LockModeType.PESSIMISTIC_WRITE)
   @Query("SELECT a FROM Account a WHERE a.id = :id")
   Optional<Account> findByIdForUpdate(@Param("id") String id);

```

2. Fila de Mensagens:
 - Utilize uma fila de mensagens (como RabbitMQ ou Kafka) para enfileirar as transações e processá-las uma a uma.
 - Diagrama Simplificado:
```
User -> API -> Message Queue -> Consumer -> Database
```
3. Caches Distribuídos:
  - Utilize caches distribuídos, como Redis, para implementar um mecanismo de lock distribuído.
  - Exemplo em Java com Redis:
```
public boolean acquireLock(String accountId) {
    Boolean success = redisTemplate.opsForValue().setIfAbsent("lock:" + accountId, "locked", 100, TimeUnit.MILLISECONDS);
    return success != null && success;
}

public void releaseLock(String accountId) {
    redisTemplate.delete("lock:" + accountId);
}

```