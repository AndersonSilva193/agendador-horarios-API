# Agendador Barbearia – API

API REST para agendamento de horários em barbearias, com autenticação JWT e dois perfis de usuário (`BARBEIRO` e `CLIENTE`).

## Tecnologias
- Java (definido no `pom.xml`)
- Spring Boot 4.1 (Web MVC, Data JPA, Security, Validation)
- JWT (`java-jwt`)
- H2 (banco em arquivo, para desenvolvimento)
- Lombok
- Maven (wrapper incluído)

## Estrutura
```
src/main/java/com/coffee/agendador_barbearia
├── controllers/      # endpoints REST
├── services/         # regras de negócio
├── exception/        # exceções de domínio
└── infrastructure/
    ├── entity/       # entidades JPA e DTOs
    ├── repository/   # Spring Data JPA
    ├── security/     # filtro JWT, TokenService, configuração do Spring Security
    └── handlers/     # tratamento global de erros
```

## Como executar
```bash
./mvnw spring-boot:run
```
A API sobe em `http://localhost:8080`.

### Configuração (`application.properties`)
| Variável | Padrão | Descrição |
|---|---|---|
| `JWT_SECRET` | `chave-de-teste-nao-usar-em-producao` | Segredo usado para assinar os tokens |
| `ADMIN_LOGIN` | `barbeiro` | Login do primeiro barbeiro |
| `ADMIN_PASSWORD` | `barbeiro123` | Senha do primeiro barbeiro |

> Os valores padrão são **apenas para desenvolvimento**. Em produção, defina as variáveis de ambiente.

Na primeira execução, se não existir nenhum barbeiro, um é criado com `ADMIN_LOGIN` / `ADMIN_PASSWORD`.

O console do H2 fica em `/h2-console` (`jdbc:h2:file:./data/agendamentosdb`, usuário `sa`, senha vazia).

## Autenticação
1. Faça login em `POST /auth/login` e receba o token.
2. Envie o token nas rotas protegidas: `Authorization: Bearer <token>`.

O token expira em 2 horas.

## Endpoints

### Autenticação
| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| POST | `/auth/login` | Público | Retorna o token JWT |
| POST | `/auth/register` | Público | Cadastra um usuário `CLIENTE` |
| POST | `/auth/register/barbeiro` | Barbeiro | Cadastra um novo barbeiro |

Corpo de `/auth/login`, `/auth/register` e `/auth/register/barbeiro`:
```json
{ "login": "fulano", "password": "senha123" }
```
A senha deve ter no mínimo 6 caracteres (cadastro). O login retorna `{ "token": "..." }`.

### Agendamentos
| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| POST | `/agendamentos` | Público | Cria um agendamento |
| GET | `/agendamentos/barbeiro_horarios?data=2026-10-01` | Barbeiro | Lista os agendamentos do dia |
| PUT | `/agendamentos?cliente=...&dataHoraAgendamento=...` | Barbeiro | Altera um agendamento |
| DELETE | `/agendamentos?cliente=...&dataHoraAgendamento=...` | Barbeiro | Remove um agendamento |
| GET | `/agendamentos/teste` | Barbeiro | Verificação de conexão |

`dataHoraAgendamento` usa o formato ISO, por exemplo `2026-10-01T14:00:00`.

Exemplo de corpo de `POST /agendamentos`:
```json
{
  "servico": "Corte",
  "profissional": "João",
  "cliente": "Maria",
  "telefoneCliente": "41999999999",
  "dataHoraAgendamento": "2026-10-01T14:00:00"
}
```

## Testes
```bash
./mvnw test
```

## Pontos de melhoria conhecidos
- A verificação de conflito de horário considera o serviço, não o profissional.
- A entidade `Agendamento` é exposta diretamente, sem DTOs.
- Token inválido hoje gera erro 500 em vez de 401.
- O console do H2 deve ser desativado fora do desenvolvimento.
