# Estacionamento – Java + MySQL + NetBeans (Tutoriais Parte I e II)

Implementação fiel dos dois tutoriais do prof. José Gonçalo dos Santos (pusivus.com.br), adaptada ao **Apache NetBeans 25**, **JDK 17+** e **MySQL 8/9**.

## Adaptações necessárias (únicas diferenças em relação ao tutorial de 2011)

| Tutorial (2011)                         | Hoje                                                                 |
|-----------------------------------------|----------------------------------------------------------------------|
| Conector `mysql-connector-java-5.0.4`   | **MySQL Connector/J 8.x ou 9.x** (`mysql-connector-j-x.y.z.jar`)      |
| Driver `com.mysql.jdbc.Driver`          | `com.mysql.cj.jdbc.Driver`                                           |
| Senha do root em branco                 | MySQL 8+ exige senha: ajuste `SENHA` em `Conexao.java`               |
| `import java.sql*;` (erro de digitação) | `import java.sql.*;`                                                 |
| URL só com user/password                | + `useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`     |

Todo o restante (classes, métodos, nomes de componentes, eventos, layout) segue o tutorial.

## Pré-requisitos
1. **JDK 17 ou superior** (NetBeans 25 roda em JDK 17/21/25).
2. **Apache NetBeans 25**: https://netbeans.apache.org/front/main/download/
3. **MySQL Server 8.x/9.x** instalado e rodando (com a senha do root que você definiu).
4. **MySQL Connector/J**: https://dev.mysql.com/downloads/connector/j/ → "Platform Independent" → baixe o ZIP, extraia e guarde o `mysql-connector-j-X.Y.Z.jar` em uma pasta fácil (ex.: `C:\psv\`).

---

## PARTE I

### 1. Criar o banco (tutorial seção 2)
Abra o **MySQL Command Line Client** (ou MySQL Workbench), digite a senha e execute:

```sql
create database estacionamento;
use estacionamento;
create table carro(
  placa char(7) not null primary key,
  cor varchar(20),
  descricao varchar(100)
);
```
(O mesmo conteúdo está em `sql/estacionamento.sql`.)

### 2. Criar o projeto (seção 3)
1. NetBeans → **File > New Project** (Arquivo > Novo projeto).
2. Categoria **Java with Ant** → **Java Application** → Next.
   *(Use Ant, não Maven: é o que o tutorial faz e gera `dist/lib` no final.)*
3. **Project Name:** `Estacionamento` · **Project Location:** `C:\psv`.
4. **Desmarque** "Create Main Class" (o tutorial exclui essa classe depois). Mantenha "Set as Main Project". Finish.

### 3. Criar o pacote (seção 4)
Botão direito em **Source Packages > New > Java Package** → nome: `psv` → Finish.

### 4. Adicionar o conector (seção 5)
1. Botão direito em **Libraries > Add JAR/Folder…**
2. Selecione o `mysql-connector-j-X.Y.Z.jar`, marque **Relative Path** e **Open**.

### 5. Criar as classes (seção 4 em diante)
Em **Source Packages > psv**, crie (botão direito > New > Java Class) e cole o conteúdo dos arquivos desta pasta `src/psv/`:

1. `Conexao.java`
2. `CarroBean.java` (dica do tutorial: `Alt+Insert > Getter and Setter` gera os métodos)
3. `CarroDAO.java`
4. `Teste.java`

Alternativa: copie os 4 arquivos `.java` direto para `C:\psv\Estacionamento\src\psv\`. O NetBeans os reconhece sozinho.

**Edite a senha** em `Conexao.java` (`private static final String SENHA = "..."`).

### 6. Testar (seção "Testes")
Clique com o botão direito em `Teste.java` > **Run File** (`Shift+F6`).
- Saída esperada: `Conexão aberta.` ... `Conexão fechada.`
- Para testar inserir/alterar/excluir, descomente um bloco por vez no `Teste.java`, execute e comente de novo. Confira no MySQL com `select * from carro;`.
- Não repita placa (chave primária).

---

## PARTE II

### 7. Criar o formulário (passo 1)
Botão direito no pacote `psv` > **New > JFrame Form…** → Class Name `FrmCarro` → Finish.

**Opção rápida (recomendada):** copie o `FrmCarro.java` desta pasta para `src/psv/`, sobrescrevendo o gerado. Ele contém exatamente o resultado do passo 2 (a–g) do tutorial: painéis, rótulos, `txtPlaca`, `txtCor`, `txtDescricao`, `lblMensagem`, botões `btnIncluir`, `btnAlterar`, `btnExcluir`, `btnPesquisar`, `Limpar`, `btnFechar` e a tabela `tblConsulta` (3 colunas, não editável, não redimensionável), mais os eventos `mouseClicked` (Fechar, Limpar, Incluir, Pesquisar, tabela, Alterar, Excluir).

> Observação: como o arquivo é código puro (sem `.form`), o NetBeans não abre a aba **Design** para ele. Se quiser desenhar visualmente, siga o passo 2 do tutorial no editor gráfico e depois cole só os métodos de evento. Para só executar, não precisa.

### 8. Executar o formulário
Botão direito em `FrmCarro.java` > **Run File** (`Shift+F6`). Teste:
- Preencha placa/cor/descrição → **Incluir** (mensagem aparece em `lblMensagem`).
- **Pesquisar** lista os registros na tabela; clique numa linha para levar os dados aos campos.
- **Alterar** (a placa não muda), **Excluir** (pede confirmação Sim/Não), **Limpar**, **Fechar**.

### 9. Criar o pacote de distribuição .jar (seção final)
1. Tutorial passos 1–2: se você criou `Main.java`, exclua-o (Delete > Safe Delete). Depois botão direito em `FrmCarro` > **Refactor > Rename…** → `Main` → Refactor.
   *Atalho equivalente:* mantenha `FrmCarro` e defina a classe principal em **botão direito no projeto > Properties > Run > Main Class: `psv.FrmCarro`** (ou `psv.Main` se renomeou).
2. Passo 3: teste com **F6** (Run Main Project).
3. Passo 4: **Run > Clean and Build Main Project** (`Shift+F11`).
4. Passo 5: abra `C:\psv\Estacionamento\dist\` – você verá `Estacionamento.jar`, `README.TXT` e a pasta `lib` (contém o conector MySQL).
5. Passo 6: dê duplo clique em `Estacionamento.jar` (ou `java -jar Estacionamento.jar`). Para distribuir, leve a pasta `dist` inteira (jar + `lib`) **e o banco de dados** (`sql/estacionamento.sql`).

---

## Problemas comuns
- **`Access denied for user 'root'`** → senha errada em `Conexao.java`.
- **`ClassNotFoundException: com.mysql.cj.jdbc.Driver`** → conector não adicionado em *Libraries* (ou é o 5.x antigo; use o 8/9).
- **`Communications link failure`** → serviço MySQL parado (Windows: `services.msc` > MySQL80 > Iniciar).
- **`Unknown database 'estacionamento'`** → execute o passo 1.
- **Acentos quebrados** → Properties do projeto > Sources > Encoding: UTF-8.
- **`Unable to load authentication plugin 'caching_sha2_password'`** → use Connector/J 8+.
