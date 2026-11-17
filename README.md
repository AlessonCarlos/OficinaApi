# Oficina API

API REST para gerenciar os clientes de uma oficina mecânica, feita em Java com Spring Boot.

Comecei este projeto para praticar o desenvolvimento back-end com Spring de ponta a ponta: modelagem da entidade, regras de negócio, validação, documentação da API e boas práticas de segurança no tratamento de dados pessoais. O projeto está em evolução, e a ideia é ir aumentando o domínio aos poucos (veículos e ordens de serviço estão no roadmap).

## Tecnologias

- Java 17+
- Spring Boot 4
- Spring Web (MVC)
- Spring Data JPA / Hibernate
- Bean Validation (Hibernate Validator)
- H2 Database (em memória)
- springdoc-openapi (Swagger UI)
- Maven

## Regras de negócio

- **CPF** é obrigatório, precisa ser válido (dígitos verificadores conferidos) e não pode se repetir.
- **CPF não pode ser alterado** depois do cadastro.
- **Nome** e **telefone** são obrigatórios.
- **E-mail** é opcional, mas, se for informado, precisa ter um formato válido.
- A **data de cadastro** é preenchida automaticamente pelo sistema.

## Endpoints

| Método | Rota | Descrição | Sucesso |
|---|---|---|---|
| `POST` | `/clientes` | Cadastra um cliente | `201 Created` |
| `GET` | `/clientes` | Lista todos os clientes | `200 OK` |
| `GET` | `/clientes/{id}` | Busca um cliente pelo id | `200 OK` |
| `PUT` | `/clientes/{id}` | Atualiza nome, telefone e e-mail | `200 OK` |
| `DELETE` | `/clientes/{id}` | Remove um cliente | `204 No Content` |

### Exemplo de cadastro

**Requisição**

```http
POST /clientes
Content-Type: application/json

{
  "nome": "João da Silva",
  "cpf": "529.982.247-25",
  "telefone": "(81) 99999-8888",
  "email": "joao@email.com"
}
```

**Resposta** `201 Created`

```json
{
  "id": 1,
  "nome": "João da Silva",
  "cpf": "***.982.247-**",
  "telefone": "81999998888",
  "email": "joao@email.com",
  "dataCadastro": "2026-11-17T11:30:00.123"
}
```

> Os CPFs usados nos exemplos são números válidos gerados para teste.

## Decisões de projeto

Algumas escolhas que fiz e o motivo de cada uma:

**DTOs separados da entidade.** A API nunca recebe nem devolve a entidade `Cliente` diretamente. Existem DTOs de entrada (`ClienteRequest` e `ClienteUpdateRequest`) e de saída (`ClienteResponse`). Assim eu controlo exatamente quais campos entram e saem, e evito que alguém altere campos como `id` ou `dataCadastro` pelo JSON.

**CPF protegido em três camadas.** A regra de que o CPF não pode ser alterado está garantida:
1. no DTO de atualização, que não tem o campo `cpf`;
2. na entidade, que não tem setters, só um método `atualizar(nome, telefone, email)`;
3. no banco, com a coluna marcada como `updatable = false` e `unique = true`.

**Dados normalizados antes de salvar.** O CPF e o telefone são gravados só com números, e o e-mail em minúsculas. Desse jeito, `529.982.247-25` e `52998224725` são tratados como o mesmo CPF.

**CPF mascarado nas respostas.** Como o CPF é um dado pessoal (LGPD), a API devolve ele mascarado (`***.982.247-**`). No banco ele fica completo, para a regra de duplicidade continuar funcionando. As mensagens de erro também não repetem o número.

**Ferramentas de desenvolvimento só no perfil `dev`.** O console do H2 e o Swagger ficam desligados por padrão e só são ativados no perfil `dev` (`application-dev.properties`). Os detalhes internos de erro (stack trace) nunca são devolvidos para quem chama a API.

## Como rodar

**Pré-requisitos:** JDK 17 ou superior. Não precisa instalar o Maven, o projeto já vem com o Maven Wrapper.

```bash
git clone https://github.com/<seu-usuario>/oficina-api.git
cd oficina-api
./mvnw spring-boot:run
```

No Windows (PowerShell), o comando também é `./mvnw spring-boot:run`. No CMD, use `mvnw.cmd spring-boot:run`.

A API sobe em `http://localhost:8081`.

### Documentação (Swagger)

Com a aplicação rodando no perfil `dev` (o padrão), acesse:

```
http://localhost:8081/swagger-ui.html
```

### Banco de dados (H2 Console)

```
http://localhost:8081/h2-console
```

- **JDBC URL:** `jdbc:h2:mem:oficinabd`
- **Usuário:** `sa`
- **Senha:** (em branco)

O banco é em memória, então os dados são apagados sempre que a aplicação é encerrada.

## Estrutura do projeto

```
src/main/java/com/projeto/oficina_api/
├── cliente/
│   ├── Cliente.java               # entidade JPA
│   ├── ClienteRepository.java     # acesso ao banco (Spring Data)
│   ├── ClienteService.java        # regras de negócio
│   ├── ClienteController.java     # endpoints REST
│   └── dto/                       # objetos de entrada e saída
├── config/
│   └── OpenApiConfig.java         # informações da documentação
└── exception/                     # exceções de negócio
```

Organizei os pacotes por funcionalidade (`cliente/`), e não por camada, para que cada parte do domínio fique agrupada quando entrarem veículos e ordens de serviço.

## Roadmap

- [x] CRUD de clientes
- [x] Validações com Bean Validation
- [x] Documentação com Swagger
- [ ] Tratamento global de erros (404, 400 e 409 com respostas padronizadas)
- [ ] Testes unitários do Service com JUnit 5 e Mockito
- [ ] Paginação na listagem
- [ ] Entidade `Veiculo` (um cliente pode ter vários veículos)
- [ ] Ordem de serviço com controle de status
- [ ] Migração para PostgreSQL com Flyway
- [ ] Docker / Docker Compose

## Autor

Feito por **Alesson**.

- LinkedIn: <link do seu LinkedIn>
- GitHub: <link do seu GitHub>
