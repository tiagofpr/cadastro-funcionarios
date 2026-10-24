# Cadastro de Funcionários

CRUD de funcionários em Java 8 com Hibernate, Servlets e JSP, sem Spring Boot. O projeto foi feito para estudar o que os frameworks costumam esconder: mapeamento objeto-relacional, divisão em camadas, ciclo de uma requisição HTTP e deploy de um `.war`.

## O que o sistema faz

- Lista os funcionários com paginação (5 por página)
- Cadastra, edita e remove funcionários
- Valida os dados no navegador (HTML5) e no servidor (camada Service)
- Mostra uma mensagem de confirmação depois de cada operação
- Grava logs no console e em arquivo, com um arquivo novo por dia

## Tecnologias

| Item | Versão |
|---|---|
| Java | 8 |
| Maven | 3.x, empacotamento `war` |
| Hibernate ORM | 5.6.15.Final, mapeamento em XML (`.hbm.xml`) |
| PostgreSQL | rodando em Docker, driver JDBC 42.7.13 |
| Servlet API e JSP | fornecidas pelo servidor (`provided`) |
| JSTL | 1.2 |
| Servidor de desenvolvimento | Jetty 9.4 pelo Maven Jetty Plugin |
| Servidor de deploy | Tomcat 9 |
| Testes | JUnit 5.10.5 e Mockito 4.11.0 |
| Logs | SLF4J 2.0.13 e Logback 1.3.15 |

As versões foram escolhidas por serem compatíveis com Java 8. Hibernate 6, Mockito 5 e Logback 1.4 em diante exigem Java 11 ou superior.

## Arquitetura

```
Requisição -> Filters -> Servlet (controller) -> Service -> DAO -> Hibernate -> PostgreSQL
                              |
                              +-> JSP (src/main/webapp/WEB-INF/views)
```

Cada camada só chama a seguinte. O Servlet cuida do HTTP, o Service das regras de negócio e o DAO do acesso ao banco. As views ficam dentro de `WEB-INF`, então só são acessíveis por `forward` de um Servlet.

## Estrutura do projeto

```
src/main/java/br/com/tiagoribeiro/
    controller/   Servlets (listar, salvar, editar, deletar)
    dao/          FuncionarioDAO e FuncionarioDAOImpl
    filter/       EncodingFilter e RequestLoggingFilter
    model/        Funcionario
    service/      FuncionarioService
    util/         HibernateUtil e Pagina

src/main/resources/
    br/com/tiagoribeiro/model/Funcionario.hbm.xml
    hibernate.cfg.xml
    logback.xml
    config.properties.example
    config.properties            (local, não vai para o Git)

src/main/webapp/
    WEB-INF/web.xml
    WEB-INF/views/               listar.jsp e formulario.jsp
    css/style.css

src/test/java/br/com/tiagoribeiro/service/
    FuncionarioServiceTest.java
```

## Pré-requisitos

- JDK 8
- Maven 3.x
- PostgreSQL, local ou em Docker
- Tomcat 9 (só se quiser testar o deploy manual)

## Configuração

Clone o repositório:

```
git clone git@github.com:tiagofpr/cadastro-funcionarios.git
cd cadastro-funcionarios
```

Suba um PostgreSQL. Exemplo com Docker:

```
docker run --name postgres-dev \
  -e POSTGRES_USER=usuario \
  -e POSTGRES_PASSWORD=senha \
  -e POSTGRES_DB=javadb \
  -p 5432:5432 -d postgres
```

Crie o schema do projeto dentro do banco:

```sql
CREATE SCHEMA cadastro_funcionarios AUTHORIZATION usuario;
```

Copie o arquivo de exemplo e ajuste os dados de conexão:

```
cp src/main/resources/config.properties.example src/main/resources/config.properties
```

```properties
db.driver=org.postgresql.Driver
db.url=jdbc:postgresql://localhost:5432/javadb
db.username=usuario
db.password=senha
```

A URL aponta para o banco (`javadb`). O schema é escolhido pela propriedade `hibernate.default_schema` do `hibernate.cfg.xml`. A tabela `FUNCIONARIO` é criada na primeira execução, porque `hibernate.hbm2ddl.auto` está como `update`.

O `config.properties` está no `.gitignore` e nunca deve ser commitado.

## Executando em desenvolvimento

```
mvn clean jetty:run
```

Acesse http://localhost:8080/cadastro-funcionarios/funcionarios

O plugin recarrega a aplicação quando detecta mudanças (`scanIntervalSeconds`). Se a IDE estiver recompilando em segundo plano ao mesmo tempo, uma requisição em andamento pode falhar com `IllegalStateException: Committed`. Rodar pela configuração de Run/Debug da IDE evita isso.

## Deploy no Tomcat

```
mvn clean package
cp target/cadastro-funcionarios.war <TOMCAT_HOME>/webapps/
<TOMCAT_HOME>/bin/startup.sh
```

O contexto da aplicação vem do nome do arquivo, então ela fica em http://localhost:8080/cadastro-funcionarios/funcionarios. Para parar o servidor, use `bin/shutdown.sh`.

## Testes

```
mvn test
```

Os testes cobrem o `FuncionarioService` com o DAO substituído por um mock (Mockito), então não precisam de banco. Eles verificam as validações, a busca de id inexistente e o cálculo da paginação.

## Rotas

| Método | Rota | Descrição |
|---|---|---|
| GET | `/funcionarios` | Lista paginada. Aceita `?pagina=N` |
| GET | `/funcionarios/novo` | Formulário de cadastro |
| POST | `/funcionarios/novo` | Salva e redireciona para a lista |
| GET | `/funcionarios/editar?id=N` | Formulário preenchido |
| POST | `/funcionarios/editar` | Atualiza e redireciona para a lista |
| POST | `/funcionarios/deletar` | Remove e redireciona para a lista |

Depois de um POST bem-sucedido a aplicação faz redirect (padrão Post/Redirect/Get), então atualizar a página não reenvia o formulário.

## Versionamento

O repositório segue um Git Flow simplificado:

- `main`: sempre estável
- `develop`: integração das features
- `feature/*`, `refactor/*`, `docs/*`: uma branch por tarefa, criada a partir da `develop`

Os commits seguem o padrão [Conventional Commits](https://www.conventionalcommits.org/) (`feat:`, `fix:`, `refactor:`, `test:`, `docs:`, `chore:`). Os marcos são registrados como tags:

| Tag | Marco |
|---|---|
| v0.0.1 | Estrutura inicial do projeto Maven |
| v0.1.0 | Hibernate conectado ao PostgreSQL |
| v0.2.0 | DAO, Service e testes unitários |
| v0.3.0 | CRUD completo pela web |
| v0.4.0 | Mensagens de feedback, CSS e logging |
| v0.5.0 | Formulário único, validação HTML5, filtros e paginação |

## Decisões de projeto

- Mapeamento em XML em vez de anotações, para deixar explícita a correspondência entre classe e tabela.
- Uma Session do Hibernate por operação do DAO, aberta e fechada com try-with-resources.
- Dependência do DAO recebida por construtor no Service, o que permite trocar por um mock nos testes.
- Um Servlet por ação, cada um mapeado no `web.xml`.

## Limitações conhecidas

- Jetty 9 e Logback 1.3 não recebem mais correções de segurança. Servem para estudo e desenvolvimento local, não para produção.
- O pool de conexões embutido do Hibernate não é recomendado em produção. O próximo passo natural seria trocá-lo por HikariCP.
- Não há autenticação nem controle de acesso.
- Depois de editar ou excluir, o redirect volta para a primeira página da lista.

## Licença

Veja o arquivo [LICENSE](LICENSE).