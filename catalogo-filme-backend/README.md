# Catálogo de Filmes — Backend

API REST para um catálogo de filmes, com avaliações de usuários, plataformas de
streaming e upload de imagens. Código, nomes e mensagens estão em **português**.

- **Stack:** Spring Boot 4.1.0 · Java 21 · Maven · MySQL 8 · Spring Security + JWT ·
  springdoc/OpenAPI (Swagger) · Cloudinary (imagens).
- **Porta:** `8081` · **Swagger UI:** http://localhost:8081/swagger-ui.html

---

## Sumário

- [Como rodar](#como-rodar)
- [Arquitetura e padrões](#arquitetura-e-padrões)
- [Autenticação e autorização](#autenticação-e-autorização)
- [Upload e exibição de imagens](#upload-e-exibição-de-imagens)
- [Endpoints](#endpoints)
- [Modelos de payload](#modelos-de-payload)
- [Convenções para adicionar um domínio](#convenções-para-adicionar-um-domínio)

---

## Como rodar

Execute a partir de `catalogo-filme-backend/` (use o wrapper `./mvnw` ou `mvnw.cmd`).

1. **Banco (obrigatório primeiro):**
   ```bash
   docker compose up -d
   ```
   Sobe um MySQL 8 na porta **3309** (db `catalogo-filme`). O script
   `db/init/db-filme.sql` cria as tabelas, os cargos (`ROLE_ADMIN`, `ROLE_USER`) e um
   **admin padrão**: `admin@catalogo.com` / `admin123`.
   > O script só roda em volume novo. Para recriar do zero: `docker compose down -v && docker compose up -d`.

2. **Variáveis de ambiente (`.env` na raiz do módulo):**
   ```env
   CLOUDINARY_URL=cloudinary://<api_key>:<api_secret>@<cloud_name>
   ```
   O `.env` é carregado via `spring.config.import` e injetado no bean `Cloudinary`.
   O arquivo está no `.gitignore`. A app sobe sem ele, mas o upload/exibição de
   imagens falha. **Nunca versione a `api_secret`; se vazar, rotacione no painel do Cloudinary.**

3. **Aplicação:**
   ```bash
   ./mvnw spring-boot:run     # http://localhost:8081
   ```

Outros comandos:

| Ação | Comando |
| --- | --- |
| Build | `./mvnw clean package` |
| Todos os testes | `./mvnw test` |
| Um teste | `./mvnw test -Dtest=ClassName#methodName` |

> `spring.jpa.hibernate.ddl-auto=update`: o Hibernate cria/atualiza o schema no boot
> (apenas **adiciona** colunas — nunca remove). Não há arquivos de migração.

---

## Arquitetura e padrões

Spring MVC em camadas, sob o pacote `com.grupo.catalogoFilme`:

```
controllers/  →  services/  →  repositories/  →  entities/
                   ↑ mapper/ (entity ↔ DTO)
```

- **DTOs** (`dto/<dominio>/`): cada domínio tem `*CreateDTO`, `*UpdateDTO` e
  `*ResponseDTO`. **Entidades nunca são expostas diretamente.**
- **Mappers** (`mapper/`): classes `@Component` escritas à mão (sem MapStruct) que
  convertem entity ↔ DTO. Podem injetar repositórios para resolver relações por id
  (ex.: `FilmeMapper` carrega `Plataforma` e lança `RegistroNaoEncontradoException`).
- **Atualização parcial:** campos de `*UpdateDTO` são opcionais — o service aplica
  somente os não-nulos / não-vazios (ver `FilmeService.atualizarFilme`).
- **Soft delete:** registros não são removidos fisicamente. Cada entidade tem um
  `StatusRegistro` (`ATIVO`/`INATIVO`) persistido por `StatusRegistroConverter`. O
  delete chama uma query `@Modifying` que marca `INATIVO` (`logicalDeleteById`), e as
  leituras "ativas" filtram via `findAllByStatusNot(INATIVO)` + guarda `buscarAtivo`
  nos services. Endpoints `/todos` retornam também os inativos.
- **Enums persistidos:** quando o enum guarda um código próprio, usa-se
  `AttributeConverter` em `enums/converter/` (ex.: `GeneroEnumConverter`) em vez de
  `@Enumerated`.
- **Tratamento de erros:** exceções de domínio em `exceptions/` são mapeadas para
  status HTTP centralmente em `handler/ExceptionHandlers.java` (`@ControllerAdvice`).
  Ex.: `RegistroNaoEncontradoException` → 404, `DadosInvalidosException` → 400,
  `AcessoNegadoException` → 403, `PlataformaJaExisteException`/`FilmeJaFoiLogadoException` → 409,
  `MaxUploadSizeExceededException` → 400.

---

## Autenticação e autorização

- JWT **stateless** (`config/SecurityConfig`), CSRF desabilitado, CORS aberto.
  `security/JwtAuthFilter` valida o token `Bearer` a cada requisição.
- **Cargos:** vêm de `Usuario` → `Cargo` (`CargoEnum`: `ROLE_USER`, `ROLE_ADMIN`).
- **Públicos:** `POST /auth/login`, `POST /auth/cadastro` e o Swagger.
- **Exigem ADMIN:** todo `POST`/`PUT`/`DELETE` em `/filmes/**` e `/plataformas/**`
  (inclui os uploads `POST /…/imagem`).
- **Demais rotas:** exigem usuário autenticado.

### Fluxo de uso

1. Cadastre-se (`POST /auth/cadastro`) ou use o admin padrão.
2. Faça login (`POST /auth/login`) e copie o `token` retornado.
3. Envie o token em todas as chamadas protegidas:
   ```
   Authorization: Bearer <token>
   ```
   No Swagger, clique em **Authorize** e cole o token.

---

## Upload e exibição de imagens

Entidades com campo `urlImage` (`Filme`, `Plataforma`, `Usuario`) **não** guardam o
binário — guardam a `secure_url` do Cloudinary. `services/ImagemService` encapsula o
bean `Cloudinary`:

- **Upload** (`POST .../imagem`, `multipart/form-data`, campo **`file`**): valida
  (`image/*`, máx. 5MB), envia ao Cloudinary e persiste a URL no `urlImage` do recurso.
- **Exibição** (`GET .../imagem`): faz proxy dos bytes de volta com o `Content-Type` real
  da imagem, de modo que o **Swagger renderiza a imagem inline** (não apenas a URL).

Filme e Plataforma usam `{id}`; Usuário usa `/me` (o próprio usuário autenticado).

---

## Endpoints

> 🔓 público · 🔒 autenticado · 🛡️ ADMIN

### Autenticação — `/auth`
| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| POST | `/auth/login` | 🔓 | Valida e-mail/senha e retorna o token JWT (`LoginResponseDTO`). |
| POST | `/auth/cadastro` | 🔓 | Cria um usuário comum (`ROLE_USER`). |

### Usuários — `/usuarios`
| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/usuarios` | 🔒 | Lista usuários ativos. |
| GET | `/usuarios/todos` | 🔒 | Lista todos (inclui inativos). |
| GET | `/usuarios/{id}` | 🔒 | Busca por id. |
| GET | `/usuarios/me/imagem` | 🔒 | Imagem (bytes) do usuário autenticado. |
| POST | `/usuarios/me/imagem` | 🔒 | Upload da imagem do próprio usuário (`file`). |
| PUT | `/usuarios/{id}/promover-admin` | 🛡️ | Promove o usuário a `ROLE_ADMIN`. |

### Filmes — `/filmes`
| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/filmes` | 🔒 | Lista filmes ativos. |
| GET | `/filmes/todos` | 🔒 | Lista todos (inclui inativos). |
| GET | `/filmes/buscar?nome=` | 🔒 | Busca por nome (parcial, ignora maiúsculas). |
| GET | `/filmes/{id}` | 🔒 | Busca por id. |
| GET | `/filmes/{id}/imagem` | 🔒 | Imagem (bytes) do filme. |
| POST | `/filmes` | 🛡️ | Cadastra filme. |
| POST | `/filmes/{id}/imagem` | 🛡️ | Upload da imagem do filme (`file`). |
| PUT | `/filmes/{id}` | 🛡️ | Atualização parcial. |
| DELETE | `/filmes/{id}` | 🛡️ | Exclusão lógica (vira `INATIVO`). |

### Plataformas — `/plataformas`
| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/plataformas` | 🔒 | Lista plataformas ativas. |
| GET | `/plataformas/todos` | 🔒 | Lista todas (inclui inativas). |
| GET | `/plataformas/{id}` | 🔒 | Busca por id. |
| GET | `/plataformas/{id}/imagem` | 🔒 | Imagem (bytes) da plataforma. |
| POST | `/plataformas` | 🛡️ | Cadastra plataforma. |
| POST | `/plataformas/{id}/imagem` | 🛡️ | Upload da imagem (`file`). |
| PUT | `/plataformas/{id}` | 🛡️ | Atualização parcial. |
| DELETE | `/plataformas/{id}` | 🛡️ | Exclusão lógica (bloqueada se houver filmes vinculados). |

### Avaliações — `/avaliacoes`
| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/avaliacoes` | 🔒 | Lista avaliações ativas. |
| GET | `/avaliacoes/todos` | 🔒 | Lista todas (inclui inativas). |
| GET | `/avaliacoes/minhas` | 🔒 | Avaliações do usuário autenticado (com dados do filme). |
| GET | `/avaliacoes/{id}` | 🔒 | Busca por id. |
| POST | `/avaliacoes` | 🔒 | Cria avaliação (o autenticado vira dono; nota de 0 a 5). |
| PUT | `/avaliacoes/{id}` | 🔒 | Atualiza (apenas o dono ou um admin). |
| DELETE | `/avaliacoes/{id}` | 🔒 | Exclusão lógica (apenas o dono ou um admin). |

### Gêneros — `/generos`
| Método | Rota | Acesso | Descrição |
| --- | --- | --- | --- |
| GET | `/generos` | 🔒 | Lista fixa de gêneros (código + nome). |

---

## Modelos de payload

**Login** — `POST /auth/login`
```json
{ "email": "admin@catalogo.com", "senha": "admin123" }
```

**Cadastro de usuário** — `POST /auth/cadastro` (`urlImage` é opcional)
```json
{ "nome": "Maria", "email": "maria@email.com", "senha": "minhaSenha", "urlImage": null }
```

**Filme** — `POST /filmes` (ADMIN). `generos` é uma lista de **códigos** (ver tabela);
`dataLancamento` no formato `YYYY-MM-DD`; `urlImage` opcional (normalmente preenchida
depois via upload).
```json
{
  "titulo": "Matrix",
  "descricao": "Um hacker descobre a verdade sobre a realidade.",
  "diretor": "Wachowski",
  "dataLancamento": "1999-03-31",
  "generos": [1, 6],
  "plataformaId": 1
}
```

**Plataforma** — `POST /plataformas` (ADMIN)
```json
{ "nome": "Netflix" }
```

**Avaliação** — `POST /avaliacoes` (`nota` de 0 a 5)
```json
{ "comentario": "Excelente!", "nota": 5, "filmeId": 1 }
```

**Códigos de gênero:** `1` Ação · `2` Aventura · `3` Comédia · `4` Drama · `5` Terror ·
`6` Ficção Científica · `7` Romance · `8` Suspense · `9` Animação · `10` Documentário ·
`11` Fantasia · `12` Musical.

### Exemplos com `curl`

```bash
# 1) Login → token
TOKEN=$(curl -s -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@catalogo.com","senha":"admin123"}' | jq -r .token)

# 2) Buscar filme por nome
curl http://localhost:8081/filmes/buscar?nome=mat -H "Authorization: Bearer $TOKEN"

# 3) Upload da imagem de um filme (ADMIN)
curl -X POST http://localhost:8081/filmes/1/imagem \
  -H "Authorization: Bearer $TOKEN" -F "file=@poster.jpg"

# 4) Minhas avaliações
curl http://localhost:8081/avaliacoes/minhas -H "Authorization: Bearer $TOKEN"
```

---

## Convenções para adicionar um domínio

Um CRUD completo percorre: entidade (+ tabela) → repository → mapper →
DTOs Create/Update/Response → service (com soft-delete + guarda de ativo) →
controller (com `@Operation`/`@ApiResponses` do springdoc) → regra em
`SecurityConfig` → mapeamento em `ExceptionHandlers`. Use as fatias de
`Filme` / `Plataforma` como modelo.
